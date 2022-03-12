package com.example.projektshopee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Orders.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UsersOrders(" +
                "name TEXT," +
                "surname TEXT," +
                "email TEXT," +
                "computer TEXT," +
                "count INTEGER," +
                "additions INTEGER," +
                "price INTEGER," +
                "orderData TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists UsersOrders");
    }

    public boolean insertOrderData(String name, String surname, String email, String computer, int count, int additions, int price, String orderDate){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("surname", surname);
        contentValues.put("email", email);
        contentValues.put("computer", computer);
        contentValues.put("count", count);
        contentValues.put("additions", additions);
        contentValues.put("price", price);
        contentValues.put("orderData", String.valueOf(orderDate));

        long result = DB.insert("UsersOrders", null, contentValues);
        return result != -1;
    }

    public Cursor getData(){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor;
        cursor = DB.rawQuery("SELECT * FROM UsersOrders", null);
        return cursor;
    }
}