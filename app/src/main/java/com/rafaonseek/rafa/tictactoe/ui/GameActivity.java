package com.rafaonseek.rafa.tictactoe.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rafa.tictactoe.R;
import com.rafaonseek.rafa.tictactoe.model.Constants;
import com.rafaonseek.rafa.tictactoe.model.EGameDifficult;
import com.rafaonseek.rafa.tictactoe.model.EGameMode;
import com.rafaonseek.rafa.tictactoe.model.EHostOrGuest;
import com.rafaonseek.rafa.tictactoe.model.EPlayerType;
import com.rafaonseek.rafa.tictactoe.model.Model;
import com.rafaonseek.rafa.tictactoe.model.WinnerInfo;
import com.rafaonseek.rafa.tictactoe.model.wifip2p.WiFiDirectBroadcastReceiver;

public class GameActivity extends AppCompatActivity {

    private Model model;
    private Button tiles[][];
    private boolean clickToNextGame;

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;

    private SharedPreferences sharedPreferences;
    private boolean isDarkThemeActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game);
        this.clickToNextGame = false;

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isDarkThemeActive = sharedPreferences.getBoolean(getString(R.string.is_black_theme_key),false);

        EGameMode gameMode = (EGameMode) getIntent()
                .getSerializableExtra("game_mode");

        EGameDifficult gameDifficult;
        int playerTypeXorO = Integer.parseInt(sharedPreferences.getString(getString(R.string.type_of_symbol_key),"1"));
        EPlayerType playerType;
        if(playerTypeXorO == 1)
            playerType = EPlayerType.PLAYERX;
        else
            playerType = EPlayerType.PLAYERO;

        if (gameMode.equals(EGameMode.SINGLE_PLAYER)) {
            gameDifficult = (EGameDifficult) getIntent()
                    .getSerializableExtra("game_difficult");
            model = new Model(gameMode, playerType, gameDifficult);
        } else if (gameMode.equals(EGameMode.MULTIPLAYER)) {
            model = new Model(gameMode, playerType);
        } else if (gameMode.equals(EGameMode.ONLINE)) {
            model = new Model(gameMode, playerType);

            EHostOrGuest hostOrGuest = (EHostOrGuest) getIntent().getSerializableExtra("host_guest");
            setUpWifiP2p(hostOrGuest);
        }

        setUpButtonsRefs();
        darkTheme(sharedPreferences,isDarkThemeActive);
    }

    protected void darkTheme(SharedPreferences sharedPreferences, boolean isDarkThemeActive){

        if(isDarkThemeActive) {
            int lightColor = getColor(R.color.mainColor1);
            findViewById(R.id.game_activity_bk).setBackgroundColor(Color.BLACK);
            ((View)findViewById(R.id.board_delimiter1)).setBackgroundColor(lightColor);
            ((View)findViewById(R.id.board_delimiter2)).setBackgroundColor(lightColor);
            ((View)findViewById(R.id.board_delimiter3)).setBackgroundColor(lightColor);
            ((View)findViewById(R.id.board_delimiter4)).setBackgroundColor(lightColor);
            ((View)findViewById(R.id.board_delimiter5)).setBackgroundColor(lightColor);
            ((View)findViewById(R.id.board_delimiter6)).setBackgroundColor(lightColor);
            ((View)findViewById(R.id.board_delimiter7)).setBackgroundColor(lightColor);
            ((View)findViewById(R.id.board_delimiter8)).setBackgroundColor(lightColor);

            ((TextView)findViewById(R.id.o_score_title)).setTextColor(lightColor);
            ((TextView)findViewById(R.id.draw_times_title)).setTextColor(lightColor);
            ((TextView)findViewById(R.id.x_score_title)).setTextColor(lightColor);
            ((TextView)findViewById(R.id.game_mode)).setTextColor(lightColor);
            ((TextView)findViewById(R.id.scoreO)).setTextColor(lightColor);
            ((TextView)findViewById(R.id.scoreDraw)).setTextColor(lightColor);
            ((TextView)findViewById(R.id.scoreX)).setTextColor(lightColor);
            ((TextView)findViewById(R.id.currentPlayerView)).setTextColor(lightColor);

            for(Button tilesRow[] : tiles){
                for(Button tile : tilesRow){
                    tile.setTextColor(lightColor);
                }
            }
        }
    }

    private void setUpWifiP2p(EHostOrGuest hostOrGuest) {
        //Obtain an instance of WifiP2pManager
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        // Register your application with the Wi-Fi P2P framework
        mChannel = mManager.initialize(this, getMainLooper(), null);
        // This allows your broadcast receiver to notify your activity of interesting events
        // and update it accordingly. It also lets you manipulate the device's Wi-Fi state if necessary
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            // Method only notifies you that the discovery process succeeded and does not provide
            // any information about the actual peers that it discovered
            @Override
            public void onSuccess() {

            }


            @Override
            public void onFailure(int reason) {

            }
        });

        if (hostOrGuest.equals(EHostOrGuest.HOST)) {

        } else if (hostOrGuest.equals(EHostOrGuest.GUEST)) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (model.getGameMode().equals(EGameMode.ONLINE)){
            // Register the broadcast receiver with the intent values to be matched
            registerReceiver(mReceiver, mIntentFilter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (model.getGameMode().equals(EGameMode.ONLINE)) {
            // Unregister the broadcast receiver
            unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Yes button
                dialog.dismiss();
                GameActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.cancel();
            }
        });
        builder.setNeutralButton(R.string.saveGame, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User saved the game
                dialog.dismiss();
            }
        });
        // Set other dialog properties
        builder.setTitle(R.string.exitGameTitle);
        builder.setMessage(R.string.exitGameMessage);

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

        //super.onBackPressed();
    }

    protected void onClickListener(View v) {
        if (clickToNextGame) {
            model.startNextGame();
            renderGame();
            return;
        }

        Button btnClicked = (Button) v;
        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLUMNS; j++) {
                if (tiles[i][j].getId() == btnClicked.getId()) {
                    model.playTile(i, j);
                }
            }
        }
        renderGame();
    }

    protected void setTile(EPlayerType playerType, Button btn) {
        if (playerType.equals(EPlayerType.EMPTY)) {
            btn.setText("");
        } else if (playerType.equals(EPlayerType.PLAYERO)) {
            btn.setText("O");
            btn.setTextColor(getColor(R.color.mainColor1));
        } else if (playerType.equals(EPlayerType.PLAYERX)) {
            btn.setText("X");
            if(isDarkThemeActive){
                btn.setBackgroundColor(getColor(R.color.mainColor1));
            }else{
                btn.setTextColor(getColor(R.color.mainColor2));
            }
        }
        if(isDarkThemeActive){
            btn.setBackgroundColor(Color.BLACK);
        }else{
            btn.setBackgroundColor(getColor(R.color.startBackground));
        }

    }

    protected void setUpButtonsRefs() {
        tiles = new Button[3][3];
        tiles[0][0] = findViewById(R.id.tile1);
        tiles[0][1] = findViewById(R.id.tile2);
        tiles[0][2] = findViewById(R.id.tile3);
        tiles[1][0] = findViewById(R.id.tile4);
        tiles[1][1] = findViewById(R.id.tile5);
        tiles[1][2] = findViewById(R.id.tile6);
        tiles[2][0] = findViewById(R.id.tile7);
        tiles[2][1] = findViewById(R.id.tile8);
        tiles[2][2] = findViewById(R.id.tile9);
    }

    protected void renderGame() {
        EPlayerType board[][] = model.getBoard();
        EPlayerType currentPlayer = model.getCurrentPlayer();
        int scoreO = model.getPlayerOWins();
        int scoreX = model.getPlayerXWins();
        int drawScore = model.getGameDraws();

        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLUMNS; j++) {
                setTile(board[i][j], tiles[i][j]);
            }
        }

        if (model.getGameMode().equals(EGameMode.SINGLE_PLAYER))
            ((TextView) findViewById(R.id.currentPlayerView)).setText(R.string.playerYourTurn);
        else if (currentPlayer.equals(EPlayerType.PLAYERO))
            ((TextView) findViewById(R.id.currentPlayerView)).setText(R.string.playerOTurn);
        else if (currentPlayer.equals(EPlayerType.PLAYERX))
            ((TextView) findViewById(R.id.currentPlayerView)).setText(R.string.playerXTurn);

        ((TextView) findViewById(R.id.scoreO)).setText(String.valueOf(scoreO));
        ((TextView) findViewById(R.id.scoreX)).setText(String.valueOf(scoreX));
        ((TextView) findViewById(R.id.scoreDraw)).setText(String.valueOf(drawScore));

        WinnerInfo winnerInfo = model.getWinnerResult();
        clickToNextGame = nextGame(winnerInfo);
    }

    /**
     * @param winnerInfo null - current game is not solved
     */
    protected boolean nextGame(WinnerInfo winnerInfo) {
        if (winnerInfo == null)
            return false;

        switch (winnerInfo.getGameResult()) {
            case PLAYERO_WON:
                ((TextView) findViewById(R.id.currentPlayerView)).setText(R.string.OWonDlgMsg);
                highlightWinnerTiles(winnerInfo.getTilesCoordsWinner());
                break;
            case PLAYERX_WON:
                ((TextView) findViewById(R.id.currentPlayerView)).setText(R.string.XWonDlgMsg);
                highlightWinnerTiles(winnerInfo.getTilesCoordsWinner());
                break;
            case DRAW:
                ((TextView) findViewById(R.id.currentPlayerView)).setText(R.string.gameDrawDlgMsg);
                break;
        }
        return true;
    }

    protected void highlightWinnerTiles(int winnerTiles[][]) {
        for (int winnerTile[] : winnerTiles) {
            int row = winnerTile[0];
            int col = winnerTile[1];

            if(isDarkThemeActive){
                tiles[row][col].setBackgroundColor(Color.GRAY);
            }else{
                tiles[row][col].setBackgroundColor(getColor(R.color.newValue));
            }
        }

    }
}
