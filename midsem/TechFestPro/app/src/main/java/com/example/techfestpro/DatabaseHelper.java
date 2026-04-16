package com.example.techfestpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TechFest.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "registrations";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_CATEGORY = "category";
    public static final String COL_TYPE = "type";
    public static final String COL_ARRIVAL = "arrival";
    public static final String COL_ACCOMMODATION = "accommodation";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_CATEGORY + " TEXT, " +
                COL_TYPE + " TEXT, " +
                COL_ARRIVAL + " TEXT, " +
                COL_ACCOMMODATION + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertRegistration(String name, String category, String type, String arrival, String accommodation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_CATEGORY, category);
        contentValues.put(COL_TYPE, type);
        contentValues.put(COL_ARRIVAL, arrival);
        contentValues.put(COL_ACCOMMODATION, accommodation);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
}