package com.rafaonseek.rafa.tictactoe.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.rafaonseek.rafa.tictactoe.R;
import com.rafaonseek.rafa.tictactoe.model.EGameDifficult;
import com.rafaonseek.rafa.tictactoe.model.EGameMode;

/**
 * Created by Rafa on 2/19/2018.
 */

public class DifficultDialog extends Dialog implements View.OnClickListener {

    private final Context context;
    private final SharedPreferences sharedPreferences;

    private Button easyBtn, mediumBtn, hardBtn;

    public DifficultDialog(@NonNull Context context, SharedPreferences sharedPreferences) {
        super(context);

        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.difficult_dialog);

        easyBtn = (Button) findViewById(R.id.easyBtn);
        mediumBtn = (Button) findViewById(R.id.mediumBtn);
        hardBtn = (Button) findViewById(R.id.hardBtn);

        easyBtn.setOnClickListener(this);
        mediumBtn.setOnClickListener(this);
        hardBtn.setOnClickListener(this);

        boolean isInDarkTheme = sharedPreferences.getBoolean(context.getString(R.string.is_black_theme_key),false);
        if(isInDarkTheme) {
            int lightColor = context.getColor(R.color.mainColor1);
            findViewById(R.id.difficult_dialog_layout_bk).setBackgroundColor(lightColor);
            findViewById(R.id.difficult_dialog_layout).setBackgroundColor(Color.BLACK);
            ((TextView)findViewById(R.id.select_your_difficult)).setTextColor(lightColor);
            ((View)findViewById(R.id.delimiter5)).setBackgroundColor(lightColor);
            ((View)findViewById(R.id.delimiter6)).setBackgroundColor(lightColor);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context,GameActivity.class);
        intent.putExtra("game_mode", EGameMode.SINGLE_PLAYER);
        switch (v.getId()){
            case R.id.easyBtn:
                intent.putExtra("game_difficult", EGameDifficult.EASY);
                context.startActivity(intent);
                break;
            case R.id.mediumBtn:
                intent.putExtra("game_difficult", EGameDifficult.MEDIUM);
                context.startActivity(intent);
                break;
            case R.id.hardBtn:
                intent.putExtra("game_difficult", EGameDifficult.HARD);
                context.startActivity(intent);
                break;
        }
        dismiss();
    }
}
