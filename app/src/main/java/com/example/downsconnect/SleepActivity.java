package com.example.downsconnect;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class SleepActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private EditText sleepTimePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        sleepTimePicker = findViewById(R.id.sleepTimePicker);
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
