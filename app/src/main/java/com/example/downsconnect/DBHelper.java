package com.example.downsconnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "downsconnect.db";
    private static final int DATABASE_VERSION = 1;
    private static final String[] TABLE_NAMES = {"AccountHolders", "Children", "Feed", "Mood", "Sleep", "Entries", "Medical"};
    private static final String[] COLUMN_1 = {"AccountID","FirstName", "LastName", "Username", "Password", "Phone"};
    private static final String[] COLUMN_2 = {"ChildID", "FirstName", "LastName", "Gender", "BloodType", "DueDate", "Birthday", "Allergies"};
    private static final String[] COLUMN_3 = {"FeedID", "ChildID", "Amount", "Substance", "TimeConsumed", "Notes", "EntryTime"};
    private static final String[] COLUMN_4 = {"MoodID", "ChildID", "MoodType", "Time", "Notes", "EntryTime"};
    private static final String[] COLUMN_5 = {"SleepID", "ChildID", "StartTime", "EndTime", "SleepType" ,"Notes", "EntryTime"};
    private static final String[] COLUMN_6 = {"EntryID", "EntryType", "TypeID"};
    private static final String[] COLUMN_7 = {"MedicalID", "ChildID", "Height", "HeightUnit", "Weight", "WeightUnit", "HeadSize", "HeadSizeUnit", "Health", "Vaccine", "Dosage", "DosageUnit", "DoctorsVisit", "Temperature", "TemperatureUnit", "Notes", "EntryTime"};
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
                "Password TEXT, " +
                "Phone TEXT);");
        db.execSQL("CREATE TABLE Children(" +
                "ChildID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "FirstName TEXT, " +
                "LastName TEXT, " +
                "Gender TEXT," +
                "BloodType TEXT," +
                "DueDate INTEGER, " +
                "Birthday INTEGER);");
        db.execSQL("CREATE TABLE Feed(" +
                "FeedID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "Amount INTEGER, " +
                "Substance TEXT, " +
                "TimeConsumed TEXT, " +
                "Notes TEXT, " +
                "EntryTime INTEGER);");
        db.execSQL("CREATE TABLE Mood(" +
                "MoodID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "MoodType TEXT, " +
                "Time TEXT, " +
                "Notes TEXT, " +
                "EntryTime INTEGER);");
        db.execSQL("CREATE TABLE Sleep(" +
                "SleepID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "StartTime INTEGER," +
                "EndTime INTEGER, " +
                "SleepType TEXT, " +
                "Notes TEXT, " +
                "EntryTime INTEGER);");
        db.execSQL("CREATE TABLE Bathroom(" +
                "BathroomID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "BathroomType TEXT, " +
                "TreatmentPlan TEXT, " +
                "DateOfLastStool INTEGER, " +
                "Notes TEXT, " +
                "EntryTime INTEGER)");
        db.execSQL("CREATE TABLE Entries(" +
                "EntryID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "EntryType TEXT, " +
                "EntryTime INTEGER, " +
                "TypeID INTEGER)");
        db.execSQL("CREATE TABLE Medical(" +
                "MedicalID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "Height INTEGER, " +
                "HeightUnit TEXT, " +
                "Weight INTEGER, " +
                "WeightUnit TEXT, " +
                "HeadSize INTEGER, " +
                "HeadSizeUnit TEXT, " +
                "Health TEXT, " +
                "Vaccine TEXT, " +
                "Dosage INTEGER, " +
                "DosageUnit TEXT, " +
                "DoctorsVisit INTEGER, " +
                "Temperature INTEGER, " +
                "TemperatureUnit TEXT, " +
                "Notes TEXT, " +
                "EntryTime INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS AccountHolders");
            db.execSQL("DROP TABLE IF EXISTS Children");
            db.execSQL("DROP TABLE IF EXISTS Feed");
            db.execSQL("DROP TABLE IF EXISTS Mood");
            db.execSQL("DROP TABLE IF EXISTS Sleep");
            db.execSQL("DROP TABLE IF EXISTS Entries");
            db.execSQL("DROP TABLE IF EXISTS Medical");
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

    public void addAccount(AccountHolder accountHolder){
        ContentValues values = new ContentValues();
        values.put(COLUMN_1[1], accountHolder.getFirstName());
        values.put(COLUMN_1[2], accountHolder.getLastName());
        values.put(COLUMN_1[3], accountHolder.getUsername());
        values.put(COLUMN_1[4], accountHolder.getPassword());
        values.put(COLUMN_1[5], accountHolder.getPhone());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAMES[0], null, values);
        db.close();
    }

    public void addChild(Child child){
        ContentValues values = new ContentValues();
        values.put(COLUMN_2[1], child.getFirstName());
        values.put(COLUMN_2[2], child.getLastName());
        values.put(COLUMN_2[3], String.valueOf(child.getGender()));
        values.put(COLUMN_2[4], String.valueOf(child.getBloodType()));
        values.put(COLUMN_2[5], child.getDueDate());
        values.put(COLUMN_2[6], child.getBirthday());
        values.put(COLUMN_2[7], child.getAllergies());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAMES[1], null, values);
        db.close();
    }

    public void addFeed(Feed feed){
        ContentValues values = new ContentValues();
        values.put(COLUMN_3[1], feed.getChildID());
        values.put(COLUMN_3[2], feed.getAmount());
        values.put(COLUMN_3[3], feed.getSubstance());
        values.put(COLUMN_3[4], feed.getTimeConsumed());
        values.put(COLUMN_3[5], feed.getNotes());
        values.put(COLUMN_3[6], feed.getEntryTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAMES[2], null, values);
        db.close();
    }


    public void addMood(Mood mood){
        ContentValues values = new ContentValues();
        values.put(COLUMN_4[1], mood.getMoodID());
        values.put(COLUMN_4[2], mood.getMoodType());
        values.put(COLUMN_4[3], mood.getTime());
        values.put(COLUMN_4[4], mood.getNotes());
        values.put(COLUMN_4[5], mood.getEntryTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAMES[3], null, values);
        db.close();
    }

    public void addSleep(Sleep sleep){
        ContentValues values = new ContentValues();
        values.put(COLUMN_5[1], sleep.getChildID());
        values.put(COLUMN_5[2], sleep.getStartTime());
        values.put(COLUMN_5[3], sleep.getEndTime());
        values.put(COLUMN_5[4], sleep.getSleepType());
        values.put(COLUMN_5[5], sleep.getNotes());
        values.put(COLUMN_5[6], sleep.getEntryTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAMES[4], null, values);
        db.close();
    }

    public void addEntry(Entry entry){
        ContentValues values = new ContentValues();
        values.put(COLUMN_6[1], entry.getEntryType());
        values.put(COLUMN_6[2], entry.getEntryTime());
        values.put(COLUMN_6[3], entry.getTypeID());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAMES[5], null, values);
        db.close();
    }

    public void addMedical(MedicalInfo medicalInfo){
        ContentValues values = new ContentValues();
        values.put(COLUMN_7[1], medicalInfo.getChildId());
        values.put(COLUMN_7[2], medicalInfo.getHeight());
        values.put(COLUMN_7[3], medicalInfo.getHeightUnit());
        values.put(COLUMN_7[4], medicalInfo.getWeight());
        values.put(COLUMN_7[5], medicalInfo.getWeightUnit());
        values.put(COLUMN_7[6], medicalInfo.getHeadSize());
        values.put(COLUMN_7[7], medicalInfo.getHeadSizeUnit());
        values.put(COLUMN_7[8], medicalInfo.getHealth());
        values.put(COLUMN_7[9], medicalInfo.getVaccine());
        values.put(COLUMN_7[10], medicalInfo.getDosage());
        values.put(COLUMN_7[11], medicalInfo.getDosageUnit());
        values.put(COLUMN_7[12], medicalInfo.getDoctorVisit());
        values.put(COLUMN_7[13], medicalInfo.getTemperature());
        values.put(COLUMN_7[14], medicalInfo.getTemperatureUnit());
        values.put(COLUMN_7[15], medicalInfo.getNotes());
        values.put(COLUMN_7[16], medicalInfo.getEntryTime());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAMES[6], null, values);
        db.close();
    }

    AccountHolder getAccountHolder(String user, String pass){
//        String query = "SELECT * FROM AccountHolders WHERE Username = '" + user +
//                "' AND Password = '" + pass + "';";

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

    ArrayList<Entry> getAllEntries(){
        String query = "SELECT * FROM Entries;";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Entry> entries = new ArrayList<>();
        int x = 0;
        while(c.moveToNext()){
            Entry entry = new Entry();
            if(x == 0){
                c.moveToFirst();
                entry.setEntryType(c.getString(1));
                entry.setTypeID(c.getInt(2));
                entries.add(entry);
                x++;
            }
        }
        c.close();
        db.close();
        return entries;
    }

    MedicalInfo findMedicalInfo(int medicalID){
        String query = "SELECT * FROM Medical WHERE MedicalID = " + medicalID + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        MedicalInfo medicalInfo = new MedicalInfo();
        if(c.moveToFirst()) {
            c.moveToFirst();
            medicalInfo.setMedicalID(Integer.parseInt(c.getString(0)));
            medicalInfo.setChildId(Integer.parseInt(c.getString(1)));
            medicalInfo.setHeight(Integer.parseInt(c.getString(2)));
            medicalInfo.setHeightUnit(c.getString(3));
            medicalInfo.setWeight(Integer.parseInt(c.getString(4)));
            medicalInfo.setWeightUnit(c.getString(5));
            medicalInfo.setHeadSize(Integer.parseInt(c.getString(6)));
            medicalInfo.setHeadSizeUnit(c.getString(7));
            medicalInfo.setHealth(c.getString(8));
            medicalInfo.setVaccine(c.getString(9));
            medicalInfo.setDosage(Integer.parseInt(c.getString(10)));
            medicalInfo.setDosageUnit(c.getString(11));
            medicalInfo.setDoctorVisit(Integer.parseInt(c.getString(12)));
            medicalInfo.setTemperature(Integer.parseInt(c.getString(13)));
            medicalInfo.setTemperatureUnit(c.getString(14));
            medicalInfo.setNotes(c.getString(15));
            medicalInfo.setEntryTime(Integer.parseInt(c.getString(16)));
        }
        else{
            medicalInfo = null;
        }
        db.close();
        return medicalInfo;
    }

}

