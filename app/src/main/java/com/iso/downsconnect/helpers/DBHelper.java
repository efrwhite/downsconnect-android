package com.iso.downsconnect.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.iso.downsconnect.objects.AccountHolder;
import com.iso.downsconnect.objects.Activity;
import com.iso.downsconnect.objects.Bathroom;
import com.iso.downsconnect.objects.Child;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Feed;
import com.iso.downsconnect.objects.Image;
import com.iso.downsconnect.objects.Journal;
import com.iso.downsconnect.objects.MedicalInfo;
import com.iso.downsconnect.objects.Medication;
import com.iso.downsconnect.objects.Message;
import com.iso.downsconnect.objects.Milestone;
import com.iso.downsconnect.objects.Mood;
import com.iso.downsconnect.objects.Point;
import com.iso.downsconnect.objects.Provider;
import com.iso.downsconnect.objects.Resource;
import com.iso.downsconnect.objects.Sleep;

import java.sql.SQLInput;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "downsconnect.db";
    //database version
    //you'll want to increase this number any time you make changes to the database so the onUpgrade method gets triggered
    private static final int DATABASE_VERSION = 10;
    //defines the names of all the tables in the database
    private static final String[] TABLE_NAMES = {"Account", "Child", "Feed", "Mood", "Sleep", "Entry", "Medical", "Milestone", "Bathroom", "Provider", "Activity", "Image", "Message", "Journal", "Med", "Resource"};
    //defines the column names in each table
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
    private static final String[] COLUMN_15 = {"MedID", "ChildID", "MedName", "MedDosage", "MedDosageUnits", "MedFrequency", "MedReason"};
    private static final String[] COLUMN_16 = {"ResourceID", "Name", "URL"};

    //constructor for create a dbhelper object
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //schema for creating tables
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
                "ChildID INTEGER, " + //Foreign Key
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
                "ChildID INTEGER, " + //Foreign Key
                "MoodType TEXT, " +
                "Time TEXT, " +
                "Notes TEXT, " +
                "Units TEXT);");
        db.execSQL("CREATE TABLE Sleep(" +
                "SleepID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " + //Foreign Key
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
                "ChildID INTEGER, " + //Foreign Key
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
                "ChildID INTEGER, " + //Foreign Key
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
                "ChildID INTEGER, " + //Foreign Key
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
                "ChildID INTEGER, " + //Foreign Key
                "ActivityName TEXT, " +
                "EntryTime INTEGER, " +
                "Duration TEXT, " +
                "DurationUnits TEXT, " +
                "Notes TEXT)");
        db.execSQL("CREATE TABLE Image(" +
                "ImageID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " + //Foreign Key
                "Image BLOB)");
        db.execSQL("CREATE TABLE Message(" +
                "MessageID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " + //Foreign Key
                "Message TEXT)");
        db.execSQL("CREATE TABLE Journal(" +
                "JournalID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER, " + //Foreign Key
                "Title TEXT, " +
                "Notes TEXT)");
        db.execSQL("CREATE TABLE Med(" +
                "MedID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ChildID INTEGER REFERENCES Child(ChildID) ON UPDATE CASCADE," + //Foreign key
                "MedName TEXT," +
                "MedDosage NUMBER," +
                "MedDosageUnits TEXT," +
                "MedFrequency TEXT," +
                "MedReason TEXT)");
        db.execSQL("CREATE TABLE Resource(" +
                "ResourceID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "Name TEXT, " +
                "URL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //changes made when database needed to be upgraded
        //put any new tables or changes to existing tables in this function so they are running
        //when the database version changes.
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
        if(newVersion == 8){
            db.execSQL("CREATE TABLE Med(" +
                    "MedID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "ChildID INTEGER REFERENCES Child(ChildID) ON UPDATE CASCADE," +
                    "MedName TEXT," +
                    "MedDosage NUMBER," +
                    "MedDosageUnits TEXT," +
                    "MedFrequency TEXT," +
                    "MedReason TEXT)");
        }
        if(newVersion == 9){
            String newName = "Med";
            db.execSQL("ALTER TABLE " + TABLE_NAMES[14] + " RENAME TO " + newName);
        }
        if(newVersion == 10){
            db.execSQL("CREATE TABLE Resource(" +
                    "ResourceID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "Name TEXT, " +
                    "URL TEXT)");
        }
    }

    //deletes an entry from it's corresponding table
    public void deleteEntry(int id, String table){
        for(String name : TABLE_NAMES){
            if(name.equals(table)){
                String query = "";
                //If account, put in proper names for variables
                if (table == "Account") {
                    query = "DELETE FROM Account WHERE AccountHolderID = " + id + ";";
                }
                else {
                    query = "DELETE FROM " + table + " WHERE " + table + "ID = " + id + ";";
                }
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(query);
            }
        }
    }

    /*
    * Add(insert table name) functions take the input in the object parameter passed in
    * and inserts it into the database
     */
    public long addAccount(AccountHolder accountHolder){
        //object that will contain the values from the object for insertion into the db
        ContentValues values = new ContentValues();
        values.put(COLUMN_1[1], accountHolder.getFirstName());
        values.put(COLUMN_1[2], accountHolder.getLastName());
        values.put(COLUMN_1[3], accountHolder.getUsername());
        values.put(COLUMN_1[4], accountHolder.getPassword());
        values.put(COLUMN_1[5], accountHolder.getPhone());
        SQLiteDatabase db = this.getWritableDatabase();
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[0], null, values);
        db.close();
        //returns the id of the entry that was just inserted
        return result;
    }

    public boolean addChild(Child child){
        //object that will contain the values from the object for insertion into the db
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
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[1], null, values);
        db.close();
        //check if child entry was inserted or not
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public long addFeed(Feed feed){
        //object that will contain the values from the object for insertion into the db
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
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[2], null, values);
        db.close();
        //returns the id of the entry that was just inserted
        return result;
    }


    public long addMood(Mood mood){
        //object that will contain the values from the object for insertion into the db
        ContentValues values = new ContentValues();
        values.put(COLUMN_4[1], mood.getMoodID());
        values.put(COLUMN_4[2], mood.getMoodType());
        values.put(COLUMN_4[3], mood.getTime());
        values.put(COLUMN_4[4], mood.getNotes());
        values.put(COLUMN_4[5], mood.getUnits());
        SQLiteDatabase db = this.getWritableDatabase();
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[3], null, values);
        db.close();
        //returns the id of the entry that was just inserted
        return result;
    }

    public long addSleep(Sleep sleep){
        //object that will contain the values from the object for insertion into the db
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
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[4], null, values);
        db.close();
        //returns the id of the entry that was just inserted
        return result;
    }

    public long addEntry(Entry entry){
        //object that will contain the values from the object for insertion into the db
        ContentValues values = new ContentValues();
        values.put(COLUMN_6[1], entry.getEntryText());
        values.put(COLUMN_6[2], entry.getEntryTime());
        values.put(COLUMN_6[3], entry.getChildID());
        values.put(COLUMN_6[4], entry.getEntryType());
        values.put(COLUMN_6[5], entry.getForeignID());
        SQLiteDatabase db = this.getWritableDatabase();
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[5], null, values);
        db.close();
        //returns the id of the entry that was just inserted
        return result;
    }

    public long addMilestone(Milestone milestone){
        //object that will contain the values from the object for insertion into the db
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
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[7], null, values);
        db.close();
        //returns the id of the entry that was just inserted
        return result;
    }

    public long addMedical(MedicalInfo medicalInfo){
        //object that will contain the values from the object for insertion into the db
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
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[6], null, values);
        db.close();
        //returns the id of the entry that was just inserted
        return result;
    }

    public long addBathroom(Bathroom bathroom){
        //object that will contain the values from the object for insertion into the db
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
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[8], null, values);
        db.close();
        //returns the id of the entry that was just inserted
        return result;

    }

    public boolean addProvider(Provider provider){
        //object that will contain the values from the object for insertion into the db
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
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[9], null, values);
        db.close();
        //check if provider entry was inserted or not
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public long addActivity(Activity activity){
        //object that will contain the values from the object for insertion into the db
        ContentValues values = new ContentValues();
        values.put(COLUMN_11[1], activity.getChildID());
        values.put(COLUMN_11[2], activity.getChildActivity());
        values.put(COLUMN_11[3], activity.getEntryTime());
        values.put(COLUMN_11[4], activity.getDuration());
        values.put(COLUMN_11[5], activity.getUnits());
        values.put(COLUMN_11[6], activity.getNotes());
        SQLiteDatabase db = this.getWritableDatabase();
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[10], null, values);
        db.close();
        //returns the id of the entry that was just inserted
        return result;
    }

    public boolean addImage(Image image){
        //object that will contain the values from the object for insertion into the db
        ContentValues values = new ContentValues();
        values.put(COLUMN_12[1], image.getChildID());
        values.put(COLUMN_12[2], image.getImage());
        SQLiteDatabase db = this.getWritableDatabase();
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[11], null, values);
        db.close();
        //check if image entry was inserted or not
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public long addMessage(Message message){
        //object that will contain the values from the object for insertion into the db
        ContentValues values = new ContentValues();
        values.put(COLUMN_13[1], message.getChildID());
        values.put(COLUMN_13[2], message.getMessage());
        SQLiteDatabase db = this.getWritableDatabase();
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[12], null, values);
        db.close();
        //returns the id of the entry that was just inserted
        return result;
    }

    public long addJournal(Journal journal){
        //object that will contain the values from the object for insertion into the db
        ContentValues values = new ContentValues();
        values.put(COLUMN_14[1], journal.getChildID());
        values.put(COLUMN_14[2], journal.getTitle());
        values.put(COLUMN_14[3], journal.getNotes());
        SQLiteDatabase db = this.getWritableDatabase();
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[13], null, values);
        db.close();
        //returns the id of the entry that was just inserted
        return result;
    }

    public long addMedication(Medication med){
        //object that will contain the values from the object for insertion into the db
        ContentValues values = new ContentValues();
        values.put(COLUMN_15[1], med.getChildID());
        values.put(COLUMN_15[2], med.getName());
        values.put(COLUMN_15[3], med.getDose());
        values.put(COLUMN_15[4], med.getDoseUnits());
        values.put(COLUMN_15[5], med.getFrequency());
        values.put(COLUMN_15[6], med.getReason());
        SQLiteDatabase db = this.getWritableDatabase();
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[14], null, values);
        db.close();
        //returns the id of the entry that was just inserted
        return result;
    }

    public long addResource(Resource resource){
        //object that will contain the values from the object for insertion into the db
        ContentValues values = new ContentValues();
        values.put(COLUMN_16[1], resource.getName());
        values.put(COLUMN_16[2], resource.getURL());
        SQLiteDatabase db = this.getWritableDatabase();
        //function for inserting data into the database
        long result = db.insert(TABLE_NAMES[15], null, values);
        db.close();
        //returns the id of the entry that was just inserted
        return result;
    }


    /*
     * get(insert table name) functions query the database for information, stores it
     * in the corresponding object and return it.
     */
    public AccountHolder getAccount(String user){
        //query to get all account info for specific username
        String query = "SELECT * FROM Account WHERE Username = '" + user + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        AccountHolder account = new AccountHolder();
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            //move through the table and add each column's info to the object and return the object
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
        //query to get all account info for specific first name
        String query = "SELECT * FROM Account WHERE FirstName = '" + fName + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        AccountHolder account = new AccountHolder();
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            //move through the table and add each column's info to the object and return the object
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
        //query to get all child info for specific child
        String query = "SELECT * FROM Child WHERE FirstName = '" + firstName + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        Child child = new Child();
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            //move through the table and add each column's info to the object and return the object
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
        //query for getting the name of a specific child
        String query = "SELECT * FROM Child WHERE ChildID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        String childName;
        //Get child name from the table and return the name
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            c.moveToFirst();
            //select name column from table
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
        //query to select specific child
        String query = "SELECT * FROM Child WHERE ChildID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        String allergies;
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            c.moveToFirst();
            //retrieve just allergy column
            allergies = c.getString(7);
        }
        //return allergy string
        else{
            c.close();
            allergies = null;
        }
        db.close();
        return allergies;
    }

    public long getChildBirthday(int id){
        //query to select specific child
        String query = "SELECT * FROM Child WHERE ChildID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        long childBirthday;
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            c.moveToFirst();
            //select birthday column
            childBirthday = c.getLong(6);
        }
        else{
            c.close();
            childBirthday = 0;
        }
        db.close();
        return childBirthday;
    }

    public String getPassword(long userID){
        //query to get password with specific user id
        String query = "SELECT Password FROM Account WHERE AccountHolderID = '" + userID + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        String password;
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            c.moveToFirst();
            //select password column
            password = c.getString(0);
        }
        //return object
        else{
            c.close();
            password = null;
        }
        db.close();
        return password;
    }

    public Bathroom getBathroom(int bathID){
        //query to get all bathroom info with specific id
        String query = "SELECT * FROM Bathroom WHERE BathroomID = '" + bathID + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        Bathroom bathroom = new Bathroom();
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            //move through the table and add each column's info to the object and return the object
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
        //query to get milestone info for a specific child
        String query = "SELECT * FROM Milestone WHERE ChildID = '" + childID + "';";
        SQLiteDatabase db = this.getWritableDatabase();
       //object that holds the the table the query returns
       Cursor c = db.rawQuery(query, null);
       Milestone milestone = new Milestone();
       //check if query table isn't empty, return null if it is
       if(c.moveToFirst()){
           //move through the table and add each column's info to the object and return the object
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
        //query for getting specific provider info with id
        String query = "SELECT * FROM Provider WHERE ProviderID = '" + providerID + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        Provider provider = new Provider();
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            //move through the table and add each column's info to the object and return the object
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
        //query to get specific feed info with id
        String query = "SELECT * FROM Feed WHERE FeedID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        Feed feed = new Feed();
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()) {
            //move through the table and add each column's info to the object and return the object
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
        //query to get specific activity info with id
        String query = "SELECT * FROM Activity WHERE ActivityID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        Activity activity = new Activity();
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            //move through the table and add each column's info to the object and return the object
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
        //query to get specific sleep info with id
        String query = "SELECT * FROM Sleep WHERE SleepID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        Sleep sleep = new Sleep();
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            //move through the table and add each column's info to the object and return the object
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
        //query to get specific medical info with id
        String query = "SELECT * FROM Medical WHERE MedicalID = '" + medicalID + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        MedicalInfo info = new MedicalInfo();
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            //move through the table and add each column's info to the object and return the object
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
        //query to get specific height info from medical table with childId
        String query = "SELECT * FROM Medical WHERE ChildID = '" + childID +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold the heights
        ArrayList<Point> data = new ArrayList<>();
        int i = 0;
        //check if query table isn't empty
        while(c.moveToNext()){
            //move through the table and add each column's info to the new object
            Point dataPoint = new Point();
            //select height column
            String text = c.getString(2);
            //set the x and y coordinates
            dataPoint.setX(i);
            dataPoint.setY(Float.parseFloat(text.substring(0, text.indexOf(" "))));
            dataPoint.setUnit(text.substring(text.indexOf(" ")));
            //add object to array
            data.add(dataPoint);
            i++;
        }
        return data;
    }

    public String checkUsername(String user){
        //query to select account info based on username
        String query = "SELECT * FROM Account WHERE UserName = '" + user + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        String username = "";
        //check if query table isn't empty
        if(c.moveToFirst()){
            c.moveToFirst();
            username = c.getString(3);
        }
        //if username is in database, it cannot be used again
        if(username.equals("")){
            return "y";
        }
        else{
            return "n";
        }
    }

    public ArrayList<Point> getWeight(int childID){
        //query to get specific weight info from medical table with childId
        String query = "SELECT * FROM Medical WHERE ChildID = '" + childID +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold the weights
        ArrayList<Point> data = new ArrayList<>();
        int i = 0;
        //check if query table isn't empty
        while(c.moveToNext()){
            //move through the table and add each column's info to the new object
            Point dataPoint = new Point();
            //select weight column
            String text = c.getString(3);
            dataPoint.setX(i);
            dataPoint.setY(Float.parseFloat(text.substring(0, text.indexOf(" "))));
            dataPoint.setUnit(text.substring(text.indexOf(" ")));
            //add object to array
            data.add(dataPoint);
            i++;
        }
        return data;
    }

    public ArrayList<Point> getHeadSizes(int childID){
        //query to get specific height info from medical table with childId
        String query = "SELECT * FROM Medical WHERE ChildID = '" + childID +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold the head sizes
        ArrayList<Point> data = new ArrayList<>();
        int i = 0;
        //check if query table isn't empty
        while(c.moveToNext()){
            //move through the table and add each column's info to the new object
            Point dataPoint = new Point();
            //select head size column
            String text = c.getString(4);
            dataPoint.setX(i);
            dataPoint.setY(Float.parseFloat(text.substring(0, text.indexOf(" "))));
            dataPoint.setUnit(text.substring(text.indexOf(" ")));
            //add object to array
            data.add(dataPoint);
            i++;
        }
        return data;
    }

    public ArrayList<Image> getChildVaccines(int childID){
        //query to select all image info for specific child
        String query = "SELECT * FROM Image WHERE ChildID = '" + childID +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold the images
        ArrayList<Image> images = new ArrayList<>();
        while(c.moveToNext()){
            //move through the table and add each column's info to the new object
            Image image = new Image();
            image.setImageID(c.getInt(0));
            image.setChildID(c.getInt(1));
            image.setImage(c.getBlob(2));
            //add object to array
            images.add(image);
        }
        return images;
    }

    public Message getMessage(int id){
        //query to select all message info with specific id
        String query = "SELECT * FROM Message WHERE MessageID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        Message message = new Message();
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            //move through the table and add each column's info to the object and return the object
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

    public Medication getMedication(int id){
        //retrieve medication information from medID
        String query = "SELECT * FROM Med WHERE MedID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        Medication medication = new Medication();
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            //move through the table and add each column's info to the object and return the object
            c.moveToFirst();
            medication.setMedID(c.getInt(0));
            medication.setChildID(c.getInt(1));
            medication.setName(c.getString(2));
            medication.setDose(c.getDouble(3));
            medication.setDoseUnits(c.getString(4));
            medication.setFrequency(c.getString(5));
            medication.setReason(c.getString(6));
        }
        else{
            c.close();
            medication = null;
        }
        db.close();
        return medication;
    }

    public Journal getJournal(int id){
        //query to select all journal info with specific id
        String query = "SELECT * FROM Journal WHERE JournalID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        Journal journal = new Journal();
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()){
            //move through the table and add each column's info to the object and return the object
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
        //query to select all mood info with specific id
        String query = "SELECT * FROM Mood WHERE MoodID = '" + id + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        Mood mood = new Mood();
        //check if query table isn't empty, return null if it is
        if(c.moveToFirst()) {
            //move through the table and add each column's info to the object and return the object
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
       //query to account with mathcing username and password
       String query = "SELECT * FROM Account WHERE Username = '" + user +
                "' AND Password = '" + pass + "';";
        SQLiteDatabase db = this.getWritableDatabase();
       //object that holds the the table the query returns
       Cursor c = db.rawQuery(query, null);
        AccountHolder accountHolder = new AccountHolder();
       //check if query table isn't empty, return null if it is
       if(c.moveToFirst()){
           //move through the table and add each column's info to the object and return the object
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
        //query to retrieve all accounts
        String query = "SELECT * FROM Account;";
        SQLiteDatabase db = getWritableDatabase();
       //object that holds the the table the query returns
       Cursor c = db.rawQuery(query, null);
       //array to hold all the accounts
       ArrayList<AccountHolder> accounts = new ArrayList<>();
        int x = 0;
       //check if query table isn't empty
       while(c.moveToNext()){
           //move through the table and add each column's info to the new object
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
                //add object to array
                accounts.add(account);
                x++;
        }
        c.close();
        //db.close();
        return accounts;
    }

    public ArrayList<Resource> getResources(){
        //query to get all resources in db
        String query = "SELECT * FROM Resource";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold the resources
        ArrayList<Resource> resources = new ArrayList<>();
        while(c.moveToNext()){
            //move through the table and add each column's info to the new object
            Resource resource = new Resource();
            resource.setResourceID(c.getInt(0));
            resource.setName(c.getString(1));
            resource.setURL(c.getString(2));
            //add object to array
            resources.add(resource);
        }
        c.close();
        return resources;
    }

    public ArrayList<MedicalInfo> getSpecificProviders(int childID, String providerType){
        //query to retrieve all medical infos on specific child based on the provider type
        String query = "SELECT * FROM Medical WHERE ChildID = '" + childID +
                "' AND ProviderType = '" + providerType + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold the providers
        ArrayList<MedicalInfo> medicalInfos = new ArrayList<>();
        //check if query table isn't empty
        while(c.moveToNext()){
            //move through the table and add each column's info to the new object
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
            //add object to array
            medicalInfos.add(info);
        }
        c.close();
        return medicalInfos;
    }

    public ArrayList<Entry> getAllEntries(){
        //query to retrieve all entires
        String query = "SELECT * FROM Entry;";
        SQLiteDatabase db = getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold all the entries
        ArrayList<Entry> entries = new ArrayList<>();
        int x = 0;
        //check if query table isn't empty
        while(c.moveToNext()){
            //move through the table and add each column's info to the new object
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
            //add object to array
            entries.add(entry);
                x++;
        }
        c.close();
        db.close();
        return entries;
    }

    //genric function for entries.........might be useful later?
    public <T> ArrayList<T> getSpecificEntries(String type){
        String query = "SELECT * FROM Entry WHERE EntryType = '" + type + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
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
        //query to retrieve all listings for specific child based on entry type
        String query = "SELECT * FROM Entry WHERE EntryType = '" + type +
               "' AND ChildID = '" + childID + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold the specific entries
        ArrayList<Entry> entries = new ArrayList<>();
        //check if query table isn't empty
        while(c.moveToNext()){
            //move through the table and add each column's info to the new object
            Entry entry = new Entry();
            entry.setEntryID(c.getInt(0));
            entry.setEntryText(c.getString(1));
            entry.setEntryTime(c.getLong(2));
            entry.setChildID(c.getInt(3));
            entry.setEntryType(c.getString(4));
            entry.setForeignID(c.getInt(5));
            //add object to array
            entries.add(entry);
        }
        c.close();
        return entries;
    }

    public ArrayList<Child> getAllChildren(){
        //query to retrieve all children
        String query = "SELECT * FROM Child;";
        SQLiteDatabase db = getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold all the children
        ArrayList<Child> children = new ArrayList<>();
        int x = 0;
        //check if query table isn't empty
        while(c.moveToNext()){
            //move through the table and add each column's info to the new object
            Child child = new Child();
                child.setChildID(c.getInt(0));
                child.setFirstName(c.getString(1));
                child.setLastName(c.getString(2));
                child.setGender(c.getString(3));
                child.setBloodType(c.getString(4));
                child.setDueDate(c.getLong(5));
                child.setBirthday(c.getLong(6));
            //add object to array
            children.add(child);
        }
        c.close();
        //db.close();
        return children;
    }
     public ArrayList<Provider> getAllProviders(){
         //query to retrieve all providers
         String query = "SELECT * FROM Provider;";
        SQLiteDatabase db = getWritableDatabase();
         //object that holds the the table the query returns
         Cursor c = db.rawQuery(query, null);
         //array to hold all the providers
         ArrayList<Provider> providers = new ArrayList<>();
         //check if query table isn't empty
         while(c.moveToNext()){
             //move through the table and add each column's info to the new object
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
             //add object to array
             providers.add(provider);
        }
        c.close();
        return providers;
     }

     public ArrayList<Activity> getAllActivities(int childID){
         //query to retrieve all activities for a specific child
         String query = "SELECT * FROM Activity WHERE ChildID = '" + childID +"';";
         SQLiteDatabase db = getWritableDatabase();
         //object that holds the the table the query returns
         Cursor c = db.rawQuery(query, null);
         //array to hold all the activities
         ArrayList<Activity> activities = new ArrayList<>();
         //check if query table isn't empty
         while(c.moveToNext()){
             //move through the table and add each column's info to the new object
             Activity activity = new Activity();
             activity.setActivityID(c.getInt(0));
             activity.setChildID(c.getInt(1));
             activity.setChildActivity(c.getString(2));
             activity.setEntryTime(c.getLong(3));
             activity.setDuration(c.getString(4));
             activity.setUnits(c.getString(5));
             activity.setNotes(c.getString(6));
             //add object to array
             activities.add(activity);
         }
         c.close();
         return activities;
    }

    public ArrayList<Medication> getAllMedication(int childID){
        //query for selecting all medication info for a specific child
        String query = "SELECT * FROM Med WHERE ChildID ='" + childID + "';";
        SQLiteDatabase db = getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold all the medications
        ArrayList<Medication> medications = new ArrayList<>();
        //check if query table isn't empty
        while(c.moveToNext()){
            //move through the table and add each column's info to the new object
            Medication medication = new Medication();
            medication.setMedID(c.getInt(0));
            medication.setChildID(c.getInt(1));
            medication.setName(c.getString(2));
            medication.setDose(c.getDouble(3));
            medication.setDoseUnits(c.getString(4));
            medication.setFrequency(c.getString(5));
            medication.setReason(c.getString(6));
            //add object to array
            medications.add(medication);
        }
        c.close();
        return medications;
    }

    public ArrayList<Mood> getAllMoods() {
        //query to retrieve all moods
        String query = "SELECT * FROM Mood;";
        SQLiteDatabase db = getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold all moods
        ArrayList<Mood> moods = new ArrayList<>();
        int x = 0;
        //check if query table isn't empty
        while (c.moveToNext()) {
            //move through the table and add each column's info to the new object
            Mood mood = new Mood();
            mood.setMoodID(c.getInt(0));
            mood.setChildID(c.getInt(1));
            mood.setMoodType(c.getString(2));
            mood.setTime(c.getString(3));
            mood.setNotes(c.getString(4));
            mood.setUnits(c.getString(5));
            //add object to array
            moods.add(mood);
        }
        c.close();
        return moods;
    }

    public ArrayList<Message> getAllMessages(){
        //query to retrieve all messages
        String query = "SELECT * FROM Message;";
        SQLiteDatabase db = getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold all the messages
        ArrayList<Message> messages = new ArrayList<>();
        //check if query table isn't empty
        while(c.moveToNext()){
            //move through the table and add each column's info to the new object
            Message message = new Message();
            message.setMessageID(c.getInt(0));
            message.setChildID(c.getInt(1));
            message.setMessage(c.getString(2));
            //add object to array
            messages.add(message);
        }
        c.close();
        return messages;
    }

    public ArrayList<Journal> getAllJournals(){
        //query to retrieve all journals
        String query = "SELECT * FROM Journal;";
        SQLiteDatabase db = getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold all the journals
        ArrayList<Journal> journals = new ArrayList<>();
        //check if query table isn't empty
        while(c.moveToNext()){
            //move through the table and add each column's info to the new object
            Journal journal = new Journal();
            journal.setJournalID(c.getInt(0));
            journal.setChildID(c.getInt(1));
            journal.setTitle(c.getString(2));
            journal.setNotes(c.getString(3));
            //add object to array
            journals.add(journal);
        }
        c.close();
        return journals;
    }

    public ArrayList<MedicalInfo> getAllMedical(){
        //query to retrieve all the medical infos
        String query = "SELECT * FROM Medical;";
        SQLiteDatabase db = getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        //array to hold all medical infos
        ArrayList<MedicalInfo> medicalInfos = new ArrayList<>();
        //check if query table isn't empty
        while(c.moveToNext()){
            //move through the table and add each column's info to the new object
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
            medicalInfo.setNotes(c.getString(13));                //add object to array
            //add object to array
            medicalInfos.add(medicalInfo);
        }

        c.close();
        return medicalInfos;
    }

    //calculates how long a child has slept for a specific date
    public int[] calculateSleepCycle(String date, int childID){
        int[] times = new int[2];
        //query to select duration and unit from sleep table for specific child on a specific day
        String query = "SELECT Duration, Unit FROM Sleep WHERE ChildID = '" + childID +"' AND " +
                "SleepDate = '" + date +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        //object that holds the the table the query returns
        Cursor c = db.rawQuery(query, null);
        int hours = 0;
        int minutes = 0;
        //check if query table isn't empty
        while(c.moveToNext()){
            //get duration and unit columns
            int duration = c.getInt(0);
            String units = c.getString(1);
            if(units.equals("mins")){
                //add minutes for that day
                minutes += duration;
            }
            if(units.equals("hrs")){
                //add hours for that day
                hours += duration;
            }
        }
        //if minutes is more than an hour, find the total hours and remaining minutes
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


    /*
    * Methods to update certain records in certain tables
     */
    public boolean updateMilestone(Milestone milestone){
        //updates milestone table for specific child when a column changes
        SQLiteDatabase db = this.getWritableDatabase();
        //object that will contain the updated values from the object for insertion into the db
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
        //call update function with the values and the id of the entry you want changed
        return db.update(TABLE_NAMES[7], values, COLUMN_8[1] + "=" + milestone.getChildId(), null) > 0;
    }

    //updates child table for specific child when a column changes
    public boolean updateChild(Child child){
        //object that will contain the updated values from the object for insertion into the db
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
        //call update function with the values and the id of the entry you want changed
        return db.update(TABLE_NAMES[1], values, COLUMN_2[0] + "=" + child.getChildID(), null) > 0;
    }

    //updates provider table for specific provider when a column changes
    public boolean updateProvider(Provider provider){
        //object that will contain the updated values from the object for insertion into the db
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
        //call update function with the values and the id of the entry you want changed
        return db.update(TABLE_NAMES[9], values, COLUMN_10[0] + "=" + provider.getProviderID(), null) > 0;
    }
    //updates account table for specific account when a column changes
    public boolean updateAccount(AccountHolder accountHolder){
        //object that will contain the updated values from the object for insertion into the db
        ContentValues values = new ContentValues();
        values.put(COLUMN_1[1], accountHolder.getFirstName());
        values.put(COLUMN_1[2], accountHolder.getLastName());
        values.put(COLUMN_1[3], accountHolder.getUsername());
        values.put(COLUMN_1[4], accountHolder.getPassword());
        values.put(COLUMN_1[5], accountHolder.getPhone());
        SQLiteDatabase db = this.getWritableDatabase();
        //call update function with the values and the id of the entry you want changed
        return db.update(TABLE_NAMES[0], values, COLUMN_1[0] + "=" + accountHolder.getAccountID(), null) > 0;
    }

    //updates Medication table for specific medication when a column changes
    public boolean updateMedication(Medication medication){
        //object that will contain the updated values from the object for insertion into the db
        ContentValues values = new ContentValues();
        values.put(COLUMN_15[1], medication.getChildID());
        values.put(COLUMN_15[2], medication.getName());
        values.put(COLUMN_15[3], medication.getDose());
        values.put(COLUMN_15[4], medication.getDoseUnits());
        values.put(COLUMN_15[5], medication.getFrequency());
        values.put(COLUMN_15[6], medication.getReason());
        SQLiteDatabase db = this.getWritableDatabase();
        //call update function with the values and the id of the entry you want changed
        return db.update(TABLE_NAMES[14], values, COLUMN_15[0] + "=" + medication.getMedID(), null) > 0;
    }

    //updates Resource table for specific resource when a column changes
    public boolean updateResource(Resource resource){
        ContentValues values = new ContentValues();
        values.put(COLUMN_16[1], resource.getName());
        values.put(COLUMN_16[2], resource.getURL());
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_NAMES[15], values, COLUMN_16[0] + "=" + resource.getResourceID(), null) > 0;
    }

}

