package com.example.matej.runrepstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PushUpActivity extends AppCompatActivity {
    TextView PushUpTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up);
        setUpUI();
    }
    private void setUpUI(){
        PushUpTv = (TextView) findViewById(R.id.PushUpTv);
    }
}
