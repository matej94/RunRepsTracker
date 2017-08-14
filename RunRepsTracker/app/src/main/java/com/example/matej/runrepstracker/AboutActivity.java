package com.example.matej.runrepstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    TextView AboutApp,AboutPushUp,AboutSitUp,AboutRun,AboutResultHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        AboutApp = (TextView) findViewById(R.id.AboutAppTv);
        AboutPushUp = (TextView) findViewById(R.id.AboutPushUpTv);
        AboutSitUp = (TextView) findViewById(R.id.AboutSitUpTv);
        AboutRun = (TextView) findViewById(R.id.AboutRunTv);
        AboutResultHistory = (TextView) findViewById(R.id.AboutResultTv);

    }
}
