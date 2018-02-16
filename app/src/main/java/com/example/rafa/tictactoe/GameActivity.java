package com.example.rafa.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    Button tiles[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game);

        setUpButtonsRefs();
    }

    protected void onClickListener(View v) {
        Button btnClicked = (Button) v;
        for (Button[] arr : tiles) {
            for (Button btn : arr) {
                if (btn.getId() == btnClicked.getId()) {
                    btn.setText("X");
                    if ((int) (Math.random() * 2 + 1) == 1)
                        btn.setText("O");
                    btn.setTextColor(getResources().getColor(R.color.mainColor1));
                }
            }
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
}
