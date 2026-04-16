package com.example.deliveryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BiteRun.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEMS = "items";
    public static final String COLUMN_DELIVERY_ZONE = "delivery_zone";
    public static final String COLUMN_DELIVERY_TYPE = "delivery_type";
    public static final String COLUMN_PAYMENT_METHOD = "payment_method";
    public static final String COLUMN_CUTLERY = "cutlery";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_STATUS = "status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ORDERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEMS + " TEXT, " +
                COLUMN_DELIVERY_ZONE + " TEXT, " +
                COLUMN_DELIVERY_TYPE + " TEXT, " +
                COLUMN_PAYMENT_METHOD + " TEXT, " +
                COLUMN_CUTLERY + " INTEGER, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_STATUS + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public long insertOrder(String items, String zone, String deliveryType, String payment, int cutlery, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEMS, items);
        values.put(COLUMN_DELIVERY_ZONE, zone);
        values.put(COLUMN_DELIVERY_TYPE, deliveryType);
        values.put(COLUMN_PAYMENT_METHOD, payment);
        values.put(COLUMN_CUTLERY, cutlery);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_STATUS, "Pending");
        return db.insert(TABLE_ORDERS, null, values);
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
    }

    public Cursor getOrderById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS + " WHERE " + COLUMN_ID + " = " + id, null);
    }

    public void updateOrder(int id, String zone, String deliveryType, String payment, int cutlery, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DELIVERY_ZONE, zone);
        values.put(COLUMN_DELIVERY_TYPE, deliveryType);
        values.put(COLUMN_PAYMENT_METHOD, payment);
        values.put(COLUMN_CUTLERY, cutlery);
        values.put(COLUMN_TIME, time);
        db.update(TABLE_ORDERS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteOrder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void updatePaymentMethod(int id, String newPayment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PAYMENT_METHOD, newPayment);
        db.update(TABLE_ORDERS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
}
