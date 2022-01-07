package com.iso.downsconnect;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.helpers.DateHandler;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Sleep;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SleepActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,  DatePickerDialog.OnDateSetListener {
    private EditText sleepTimePicker, sleepDatePicker;
    private CheckBox snoringYes, snoringNo, studyYes, studyNo, medication, supplements, cpap, other;
    Boolean snoring, study, isDrop;
    private EditText notes, wokeUp, otherText;
    private Spinner timeUnit;
    private String sleepTime, sleepNotes, timeWoke, unitTime, otherMeds, snoringDecision, studyDecision, date, sleepDate;
    private long timeSlept, dateSlept;
    private int duration;
    private DBHelper helper;
    private Button drop, sleepCycle;
    private ScrollView scrollView;
    private DateHandler dateHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        Intent intent = getIntent();
        String msgID = intent.getStringExtra("sleepID");
        int id = Integer.parseInt(msgID);

        helper = new DBHelper(this);
        sleepTimePicker = findViewById(R.id.sleepTimePicker);
        sleepDatePicker = findViewById(R.id.sleepDatePicker);
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
        sleepCycle = findViewById(R.id.sleepCycleButton);
        study = false;
        snoring = false;
        isDrop = false;
        drop = findViewById(R.id.dropDownButton);
        scrollView = findViewById(R.id.scrollView3);
        scrollView.setVisibility(View.INVISIBLE);
        final Button back = findViewById(R.id.backButton);
        final Button save = findViewById(R.id.saveButton);
        dateHandler = new DateHandler();

        if(id != -1){
            save.setEnabled(false);
            Sleep sleep = helper.getSleep(id);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(sleep.getSleepTime());
            Date dat = calendar.getTime();
            DateFormat formatter = new SimpleDateFormat("hh:mm a");
            String time = formatter.format(dat);

//            sleep.getSleepDate().replace(sleep.getSleepDate().charAt(0), dateHandler.getMonth(sleep.getSleepDate().charAt(0)));
//            String newDate = dateHandler.getMonth()sleep.getSleepDate().charAt(0))
//            Calendar cal = Calendar.getInstance();
//            cal.setTimeInMillis(sleep.getSleepDate());

            sleepTimePicker.setText(time);
            wokeUp.setText(String.valueOf(sleep.getDuration()));
            timeUnit.setSelection(getIndex(timeUnit, sleep.getUnit()));
            sleepDatePicker.setText(sleep.getSleepDate());
            if(!sleep.getNotes().equals("None")){
                notes.setText(sleep.getNotes());
            }
            if(!sleep.getCPAP().equals("no")){
                cpap.setChecked(true);
                isDrop = true;
                scrollView.setVisibility(View.VISIBLE);
            }
            else {
                cpap.setChecked(false);
            }

            if(!sleep.getSnoring().equals("no")){
                snoringYes.setChecked(true);
                isDrop = true;
                scrollView.setVisibility(View.VISIBLE);
            }
            else{
                snoringNo.setChecked(true);
            }

            if(!sleep.getMedication().equals("no")){
                medication.setChecked(true);
                isDrop = true;
                scrollView.setVisibility(View.VISIBLE);
            }

            if(!sleep.getSupplements().equals("no")){
                supplements.setChecked(true);
                isDrop = true;
                scrollView.setVisibility(View.VISIBLE);
            }

            if(!sleep.getOther().equals("no")){
                other.setChecked(true);
                isDrop = true;
                otherText.setText(sleep.getOther());
                scrollView.setVisibility(View.VISIBLE);
            }

            if(!sleep.getStudy().equals("no")){
                studyYes.setChecked(true);
                isDrop = true;
                scrollView.setVisibility(View.VISIBLE);
            }
            else{
                studyNo.setChecked(true);
            }


        }


        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDrop = !isDrop;
                if(isDrop){
                    scrollView.setVisibility(View.VISIBLE);
                }
                else{
                    scrollView.setVisibility(View.INVISIBLE);
                }
            }
        });

        sleepCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SleepActivity.this, TwentyHourSleepActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);



        TextView currentTime = findViewById(R.id.current_time_text);

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sleepTime = sleepTimePicker.getText().toString();
                sleepNotes = notes.getText().toString();
                timeWoke = wokeUp.getText().toString();
                unitTime = timeUnit.getSelectedItem().toString();
                sleepDate = sleepDatePicker.getText().toString();
                duration = Integer.parseInt(wokeUp.getText().toString());

                if(!sleepTime.equals("") && !timeWoke.equals("") && !unitTime.equals("Select") && !sleepDate.equals("")){
                    Sleep sleep = new Sleep();
                    Entry entry = new Entry();
                    Calendar calendar1 = Calendar.getInstance();
                    entry.setEntryTime(calendar1.getTimeInMillis());
                    entry.setChildID(childID);
                    entry.setEntryType("Sleep");
                    sleep.setChildID(childID);
                    entry.setEntryText(helper.getChildName(childID) + " slept for " + duration + unitTime);
                    sleep.setSleepTime(timeSlept);
                    sleep.setSleepDate(date);
                    if(!sleepNotes.equals(" ")){
                        sleep.setNotes(sleepNotes);
                    }
                    else{
                        sleep.setNotes("None");
                    }
                    if(snoringYes.isChecked()) {
                        sleep.setSnoring("yes");
                    }
                    else{
                        sleep.setSnoring("no");
                    }
                    if(studyYes.isChecked()) {
                        sleep.setStudy("yes");
                    }
                    else{
                        sleep.setStudy("no");
                    }
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
                    long id = helper.addSleep(sleep);
                    entry.setForeignID(id);
                    helper.addEntry(entry);
                    Intent intent = new Intent(SleepActivity.this, ActivityContainer.class);
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

        sleepDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
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

    private void showDatePickerDialog(){
        @SuppressLint("ResourceType") DatePickerDialog datePickerDialog = new DatePickerDialog(this, 2, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
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

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-YYYY");
//        Date date = new Date();
        Date dat = cal.getTime();
        date = sdf.format(dat);
//        Log.i("asfdjk", date);
        sleepDatePicker.setText(dateHandler.writtenDate(month, dayOfMonth, year));
    }
}
