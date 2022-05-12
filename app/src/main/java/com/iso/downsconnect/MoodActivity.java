package com.iso.downsconnect;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Mood;

import java.util.Calendar;
//activity for creating new mood entries as well as updating/displaying existing ones
public class MoodActivity extends AppCompatActivity {
    private TextView currentTime, history;
    private EditText notes, time;
    private Spinner moodType, units;
    private Button save;
    private Mood mood;
    private Entry entry;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        //get current childID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        //get mood ID
        Intent intent = getIntent();
        String msgID = intent.getStringExtra("moodID");
        int id = Integer.parseInt(msgID);


        //initialize objects
        mood = new Mood();
        entry = new Entry();
        db = new DBHelper(this);

//        Log.i("moodtesting", String.valueOf(db.getAllMoods().size()));
        mood.setChildID(childID);

        //initialize variables
        final Button back = findViewById(R.id.backButton);
        currentTime = findViewById(R.id.moodTime);
        notes = findViewById(R.id.moodNotes);
        moodType = findViewById(R.id.moodSpinner);
        units = findViewById(R.id.moodUnits);
        save = findViewById(R.id.moodSave);
        time = findViewById(R.id.durationTimeText);
        history = findViewById(R.id.messageHistory);

        //if this is an existing mood entry, display the entry's info
        if(id != -1){
            save.setEnabled(false);
            mood = db.getMood(id);
            moodType.setSelection(getIndex(moodType, mood.getMoodType()));
            units.setSelection(getIndex(units, mood.getUnits()));
            time.setText(mood.getTime());
            if(!mood.getNotes().equals("")){
                notes.setText(mood.getNotes());
            }
        }


        //get current time and display it it in the textview
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String realMins;
        //adjust minute string if minutes are less than 10
        if(minute <= 10){
            realMins = "0" + minute;
        }
        else{
            realMins = String.valueOf(minute);
        }
        //determines whether the time is in AM or PM
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

        //button that navigates back to home screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoodActivity.this, ActivityContainer.class);
                intent.putExtra("moodID", "-1");
                startActivity(intent);
            }
        });

        //navigates to history page to display all past mood
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoodActivity.this, ListingActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });

        //adds a new mood entry to the db
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("type", moodType.getSelectedItem().toString());
//                Log.i("units", units.getSelectedItem().toString());
//                Log.i("time", time.getText().toString());
                //check if all needed fields have been filled out
                if(!moodType.getSelectedItem().toString().equals("Select") && !units.getSelectedItem().toString().equals("Select") &&
                    !time.getText().toString().equals("")){
                    //add the entered info to the mood object
                    mood.setMoodType(moodType.getSelectedItem().toString());
                    mood.setTime(time.getText().toString());
                    mood.setUnits(units.getSelectedItem().toString());
                    //check if user entered anything the notes section
                    if(!notes.getText().toString().equals("")){
                        mood.setNotes(notes.getText().toString());
                    }
                    else{
                        mood.setNotes("");
                    }
                    //fill in the rest of the entry info
                    entry.setChildID(childID);
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    entry.setEntryText(db.getChildName(childID) + " was " + mood.getMoodType() + " for " + mood.getTime() + mood.getUnits());
                    entry.setEntryType("Mood");

                    //insert the new mood and get the id associated with it
                    long id = db.addMood(mood);
                    //add the returned ID to the entry object and add it to the db
                    entry.setForeignID(id);
                    db.addEntry(entry);

                    //display success message and navigate back to home screen
                    Toast.makeText(getApplicationContext(), "Mood infomation saved", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MoodActivity.this, ActivityContainer.class);
                    startActivity(intent);
                }
                else{
                    //display error message
                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
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
