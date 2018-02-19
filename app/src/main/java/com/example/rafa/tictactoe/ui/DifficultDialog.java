package com.example.rafa.tictactoe.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.rafa.tictactoe.R;
import com.example.rafa.tictactoe.model.EGameDifficult;
import com.example.rafa.tictactoe.model.EGameMode;

/**
 * Created by Rafa on 2/19/2018.
 */

public class DifficultDialog extends Dialog implements View.OnClickListener {

    private final Context context;

    private Button easyBtn, mediumBtn, hardBtn;

    public DifficultDialog(@NonNull Context context) {
        super(context);

        this.context = context;
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
