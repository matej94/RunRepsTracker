package com.example.matej.runrepstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Matej on 8.6.2017..
 */

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper mDBHelper = null;
    private DBHelper (Context context){
        super(context.getApplicationContext(),Schema.DATABASE_NAME,null,Schema.SCHEMA_VERSION);
    }
    public static synchronized DBHelper getInstance(Context context){
        if(mDBHelper == null){
            mDBHelper = new DBHelper(context);
        }
        return mDBHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(CREATE_TABLE_MY_RESULTS); }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_MY_RESULTS);
        this.onCreate(db);
    }
    //SQL statements
    static final String CREATE_TABLE_MY_RESULTS = "CREATE TABLE " + Schema.TABLE_MY_RESULTS +
            " (" + Schema.ID + " INTEGER PRIMARY KEY, " + Schema.PUSHUP + " TEXT, " + Schema.SETS + " TEXT);";
    static final String DROP_TABLE_MY_RESULTS = "DROP TABLE IF EXISTS " + Schema.TABLE_MY_RESULTS;
    static final String SELECT_ALL_RESULTS = "SELECT " + Schema.ID + "," + Schema.PUSHUP + "," + Schema.SETS  + " FROM " + Schema.TABLE_MY_RESULTS;
    // CRUD should be performed on another thread
    public void insertResult(Result result){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Schema.PUSHUP, result.getPushUp());
        contentValues.put(Schema.SETS, result.getSets());
        SQLiteDatabase writeableDatabase = this.getWritableDatabase();
        writeableDatabase.insert(Schema.TABLE_MY_RESULTS, Schema.PUSHUP,contentValues);
        writeableDatabase.close();
    }
    public ArrayList<Result> getAllResults(){
        SQLiteDatabase writeableDatabase = this.getWritableDatabase();
        Cursor resultCursor = writeableDatabase.rawQuery(SELECT_ALL_RESULTS,null);
        ArrayList<Result> results = new ArrayList<>();
        if(resultCursor.moveToFirst()){
            do{
                int fID = resultCursor.getInt(0);
                String fPushUp = resultCursor.getString(1);
                String fSets = resultCursor.getString(2);
                results.add(new Result(fID,fPushUp, fSets));
            }while(resultCursor.moveToNext());
        }
        resultCursor.close();
        writeableDatabase.close();
        return results;
    }

    public void deleteResult(Result result) {
        int id = result.getID();
        String[] arg = new String[]{String.valueOf(id)};
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Schema.TABLE_MY_RESULTS, Schema.ID + "=?",arg);
        db.close();

    }

    public static class Schema{
        private static final int SCHEMA_VERSION = 1;
        private static final String DATABASE_NAME = "results.db";
        static final String ID = "id";
        static final String TABLE_MY_RESULTS = "my_results";
        static final String PUSHUP = "pushup";
        static final String SETS = "sets";
    }
}

