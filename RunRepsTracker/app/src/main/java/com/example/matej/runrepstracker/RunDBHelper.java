package com.example.matej.runrepstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Matej on 4.8.2017..
 */

public class RunDBHelper extends SQLiteOpenHelper {
    private static RunDBHelper mDBHelper = null;
    private RunDBHelper(Context context){
        super(context.getApplicationContext(), RunDBHelper.Schema.DATABASE_NAME,null, RunDBHelper.Schema.SCHEMA_VERSION);
    }
    public static synchronized RunDBHelper getInstance(Context context){
        if(mDBHelper == null){
            mDBHelper = new RunDBHelper(context);
        }
        return mDBHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MY_RUN_RESULTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_MY_RUN_RESULTS);
        this.onCreate(db);
    }
    //SQL statements
    static final String CREATE_TABLE_MY_RUN_RESULTS = "CREATE TABLE " + RunDBHelper.Schema.TABLE_MY_RUN_RESULTS +
            " (" + RunDBHelper.Schema.ID + " INTEGER PRIMARY KEY, " + RunDBHelper.Schema.TIME + " TEXT, " + RunDBHelper.Schema.DIST + " TEXT);";
    static final String DROP_TABLE_MY_RUN_RESULTS = "DROP TABLE IF EXISTS " + RunDBHelper.Schema.TABLE_MY_RUN_RESULTS;
    static final String SELECT_ALL_RUN_RESULTS = "SELECT " + RunDBHelper.Schema.ID + "," + RunDBHelper.Schema.TIME + "," + RunDBHelper.Schema.DIST  + " FROM " + RunDBHelper.Schema.TABLE_MY_RUN_RESULTS;
    // CRUD should be performed on another thread
    public void insertRunResult(Run run){
        ContentValues contentValues = new ContentValues();
        contentValues.put(RunDBHelper.Schema.TIME, run.getTime());
        contentValues.put(RunDBHelper.Schema.DIST, run.getDist());
        SQLiteDatabase writeableDatabase = this.getWritableDatabase();
        writeableDatabase.insert(RunDBHelper.Schema.TABLE_MY_RUN_RESULTS, RunDBHelper.Schema.TIME,contentValues);
        writeableDatabase.close();
    }
    public ArrayList<Run> getAllRunResults(){
        SQLiteDatabase writeableDatabase = this.getWritableDatabase();
        Cursor runCursor = writeableDatabase.rawQuery(SELECT_ALL_RUN_RESULTS,null);
        ArrayList<Run> runs = new ArrayList<>();
        if(runCursor.moveToFirst()){
            do{
                int fID = runCursor.getInt(0);
                String fTime = runCursor.getString(1);
                String fDist = runCursor.getString(2);
                runs.add(new Run(fID,fTime, fDist));
            }while(runCursor.moveToNext());
        }
        runCursor.close();
        writeableDatabase.close();
        return runs;
    }

    public void deleteRunResult(Run run) {
        int id = run.getID();
        String[] arg = new String[]{String.valueOf(id)};
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RunDBHelper.Schema.TABLE_MY_RUN_RESULTS, RunDBHelper.Schema.ID + "=?",arg);
        db.close();

    }

    public static class Schema{
        private static final int SCHEMA_VERSION = 1;
        private static final String DATABASE_NAME = "run_results.db";
        static final String ID = "id";
        static final String TABLE_MY_RUN_RESULTS = "my_run_results";
        static final String TIME = "time";
        static final String DIST = "dist";
    }
}

