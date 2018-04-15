package com.example.askarbayev1.demo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ShoppingApp.db";
    public static final String SHOPAPP_TABLE_NAME = "shopping_items";
    public static final String SHOPAPP_COLUMN_ID = "id";
    public static final String SHOPAPP_COLUMN_NAME = "name";
    public static final String SHOPAPP_COLUMN_TYPE = "type";
    public static final String SHOPAPP_COLUMN_PRICE = "price";
    public static final String SHOPAPP_COLUMN_QUANTITY = "quantity";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table shopping_items " +
                        "(id INTEGER primary key, name TEXT, type TEXT, price REAL, quantity INTEGER, date DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table switch_data " +
                        "(id INTEGER primary key, status TEXT)"
        );
        db.execSQL(
                "create table budget_data " +
                        "(id INTEGER primary key, budget REAL)"
        );
        db.execSQL(
                "create table cart_data " +
                        "(id INTEGER primary key, name TEXT, type TEXT, price REAL, quantity INTEGER, date DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS shopping_items");
        db.execSQL("DROP TABLE IF EXISTS switch_data");
        db.execSQL("DROP TABLE IF EXISTS budget_data");
        db.execSQL("DROP TABLE IF EXISTS cart_data");
        onCreate(db);
    }
    public boolean insertBudget(double budget){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("budget", budget);
        db.insert("budget_data", null, contentValues);
        return true;
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
    public boolean insertCartItem(String name, String type, double price, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("type", type);
        contentValues.put("price", price);
        contentValues.put("quantity", quantity);
        db.insert("cart_data", null, contentValues);
        return true;
    }
    public boolean insertStatus (String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);
        db.insert("switch_data", null, contentValues);
        return true;
    }
    public boolean updateBudget(double budget){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("budget", budget);
        db.update("budget_data", contentValues, "id = ? ", new String[] { Integer.toString(1) } );
        return true;
    }
    public boolean updateStatus (String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);
        db.update("switch_data", contentValues, "id = ? ", new String[] { Integer.toString(1) } );
        return true;
    }

    public double getBudget(){
        SQLiteDatabase db = this.getReadableDatabase();
        int id = 1;
        Cursor res =  db.rawQuery("select * from budget_data where id="+id+"", null);
        res.moveToFirst();
        double budget = -1;
        while(res.isAfterLast() == false){
            budget = res.getDouble(res.getColumnIndex("budget"));
            res.moveToNext();
        }
        return budget;
    }
    public String getStatus(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                //Toast.makeText(t, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
                Log.d("Table Name=> ", c.getString(0));
                c.moveToNext();
            }
        }
        int id = 1;
        Cursor res =  db.rawQuery("select * from switch_data where id="+id+"", null);
        res.moveToFirst();
        String status = "No way";
        while(res.isAfterLast() == false){
            status = res.getString(res.getColumnIndex("status"));
            res.moveToNext();
        }
        return status;
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
        if (quantity<1){
            deleteItem(id);
        }else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("type", type);
            contentValues.put("price", price);
            contentValues.put("quantity", quantity);
            db.update("shopping_items", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        }

        return true;
    }
    public Integer deleteItem (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("shopping_items",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public ArrayList<Item> getAllItems() {
        ArrayList<Item> linkedList = new ArrayList<>();
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

    public ArrayList<Item> getCartItems(){
        ArrayList<Item> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from cart_data", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            int id = res.getInt(res.getColumnIndex("id"));
            String name = res.getString(res.getColumnIndex("name"));
            String type = res.getString(res.getColumnIndex("type"));
            double price = res.getDouble(res.getColumnIndex("price"));
            int quantity = res.getInt(res.getColumnIndex("quantity"));
            arrayList.add(new Item(id, name, type, price, quantity));
            res.moveToNext();
        }
        return arrayList;
    }
}
