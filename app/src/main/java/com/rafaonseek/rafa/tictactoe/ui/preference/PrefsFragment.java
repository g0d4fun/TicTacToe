package com.rafaonseek.rafa.tictactoe.ui.preference;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rafa.tictactoe.R;

/**
 * Created by Rafa on 2/19/2018.
 */

public class PrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        Preference mailTo = findPreference("email_us_key");
        mailTo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Greetings");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"portuguese.dev@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Email Client Chooser"));

                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.mainColor1));

        return view;
    }
}
