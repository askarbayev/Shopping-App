package com.example.askarbayev1.demo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ShoppingApp.db";
    public static final String SHOPAPP_TABLE_NAME = "shopping_items";
    public static final String SHOPAPP_COLUMN_ID = "id";
    public static final String SHOPAPP_COLUMN_NAME = "name";
    public static final String SHOPAPP_COLUMN_TYPE = "type";
    public static final String SHOPAPP_COLUMN_PRICE = "price";
    public static final String SHOPAPP_COLUMN_QUANTITY = "quantity";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table shopping_items " +
                        "(id INTEGER primary key, name TEXT, type TEXT, price REAL, quantity INTEGER, date DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS shopping_items");
        onCreate(db);
    }
    public boolean insertItem (String name, String type, double price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("type", type);
        contentValues.put("price", price);
        contentValues.put("quantity", quantity);
        db.insert("shopping_items", null, contentValues);
        return true;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from shopping_items where id="+id+"", null );
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SHOPAPP_TABLE_NAME);
        return numRows;
    }
    public boolean updateItem (Integer id, String name, String type, double price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("type", type);
        contentValues.put("price", price);
        contentValues.put("quantity", quantity);
        db.update("shopping_items", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteItem (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("shopping_items",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public LinkedList<Item> getAllItems() {
        LinkedList<Item> linkedList = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from shopping_items", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            int id = res.getInt(res.getColumnIndex(SHOPAPP_COLUMN_ID));
            String name = res.getString(res.getColumnIndex(SHOPAPP_COLUMN_NAME));
            String type = res.getString(res.getColumnIndex(SHOPAPP_COLUMN_TYPE));
            double price = res.getDouble(res.getColumnIndex(SHOPAPP_COLUMN_PRICE));
            int quantity = res.getInt(res.getColumnIndex(SHOPAPP_COLUMN_QUANTITY));
            linkedList.add(new Item(id, name, type, price, quantity));
            res.moveToNext();
        }
        return linkedList;
    }
}
