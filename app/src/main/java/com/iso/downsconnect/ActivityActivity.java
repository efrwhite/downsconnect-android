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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        entry = new Entry();
        activity = new Activity();

        entry.setChildID(childID);
        activity.setChildID(childID);

        durationText.setPaintFlags(durationText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        activities = helper.getAllActivities(childID);



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

        c_activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    activityText.setText("");
                }
                else {
                    activityText.setText(helper.getChildName(childID) + " is " + c_activity.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityActivity.this, ActivityListingActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });

        final Button back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!duration.getText().toString().equals("") && !units.getSelectedItem().toString().equals("Select")
                        && !c_activity.getSelectedItem().toString().equals("Select")){
                    activity.setDuration(duration.getText().toString());
                    activity.setUnits(units.getSelectedItem().toString());
                    activity.setChildActivity(helper.getChildName(childID) + " was " + c_activity.getSelectedItem().toString() + " for " + activity.getDuration() + " " + activity.getUnits());
                    Calendar cal = Calendar.getInstance();
                    activity.setEntryTime(cal.getTimeInMillis());
                    entry.setEntryTime(cal.getTimeInMillis());
                    entry.setEntryText(activity.getChildActivity());
                    entry.setEntryType("Activity");
                    if(!notes.getText().toString().equals("")){
                        activity.setNotes(notes.getText().toString());
                    }
                    else{
                        activity.setNotes("");
                    }
                    long result = helper.addActivity(activity);
                    entry.setForeignID(result);
                    helper.addEntry(entry);
                    Intent intent = new Intent(ActivityActivity.this, ActivityContainer.class);
                    startActivity(intent);
                }
            }
        });
    }
}
