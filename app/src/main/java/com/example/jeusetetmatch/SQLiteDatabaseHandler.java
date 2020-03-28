package com.example.jeusetetmatch;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MatchDB";
    private static final String TABLE_NAME = "Match";
    private static final String KEY_ID = "id";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_FAULTS = "faults";
    private static final String KEY_ACE = "ace";

    private static final String[] COLUMNS = { KEY_ID, KEY_ACE, KEY_DURATION, KEY_FAULTS };

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATION_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "duration INTEGER, "
                + "faults INTEGER, " + "ace INTEGER )";

        sqLiteDatabase.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public Match getMatch(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Match match = new Match();
        match.setId(Integer.parseInt(cursor.getString(0)));
        match.setDuration(Integer.parseInt(cursor.getString(1)));
        match.setAce(Integer.parseInt(cursor.getString(2)));
        match.setFaults(Integer.parseInt(cursor.getString(3)));

        return match;
    }

    public List<Match> allMatch() {

        List<Match> matchs = new LinkedList<Match>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Match match = null;

        if (cursor.moveToFirst()) {
            do {
                match = new Match();
                match.setId(Integer.parseInt(cursor.getString(0)));
                match.setDuration(Integer.parseInt(cursor.getString(1)));
                match.setFaults(Integer.parseInt(cursor.getString(2)));
                match.setAce(Integer.parseInt(cursor.getString(3)));
                matchs.add(match);
            } while (cursor.moveToNext());
        }

        return matchs;
    }

    public void addMatch(Match match) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DURATION, match.getDuration());
        values.put(KEY_FAULTS, match.getFaults());
        values.put(KEY_ACE, match.getAce());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }
}