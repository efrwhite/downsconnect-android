package com.example.downsconnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import com.example.downsconnect.objects.AccountHolder;
import com.example.downsconnect.objects.Bathroom;
import com.example.downsconnect.objects.Child;
import com.example.downsconnect.objects.Entry;
import com.example.downsconnect.objects.Feed;
import com.example.downsconnect.objects.Height;
import com.example.downsconnect.objects.MedicalInfo;
import com.example.downsconnect.objects.Milestone;
import com.example.downsconnect.objects.Mood;
import com.example.downsconnect.objects.Sleep;

import java.lang.reflect.Array;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "downsconnect.db";
    private static final int DATABASE_VERSION = 1;
    private static final String[] TABLE_NAMES = {"Account", "Child", "Feed", "Mood", "Sleep", "Entry", "Medical", "Milestone", "Bathroom"};
    private static final String[] COLUMN_1 = {"AccountID","FirstName", "LastName", "Username", "Password", "Phone"};
    private static final String[] COLUMN_2 = {"ChildID", "FirstName", "LastName", "Gender", "BloodType", "DueDate", "Birthday", "Allergies", "Medications"};
    private static final String[] COLUMN_3 = {"FeedID", "ChildID", "Amount", "Substance", "Notes", "FoodUnit" , "EntryTime"};
    private static final String[] COLUMN_4 = {"MoodID", "ChildID", "MoodType", "Time", "Notes", "EntryTime"};
    private static final String[] COLUMN_5 = {"SleepID", "ChildID", "SleepTime", "Duration", "Snoring" ,"Medication", "Supplements", "CPAP", "Other", "Study", "Unit", "Notes"};
    private static final String[] COLUMN_6 = {"EntryID", "EntryText", "EntryTime", "ChildID"};
    private static final String[] COLUMN_7 = {"MedicalID", "ChildID", "Height", "Weight", "HeadSize", "DoctorsVisit", "Temperature", "Provider", "VisitNum", "ProviderType"};
    private static final String[] COLUMN_8 = {"MilestoneID", "ChildID", "Rolling", "Sitting", "Standing", "Walking"};
    private static final String[] COLUMN_9 = {"BathroomID", "ChildID", "BathroomType", "TreatmentPlan", "Leak", "OpenAir", "DiaperCream", "Quantity", "PottyAccident", "DateOfLastStool", "Duration"};
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Account(" +
                "AccountHolderID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "FirstName TEXT, " +
                "LastName TEXT, " +
                "Username TEXT, " +
                "Password TEXT, " +
                "Phone TEXT);");
        db.execSQL("CREATE TABLE Child(" +
                "ChildID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "FirstName TEXT, " +
                "LastName TEXT, " +
                "Gender TEXT," +
                "BloodType TEXT," +
                "DueDate INTEGER, " +
                "Birthday INTEGER, " +
                "Allergies TEXT, " +
                "Medications TEXT);");
        db.execSQL("CREATE TABLE Feed(" +
                "FeedID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "Amount INTEGER, " +
                "Substance TEXT, " +
                "Notes TEXT, " +
                "FoodUnit TEXT, " +
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
                "SleepTime INTEGER," +
                "Duration INTEGER, " +
                "Snoring TEXT, " +
                "Medication TEXT, " +
                "Supplements TEXT, " +
                "CPAP TEXT, " +
                "Other TEXT, " +
                "Study TEXT, " +
                "Unit TEXT, " +
                "Notes TEXT);");
        db.execSQL("CREATE TABLE Entry(" +
                "EntryID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "EntryText TEXT, " +
                "EntryTime INTEGER, " +
                "ChildID INTEGER)");
        db.execSQL("CREATE TABLE Medical(" +
                "MedicalID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "Height TEXT, " +
                "Weight TEXT, " +
                "HeadSize TEXT, " +
                "DoctorsVisit INTEGER, " +
                "Temperature TEXT, " +
                "Provider TEXT, " +
                "VisitNum TEXT, " +
                "ProviderType TEXT)");
        db.execSQL("CREATE TABLE Milestone(" +
                "MilestoneID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "Rolling INTEGER, " +
                "Sitting INTEGER, " +
                "Standing INTEGER, " +
                "Walking INTEGER)");
        db.execSQL("CREATE TABLE Bathroom(" +
                "BathroomID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "BathroomType TEXT, " +
                "Treatment TEXT, " +
                "Leak TEXT, " +
                "OpenAir TEXT, " +
                "DiaperCream TEXT, " +
                "Quantity TEXT, " +
                "PottyAccident TEXT, " +
                "DateOfLastStool INTEGER, " +
                "Duration TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS Account");
            db.execSQL("DROP TABLE IF EXISTS Child");
            db.execSQL("DROP TABLE IF EXISTS Feed");
            db.execSQL("DROP TABLE IF EXISTS Mood");
            db.execSQL("DROP TABLE IF EXISTS Sleep");
            db.execSQL("DROP TABLE IF EXISTS Entry");
            db.execSQL("DROP TABLE IF EXISTS Medical");
            db.execSQL("DROP TABLE IF EXISTS Milestone");
            db.execSQL("DROP TABLE IF EXISTS Bathroom");
        }
    }

    public void deleteEntry(int id, String table){
//        String tableID = table.substring(0, table.length() - 1);
        for(String name : TABLE_NAMES){
            if(name.equals(table)){
                String query = "DELETE FROM " + table + " WHERE " + table + "ID = " + id + ";";
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

    public boolean addChild(Child child){
        ContentValues values = new ContentValues();
        values.put(COLUMN_2[1], child.getFirstName());
        values.put(COLUMN_2[2], child.getLastName());
        values.put(COLUMN_2[3], child.getGender());
        values.put(COLUMN_2[4], child.getBloodType());
        values.put(COLUMN_2[5], child.getDueDate());
        values.put(COLUMN_2[6], child.getBirthday());
        values.put(COLUMN_2[7], child.getAllergies());
        values.put(COLUMN_2[8], child.getMedications());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[1], null, values);
        db.close();
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public void addFeed(Feed feed){
        ContentValues values = new ContentValues();
        values.put(COLUMN_3[1], feed.getChildID());
        values.put(COLUMN_3[2], feed.getAmount());
        values.put(COLUMN_3[3], feed.getSubstance());
        values.put(COLUMN_3[4], feed.getNotes());
        values.put(COLUMN_3[5], feed.getFoodUnit());
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
        values.put(COLUMN_5[2], sleep.getSleepTime());
        values.put(COLUMN_5[3], sleep.getDuration());
        values.put(COLUMN_5[4], sleep.getSnoring());
        values.put(COLUMN_5[5], sleep.getMedication());
        values.put(COLUMN_5[6], sleep.getSupplements());
        values.put(COLUMN_5[7], sleep.getCPAP());
        values.put(COLUMN_5[8], sleep.getOther());
        values.put(COLUMN_5[9], sleep.getStudy());
        values.put(COLUMN_5[10], sleep.getUnit());
        values.put(COLUMN_5[11], sleep.getNotes());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAMES[4], null, values);
        db.close();
    }

    public void addEntry(Entry entry){
        ContentValues values = new ContentValues();
        values.put(COLUMN_6[1], entry.getEntryText());
        values.put(COLUMN_6[2], entry.getEntryTime());
        values.put(COLUMN_6[3], entry.getChildID());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAMES[5], null, values);
        db.close();
    }

    public void addMilestone(Milestone milestone){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(COLUMN_8[1], milestone.getChildId());
        values.put(COLUMN_8[2], milestone.getRollingDate());
        values.put(COLUMN_8[3], milestone.getSittingDate());
        values.put(COLUMN_8[4], milestone.getStandingDate());
        values.put(COLUMN_8[5], milestone.getWalkingDate());
        db.insert(TABLE_NAMES[7], null, values);
        db.close();
    }

    public boolean addMedical(MedicalInfo medicalInfo){
        ContentValues values = new ContentValues();
        values.put(COLUMN_7[1], medicalInfo.getChildID());
        values.put(COLUMN_7[2], medicalInfo.getHeight());
        values.put(COLUMN_7[3], medicalInfo.getWeight());
        values.put(COLUMN_7[4], medicalInfo.getHeadInfo());
        values.put(COLUMN_7[5], medicalInfo.getDoctorDate());
        values.put(COLUMN_7[6], medicalInfo.getTemperatureInfo());
        values.put(COLUMN_7[7], medicalInfo.getProvider());
        values.put(COLUMN_7[8], medicalInfo.getVisit());
        values.put(COLUMN_7[9], medicalInfo.getProviderType());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[6], null, values);
        db.close();
        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean addBathroom(Bathroom bathroom){
        ContentValues values = new ContentValues();
        values.put(COLUMN_9[1], bathroom.getChildID());
        values.put(COLUMN_9[2], bathroom.getBathroomType());
        values.put(COLUMN_9[3], bathroom.getTreatmentPlan());
        values.put(COLUMN_9[4], bathroom.getLeak());
        values.put(COLUMN_9[5], bathroom.getOpenAir());
        values.put(COLUMN_9[6], bathroom.getQuantity());
        values.put(COLUMN_9[7], bathroom.getPottyAccident());
        values.put(COLUMN_9[8], bathroom.getDateOfLastStool());
        values.put(COLUMN_9[9], bathroom.getDuration());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[8], null, values);
        db.close();
        if(result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public Child getChild(String firstName){
        String query = "SELECT * FROM Child WHERE FirstName = '" + firstName + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        Child child = new Child();
        if(c.moveToFirst()){
            c.moveToFirst();
            child.setChildID(c.getInt(0));
            child.setFirstName(c.getString(1));
            child.setLastName(c.getString(2));
            child.setGender(c.getString(3));
            child.setBloodType(c.getString(4));
            child.setDueDate(c.getLong(5));
            child.setBirthday(c.getLong(6));
            child.setAllergies(c.getString(7));
            child.setMedications(c.getString(8));
        }
        else{
            c.close();
            child = null;
        }
        db.close();
        return child;
    }

    public String getChildName(int id){
        String query = "SELECT * FROM Child WHERE ChildID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        String childName;
        if(c.moveToFirst()){
            c.moveToFirst();
            childName = c.getString(1);
        }
        else{
            c.close();
            childName = null;
        }
        db.close();
        return childName;
    }

    public long getChildBirthday(int id){
        String query = "SELECT * FROM Child WHERE ChildID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        long childBirthday;
        if(c.moveToFirst()){
            c.moveToFirst();
            childBirthday = c.getLong(6);
        }
        else{
            c.close();
            childBirthday = 0;
        }
        db.close();
        return childBirthday;
    }

   public Milestone getMilestone(int childID){
        String query = "SELECT * FROM Milestone WHERE ChildID = '" + childID + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        Milestone milestone = new Milestone();
        if(c.moveToFirst()){
            c.moveToFirst();
            milestone.setMilestoneId(c.getInt(0));
            milestone.setChildId(c.getInt(1));
            milestone.setRollingDate(c.getLong(2));
            milestone.setSittingDate(c.getLong(3));
            milestone.setStandingDate(c.getLong(4));
            milestone.setWalkingDate(c.getLong(5));
        }
        else{
            c.close();
            milestone = null;
        }
        db.close();
        return milestone;
    }

    public MedicalInfo getMedical(int medicalID){
        String query = "SELECT * FROM Medical WHERE MedicalID = '" + medicalID + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        MedicalInfo info = new MedicalInfo();
        if(c.moveToFirst()){
            c.moveToFirst();
            info.setMedicalID(c.getInt(0));
            info.setMedicalID(c.getInt(1));
            info.setHeight(c.getString(2));
            info.setWeight(c.getString(3));
            info.setHeadInfo(c.getString(4));
            info.setDoctorDate(c.getLong(5));
            info.setTemperatureInfo(c.getString(6));
            info.setProvider(c.getString(7));
            info.setVisit(c.getString(8));
            info.setProviderType(c.getString(9));
        }
        else{
            c.close();
            info = null;
        }
        db.close();
        return info;
    }

    public ArrayList<Integer> getHeight(int childID){
        String query = "SELECT * FROM Medical WHERE ChildID = '" + childID +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Integer> data = new ArrayList<>();
        while(c.moveToNext()){
            String text = c.getString(2);
            int dataPoint = Integer.parseInt(text.substring(0, text.indexOf(" ")));
            data.add(dataPoint);
        }
        return data;
    }

    public ArrayList<Integer> getWeight(int childID){
        String query = "SELECT * FROM Medical WHERE ChildID = '" + childID +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Integer> data = new ArrayList<>();
        while(c.moveToNext()){
            String text = c.getString(3);
            int dataPoint = Integer.parseInt(text.substring(0, text.indexOf(" ")));
            data.add(dataPoint);
        }
        return data;
    }

    public ArrayList<Integer> getHeadSizes(int childID){
        String query = "SELECT * FROM Medical WHERE ChildID = '" + childID +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Integer> data = new ArrayList<>();
        while(c.moveToNext()){
            String text = c.getString(4);
            int dataPoint = Integer.parseInt(text.substring(0, text.indexOf(" ")));
            data.add(dataPoint);
        }
        return data;
    }

   public AccountHolder getAccount(String user, String pass){
//        String query = "SELECT * FROM AccountHolders WHERE Username = '" + user +
//                "' AND Password = '" + pass + "';";

        String query = "SELECT * FROM Account WHERE Username = '" + user +
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

   public ArrayList<AccountHolder> getAllAccounts(){
        String query = "SELECT * FROM Account;";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<AccountHolder> accounts = new ArrayList<>();
        int x = 0;
        while(c.moveToNext()){
            AccountHolder account = new AccountHolder();
            if(x == 0){
                c.moveToFirst();
            }
                account.setAccountID(c.getInt(0));
                account.setFirstName(c.getString(1));
                account.setLastName(c.getString(2));
                account.setUsername(c.getString(3));
                account.setPassword(c.getString(4));
                account.setPhone(c.getString(5));
                accounts.add(account);
                x++;
        }
        c.close();
        //db.close();
        return accounts;
    }

    public ArrayList<MedicalInfo> getSpecificProviders(int childID, String providerType){
        String query = "SELECT * FROM Medical WHERE ChildID = '" + childID +
                "' AND ProviderType = '" + providerType + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<MedicalInfo> medicalInfos = new ArrayList<>();
        while(c.moveToNext()){
            MedicalInfo info = new MedicalInfo();
            info.setMedicalID(c.getInt(0));
            info.setChildID(c.getInt(1));
            info.setHeight(c.getString(2));
            info.setWeight(c.getString(3));
            info.setHeadInfo(c.getString(4));
            info.setDoctorDate(c.getLong(5));
            info.setTemperatureInfo(c.getString(6));
            info.setProvider(c.getString(7));
            info.setVisit(c.getString(8));
            medicalInfos.add(info);
        }
        c.close();
        return medicalInfos;
    }

    public ArrayList<Entry> getAllEntries(){
        String query = "SELECT * FROM Entry;";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Entry> entries = new ArrayList<>();
        int x = 0;
        while(c.moveToNext()){
            Entry entry = new Entry();
            if(x == 0) {
                c.moveToFirst();
            }
                entry.setEntryText(c.getString(1));
                entry.setEntryTime(c.getLong(2));
                entry.setChildID(c.getInt(3));
                entries.add(entry);
                x++;
        }
        c.close();
        db.close();
        return entries;
    }

    public ArrayList<Child> getAllChildren(){
        String query = "SELECT * FROM Child;";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Child> children = new ArrayList<>();
        int x = 0;
        while(c.moveToNext()){
            Child child = new Child();
                child.setChildID(c.getInt(0));
                child.setFirstName(c.getString(1));
                child.setLastName(c.getString(2));
                child.setGender(c.getString(3));
                child.setBloodType(c.getString(4));
                child.setDueDate(c.getLong(5));
                child.setBirthday(c.getLong(6));
                children.add(child);
        }
        c.close();
        //db.close();
        return children;
    }

    public MedicalInfo findMedicalInfo(int medicalID){
        return null;
    }


    public boolean updateMilestone(Milestone milestone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_8[2], milestone.getRollingDate());
        values.put(COLUMN_8[3], milestone.getSittingDate());
        values.put(COLUMN_8[4], milestone.getStandingDate());
        values.put(COLUMN_8[5], milestone.getWalkingDate());
        return db.update(TABLE_NAMES[7], values, COLUMN_8[1] + "=" + milestone.getChildId(), null) > 0;

    }

}

