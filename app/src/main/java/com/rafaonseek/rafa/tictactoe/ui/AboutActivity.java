package com.rafaonseek.rafa.tictactoe.ui;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.rafa.tictactoe.R;

/**
 * Created by Rafa on 2/19/2018.
 */

public class AboutActivity extends AppCompatActivity{

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_about);

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        boolean isInDarkTheme = sharedPreferences.getBoolean(getString(R.string.is_black_theme_key),false);
        if(isInDarkTheme) {
            int lightColor = getColor(R.color.mainColor1);
            findViewById(R.id.activity_about_bk).setBackgroundColor(lightColor);
            ((TextView)findViewById(R.id.about_developed_by)).setTextColor(Color.BLACK);
            ((TextView)findViewById(R.id.about_email)).setTextColor(Color.BLACK);
        }
    }
}
