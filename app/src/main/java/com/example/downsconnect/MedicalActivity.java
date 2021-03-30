package com.example.downsconnect;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MedicalActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText doctorDatePicker;
    private boolean doctorDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);

        final Button back = findViewById(R.id.backButton);
        TextView currentTime = findViewById(R.id.current_time_text);
        doctorDatePicker = findViewById(R.id.doctorDatePicker);

        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();
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
                Intent intent = new Intent(MedicalActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        doctorDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorDate = true;
                showDatePickerDialog();
            }
        });
    }



    private void showDatePickerDialog() {
        @SuppressLint("ResourceType") DatePickerDialog datePickerDialog = new DatePickerDialog(this, 2,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        if(doctorDate){
            doctorDatePicker.setText(month + "/" + day + "/" + year);
        }
    }
}

