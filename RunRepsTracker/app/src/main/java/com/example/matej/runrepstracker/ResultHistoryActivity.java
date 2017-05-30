package com.example.matej.runrepstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultHistoryActivity extends AppCompatActivity {
    TextView ResultHistoryTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_history);
        setUpUI();
    }
    private void setUpUI(){
        ResultHistoryTv = (TextView) findViewById(R.id.ResultHistoryTv);
    }
}
