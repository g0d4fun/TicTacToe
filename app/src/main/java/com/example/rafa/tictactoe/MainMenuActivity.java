package com.example.rafa.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rafa.tictactoe.model.EGameMode;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_menu);


    }

    public void onClickSinglePlayer(View v){
        //Toast.makeText(this, "SinglePlayer", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra("game_mode", EGameMode.SINGLE_PLAYER);
        startActivity(intent);
    }

    public void onClickMultiplayer(View v){
        //Toast.makeText(this, "Multiplayer", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra("game_mode", EGameMode.MULTIPLAYER);
        startActivity(intent);
    }

    public void onClickOnline(View v){
        Toast.makeText(this, "Online", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra("game_mode", EGameMode.ONLINE);
        startActivity(intent);
    }

    public void onClickSettings(View v){
        //Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();

    }

    public void onClickAboutUs(View v){
        Toast.makeText(this, "About us", Toast.LENGTH_SHORT).show();
    }
}
