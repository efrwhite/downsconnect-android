package com.iso.downsconnect.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iso.downsconnect.objects.AccountHolder;
import com.iso.downsconnect.objects.Activity;
import com.iso.downsconnect.objects.Bathroom;
import com.iso.downsconnect.objects.Child;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Feed;
import com.iso.downsconnect.objects.Image;
import com.iso.downsconnect.objects.Journal;
import com.iso.downsconnect.objects.MedicalInfo;
import com.iso.downsconnect.objects.Message;
import com.iso.downsconnect.objects.Milestone;
import com.iso.downsconnect.objects.Mood;
import com.iso.downsconnect.objects.Point;
import com.iso.downsconnect.objects.Provider;
import com.iso.downsconnect.objects.Sleep;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "downsconnect.db";
    private static final int DATABASE_VERSION = 7;
    private static final String[] TABLE_NAMES = {"Account", "Child", "Feed", "Mood", "Sleep", "Entry", "Medical", "Milestone", "Bathroom", "Provider", "Activity", "Image", "Message", "Journal", "VisitInfo"};
    private static final String[] COLUMN_1 = {"AccountHolderID","FirstName", "LastName", "Username", "Password", "Phone"};
    private static final String[] COLUMN_2 = {"ChildID", "FirstName", "LastName", "Gender", "BloodType", "DueDate", "Birthday", "Allergies", "Medications"};
    private static final String[] COLUMN_3 = {"FeedID", "ChildID", "Amount", "Substance", "Notes", "FoodUnit" , "EntryTime", "Iron", "Vitamin", "Other", "EatMode"};
    private static final String[] COLUMN_4 = {"MoodID", "ChildID", "MoodType", "Time", "Notes", "Units"};
    private static final String[] COLUMN_5 = {"SleepID", "ChildID", "SleepTime", "Duration", "Snoring" ,"Medication", "Supplements", "CPAP", "Other", "Study", "Unit", "Notes", "SleepDate"};
    private static final String[] COLUMN_6 = {"EntryID", "EntryText", "EntryTime", "ChildID", "EntryType", "ForeignID"};
    private static final String[] COLUMN_7 = {"MedicalID", "ChildID", "Height", "Weight", "HeadSize", "DoctorsVisit", "Temperature", "Provider", "VisitNum", "ProviderType", "CheckAnswers", "AppointmentDates", "AppointmentProviders", "Notes"};
    private static final String[] COLUMN_8 = {"MilestoneID", "ChildID", "Roll", "Walk", "Stand", "Sit", "Crawl", "NoHandWalk", "Jump", "Holds", "HandMouth", "Passes", "Pincher", "Drinks", "Scribbles", "SpoonFeed", "Points", "Emotion", "Affection", "Interest", "Coos", "Babbles", "Speaks", "TwoWords", "Sentence", "Startles", "Turns"};
    private static final String[] COLUMN_9 = {"BathroomID", "ChildID", "BathroomType", "Treatment", "Leak", "OpenAir", "DiaperCream", "Quantity", "PottyAccident", "DateOfLastStool", "Duration"};
    private static final String[] COLUMN_10 = {"ProviderID", "ProviderName", "PracticeName", "Specialty", "Phone", "Fax", "Email", "Website", "Address", "State", "City", "Zip"};
    private static final String[] COLUMN_11 = {"ActivityID", "ChildID", "ActivityName", "EntryTime", "Duration", "DurationUnits" ,"Notes"};
    private static final String[] COLUMN_12 = {"ImageID", "ChildID", "Image"};
    private static final String[] COLUMN_13 = {"MessageID", "ChildID", "Message"};
    private static final String[] COLUMN_14 = {"JournalID", "ChildID", "Title", "Notes"};
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
                "EntryTime INTEGER, " +
                "Iron TEXT, " + "" +
                "Vitamin TEXT, " +
                "Other TEXT, " +
                "EatMode TEXT);");
        db.execSQL("CREATE TABLE Mood(" +
                "MoodID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "MoodType TEXT, " +
                "Time TEXT, " +
                "Notes TEXT, " +
                "Units TEXT);");
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
                "Notes TEXT, " +
                "SleepDate TEXT);");
        db.execSQL("CREATE TABLE Entry(" +
                "EntryID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "EntryText TEXT, " +
                "EntryTime INTEGER, " +
                "ChildID INTEGER, " +
                "EntryType TEXT, " +
                "ForeignID INTEGER)");
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
                "ProviderType TEXT, " +
                "CheckAnswers TEXT, " +
                "AppointmentDates TEXT, "+
                "AppointmentProviders TEXT, " +
                "Notes TEXT)");
        db.execSQL("CREATE TABLE Milestone(" +
                "MilestoneID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "Roll TEXT, " +
                "Walk TEXT, " +
                "Stand TEXT, " +
                "Sit TEXT, " +
                "Crawl TEXT, " +
                "NoHandWalk TEXT, " +
                "Jump TEXT, " +
                "Holds TEXT, " +
                "HandMouth TEXT, " +
                "Passes TEXT, " +
                "Pincher TEXT, " +
                "Drinks TEXT, " +
                "Scribbles TEXT, " +
                "SpoonFeed TEXT, " +
                "Points TEXT, " +
                "Emotion TEXT, " +
                "Affection TEXT, " +
                "Interest TEXT, " +
                "Coos TEXT, " +
                "Babbles TEXT, " +
                "Speaks TEXT, " +
                "TwoWords TEXT, " +
                "Sentence TEXT, " +
                "Startles TEXT, " +
                "Turns TEXT)");
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
        db.execSQL("CREATE TABLE Provider(" +
                "ProviderID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ProviderName TEXT, " +
                "PracticeName TEXT, " +
                "Specialty TEXT, " +
                "Phone TEXT, " +
                "Fax TEXT, " +
                "Email TEXT, " +
                "Website TEXT, " +
                "Address TEXT, " +
                "State TEXT, " +
                "City TEXT, " +
                "Zip TEXT)");
        db.execSQL("CREATE TABLE Activity("+
                "ActivityID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "ActivityName TEXT, " +
                "EntryTime INTEGER, " +
                "Duration TEXT, " +
                "DurationUnits TEXT, " +
                "Notes TEXT)");
        db.execSQL("CREATE TABLE Image(" +
                "ImageID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "Image BLOB)");
        db.execSQL("CREATE TABLE Message(" +
                "MessageID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "Message TEXT)");
        db.execSQL("CREATE TABLE Journal(" +
                "JournalID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " +
                "Title TEXT, " +
                "Notes TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
        }
        if(newVersion == 4){
            db.execSQL("ALTER TABLE Feed ADD COLUMN EatMode TEXT");
        }
        if(newVersion == 5){
            db.execSQL("ALTER TABLE Sleep ADD COLUMN SleepDate TEXT");
        }
        if(newVersion == 6){
            db.execSQL("DROP TABLE Sleep");
        }
        if(newVersion == 7){
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
                    "Notes TEXT, " +
                    "SleepDate TEXT);");
        }
    }

    public void deleteEntry(int id, String table){
        for(String name : TABLE_NAMES){
            if(name.equals(table)){
                String query = "DELETE FROM " + table + " WHERE " + table + "ID = " + id + ";";
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(query);
            }
        }
    }

    public long addAccount(AccountHolder accountHolder){
        ContentValues values = new ContentValues();
        values.put(COLUMN_1[1], accountHolder.getFirstName());
        values.put(COLUMN_1[2], accountHolder.getLastName());
        values.put(COLUMN_1[3], accountHolder.getUsername());
        values.put(COLUMN_1[4], accountHolder.getPassword());
        values.put(COLUMN_1[5], accountHolder.getPhone());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[0], null, values);
        db.close();
        return result;
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

    public long addFeed(Feed feed){
        ContentValues values = new ContentValues();
        values.put(COLUMN_3[1], feed.getChildID());
        values.put(COLUMN_3[2], feed.getAmount());
        values.put(COLUMN_3[3], feed.getSubstance());
        values.put(COLUMN_3[4], feed.getNotes());
        values.put(COLUMN_3[5], feed.getFoodUnit());
        values.put(COLUMN_3[6], feed.getEntryTime());
        values.put(COLUMN_3[7], feed.getIron());
        values.put(COLUMN_3[8], feed.getVitamin());
        values.put(COLUMN_3[9], feed.getOther());
        values.put(COLUMN_3[10], feed.getFeedMode());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[2], null, values);
        db.close();
        return result;
    }


    public long addMood(Mood mood){
        ContentValues values = new ContentValues();
        values.put(COLUMN_4[1], mood.getMoodID());
        values.put(COLUMN_4[2], mood.getMoodType());
        values.put(COLUMN_4[3], mood.getTime());
        values.put(COLUMN_4[4], mood.getNotes());
        values.put(COLUMN_4[5], mood.getUnits());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[3], null, values);
        db.close();
        return result;
    }

    public long addSleep(Sleep sleep){
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
        values.put(COLUMN_5[12], sleep.getSleepDate());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[4], null, values);
        db.close();
        return result;
    }

    public long addEntry(Entry entry){
        ContentValues values = new ContentValues();
        values.put(COLUMN_6[1], entry.getEntryText());
        values.put(COLUMN_6[2], entry.getEntryTime());
        values.put(COLUMN_6[3], entry.getChildID());
        values.put(COLUMN_6[4], entry.getEntryType());
        values.put(COLUMN_6[5], entry.getForeignID());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[5], null, values);
        db.close();
        return result;
    }

    public long addMilestone(Milestone milestone){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(COLUMN_8[1], milestone.getChildId());
        values.put(COLUMN_8[2], milestone.getRoll());
        values.put(COLUMN_8[3], milestone.getWalk());
        values.put(COLUMN_8[4], milestone.getStand());
        values.put(COLUMN_8[5], milestone.getSit());
        values.put(COLUMN_8[6], milestone.getCrawl());
        values.put(COLUMN_8[7], milestone.getNh_walk());
        values.put(COLUMN_8[8], milestone.getJump());
        values.put(COLUMN_8[9], milestone.getHolds());
        values.put(COLUMN_8[10], milestone.getHands_mouth());
        values.put(COLUMN_8[11], milestone.getPasses());
        values.put(COLUMN_8[12], milestone.getPincher());
        values.put(COLUMN_8[13], milestone.getDrinks());
        values.put(COLUMN_8[14], milestone.getScribbles());
        values.put(COLUMN_8[15], milestone.getFeed_spoon());
        values.put(COLUMN_8[16], milestone.getPoints());
        values.put(COLUMN_8[17], milestone.getEmotion());
        values.put(COLUMN_8[18], milestone.getAffection());
        values.put(COLUMN_8[19], milestone.getInterest());
        values.put(COLUMN_8[20], milestone.getCoos());
        values.put(COLUMN_8[21], milestone.getBabbles());
        values.put(COLUMN_8[22], milestone.getSpeaks());
        values.put(COLUMN_8[23], milestone.getTwo_word());
        values.put(COLUMN_8[24], milestone.getSentence());
        values.put(COLUMN_8[25], milestone.getStartles());
        values.put(COLUMN_8[26], milestone.getTurns());
        long result = db.insert(TABLE_NAMES[7], null, values);
        db.close();
        return result;
    }

    public long addMedical(MedicalInfo medicalInfo){
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
        values.put(COLUMN_7[10], medicalInfo.getAnswers());
        values.put(COLUMN_7[11], medicalInfo.getDates());
        values.put(COLUMN_7[12], medicalInfo.getProviders());
        values.put(COLUMN_7[13], medicalInfo.getNotes());

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[6], null, values);
        db.close();
        return result;
    }

    public long addBathroom(Bathroom bathroom){
        ContentValues values = new ContentValues();
        values.put(COLUMN_9[1], bathroom.getChildID());
        values.put(COLUMN_9[2], bathroom.getBathroomType());
        values.put(COLUMN_9[3], bathroom.getTreatmentPlan());
        values.put(COLUMN_9[4], bathroom.getLeak());
        values.put(COLUMN_9[5], bathroom.getOpenAir());
        values.put(COLUMN_9[6], bathroom.getDiaperCream());
        values.put(COLUMN_9[7], bathroom.getQuantity());
        values.put(COLUMN_9[8], bathroom.getPottyAccident());
        values.put(COLUMN_9[9], bathroom.getDateOfLastStool());
        values.put(COLUMN_9[10], bathroom.getDuration());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[8], null, values);
        db.close();
        return result;

    }

    public boolean addProvider(Provider provider){
        ContentValues values = new ContentValues();
        values.put(COLUMN_10[1], provider.getName());
        values.put(COLUMN_10[2], provider.getPrac_name());
        values.put(COLUMN_10[3], provider.getSpecialty());
        values.put(COLUMN_10[4], provider.getPhone());
        values.put(COLUMN_10[5], provider.getFax());
        values.put(COLUMN_10[6], provider.getEmail());
        values.put(COLUMN_10[7], provider.getWebsite());
        values.put(COLUMN_10[8], provider.getAddress());
        values.put(COLUMN_10[9], provider.getState());
        values.put(COLUMN_10[10], provider.getCity());
        values.put(COLUMN_10[11], provider.getZip());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[9], null, values);
        db.close();
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public long addActivity(Activity activity){
        ContentValues values = new ContentValues();
        values.put(COLUMN_11[1], activity.getChildID());
        values.put(COLUMN_11[2], activity.getChildActivity());
        values.put(COLUMN_11[3], activity.getEntryTime());
        values.put(COLUMN_11[4], activity.getDuration());
        values.put(COLUMN_11[5], activity.getUnits());
        values.put(COLUMN_11[6], activity.getNotes());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[10], null, values);
        db.close();
        return result;
    }

    public boolean addImage(Image image){
        ContentValues values = new ContentValues();
        values.put(COLUMN_12[1], image.getChildID());
        values.put(COLUMN_12[2], image.getImage());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[11], null, values);
        db.close();
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public long addMessage(Message message){
        ContentValues values = new ContentValues();
        values.put(COLUMN_13[1], message.getChildID());
        values.put(COLUMN_13[2], message.getMessage());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[12], null, values);
        db.close();
        return result;
    }

    public long addJournal(Journal journal){
        ContentValues values = new ContentValues();
        values.put(COLUMN_14[1], journal.getChildID());
        values.put(COLUMN_14[2], journal.getTitle());
        values.put(COLUMN_14[3], journal.getNotes());
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAMES[13], null, values);
        db.close();
        db.close();
        return result;
    }

    public AccountHolder getAccount(String user){
        String query = "SELECT * FROM Account WHERE Username = '" + user + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        AccountHolder account = new AccountHolder();
        if(c.moveToFirst()){
            c.moveToFirst();
            account.setAccountID(c.getInt(0));
            account.setFirstName(c.getString(1));
            account.setLastName(c.getString(2));
            account.setUsername(c.getString(3));
            account.setPassword(c.getString(4));
            account.setPhone(c.getString(5));
        }
        else{
            c.close();
            account = null;
        }
        db.close();
        return account;
    }

    public AccountHolder getAccountWithName(String fName){
        String query = "SELECT * FROM Account WHERE FirstName = '" + fName + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        AccountHolder account = new AccountHolder();
        if(c.moveToFirst()){
            c.moveToFirst();
            account.setAccountID(c.getInt(0));
            account.setFirstName(c.getString(1));
            account.setLastName(c.getString(2));
            account.setUsername(c.getString(3));
            account.setPassword(c.getString(4));
            account.setPhone(c.getString(5));
        }
        else{
            c.close();
            account = null;
        }
        db.close();
        return account;
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

    public String getAllergies(int id){
        String query = "SELECT * FROM Child WHERE ChildID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        String allergies;
        if(c.moveToFirst()){
            c.moveToFirst();
            allergies = c.getString(7);
        }
        else{
            c.close();
            allergies = null;
        }
        db.close();
        return allergies;
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

    public String getPassword(Long userID){
        String query = "SELECT Password FROM Account WHERE AccountHolderID = '" + userID + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        String password;
        if(c.moveToFirst()){
            c.moveToFirst();
            password = c.getString(0);
        }
        else{
            c.close();
            password = null;
        }
        db.close();
        return password;
    }

    public Bathroom getBathroom(int childID){
        String query = "SELECT * FROM Bathroom WHERE BathroomID = '" + childID + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        Bathroom bathroom = new Bathroom();
        if(c.moveToFirst()){
            c.moveToFirst();
            bathroom.setBathroomID(c.getInt(0));
            bathroom.setChildID(c.getInt(1));
            bathroom.setBathroomType(c.getString(2));
            bathroom.setTreatmentPlan(c.getString(3));
            bathroom.setLeak(c.getString(4));
            bathroom.setOpenAir(c.getString(5));
            bathroom.setDiaperCream(c.getString(6));
            bathroom.setQuantity(c.getString(7));
            bathroom.setPottyAccident(c.getString(8));
            bathroom.setDateOfLastStool(c.getLong(9));
            bathroom.setDuration(c.getString(10));
        }
        else{
            c.close();
            bathroom = null;
        }
        db.close();
        return bathroom;
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
            milestone.setRoll(c.getString(2));
            milestone.setWalk(c.getString(3));
            milestone.setStand(c.getString(4));
            milestone.setSit(c.getString(5));
            milestone.setCrawl(c.getString(6));
            milestone.setNh_walk(c.getString(7));
            milestone.setJump(c.getString(8));
            milestone.setHolds(c.getString(9));
            milestone.setHands_mouth(c.getString(10));
            milestone.setPasses(c.getString(11));
            milestone.setPincher(c.getString(12));
            milestone.setDrinks(c.getString(13));
            milestone.setScribbles(c.getString(14));
            milestone.setFeed_spoon(c.getString(15));
            milestone.setPoints(c.getString(16));
            milestone.setEmotion(c.getString(17));
            milestone.setAffection(c.getString(18));
            milestone.setInterest(c.getString(19));
            milestone.setCoos(c.getString(20));
            milestone.setBabbles(c.getString(21));
            milestone.setSpeaks(c.getString(22));
            milestone.setTwo_word(c.getString(23));
            milestone.setSentence(c.getString(24));
            milestone.setStartles(c.getString(25));
            milestone.setTurns(c.getString(26));
        }
        else{
            c.close();
            milestone = null;
        }
        db.close();
        return milestone;
    }

    public Provider getProvider(int providerID){
        String query = "SELECT * FROM Provider WHERE ProviderID = '" + providerID + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        Provider provider = new Provider();
        if(c.moveToFirst()){
            c.moveToFirst();
            provider.setProviderID(c.getInt(0));
            provider.setName(c.getString(1));
            provider.setPrac_name(c.getString(2));
            provider.setSpecialty(c.getString(3));
            provider.setPhone(c.getString(4));
            provider.setFax(c.getString(5));
            provider.setEmail(c.getString(6));
            provider.setWebsite(c.getString(7));
            provider.setAddress(c.getString(8));
            provider.setState(c.getString(9));
            provider.setCity(c.getString(10));
            provider.setZip(c.getString(11));
        }
        else{
            c.close();
            provider = null;
        }
        db.close();
        return provider;
    }

    public Feed getFeed(int id){
        String query = "SELECT * FROM Feed WHERE FeedID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        Feed feed = new Feed();
        if(c.moveToFirst()) {
            c.moveToFirst();
            feed.setFeedID(c.getInt(0));
            feed.setChildID(c.getInt(1));
            feed.setAmount(c.getInt(2));
            feed.setSubstance(c.getString(3));
            feed.setNotes(c.getString(4));
            feed.setFoodUnit(c.getString(5));
            feed.setEntryTime(c.getLong(6));
            feed.setIron(c.getString(7));
            feed.setVitamin(c.getString(8));
            feed.setOther(c.getString(9));
            feed.setFeedMode(c.getString(10));
        }
        else{
            c.close();
            feed = null;
        }
        db.close();
        return feed;
    }

    public Activity getActivity(int id){
        String query = "SELECT * FROM Activity WHERE ActivityID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        Activity activity = new Activity();
        if(c.moveToFirst()){
            c.moveToFirst();
            activity.setActivityID(c.getInt(0));
            activity.setChildID(c.getInt(1));
            activity.setChildActivity(c.getString(2));
            activity.setEntryTime(c.getLong(3));
            activity.setDuration(c.getString(4));
            activity.setUnits(c.getString(5));
            activity.setNotes(c.getString(6));
        }
        else{
            c.close();
            activity = null;
        }
        db.close();
        return activity;
    }

    public Sleep getSleep(int id){
        String query = "SELECT * FROM Sleep WHERE SleepID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        Sleep sleep = new Sleep();
        if(c.moveToFirst()){
            c.moveToFirst();
            sleep.setSleepID(c.getInt(0));
            sleep.setChildID(c.getInt(1));
            sleep.setSleepTime(c.getLong(2));
            sleep.setDuration(c.getInt(3));
            sleep.setSnoring(c.getString(4));
            sleep.setMedication(c.getString(5));
            sleep.setSupplements(c.getString(6));
            sleep.setCPAP(c.getString(7));
            sleep.setOther(c.getString(8));
            sleep.setStudy(c.getString(9));
            sleep.setUnit(c.getString(10));
            sleep.setNotes(c.getString(11));
            sleep.setSleepDate(c.getString(12));
        }
        else{
            c.close();
            sleep = null;
        }
        db.close();
        return sleep;
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
            info.setAnswers(c.getString(10));
            info.setDates(c.getString(11));
            info.setProviders(c.getString(12));
            info.setNotes(c.getString(13));
        }
        else{
            c.close();
            info = null;
        }
        db.close();
        return info;
    }

    public ArrayList<Point> getHeight(int childID){
        String query = "SELECT * FROM Medical WHERE ChildID = '" + childID +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Point> data = new ArrayList<>();
        int i = 0;
        while(c.moveToNext()){
            Point dataPoint = new Point();
            String text = c.getString(2);
            dataPoint.setX(i);
            dataPoint.setY(Float.parseFloat(text.substring(0, text.indexOf(" "))));
            dataPoint.setUnit(text.substring(text.indexOf(" ")));
            data.add(dataPoint);
            i++;
        }
        return data;
    }

    public String checkUsername(String user){
        String query = "SELECT * FROM Account WHERE UserName = '" + user + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        String username = null;
        if(c.moveToFirst()){
            c.moveToFirst();
            username = c.getString(3);
        }
        if(username.equals(null)){
            return "y";
        }
        else{
            return "n";
        }
    }

    public ArrayList<Point> getWeight(int childID){
        String query = "SELECT * FROM Medical WHERE ChildID = '" + childID +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Point> data = new ArrayList<>();
        int i = 0;
        while(c.moveToNext()){
            Point dataPoint = new Point();
            String text = c.getString(3);
            dataPoint.setX(i);
            dataPoint.setY(Float.parseFloat(text.substring(0, text.indexOf(" "))));
            dataPoint.setUnit(text.substring(text.indexOf(" ")));
            data.add(dataPoint);
            i++;
        }
        return data;
    }

    public ArrayList<Point> getHeadSizes(int childID){
        String query = "SELECT * FROM Medical WHERE ChildID = '" + childID +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Point> data = new ArrayList<>();
        int i = 0;
        while(c.moveToNext()){
            Point dataPoint = new Point();
            String text = c.getString(4);
            dataPoint.setX(i);
            dataPoint.setY(Float.parseFloat(text.substring(0, text.indexOf(" "))));
            dataPoint.setUnit(text.substring(text.indexOf(" ")));
            data.add(dataPoint);
            i++;
        }
        return data;
    }

    public ArrayList<Image> getChildVaccines(int childID){
        String query = "SELECT * FROM Image WHERE ChildID = '" + childID +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Image> images = new ArrayList<>();
        while(c.moveToNext()){
            Image image = new Image();
            image.setImageID(c.getInt(0));
            image.setChildID(c.getInt(1));
            image.setImage(c.getBlob(2));
            images.add(image);
        }
        return images;
    }

    public Message getMessage(int id){
        String query = "SELECT * FROM Message WHERE MessageID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        Message message = new Message();
        if(c.moveToFirst()){
            c.moveToFirst();
            message.setMessageID(c.getInt(0));
            message.setChildID(c.getInt(1));
            message.setMessage(c.getString(2));
        }
        else{
            c.close();
            message = null;
        }
        db.close();
        return message;
    }

    public Journal getJournal(int id){
        String query = "SELECT * FROM Journal WHERE JournalID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        Journal journal = new Journal();
        if(c.moveToFirst()){
            c.moveToFirst();
            journal.setJournalID(c.getInt(0));
            journal.setChildID(c.getInt(1));
            journal.setTitle(c.getString(2));
            journal.setNotes(c.getString(3));
        }
        else{
            c.close();
            journal = null;
        }
        db.close();
        return journal;
    }

    public Mood getMood(int id){
        String query = "SELECT * FROM Mood WHERE MoodID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        Mood mood = new Mood();
        if(c.moveToFirst()) {
            c.moveToFirst();
            mood.setMoodID(c.getInt(0));
            mood.setChildID(c.getInt(1));
            mood.setMoodType(c.getString(2));
            mood.setTime(c.getString(3));
            mood.setNotes(c.getString(4));
            mood.setUnits(c.getString(5));
        }
            else{
                c.close();
                mood = null;
            }
            db.close();
            return mood;
        }

   public AccountHolder getAccount(String user, String pass){
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
                entry.setEntryID(c.getInt(0));
                entry.setEntryText(c.getString(1));
                entry.setEntryTime(c.getLong(2));
                entry.setChildID(c.getInt(3));
                entry.setEntryType(c.getString(4));
                entry.setForeignID(c.getInt(5));
                entries.add(entry);
                x++;
        }
        c.close();
        db.close();
        return entries;
    }

    public <T> ArrayList<T> getSpecificEntries(String type){
        String query = "SELECT * FROM Entry WHERE EntryType = '" + type + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<T> items = new ArrayList<>();
        switch(type){
            case "Message":
                ArrayList<Message> messages = new ArrayList<>();
                while(c.moveToNext()){
                    items.add((T) getMessage(c.getInt(0)));
                }
                return items;
        }
        return null;
    }

    public ArrayList<Entry> getListing(String type, int childID){
        String query = "SELECT * FROM Entry WHERE EntryType = '" + type +
               "' AND ChildID = '" + childID + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Entry> entries = new ArrayList<>();
        while(c.moveToNext()){
            Entry entry = new Entry();
            entry.setEntryID(c.getInt(0));
            entry.setEntryText(c.getString(1));
            entry.setEntryTime(c.getLong(2));
            entry.setChildID(c.getInt(3));
            entry.setEntryType(c.getString(4));
            entry.setForeignID(c.getInt(5));
            entries.add(entry);
        }
        c.close();
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
     public ArrayList<Provider> getAllProviders(){
        String query = "SELECT * FROM Provider;";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Provider> providers = new ArrayList<>();
        while(c.moveToNext()){
            Provider provider = new Provider();
            provider.setProviderID(c.getInt(0));
            provider.setName(c.getString(1));
            provider.setPrac_name(c.getString(2));
            provider.setSpecialty(c.getString(3));
            provider.setPhone(c.getString(4));
            provider.setFax(c.getString(5));
            provider.setEmail(c.getString(6));
            provider.setWebsite(c.getString(7));
            provider.setAddress(c.getString(8));
            provider.setState(c.getString(9));
            provider.setCity(c.getString(10));
            provider.setZip(c.getString(11));
            providers.add(provider);
        }
        c.close();
        return providers;
     }

     public ArrayList<Activity> getAllActivities(int childID){
         String query = "SELECT * FROM Activity WHERE ChildID = '" + childID +"';";
         SQLiteDatabase db = getWritableDatabase();
         Cursor c = db.rawQuery(query, null);
         ArrayList<Activity> activities = new ArrayList<>();
         while(c.moveToNext()){
             Activity activity = new Activity();
             activity.setActivityID(c.getInt(0));
             activity.setChildID(c.getInt(1));
             activity.setChildActivity(c.getString(2));
             activity.setEntryTime(c.getLong(3));
             activity.setDuration(c.getString(4));
             activity.setUnits(c.getString(5));
             activity.setNotes(c.getString(6));
             activities.add(activity);
         }
         c.close();
         return activities;
    }

    public ArrayList<Mood> getAllMoods() {
        String query = "SELECT * FROM Mood;";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Mood> moods = new ArrayList<>();

        int x = 0;
        while (c.moveToNext()) {
            Mood mood = new Mood();
            mood.setMoodID(c.getInt(0));
            mood.setChildID(c.getInt(1));
            mood.setMoodType(c.getString(2));
            mood.setTime(c.getString(3));
            mood.setNotes(c.getString(4));
            mood.setUnits(c.getString(5));
            moods.add(mood);
        }
        c.close();
        return moods;
    }

    public ArrayList<Message> getAllMessages(){
        String query = "SELECT * FROM Message;";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Message> messages = new ArrayList<>();

        while(c.moveToNext()){
            Message message = new Message();
            message.setMessageID(c.getInt(0));
            message.setChildID(c.getInt(1));
            message.setMessage(c.getString(2));
            messages.add(message);
        }
        c.close();
        return messages;
    }

    public ArrayList<Journal> getAllJournals(){
        String query = "SELECT * FROM Journal;";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<Journal> journals = new ArrayList<>();

        while(c.moveToNext()){
            Journal journal = new Journal();
            journal.setJournalID(c.getInt(0));
            journal.setChildID(c.getInt(1));
            journal.setTitle(c.getString(2));
            journal.setNotes(c.getString(3));
            journals.add(journal);
        }
        c.close();
        return journals;
    }

    public ArrayList<MedicalInfo> getAllMedical(){
        String query = "SELECT * FROM Medical;";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        ArrayList<MedicalInfo> medicalInfos = new ArrayList<>();

        while(c.moveToNext()){
            MedicalInfo medicalInfo = new MedicalInfo();
            medicalInfo.setMedicalID(c.getInt(0));
            medicalInfo.setChildID(c.getInt(1));
            medicalInfo.setHeight(c.getString(2));
            medicalInfo.setWeight(c.getString(3));
            medicalInfo.setHeadInfo(c.getString(4));
            medicalInfo.setDoctorDate(c.getLong(5));
            medicalInfo.setTemperatureInfo(c.getString(6));
            medicalInfo.setProvider(c.getString(7));
            medicalInfo.setVisit(c.getString(8));
            medicalInfo.setProviderType(c.getString(9));
            medicalInfo.setAnswers(c.getString(10));
            medicalInfo.setDates(c.getString(11));
            medicalInfo.setProviders(c.getString(12));
            medicalInfo.setNotes(c.getString(13));
            medicalInfos.add(medicalInfo);
        }

        c.close();
        return medicalInfos;
    }

    public int[] calculateSleepCycle(String date, int childID){
        int[] times = new int[2];
        String query = "SELECT Duration, Unit FROM Sleep WHERE ChildID = '" + childID +"' AND " +
                "SleepDate = '" + date +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        int hours = 0;
        int minutes = 0;
        while(c.moveToNext()){
            int duration = c.getInt(0);
            String units = c.getString(1);
            if(units.equals("mins")){
                minutes += duration;
            }
            if(units.equals("hrs")){
                hours += duration;
            }
        }
        if(minutes > 60){
            int moreHours = minutes / 60;
            int remainder = minutes % 60;
            hours += moreHours;
            minutes = remainder;
        }
        times[0] = hours;
        times[1] = minutes;

        return times;
    }

    public boolean updateMilestone(Milestone milestone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_8[2], milestone.getRoll());
        values.put(COLUMN_8[3], milestone.getWalk());
        values.put(COLUMN_8[4], milestone.getStand());
        values.put(COLUMN_8[5], milestone.getSit());
        values.put(COLUMN_8[6], milestone.getCrawl());
        values.put(COLUMN_8[7], milestone.getNh_walk());
        values.put(COLUMN_8[8], milestone.getJump());
        values.put(COLUMN_8[9], milestone.getHolds());
        values.put(COLUMN_8[10], milestone.getHands_mouth());
        values.put(COLUMN_8[11], milestone.getPasses());
        values.put(COLUMN_8[12], milestone.getPincher());
        values.put(COLUMN_8[13], milestone.getDrinks());
        values.put(COLUMN_8[14], milestone.getScribbles());
        values.put(COLUMN_8[15], milestone.getFeed_spoon());
        values.put(COLUMN_8[16], milestone.getPoints());
        values.put(COLUMN_8[17], milestone.getEmotion());
        values.put(COLUMN_8[18], milestone.getAffection());
        values.put(COLUMN_8[19], milestone.getInterest());
        values.put(COLUMN_8[20], milestone.getCoos());
        values.put(COLUMN_8[21], milestone.getBabbles());
        values.put(COLUMN_8[22], milestone.getSpeaks());
        values.put(COLUMN_8[23], milestone.getTwo_word());
        values.put(COLUMN_8[24], milestone.getSentence());
        values.put(COLUMN_8[25], milestone.getStartles());
        values.put(COLUMN_8[26], milestone.getTurns());
        return db.update(TABLE_NAMES[7], values, COLUMN_8[1] + "=" + milestone.getChildId(), null) > 0;
    }

    public boolean updateChild(Child child){
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
        return db.update(TABLE_NAMES[1], values, COLUMN_2[0] + "=" + child.getChildID(), null) > 0;
    }

    public boolean updateProvider(Provider provider){
        ContentValues values = new ContentValues();
        values.put(COLUMN_10[1], provider.getName());
        values.put(COLUMN_10[2], provider.getPrac_name());
        values.put(COLUMN_10[3], provider.getSpecialty());
        values.put(COLUMN_10[4], provider.getPhone());
        values.put(COLUMN_10[5], provider.getFax());
        values.put(COLUMN_10[6], provider.getEmail());
        values.put(COLUMN_10[7], provider.getWebsite());
        values.put(COLUMN_10[8], provider.getAddress());
        values.put(COLUMN_10[9], provider.getState());
        values.put(COLUMN_10[10], provider.getCity());
        values.put(COLUMN_10[11], provider.getZip());
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_NAMES[9], values, COLUMN_10[0] + "=" + provider.getProviderID(), null) > 0;
    }

    public boolean updateAccount(AccountHolder accountHolder){
        ContentValues values = new ContentValues();
        values.put(COLUMN_1[1], accountHolder.getFirstName());
        values.put(COLUMN_1[2], accountHolder.getLastName());
        values.put(COLUMN_1[3], accountHolder.getUsername());
        values.put(COLUMN_1[4], accountHolder.getPassword());
        values.put(COLUMN_1[5], accountHolder.getPhone());
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_NAMES[0], values, COLUMN_1[0] + "=" + accountHolder.getAccountID(), null) > 0;
    }

}

