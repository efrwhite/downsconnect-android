package com.example.downsconnect;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MedicalActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText doctorDatePicker, height, weight, headSize, vaccine, dosage, temperature, notes;
    private Spinner heightUnit, weightUnit, headUnit, health, dosageUnit, temperatureUnit;
    private long doctorDate;
    private MedicalInfo medicalInfo = new MedicalInfo();
    private Button back, save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);

        back = findViewById(R.id.backButton);
        save = findViewById(R.id.saveButton);
        TextView currentTime = findViewById(R.id.current_time_text);
        doctorDatePicker = findViewById(R.id.doctorDatePicker);
        height = findViewById(R.id.measuredHeightEditText);
        heightUnit = findViewById(R.id.heightSpinner);
        weight = findViewById(R.id.measuredWeightEditText);
        weightUnit = findViewById(R.id.weightSpinner);
        headSize = findViewById(R.id.headSizeEditText);
        headUnit = findViewById(R.id.headSpinner);
        health = findViewById(R.id.healthSpinner);
        vaccine = findViewById(R.id.vaccineEditText);
        dosage = findViewById(R.id.dosageEditText);
        dosageUnit = findViewById(R.id.dosageSpinner);
        temperature = findViewById(R.id.temperatureEditText);
        temperatureUnit = findViewById(R.id.temperatureSpinner);
        notes = findViewById(R.id.notesEditText);

        if(!notes.getText().toString().equals("")){
            medicalInfo.setNotes(notes.getText().toString());
        }



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
                showDatePickerDialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicalInfo.setHeight(Integer.parseInt(height.getText().toString()));
                medicalInfo.setHeightUnit(heightUnit.getSelectedItem().toString());
                medicalInfo.setWeight(Integer.parseInt(weight.getText().toString()));
                medicalInfo.setWeightUnit(weightUnit.getSelectedItem().toString());
                medicalInfo.setHeadSize(Integer.parseInt(headSize.getText().toString()));
                medicalInfo.setHeadSizeUnit(headUnit.getSelectedItem().toString());
                medicalInfo.setVaccine(vaccine.getText().toString());
                medicalInfo.setDosage(Integer.parseInt(dosage.getText().toString()));
                medicalInfo.setDosageUnit(dosageUnit.getSelectedItem().toString());
                medicalInfo.setTemperature(Integer.parseInt(temperature.getText().toString()));
                medicalInfo.setTemperatureUnit(temperatureUnit.getSelectedItem().toString());
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        doctorDate = calendar.getTimeInMillis();
        medicalInfo.setDoctorVisit(doctorDate);
        doctorDatePicker.setText(month + "/" + day + "/" + year);
    }
}

