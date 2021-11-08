package com.example.finalprojectlabmcs_2301865842.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "User DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query1 = "CREATE TABLE USERS(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT NOT NULL," +
                        "password TEXT NOT NULL," +
                        "phone TEXT NOT NULL," +
                        "gender TEXT NOT NULL," +
                        "wallet INTEGER NOT NULL," +
                        "dob TEXT NOT NULL" + ")";
        db.execSQL(query1);

        String query2 = "CREATE TABLE PRODUCTS(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "minplayer INTEGER NOT NULL," +
                "maxplayer INTEGER NOT NULL," +
                "price INTEGER NOT NULL," +
                "latitude REAL NOT NULL," +
                "longitude REAL NOT NULL" + ")";
        db.execSQL(query2);

        String query3 = "CREATE TABLE TRANSACTIONS(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "transactionDate TEXT NOT NULL," +
                "Userid INTEGER NOT NULL," +
                "Prodid INTEGER NOT NULL," +
                "FOREIGN KEY (Userid) REFERENCES USERS (id)," +
                "FOREIGN KEY (Prodid) REFERENCES PRODUCTS (id)" + ")";
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS PRODUCTS");
        db.execSQL("DROP TABLE IF EXISTS TRANSACTIONS");
        onCreate(db);
    }
}
