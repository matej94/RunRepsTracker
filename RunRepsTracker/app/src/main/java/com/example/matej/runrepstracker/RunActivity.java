package com.example.matej.runrepstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RunActivity extends AppCompatActivity {
    TextView RunTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        setUpUI();
    }
    private void setUpUI(){
        RunTv = (TextView) findViewById(R.id.RunTv);
    }
}
