package com.example.matej.runrepstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView AppTitleTv;
    Button PushUpBtn, SitUpBtn, RunBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUI();
    }

    private void setUpUI(){
        AppTitleTv = (TextView) findViewById(R.id.AppTitleTv);
        PushUpBtn = (Button) findViewById(R.id.PushUpBtn);
        PushUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicitIntent = new Intent();
                explicitIntent.setClass(getApplicationContext(),PushUpActivity.class);
                startActivity(explicitIntent);
            }
        });
        SitUpBtn = (Button) findViewById(R.id.SitUpBtn);
        SitUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicitIntent = new Intent();
                explicitIntent.setClass(getApplicationContext(),SitUpActivity.class);
                startActivity(explicitIntent);
            }
        });
        RunBtn = (Button) findViewById(R.id.RunBtn);
        RunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicitIntent = new Intent();
                explicitIntent.setClass(getApplicationContext(),RunActivity.class);
                startActivity(explicitIntent);
            }
        });
    }
}
