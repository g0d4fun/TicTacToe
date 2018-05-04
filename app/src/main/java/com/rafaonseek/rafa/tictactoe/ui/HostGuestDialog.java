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

import com.example.rafa.tictactoe.R;
import com.rafaonseek.rafa.tictactoe.model.EGameMode;
import com.rafaonseek.rafa.tictactoe.model.EHostOrGuest;

public class HostGuestDialog extends Dialog implements View.OnClickListener {

    private final Context context;
    private final SharedPreferences sharedPreferences;

    private Button hostBtn, guestBtn;

    public HostGuestDialog(@NonNull Context context, SharedPreferences sharedPreferences) {
        super(context);

        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hostguest_dialog);

        hostBtn = (Button) findViewById(R.id.hostBtn);
        guestBtn = (Button) findViewById(R.id.guestBtn);

        hostBtn.setOnClickListener(this);
        guestBtn.setOnClickListener(this);

        boolean isInDarkTheme = sharedPreferences.getBoolean(context.getString(R.string.is_black_theme_key),false);
        if(isInDarkTheme) {
            int lightColor = context.getColor(R.color.mainColor1);
            findViewById(R.id.guesthost_layout_bk).setBackgroundColor(lightColor);
            findViewById(R.id.guesthost_layout).setBackgroundColor(Color.BLACK);
            ((TextView)findViewById(R.id.wifi_game)).setTextColor(lightColor);
            ((View)findViewById(R.id.delimiter7)).setBackgroundColor(lightColor);

        }

    }

    @Override
    public void onClick(View v) {
        dismiss();

        Intent intent = new Intent(context,GameActivity.class);
        intent.putExtra("game_mode", EGameMode.ONLINE);
        switch (v.getId()) {
            case R.id.hostBtn:
                intent.putExtra("host_guest", EHostOrGuest.HOST);
                //context.startActivity(intent);
                break;
            case R.id.guestBtn:
                intent.putExtra("host_guest", EHostOrGuest.GUEST);
                //context.startActivity(intent);
                break;
        }
        dismiss();
    }

}
