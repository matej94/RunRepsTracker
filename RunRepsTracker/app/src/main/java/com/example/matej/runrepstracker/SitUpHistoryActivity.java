package com.example.matej.runrepstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class SitUpHistoryActivity extends AppCompatActivity {
    ListView lvSitUpHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sit_up_history);
        setUpUI();
    }
    private void setUpUI(){
        lvSitUpHistory = (ListView) findViewById(R.id.lvSitUpHistory);
    }
}
