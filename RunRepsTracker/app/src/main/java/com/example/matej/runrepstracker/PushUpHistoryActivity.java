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

public class PushUpHistoryActivity extends AppCompatActivity {

    ListView lvPushUpHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_up_history);
        setUpUI();
        Intent Intent = getIntent();
        if (Intent.hasExtra(PushUpActivity.KEY_PUSHUP)) populateListView();
    }
    private void setUpUI(){
        lvPushUpHistory = (ListView) findViewById(R.id.lvPushUpHistory);
        ArrayList<Result> results = this.loadResults();
        final ResultAdapter resultAdapter = new ResultAdapter(results);
        lvPushUpHistory.setAdapter(resultAdapter);
        lvPushUpHistory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PushUpHistoryActivity.this);
                dialogBuilder.setMessage("Do you want to delete result?");

                dialogBuilder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                DBHelper.getInstance(getApplicationContext()).deleteResult((Result) resultAdapter.getItem(position));
                                resultAdapter.deleteAt(position);
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
    private ArrayList<Result> loadResults() {
        return DBHelper.getInstance(this).getAllResults();
    }

    public void populateListView() {
        String sPushUp = null, sSets = null;
        Intent Intent = getIntent();
        if (Intent.hasExtra(PushUpActivity.KEY_PUSHUP)) {
            sPushUp = Intent.getStringExtra(PushUpActivity.KEY_PUSHUP);
        }
        if (Intent.hasExtra(PushUpActivity.KEY_SETS)) {
            sSets = Intent.getStringExtra(PushUpActivity.KEY_SETS);
        }
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String sDate = sdf.format(date);

        Result result = new Result(sDate, sPushUp, sSets);
        DBHelper.getInstance(getApplicationContext()).insertResult(result);
        ResultAdapter adapter = (ResultAdapter) lvPushUpHistory.getAdapter();
        adapter.insert(result);
    }
}
