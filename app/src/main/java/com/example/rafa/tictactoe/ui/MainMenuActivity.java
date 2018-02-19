package com.example.rafa.tictactoe.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rafa.tictactoe.R;
import com.example.rafa.tictactoe.model.EGameMode;
import com.example.rafa.tictactoe.ui.preference.PrefsActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_menu);


    }

    public void onClickSinglePlayer(View v){
        //Toast.makeText(this, "SinglePlayer", Toast.LENGTH_SHORT).show();
        DifficultDialog difficultDialog = new DifficultDialog(this);
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
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra("game_mode", EGameMode.ONLINE);
        startActivity(intent);
    }

    public void onClickSettings(View v){
        Intent intent = new Intent(getApplicationContext(),PrefsActivity.class);
        startActivityForResult(intent,0);
    }

    public void onClickAboutUs(View v){
        Intent intent = new Intent(this,AboutActivity.class);
        startActivity(intent);
    }
}
