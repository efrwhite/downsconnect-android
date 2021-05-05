package com.example.downsconnect;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.downsconnect.objects.Entry;
import com.example.downsconnect.objects.Sleep;

import java.util.Calendar;

public class SleepActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private EditText sleepTimePicker;
    private CheckBox snoringYes, snoringNo, studyYes, studyNo, medication, supplements, cpap, other;
    Boolean snoring, study;
    private EditText notes, wokeUp, otherText;
    private Spinner timeUnit;
    private String sleepTime, sleepNotes, timeWoke, unitTime, otherMeds, snoringDecision, studyDecision;
    private long timeSlept;
    private int duration;
    private DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        helper = new DBHelper(this);
        sleepTimePicker = findViewById(R.id.sleepTimePicker);
        snoringNo = findViewById(R.id.snoringNoCheckBox);
        snoringYes = findViewById(R.id.snoringYesCheckBox);
        studyNo = findViewById(R.id.studyNoCheckBox);
        studyYes = findViewById(R.id.studyYesCheckBox);
        notes = findViewById(R.id.notesEditText);
        wokeUp = findViewById(R.id.wokeUpEditText);
        timeUnit = findViewById(R.id.timerSpinner);
        medication = findViewById(R.id.medicationCheckBox);
        supplements = findViewById(R.id.supplementsCheckBox);
        cpap = findViewById(R.id.cpapCheckBox);
        other = findViewById(R.id.otherCheckBox);
        otherText = findViewById(R.id.otherEditText);



        final Button back = findViewById(R.id.backButton);
        final Button save = findViewById(R.id.saveButton);
        TextView currentTime = findViewById(R.id.current_time_text);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if(hour >= 12){
            hour = hour - 12;
            currentTime.setText("Today " + String.valueOf(hour) + ":" + String.valueOf(minute) + "PM");
        }
        else{
            currentTime.setText("Today " + String.valueOf(hour) + ":" + String.valueOf(minute) + "AM");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleepTime = sleepTimePicker.getText().toString();
                sleepNotes = notes.getText().toString();
                timeWoke = wokeUp.getText().toString();
                unitTime = timeUnit.getSelectedItem().toString();
                duration = Integer.parseInt(wokeUp.getText().toString());
                if(!sleepTime.equals(" ") && !timeWoke.equals(" ") && !unitTime.equals("Select")
                    && study && snoring){
                    Sleep sleep = new Sleep();
                    Entry entry = new Entry();
                    Calendar calendar1 = Calendar.getInstance();
                    entry.setEntryTime(calendar1.getTimeInMillis());
                    entry.setChildID(1);
                    entry.setEntryText("Jeff Slept for " + duration + unitTime);
                    sleep.setSleepTime(timeSlept);
                    if(!sleepNotes.equals(" ")){
                        sleep.setNotes(sleepNotes);
                    }
                    else{
                        sleep.setNotes("None");
                    }
                    sleep.setSnoring(snoringDecision);
                    sleep.setStudy(studyDecision);
                    sleep.setUnit(unitTime);
                    sleep.setDuration(duration);
                    if(medication.isChecked()){
                        sleep.setMedication("yes");
                    }
                    else{
                        sleep.setMedication("no");
                    }
                    if(supplements.isChecked()){
                        sleep.setSupplements("yes");
                    }
                    else{
                        sleep.setSupplements("no");
                    }
                    if(cpap.isChecked()){
                        sleep.setCPAP("yes");
                    }
                    else{
                        sleep.setCPAP("no");
                    }
                    if(other.isChecked()){
                        sleep.setOther(otherText.getText().toString());
                    }
                    else{
                        sleep.setOther("no");
                    }
                    helper.addSleep(sleep);
                    helper.addEntry(entry);
                    Intent intent = new Intent(SleepActivity.this, ActivityContainer.class);
                    startActivity(intent);
                    Log.i("hi", "Worked");
                }
                else{
                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });

        studyYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studyYes.isChecked()){
                    studyNo.setEnabled(false);
                    study = true;
                    studyDecision = "yes";
                }
                else{
                    studyNo.setEnabled(true);
                    study = false;
                    studyDecision = "";
                }
            }
        });

        studyNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studyNo.isChecked()){
                    studyYes.setEnabled(false);
                    study = true;
                    studyDecision = "no";
                }
                else{
                    studyYes.setEnabled(true);
                    study = false;
                    studyDecision = "";

                }
            }
        });

        snoringYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(snoringYes.isChecked()) {
                    snoringNo.setEnabled(false);
                    snoring = true;
                    snoringDecision = "yes";

                }
                else{
                    snoringNo.setEnabled(true);
                    snoring = false;
                    snoringDecision = "";

                }
            }
        });

        snoringNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(snoringNo.isChecked()) {
                    snoringYes.setEnabled(false);
                    snoring = true;
                    snoringDecision = "no";
                }
                else{
                    snoringYes.setEnabled(true);
                    snoring = false;
                    snoringDecision = "";

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SleepActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        sleepTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
    }

    private void showTimePickerDialog(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, 2, this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                false);
        timePickerDialog.show();
    }

    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        timeSlept = calendar.getTimeInMillis();
        String fixedMinute = String.valueOf(minute);
        if(minute < 10){
            fixedMinute = "0" + fixedMinute;
        }
        String diff;
        if(hour >= 0 && hour < 12){
            diff = " AM";
        }else{
            if(hour > 12) {
                hour = hour - 12;
            }
            diff = " PM";
        }
        String time = hour + ":" + fixedMinute + diff;
        sleepTimePicker.setText(time);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
