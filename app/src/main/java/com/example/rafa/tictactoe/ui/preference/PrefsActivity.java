package com.example.rafa.tictactoe.ui.preference;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

/**
 * Created by Rafa on 2/19/2018.
 */

public class PrefsActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getActionBar().hide();
        super.onCreate(savedInstanceState);

        PrefsFragment prefsFragment = new PrefsFragment();
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content,prefsFragment);
        fragmentTransaction.commit();
    }
}
