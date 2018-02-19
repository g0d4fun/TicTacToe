package com.example.rafa.tictactoe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rafa.tictactoe.model.Constants;
import com.example.rafa.tictactoe.model.EGameMode;
import com.example.rafa.tictactoe.model.EPlayerType;
import com.example.rafa.tictactoe.model.Model;
import com.example.rafa.tictactoe.model.WinnerInfo;

public class GameActivity extends AppCompatActivity {

    private Model model;
    private Button tiles[][];
    private boolean clickToNextGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game);
        EGameMode gameMode = (EGameMode) getIntent().getSerializableExtra("game_mode");
        clickToNextGame = false;
        model = new Model(gameMode, EPlayerType.PLAYERX);
        setUpButtonsRefs();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Yes button
                dialog.dismiss();
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
            btn.setTextColor(getColor(R.color.mainColor2));
        }
        btn.setBackgroundColor(getColor(R.color.startBackground));
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

        if (currentPlayer.equals(EPlayerType.PLAYERO))
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

    protected void highlightWinnerTiles(int winnerTiles[][]){
        for (int winnerTile[] : winnerTiles) {
            int row = winnerTile[0];
            int col = winnerTile[1];
            tiles[row][col].setBackgroundColor(getColor(R.color.newValue));
        }

    }
}
