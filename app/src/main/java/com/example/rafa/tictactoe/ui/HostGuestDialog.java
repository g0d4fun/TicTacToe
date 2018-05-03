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
import com.example.rafa.tictactoe.model.EGameMode;
import com.example.rafa.tictactoe.model.EHostOrGuest;

public class HostGuestDialog extends Dialog implements View.OnClickListener {

    private final Context context;

    private Button hostBtn, guestBtn;

    public HostGuestDialog(@NonNull Context context) {
        super(context);

        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hostguest_dialog);

        hostBtn = (Button) findViewById(R.id.hostBtn);
        guestBtn = (Button) findViewById(R.id.guestBtn);

        hostBtn.setOnClickListener(this);
        guestBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context,GameActivity.class);
        intent.putExtra("game_mode", EGameMode.ONLINE);
        switch (v.getId()) {
            case R.id.hostBtn:
                intent.putExtra("host_guest", EHostOrGuest.HOST);
                context.startActivity(intent);
                break;
            case R.id.guestBtn:
                intent.putExtra("host_guest", EHostOrGuest.GUEST);
                context.startActivity(intent);
                break;
        }
        dismiss();
    }

}
