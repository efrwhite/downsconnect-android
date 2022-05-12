package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.helpers.DateHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//activity to calculate and display how long a child has slept for a specific
public class TwentyHourSleepActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Button go, back;
    private EditText datePicker;
    private TextView hours, minutes;
    private DateHandler handler;
    private DBHelper helper;
    private long cycleDate;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twenty_hour_sleep);

        //get childID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        //initialize variables
        go = findViewById(R.id.goBtn);
        back = findViewById(R.id.backButton2);
        datePicker = findViewById(R.id.sleepCycleDate);
        hours = findViewById(R.id.hoursText);
        minutes = findViewById(R.id.minText);
        handler = new DateHandler();
        helper = new DBHelper(this);

        //defaults the date to the current date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-YYYY");
        Date dat = cal.getTime();
        String currentDate = sdf.format(dat);
        String writtenDate = handler.writtenDateWithString(currentDate);
        datePicker.setText(writtenDate);

        //get the time the child has slept for the date
        int[] times = helper.calculateSleepCycle(currentDate, childID);
        setTimes(times);

        //navigate back to home screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TwentyHourSleepActivity.this, SleepActivity.class);
                intent.putExtra("sleepID", "-1");
                startActivity(intent);
            }
        });

        //display a date picker dialog box
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //button user to calculate and display how long a child has slept for a specific date
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!datePicker.getText().toString().equals("")) {
                    int[] times = helper.calculateSleepCycle(date, childID);
                    setTimes(times);
                }
                else{
                    AlertDialog a = new AlertDialog.Builder(go.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });
    }

    //set the times returned from the method in the dbhelper
    private void setTimes(int[] times) {
        hours.setText(String.valueOf(times[0]));
        if(times[1] < 10){
            minutes.setText("0" + times[1]);
        }
        else{
            minutes.setText(String.valueOf(times[1]));
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //saves and displays the date the user selected
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
//        cycleDate = cal.getTimeInMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-YYYY");
//        Date date = new Date();
        Date dat = cal.getTime();
        date = sdf.format(dat);
        datePicker.setText(handler.writtenDate(month, dayOfMonth, year));
    }

    //displays the date picker dialog box
    private void showDatePickerDialog(){
        @SuppressLint("ResourceType") DatePickerDialog datePickerDialog = new DatePickerDialog(this, 2, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}