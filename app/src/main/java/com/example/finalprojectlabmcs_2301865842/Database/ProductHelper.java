package com.example.finalprojectlabmcs_2301865842.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalprojectlabmcs_2301865842.Database.DatabaseHelper;
import com.example.finalprojectlabmcs_2301865842.Product;

import java.util.ArrayList;

public class ProductHelper {

    DatabaseHelper databaseHelper;

    public ProductHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void insertItem(String name, int minplayer, int maxplayer, int price, Double latitude, Double longitude) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("INSERT INTO PRODUCTS (name, minplayer, maxplayer, price, latitude, longitude) " +
                "VALUES('"+name+"', " +
                "       '"+minplayer+"', " +
                "       '"+maxplayer+"', " +
                "       '"+price+"', " +
                "       '"+latitude+"', " +
                "       '"+longitude+"')");
        db.close();
        databaseHelper.close();
    }

    public ArrayList<Product> getAllProduct() {

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM PRODUCTS", null);
        cursor.moveToFirst();

        ArrayList<Product> listProduct = null;

        if (cursor.getCount() > 0)
        {
            listProduct = new ArrayList<>();

            while (!cursor.isAfterLast())
            {
                int id = cursor.getInt(0);
                String tempName = cursor.getString(cursor.getColumnIndex("name"));
                int tempMinPlayer = cursor.getInt(cursor.getColumnIndex("minplayer"));
                int tempMaxPlayer = cursor.getInt(cursor.getColumnIndex("maxplayer"));
                int tempPrice = cursor.getInt(cursor.getColumnIndex("price"));
                Double tempLatitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                Double tempLongitude = cursor.getDouble(cursor.getColumnIndex("longitude"));

                listProduct.add(new Product(id, tempName, tempMinPlayer, tempMaxPlayer, tempPrice, tempLatitude, tempLongitude));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        databaseHelper.close();
        return  listProduct;
    }
}
