package com.iso.downsconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.Activity;
import com.iso.downsconnect.objects.Entry;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityActivity extends AppCompatActivity {
    private DBHelper helper;
    private TextView currentTime, activityText, durationText, history;
    private Spinner c_activity, units;
    private Button save;
    private Activity activity;
    private EditText duration, notes;
    private Entry entry;
    private ArrayList<Activity> activities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        //Get activity id to figure out whether this is a new entry of not
        Intent intent = getIntent();
        String msgID = intent.getStringExtra("activityID");
        int id = Integer.parseInt(msgID);

        //get current child ID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        //declare and initialize variables
        helper = new DBHelper(this);
        currentTime = findViewById(R.id.timeText);
        c_activity = findViewById(R.id.activitySpinner);
        activityText = findViewById(R.id.childActivityText);
        save = findViewById(R.id.saveButton);
        durationText = findViewById(R.id.durationText);
        duration = findViewById(R.id.durationEditText);
        units = findViewById(R.id.durationUnits);
        notes = findViewById(R.id.notesEditText);
        history = findViewById(R.id.historyBtn);
        final Button back = findViewById(R.id.backButton);

        //Initialize and fill in initial information for entry and activity objects
        entry = new Entry();
        entry.setChildID(childID);
        entry.setEntryType("Activity");

        activity = new Activity();
        activity.setChildID(childID);

        //get all activities currently in db
        activities = helper.getAllActivities(childID);

        //if viewing an already existing activity entry, display the information about that entry
        if(id != -1){
            //fill in each field with the necessary information from the activity entry
            activity = helper.getActivity(id);
            save.setEnabled(false);
            c_activity.setSelection(getIndex(c_activity, activity.getChildActivity()));
            duration.setText(activity.getDuration());
            if(!activity.getNotes().equals("")){
                notes.setText(activity.getNotes());
            }
            units.setSelection(getIndex(units, activity.getUnits()));
        }



        //Calculate and display the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String realMins;
        if(minute <= 10){
            realMins = "0" + minute;
        }
        else{
            realMins = String.valueOf(minute);
        }
        if(hour > 12){
            hour = hour - 12;
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + "PM");
        }
        else if(hour == 12){
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + "PM");
        }
        else{
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + "AM");
        }

        //
//        c_activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position == 0){
//                    activityText.setText("");
//                }
//                else {
//                    activityText.setText(helper.getChildName(childID) + " is " + c_activity.getSelectedItem().toString());
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        //takes user to listing page to display previous activity entries
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityActivity.this, ListingActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });


        //button for navigating back to home screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        //saves activity information to db
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks to make sure necessary fields are filled out
                if(!duration.getText().toString().equals("") && !units.getSelectedItem().toString().equals("Select")
                        && !c_activity.getSelectedItem().toString().equals("Select")){
                    //add the info to the activity object
                    activity.setDuration(duration.getText().toString());
                    activity.setUnits(units.getSelectedItem().toString());
                    activity.setChildActivity(c_activity.getSelectedItem().toString());
                    Calendar cal = Calendar.getInstance();
                    activity.setEntryTime(cal.getTimeInMillis());

                    //add info to entry object
                    entry.setEntryTime(cal.getTimeInMillis());
                    entry.setEntryText(helper.getChildName(childID) + " was " + c_activity.getSelectedItem().toString() + " for " + activity.getDuration() + " " + activity.getUnits());
                    entry.setEntryType("Activity");

                    //if user wrote notes, add them accordingly
                    if(!notes.getText().toString().equals("")){
                        activity.setNotes(notes.getText().toString());
                    }
                    else{
                        activity.setNotes("");
                    }

                    //add activity to db and get the id associated with it
                    long result = helper.addActivity(activity);

                    //add new entry to database and navigate back to home page
                    entry.setForeignID(result);
                    helper.addEntry(entry);
                    Intent intent = new Intent(ActivityActivity.this, ActivityContainer.class);
                    startActivity(intent);
                }
            }
        });
    }

    //get index of a selection in a spinner
    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;

    }
}
