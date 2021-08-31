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

import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Mood;

import java.util.Calendar;

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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);


        mood = new Mood();
        entry = new Entry();
        db = new DBHelper(this);

        Log.i("moodtesting", String.valueOf(db.getAllMoods().size()));
        mood.setChildID(childID);

        final Button back = findViewById(R.id.backButton);
        currentTime = findViewById(R.id.moodTime);
        notes = findViewById(R.id.moodNotes);
        moodType = findViewById(R.id.moodSpinner);
        units = findViewById(R.id.moodUnits);
        save = findViewById(R.id.moodSave);
        time = findViewById(R.id.durationTimeText);
        history = findViewById(R.id.messageHistory);


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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoodActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoodActivity.this, ListingActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("type", moodType.getSelectedItem().toString());
                Log.i("units", units.getSelectedItem().toString());
                Log.i("time", time.getText().toString());
                if(!moodType.getSelectedItem().toString().equals("Select") && !units.getSelectedItem().toString().equals("Select") &&
                    !time.getText().toString().equals("")){
                    mood.setMoodType(moodType.getSelectedItem().toString());
                    mood.setTime(time.getText().toString());
                    mood.setUnits(units.getSelectedItem().toString());
                    if(!notes.getText().toString().equals("")){
                        mood.setNotes(notes.getText().toString());
                    }
                    Toast.makeText(getApplicationContext(), "Mood infomation saved", Toast.LENGTH_SHORT).show();

                    entry.setChildID(childID);
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    entry.setEntryText(db.getChildName(childID) + " was " + mood.getMoodType() + " for " + mood.getTime() + mood.getUnits());
                    entry.setEntryType("Mood");

                    long id = db.addMood(mood);
                    entry.setForeignID(id);
                    db.addEntry(entry);

                    Intent intent = new Intent(MoodActivity.this, ActivityContainer.class);
                    startActivity(intent);
                }
                else{
                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });
    }
}
