package com.example.matej.runrepstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class PushUpHistoryActivity extends AppCompatActivity {
    ListView lvPushUpHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up_history);
        setUpUI();
    }
    private void setUpUI(){
        lvPushUpHistory = (ListView) findViewById(R.id.lvPushUpHistory);
    }
}
