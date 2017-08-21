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

public class SitUpHistoryActivity extends AppCompatActivity {
    ListView lvSitUpHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sit_up_history);
        setUpUI();
        Intent Intent = getIntent();
        if (Intent.hasExtra(SitUpActivity.KEY_SITUP)) populateListView();
    }

    private void setUpUI() {

        lvSitUpHistory = (ListView) findViewById(R.id.lvSitUpHistory);
        ArrayList<SitUp> situps = this.loadSitUpResults();
        final SitUpAdapter situpAdapter = new SitUpAdapter(situps);
        lvSitUpHistory.setAdapter(situpAdapter);
        lvSitUpHistory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SitUpHistoryActivity.this);
                dialogBuilder.setMessage("Do you want to delete result?");
                //dialogBuilder.setCancelable(true);

                dialogBuilder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                SitUpDBHelper.getInstance(getApplicationContext()).deleteSitUpResult((SitUp) situpAdapter.getItem(position));
                                situpAdapter.deleteAt(position);
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

    private ArrayList<SitUp> loadSitUpResults() {
        return SitUpDBHelper.getInstance(this).getAllSitUpResults();
    }

    public void populateListView() {
        String sSitUp = null, sSets = null;
        Intent Intent = getIntent();
        if (Intent.hasExtra(SitUpActivity.KEY_SITUP)) {
            sSitUp = Intent.getStringExtra(SitUpActivity.KEY_SITUP);
        }
        if (Intent.hasExtra(SitUpActivity.KEY_SETS)) {
            sSets = Intent.getStringExtra(SitUpActivity.KEY_SETS);
        }

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String sDate = sdf.format(date);
        SitUp situp = new SitUp(sDate, sSitUp, sSets);
        SitUpDBHelper.getInstance(getApplicationContext()).insertSitUpResult(situp);
        SitUpAdapter adapter = (SitUpAdapter) lvSitUpHistory.getAdapter();
        adapter.insertSitUp(situp);
    }
}
