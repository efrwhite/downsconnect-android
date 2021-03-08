package com.example.downsconnect;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "downsconnect.db";
    private static final int DATABASE_VERSION = 1;
    private static final String[] TABLE_NAMES = {"AccountHolders", "Children", "Feed", "Mood", "Sleep"};
    private static final String[] COLUMN_1 = {"FirstName", "LastName", "Username", "Password", "Phone"};
    private static final String[] COLUMN_2 = {"ChildID", "FirstName", "LastName", "Age" , "Gender", "BloodType", "DueDate", "Birthday", "Allergies"};
    private static final String[] COLUMN_3 = {"FeedID", "ChildID", "Amount", "Substance", "TimeConsumed", "Notes"};
    private static final String[] COLUMN_4 = {"MoodID", "ChildID", "MoodType", "Time", "Notes"};
    private static final String[] COLUMN_5 = {"SleepID", "ChildID", "StartTime", "EndTime", "SleepType" ,"Notes"};
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE AccountHolders(" +
                //"AccountHolderID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "FirstName TEXT, " +
                "LastName TEXT, " +
                "Username TEXT, " +
                "Passwrod TEXT, " +
                "Phone TEXT);");
        db.execSQL("CREATE TABLE Children(" +
                "ChildID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "FirstName TEXT, " +
                "LastName TEXT, " +
                "Age INTEGER, " +
                "Gender TEXT," +
                "BloodType TEXT," +
                "DueDate TEXT, " +
                "Birthday TEXT, " +
                "Notes TEXT);");
        db.execSQL("CREATE TABLE Feed(" +
                "FeedID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "Amount INTEGER, " +
                "Substance TEXT, " +
                "TimeConsumed TEXT, " +
                "Notes TEXT);");
        db.execSQL("CREATE TABLE Mood(" +
                "MoodID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "MoodType TEXT, " +
                "Time TEXT, " +
                "Notes TEXT);");
        db.execSQL("CREATE TABLE Sleep(" +
                "SleepID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "StartTime TEXT," +
                "EndTime TEXT, " +
                "SleepType TEXT, " +
                "Notes TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS AccountHolders");
            db.execSQL("DROP TABLE IF EXISTS Children");
            db.execSQL("DROP TABLE IF EXISTS Feed");
            db.execSQL("DROP TABLE IF EXISTS Mood");
        }
    }

    public void deleteEntry(int id, String table){
        String tableID = table.substring(0, table.length() - 1);
        for(String name : TABLE_NAMES){
            if(name.equals(table)){
                String query = "DELETE FROM " + table + " WHERE " + tableID + "ID = " + id + ";";
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(query);
            }
        }
    }

    AccountHolder getAccountHolder(String user, String pass){
        String query = "SELECT * FROM AccountHolders WHERE Username = '" + user +
                "' AND Password = '" + pass + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        AccountHolder accountHolder = new AccountHolder();
        if(c.moveToFirst()){
            c.moveToFirst();
            accountHolder.setAccountID(Integer.parseInt(c.getString(0)));
            accountHolder.setFirstName(c.getString(1));
            accountHolder.setLastName(c.getString(2));
            accountHolder.setUsername(c.getString(3));
            accountHolder.setPassword(c.getString(4));
            accountHolder.setPhone(c.getString(5));
        }
        else{
            c.close();
            accountHolder = null;
        }
        db.close();
        return accountHolder;
    }

}
