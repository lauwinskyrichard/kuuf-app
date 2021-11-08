package com.example.finalprojectlabmcs_2301865842.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finalprojectlabmcs_2301865842.Database.DatabaseHelper;
import com.example.finalprojectlabmcs_2301865842.UserData;

import java.util.ArrayList;

public class UserDataHelper {

    DatabaseHelper databaseHelper;

    public UserDataHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void insertUserData(String username, String password, String phone, String gender, int wallet, String dob) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("INSERT INTO USERS (username, password, phone, gender, wallet, dob) " +
                "VALUES('"+username+"', " +
                "       '"+password+"', " +
                "       '"+phone+"', " +
                "       '"+gender+"', " +
                "       '"+wallet+"', " +
                "       '"+dob+"')");
        db.close();
        databaseHelper.close();
    }

    public void updateWallet(int id, int wallet) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("UPDATE USERS SET wallet = '"+wallet+"' WHERE id = '"+id+"'");
        db.close();
        databaseHelper.close();
    }

    public ArrayList<UserData> getAllUserData() {

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USERS", null);
        cursor.moveToFirst();

        ArrayList<UserData> listUser = null;

        if (cursor.getCount() > 0)
        {
            listUser = new ArrayList<>();

            while (!cursor.isAfterLast())
            {
                int id = cursor.getInt(0);
                String tempUsername = cursor.getString(cursor.getColumnIndex("username"));
                String tempPassword = cursor.getString(cursor.getColumnIndex("password"));
                String tempPhone = cursor.getString(cursor.getColumnIndex("phone"));
                String tempGender = cursor.getString(cursor.getColumnIndex("gender"));
                int tempWallet = cursor.getInt(cursor.getColumnIndex("wallet"));
                String tempDob = cursor.getString(cursor.getColumnIndex("dob"));

                listUser.add(new UserData(id, tempUsername, tempPassword, tempDob, tempPhone, tempGender, tempWallet));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        databaseHelper.close();
        return  listUser;
    }
}
