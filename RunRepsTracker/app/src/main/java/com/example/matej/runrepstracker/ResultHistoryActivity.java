package com.example.matej.runrepstracker;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ResultHistoryActivity extends AppCompatActivity {
    Button PushUpHistoryBtn,SitUpHistoryBtn,RunHistoryBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_history);
        this.setUpUI();
    }
private void setUpUI(){

    PushUpHistoryBtn = (Button) findViewById(R.id.PushUpHistoryBtn);
    SitUpHistoryBtn = (Button) findViewById(R.id.SitUpHistoryBtn);
    RunHistoryBtn = (Button) findViewById(R.id.RunHistoryBtn);
    PushUpHistoryBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent explicitIntent = new Intent();
            explicitIntent.setClass(getApplicationContext(),PushUpHistoryActivity.class);
            startActivity(explicitIntent);
        }
    });
    SitUpHistoryBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent explicitIntent = new Intent();
            explicitIntent.setClass(getApplicationContext(),SitUpHistoryActivity.class);
            startActivity(explicitIntent);
        }
    });
    RunHistoryBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent explicitIntent = new Intent();
            explicitIntent.setClass(getApplicationContext(),RunHistoryActivity.class);
            startActivity(explicitIntent);
        }
    });
}
    }