package com.example.matej.runrepstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SitUpActivity extends AppCompatActivity {
    TextView SitUpTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sit_up);
        setUpUI();
    }

    private void setUpUI(){
        SitUpTv = (TextView) findViewById(R.id.SitUpTv);
    }
}
