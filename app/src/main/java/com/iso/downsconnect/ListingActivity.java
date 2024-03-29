package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.Activity;
import com.iso.downsconnect.helpers.DateHandler;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Journal;
import com.iso.downsconnect.objects.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//activity to display past entries for either activities, moods, messages, or journals
public class ListingActivity extends AppCompatActivity {
    private Button back;
    private DBHelper helper;
    private ArrayList<Activity> activities = new ArrayList<>();
    //private ArrayList<Mood> moods = new ArrayList<>();
    private ArrayList<Entry> entries = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<Journal> journals = new ArrayList<>();
    private LinearLayout linearLayout;
    private DateHandler dateHandler = new DateHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        //get which type of info will be displayed on the page
        final Intent intent = getIntent();
        final int type = intent.getIntExtra("type", 0);

        //get currently selected child from shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        //initialize variables
        helper = new DBHelper(this);
        back = findViewById(R.id.backButton);
        linearLayout = findViewById(R.id.activities_layout);

        //based on the listing type, call the appropriate method
        if(type == 0) {
            addActivites(childID);
        }

        if(type == 1){
            addMoods(childID);
        }

        if(type == 2){
            addMessages(childID);
        }

        if(type == 3){
            addJournals(childID);
        }



        //navigates back to the original activity that the listing was called from
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //based on the listing type, return to the appropriate activity
                if(type == 0) {
                    Intent intent = new Intent(ListingActivity.this, ActivityActivity.class);
                    intent.putExtra("activityID", "-1");
                    startActivity(intent);
                }
                if(type == 1){
                    Intent intent = new Intent(ListingActivity.this, MoodActivity.class);
                    intent.putExtra("moodID", "-1");
                    startActivity(intent);
                }
                if(type == 2){
                    Intent intent = new Intent(ListingActivity.this, MessageActivity.class);
                    intent.putExtra("msgID", "-1");
                    startActivity(intent);
                }
                if(type == 3){
                    Intent intent = new Intent(ListingActivity.this, JournalActivity.class);
                    intent.putExtra("journID", "-1");
                    startActivity(intent);
                }
            }
        });


    }

    private void addActivites(int childID){
        //display unique headings for activities
        TextView title = findViewById(R.id.listingTitle);
        title.setText("Past Activities");

        TextView listText = findViewById(R.id.listingText);
        listText.setText("Activities");

        //get all the entries with the activity type
        entries = helper.getListing("Activity", childID);
//        Log.i("conut", String.valueOf(entries.size()));

        //call build layout function to display the listing objects on the screen
        buildLayout("Activity");

//        activities = helper.getAllActivities(childID);
//        for(Activity activity: activities){
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            marginLayoutParams.setMargins(20, 0, 50,10);
//
//            LinearLayout mainLayout = new LinearLayout(this);
//            mainLayout.setTag(activity.getActivityID() + "Layout");
//            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
//            mainLayout.setLayoutParams(marginLayoutParams);
//
//
//            layoutParams.setMargins(200, 0, 0, 30);
//            textParams.setMargins(20, 30, 10, 30);
//
//            LinearLayout horizontalLayout = new LinearLayout(this);
//            horizontalLayout.setTag(activity.getActivityID() + "Layout");
//            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
//            horizontalLayout.setLayoutParams(marginLayoutParams);
//
//            LinearLayout horizontalLayout2 = new LinearLayout(this);
//            horizontalLayout2.setTag(activity.getActivityID() + "Layout2");
//            horizontalLayout2.setOrientation(LinearLayout.HORIZONTAL);
//            //horizontalLayout2.setLayoutParams(marginLayoutParams);
//
//            TextView date = new TextView(this);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(activity.getEntryTime());
//            Date dat = calendar.getTime();
//            DateFormat formatter = new SimpleDateFormat("h:mm a");
//            String time = formatter.format(dat);
//
//            date.setText(dateHandler.getMonth(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.DATE) + ", " + calendar.get(Calendar.YEAR) + " at: " + time);
//            date.setTextSize(15);
//            date.setTextColor(Color.BLACK);
//            date.setWidth(500);
//            date.setLayoutParams(textParams);
//            horizontalLayout.addView(date);
//            mainLayout.addView(horizontalLayout);
//
//            TextView c_activity = new TextView(this);
//            c_activity.setText(activity.getChildActivity());
//            c_activity.setTextSize(15);
//            c_activity.setWidth(500);
//            c_activity.setTextColor(Color.BLACK);
//            c_activity.setLayoutParams(textParams);
//            horizontalLayout.addView(c_activity);
//
//            linearLayout.addView(mainLayout);
//        }
    }

    private void addMoods(int childID){
        //display unique headings for moods
        TextView title = findViewById(R.id.listingTitle);
        title.setText("Past Moods");

        TextView listText = findViewById(R.id.listingText);
        listText.setText("Moods");

        //get all the entries with the mood type
        entries = helper.getListing("Mood", childID);
        Log.i("conut", String.valueOf(entries.size()));

        //call build layout function to display the listing objects on the screen
        buildLayout("Mood");
    }

    private void addMessages(int childID){
        //display unique headings for messages
        TextView title = findViewById(R.id.listingTitle);
        title.setText("Past Messages");

        TextView listText = findViewById(R.id.listingText);
        listText.setText("Messages");

        //get all the entries with the message type
        entries = helper.getListing("Message", childID);
        Log.i("conut", String.valueOf(entries.size()));

        //call build layout function to display the listing objects on the screen
        buildLayout("Message");
    }

    private void addJournals(int childID){
        //display unique headings for journals
        TextView title = findViewById(R.id.listingTitle);
        title.setText("Past Journal Entries");

        TextView listText = findViewById(R.id.listingText);
        listText.setText("Journals");

        //get all the entries with the journal type
        entries = helper.getListing("Journal", childID);
        Log.i("conut", String.valueOf(entries.size()));

        //call build layout function to display the listing objects on the screen
        buildLayout("Journal");
    }

    private void buildLayout(String type) {
        //for each entry, creates a horizontal layout, adds objects to linear layout
        // and displays info it
        for(Entry entry: entries){
            //define layout spacing/parameters for objects
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //controls the margin for whatever object has these parameters
            marginLayoutParams.setMargins(20, 0, 50,10);
            layoutParams.setMargins(200, 0, 0, 30);
            textParams.setMargins(20, 30, 10, 30);

            //creates horizontal layout for placing object correctly on screen
            LinearLayout mainLayout = new LinearLayout(this);
            mainLayout.setTag(entry.getEntryID() + "Layout");
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.setLayoutParams(marginLayoutParams);

            //creates secondary horizontal layout for placing object correctly on screen
            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setTag(entry.getEntryID()  + "Layout");
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setLayoutParams(marginLayoutParams);

//            LinearLayout horizontalLayout2 = new LinearLayout(this);
//            horizontalLayout2.setTag(entry.getEntryID()  + "Layout2");
//            horizontalLayout2.setOrientation(LinearLayout.HORIZONTAL);

            //Text view for display the entry's date and time
            TextView date = new TextView(this);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(entry.getEntryTime());
            Date dat = calendar.getTime();
            //formats time string
            DateFormat formatter = new SimpleDateFormat("h:mm a");
            String time = formatter.format(dat);
            //set the text for the time and date
            date.setText(dateHandler.getMonth(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.DATE) + ", " + calendar.get(Calendar.YEAR) + " at: " + time);
            date.setTextSize(15);
            date.setTextColor(Color.BLACK);
            date.setWidth(350);
            //define the margins for the object
            date.setLayoutParams(textParams);
            //add to secondary layout and add secondary layout to main layout
            horizontalLayout.addView(date);
            mainLayout.addView(horizontalLayout);

            //based on the type of entries you're looking, get the necessary entry info to display
            TextView c_activity = new TextView(this);
            if(type.equals("Message")){
                c_activity.setText(helper.getMessage((int) entry.getForeignID()).getMessage());
            }
            else if(type.equals("Journal")){
                c_activity.setText(helper.getJournal((int) entry.getForeignID()).getNotes());
            }
            else {
                c_activity.setText(entry.getEntryText());
            }

            //create text view to display the information
            c_activity.setTextSize(15);
            c_activity.setWidth(500);
            c_activity.setTextColor(Color.BLACK);
            c_activity.setLayoutParams(textParams);
            //add to linear layout
            horizontalLayout.addView(c_activity);

            //add layout to main linear layout
            linearLayout.addView(mainLayout);
        }
    }
}