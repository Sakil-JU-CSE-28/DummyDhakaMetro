package com.example.dummydhakametro;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashSet;
import java.util.Set;

public class StationDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StationDB";
    private static final String TABLE_STATIONS = "stations";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    public StationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STATIONS_TABLE = "CREATE TABLE " + TABLE_STATIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_STATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATIONS);
        onCreate(db);
    }

    public void addStation(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        db.insert(TABLE_STATIONS, null, values);
        db.close();
    }

    public Set<String> getAllStations() {
        Set<String> stationList = new HashSet<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + StationDbHelper.TABLE_STATIONS, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String stationName = cursor.getString(cursor.getColumnIndex(StationDbHelper.KEY_NAME));
                stationList.add(stationName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return stationList;
    }
}
