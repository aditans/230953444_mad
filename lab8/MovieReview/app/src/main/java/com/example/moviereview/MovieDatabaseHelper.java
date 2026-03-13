package com.example.moviereview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MovieReview.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_MOVIES = "Movies";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_POINTS = "points";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_MOVIES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_YEAR + " INTEGER, " +
            COLUMN_POINTS + " INTEGER);";

    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    public long insertMovie(String name, int year, int points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_POINTS, points);
        return db.insert(TABLE_MOVIES, null, values);
    }

    public List<String> getAllMovies() {
        List<String> moviesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MOVIES, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                int year = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR));
                int points = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_POINTS));
                moviesList.add(name + " (" + year + ") - " + points + " pts");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return moviesList;
    }
}
