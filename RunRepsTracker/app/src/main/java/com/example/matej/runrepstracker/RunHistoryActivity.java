package com.example.matej.runrepstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class RunHistoryActivity extends AppCompatActivity {
    ListView lvRunHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_history);
        setUpUI();
    }
    private void setUpUI(){
        lvRunHistory = (ListView) findViewById(R.id.lvRunHistory);
    }
}
