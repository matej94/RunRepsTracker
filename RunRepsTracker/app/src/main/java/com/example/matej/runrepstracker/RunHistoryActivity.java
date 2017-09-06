package com.example.matej.runrepstracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RunHistoryActivity extends AppCompatActivity {
    ListView lvRunHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_history);
        setUpUI();
        Intent Intent = getIntent();
        if (Intent.hasExtra(RunActivity.KEY_TIME)) populateListView();
    }

    private void setUpUI(){

        lvRunHistory = (ListView) findViewById(R.id.lvRunHistory);
        ArrayList<Run> runs = this.loadRunResults();
        final RunAdapter runAdapter = new RunAdapter(runs);
        lvRunHistory.setAdapter(runAdapter);
        lvRunHistory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RunHistoryActivity.this);
                dialogBuilder.setMessage("Do you want to delete result?");

                dialogBuilder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                RunDBHelper.getInstance(getApplicationContext()).deleteRunResult((Run) runAdapter.getItem(position));
                                runAdapter.deleteAt(position);
                                dialog.cancel();
                            }
                        });

                dialogBuilder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                return true;
            }

        });
    }

    private ArrayList<Run> loadRunResults() {
        return RunDBHelper.getInstance(this).getAllRunResults();
    }

    public void populateListView() {
        String sTime = null, sDist = null;
        Intent Intent = getIntent();
        if (Intent.hasExtra(RunActivity.KEY_TIME)) {
            sTime = Intent.getStringExtra(RunActivity.KEY_TIME);
        }
        if (Intent.hasExtra(RunActivity.KEY_DIST)) {
            sDist = Intent.getStringExtra(RunActivity.KEY_DIST);
        }

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String sDate = sdf.format(date);
        Run run = new Run(sDate, sTime, sDist);
        RunDBHelper.getInstance(getApplicationContext()).insertRunResult(run);
        RunAdapter adapter = (RunAdapter) lvRunHistory.getAdapter();
        adapter.insertRun(run);
    }
    }
