package com.example.finalprojectlabmcs_2301865842.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalprojectlabmcs_2301865842.TransactionHistory;
import com.example.finalprojectlabmcs_2301865842.UserData;

import java.util.ArrayList;

public class HistoryHelper {

    DatabaseHelper databaseHelper;

    public HistoryHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void insertHistory(String transactionDate, int userID, int productID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("INSERT INTO TRANSACTIONS (transactionDate, Userid, Prodid) " +
                "VALUES('"+transactionDate+"', " +
                "       '"+userID+"', " +
                "       '"+productID+"')");
        db.close();
        databaseHelper.close();
    }

    public void deleteTrans(int id) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM TRANSACTIONS WHERE Userid='"+id+"'");
        db.close();
        databaseHelper.close();
    }

    public ArrayList<TransactionHistory> getAllTransaction(int userID) {

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM TRANSACTIONS WHERE Userid='"+userID+"'",  null);
        cursor.moveToFirst();

        ArrayList<TransactionHistory> listTransaction = null;

        if (cursor.getCount() > 0)
        {
            listTransaction = new ArrayList<>();

            while (!cursor.isAfterLast())
            {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String tempTransDate = cursor.getString(cursor.getColumnIndex("transactionDate"));
                int tempUserID = cursor.getInt(cursor.getColumnIndex("Userid"));
                int tempProductID = cursor.getInt(cursor.getColumnIndex("Prodid"));

                listTransaction.add(new TransactionHistory(id, tempTransDate, tempUserID, tempProductID));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        databaseHelper.close();
        return  listTransaction;
    }
}
