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
    private static final String[] TABLE_NAMES = {"AccountHolders", "Children", "Feed", "Mood", "Sleep", "Entries"};
    private static final String[] COLUMN_1 = {"AccountID","FirstName", "LastName", "Username", "Password", "Phone"};
    private static final String[] COLUMN_2 = {"ChildID", "FirstName", "LastName", "Gender", "BloodType", "DueDate", "Birthday", "Allergies"};
    private static final String[] COLUMN_3 = {"FeedID", "ChildID", "Amount", "Substance", "TimeConsumed", "Notes", "EntryTime"};
    private static final String[] COLUMN_4 = {"MoodID", "ChildID", "MoodType", "Time", "Notes", "EntryTime"};
    private static final String[] COLUMN_5 = {"SleepID", "ChildID", "StartTime", "EndTime", "SleepType" ,"Notes", "EntryTime"};
    private static final String[] COLUMN_6 = {"EntryID", "EntryType", "TypeID"};
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
                "DueDate TEXT, " +
                "Birthday TEXT, " +
                "Notes TEXT);");
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
                "EntryTime TEXT);");
        db.execSQL("CREATE TABLE Entries(" +
                "EntryID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "EntryType TEXT, " +
                "TypeID INTEGER)");

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
        db.insert(TABLE_NAMES[0], null, values);
        db.close();
    }

    public void addFeed(Feed feed){
        ContentValues values = new ContentValues();
        values.put(COLUMN_3[1], feed.getChildID());
        values.put(COLUMN_3[2], feed.getAmount());
        values.put(COLUMN_3[3], feed.getSubstance());
        values.put(COLUMN_3[4], feed.getTimeConsumed());
        values.put(COLUMN_3[5], feed.getNotes());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAMES[0], null, values);
        db.close();
    }


    public void addMood(Mood mood){
        ContentValues values = new ContentValues();
        values.put(COLUMN_4[1], mood.getMoodID());
        values.put(COLUMN_4[2], mood.getMoodType());
        values.put(COLUMN_4[3], mood.getTime());
        values.put(COLUMN_4[4], mood.getNotes());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAMES[0], null, values);
        db.close();
    }

    public void addSleep(Sleep sleep){
        ContentValues values = new ContentValues();
        values.put(COLUMN_5[1], sleep.getChildID());
        values.put(COLUMN_5[2], sleep.getStartTime());
        values.put(COLUMN_5[3], sleep.getEndTime());
        values.put(COLUMN_5[4], sleep.getSleepType());
        values.put(COLUMN_5[5], sleep.getNotes());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAMES[0], null, values);
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

}

//public class HomeFragment extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        DBHelper helper = new DBHelper(this);
//
//        Button feed = findViewById(R.id.feedButton);
//        Button activity = findViewById(R.id.activityButton);
//        Button sleep = findViewById(R.id.sleepButton);
//        Button mood = findViewById(R.id.moodButton);
//        Button resources = findViewById(R.id.resourcesButton);
//        Button medical = findViewById(R.id.medicalButton);
//        Button message = findViewById(R.id.messageButton);
//        Button milestone = findViewById(R.id.milestoneButton);
//        Button photo = findViewById(R.id.photoButton);
//        Button diary = findViewById(R.id.diaryButton);
//        Button more = findViewById(R.id.moreButton);
//        Button signOut = findViewById(R.id.signoutButton);
//
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(HomeFragment.this)
//                        .setTitle("Sign Out")
//                        .setMessage("Are you sure you want to sign out")
//                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                    sharedPreferences.edit().putBoolean("signedIn", false).commit();
//                                    Intent intent = new Intent(HomeFragment.this, MainActivity.class);
//                                    startActivity(intent);
//                            }
//                        })
//                        .setNegativeButton("no", null).show();
//            }
//
//        });
//
//
//        feed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, FeedActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        activity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, ActivityActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        sleep.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, SleepActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        mood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, MoodActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        resources.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, ResourcesActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        medical.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, MedicalActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, MessageActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        milestone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, MilestoneActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, PhotoActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        diary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, DiaryActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, MoreActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//}

