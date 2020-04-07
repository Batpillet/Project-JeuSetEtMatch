package com.example.jeusetetmatch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 15;
    private static final String DATABASE_NAME = "MatchDB.db";
    private static final String TABLE_NAME = "tablematch";
    private static final String KEY_ID = "id";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_FAULTS = "faults";
    private static final String KEY_ACE = "ace";

    private static final String TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT , "+KEY_DURATION+" INTEGER , "+KEY_ACE+" INTEGER , "+KEY_FAULTS+" INTEGER )";

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
        Log.i("DATABASE", "oncreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addMatch(Match m) {
        Log.i("Ajout match : ", m.toString());
        ContentValues values = new ContentValues();
        values.put(KEY_DURATION, m.getDuration());
        values.put(KEY_FAULTS, m.getFaults());
        values.put(KEY_ACE, m.getAce());
        SQLiteDatabase db = this.getWritableDatabase();
        // insert
        db.insert(TABLE_NAME,null, values);
    }
/*
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where id =" + id + "", null);
        return res;
    }

    public Match getMatch(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_DURATION, KEY_FAULTS, KEY_ACE}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Match match = new Match(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)));
        return match;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateMatch(Integer id, int duration, int faults, int ace) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("duration", duration);
        values.put("faults", faults);
        values.put("ace", ace);
        db.update("match", values, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteMatch(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("match",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getAllMatch() {
        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            arrayList.add(res.getString(res.getColumnIndex(KEY_DURATION)));
            res.moveToNext();
        }
        return arrayList;
    }*/

    public int getCount() {

        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public Cursor viewData() {

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // return count
        return cursor;
    }

}