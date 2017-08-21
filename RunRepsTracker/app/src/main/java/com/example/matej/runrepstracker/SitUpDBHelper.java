package com.example.matej.runrepstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Matej on 10.6.2017..
 */

public class SitUpDBHelper extends SQLiteOpenHelper {
    private static SitUpDBHelper mDBHelper = null;
    private SitUpDBHelper(Context context){
        super(context.getApplicationContext(), SitUpDBHelper.Schema.DATABASE_NAME,null, SitUpDBHelper.Schema.SCHEMA_VERSION);
    }
    public static synchronized SitUpDBHelper getInstance(Context context){
        if(mDBHelper == null){
            mDBHelper = new SitUpDBHelper(context);
        }
        return mDBHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MY_SITUP_RESULTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_MY_SITUP_RESULTS);
        this.onCreate(db);
    }
    //SQL statements
    static final String CREATE_TABLE_MY_SITUP_RESULTS = "CREATE TABLE " + SitUpDBHelper.Schema.TABLE_MY_SITUP_RESULTS +
            " (" + SitUpDBHelper.Schema.ID + " INTEGER PRIMARY KEY, " + SitUpDBHelper.Schema.DATE + " TEXT, " + SitUpDBHelper.Schema.SITUP + " TEXT, " + SitUpDBHelper.Schema.SETS + " TEXT);";
    static final String DROP_TABLE_MY_SITUP_RESULTS = "DROP TABLE IF EXISTS " + SitUpDBHelper.Schema.TABLE_MY_SITUP_RESULTS;
    static final String SELECT_ALL_SITUP_RESULTS = "SELECT " + SitUpDBHelper.Schema.ID + "," + SitUpDBHelper.Schema.DATE + "," + SitUpDBHelper.Schema.SITUP + "," + SitUpDBHelper.Schema.SETS  + " FROM " + SitUpDBHelper.Schema.TABLE_MY_SITUP_RESULTS;
    // CRUD should be performed on another thread
    public void insertSitUpResult(SitUp situp){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SitUpDBHelper.Schema.DATE, situp.getDate());
        contentValues.put(SitUpDBHelper.Schema.SITUP, situp.getSitUp());
        contentValues.put(SitUpDBHelper.Schema.SETS, situp.getSets());
        SQLiteDatabase writeableDatabase = this.getWritableDatabase();
        writeableDatabase.insert(SitUpDBHelper.Schema.TABLE_MY_SITUP_RESULTS, SitUpDBHelper.Schema.SITUP,contentValues);
        writeableDatabase.close();
    }
    public ArrayList<SitUp> getAllSitUpResults(){
        SQLiteDatabase writeableDatabase = this.getWritableDatabase();
        Cursor situpCursor = writeableDatabase.rawQuery(SELECT_ALL_SITUP_RESULTS,null);
        ArrayList<SitUp> situps = new ArrayList<>();
        if(situpCursor.moveToFirst()){
            do{
                int fID = situpCursor.getInt(0);
                String fdate = situpCursor.getString(1);
                String fSitUp = situpCursor.getString(2);
                String fSets = situpCursor.getString(3);
                situps.add(new SitUp(fID,fdate, fSitUp, fSets));
            }while(situpCursor.moveToNext());
        }
        situpCursor.close();
        writeableDatabase.close();
        return situps;
    }

    public void deleteSitUpResult(SitUp situp) {
        int id = situp.getID();
        String[] arg = new String[]{String.valueOf(id)};
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SitUpDBHelper.Schema.TABLE_MY_SITUP_RESULTS, SitUpDBHelper.Schema.ID + "=?",arg);
        db.close();

    }

    public static class Schema{
        private static final int SCHEMA_VERSION = 1;
        private static final String DATABASE_NAME = "situp_results.db";
        static final String ID = "id";
        static final String DATE = "date";
        static final String TABLE_MY_SITUP_RESULTS = "my_situp_results";
        static final String SITUP = "situp";
        static final String SETS = "sets";
    }
}
