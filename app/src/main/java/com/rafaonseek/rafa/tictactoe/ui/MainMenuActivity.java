package com.rafaonseek.rafa.tictactoe.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rafaonseek.rafa.tictactoe.R;
import com.rafaonseek.rafa.tictactoe.model.EGameMode;
import com.rafaonseek.rafa.tictactoe.ui.preference.PrefsActivity;

public class MainMenuActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_menu);

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isInDarkTheme = sharedPreferences.getBoolean(getString(R.string.is_black_theme_key),false);
        if(isInDarkTheme){
            findViewById(R.id.main_menu_layout).setBackgroundColor(Color.BLACK);
            int lightColor = getColor(R.color.mainColor1);
            Button[] btns ={
                    (Button)findViewById(R.id.singlePlayerBtn),
                    (Button)findViewById(R.id.multiplayerBtn),
                    (Button)findViewById(R.id.onlineBtn),
                    (Button)findViewById(R.id.settingsBtn),
                    (Button)findViewById(R.id.aboutUsBtn)
            };
            for(Button btn : btns){
                btn.setTextColor(lightColor);
            }
            View delimiters[] = {
                    findViewById(R.id.delimiter1),
                    findViewById(R.id.delimiter2),
                    findViewById(R.id.delimiter3),
                    findViewById(R.id.delimiter4)
            };
            for(View delimiter : delimiters){
                delimiter.setBackgroundColor(lightColor);
            }
        }
    }

    public void onClickSinglePlayer(View v){
        //Toast.makeText(this, "SinglePlayer", Toast.LENGTH_SHORT).show();
        DifficultDialog difficultDialog = new DifficultDialog(this, sharedPreferences);
        difficultDialog.show();
    }

    public void onClickMultiplayer(View v){
        //Toast.makeText(this, "Multiplayer", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra("game_mode", EGameMode.MULTIPLAYER);
        startActivity(intent);
    }

    public void onClickOnline(View v){
        Toast.makeText(this, R.string.commingSoon, Toast.LENGTH_SHORT).show();
        HostGuestDialog hostGuestDialog = new HostGuestDialog(this, sharedPreferences);
        hostGuestDialog.show();
    }

    public void onClickSettings(View v){
        Intent intent = new Intent(this,PrefsActivity.class);
        finishActivity(0);
        startActivityForResult(intent,0);
    }

    public void onClickAboutUs(View v){
        Intent intent = new Intent(this,AboutActivity.class);
        startActivity(intent);
    }
}
