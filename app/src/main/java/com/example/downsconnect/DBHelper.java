package com.example.downsconnect;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "downsconnect.db";
    private static final int DATABASE_VERSION = 1;
    private static final String[] TABLE_NAMES = {"AccountHolders", "Children"};
    private static final String[] COLUMN_1 = {"FirstName", "LastName", "Username", "Password", "Phone"};
    private static final String[] COLUMN_2 = {"FirstName", "LastName", "Gender", "Bloodtype", "DueDate", "Birthday"};
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE AccountHolders(" +
                "AccountHolderID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "FirstName TEXT, " +
                "LastName TEXT, " +
                "Username TEXT, " +
                "Passwrod TEXT, " +
                "Phone TEXT);");
        db.execSQL("CREATE TABLE Children(" +
                "ChildID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "FirstName TEXT, " +
                "LastName TEXT, " +
                "Gender TEXT," +
                "BloodType TEXT," +
                "DueDate TEXT, " +
                "Birthday TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
