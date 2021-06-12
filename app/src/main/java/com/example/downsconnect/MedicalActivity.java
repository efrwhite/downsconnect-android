package com.example.downsconnect;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.downsconnect.objects.Entry;
import com.example.downsconnect.objects.MedicalInfo;

import java.util.Calendar;

public class MedicalActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText doctorDatePicker, headSize, vaccine, dosage, temperature, notes;
    private Spinner heightUnit, weightUnit, headUnit, health, dosageUnit, temperatureUnit;
    private long doctorDate = 0;
    private long time;
    private MedicalInfo medicalInfo = new MedicalInfo();
    private Button back, save, doctorVisitBtn, OTBtn, PTBtn, opthBtn, speech, hearing, dental, cardio, neck, height, weight, circumference;
    private DBHelper dbHelper;
    private Entry entry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        dbHelper = new DBHelper(this);
        back = findViewById(R.id.backButton);
        save = findViewById(R.id.saveButton);
        TextView currentTime = findViewById(R.id.current_time_text);
        doctorVisitBtn = findViewById(R.id.doctorVisitButton);
        doctorDatePicker = findViewById(R.id.rolledDatePicker);
        OTBtn = findViewById(R.id.occupationalTherapyButton);
        PTBtn = findViewById(R.id.physicalTherapyButton);
        opthBtn = findViewById(R.id.ophthamologyButton);
        speech = findViewById(R.id.speechButton);
        hearing = findViewById(R.id.hearingButton);
        dental = findViewById(R.id.dentalButton);
        cardio = findViewById(R.id.cardioButton);
        neck = findViewById(R.id.neckSafetyButton);
        height = findViewById(R.id.height_button);
        weight = findViewById(R.id.weight_button);
        circumference = findViewById(R.id.circumference_button);

        OTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                intent.putExtra("type", "OT");
                startActivity(intent);
            }
        });

        PTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                intent.putExtra("type", "PT");
                startActivity(intent);
            }
        });

        opthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                intent.putExtra("type", "Ophthalmology");
                startActivity(intent);
            }
        });

        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                intent.putExtra("type", "Speech");
                startActivity(intent);
            }
        });

        hearing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                intent.putExtra("type", "Hearing");
                startActivity(intent);
            }
        });

        dental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                intent.putExtra("type", "Dental");
                startActivity(intent);
            }
        });

        cardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                intent.putExtra("type", "Cardio");
                startActivity(intent);
            }
        });

        neck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                intent.putExtra("type", "Neck");
                startActivity(intent);
            }
        });


        doctorVisitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, DoctorsVisitActivity.class);
                startActivity(intent);
            }
        });

        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ChartActivity.class);
                intent.putExtra("chart", "Height");
                startActivity(intent);
            }
        });

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ChartActivity.class);
                intent.putExtra("chart", "Weight");
                startActivity(intent);
            }
        });

        circumference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ChartActivity.class);
                intent.putExtra("chart", "HeadSize");
                startActivity(intent);
            }
        });

        Calendar calendar = Calendar.getInstance();
        time = calendar.getTimeInMillis();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String realMins;
        if(minute <= 10){
            realMins = "0" + minute;
        }
        else{
            realMins = String.valueOf(minute);
        }

        if(hour >= 12){
            hour = hour - 12;
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + " PM");
        }
        else{
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + " AM");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

//        doctorDatePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePickerDialog();
//            }
//        });

//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!height.getText().equals("") && !heightUnit.getSelectedItem().equals("Select") && !weight.getText().equals("")
//                    && !weightUnit.getSelectedItem().equals("Select") && !headSize.getText().equals("") && !headUnit.getSelectedItem().equals("Select")
//                    && doctorDate != 0 && !health.getSelectedItem().equals("Select") && !vaccine.getText().equals("") && !dosage.getText().equals("") && !dosageUnit.getSelectedItem().equals("Select")
//                    && !temperature.getText().equals("") && !temperatureUnit.getSelectedItem().equals("Select") && !notes.getText().equals("")) {
//
//                    medicalInfo.setChildId(childID);
//                    medicalInfo.setHeight(Integer.parseInt(height.getText().toString()));
//                    medicalInfo.setHeightUnit(heightUnit.getSelectedItem().toString());
//                    medicalInfo.setWeight(Integer.parseInt(weight.getText().toString()));
//                    medicalInfo.setWeightUnit(weightUnit.getSelectedItem().toString());
//                    medicalInfo.setHeadSize(Integer.parseInt(headSize.getText().toString()));
//                    medicalInfo.setHeadSizeUnit(headUnit.getSelectedItem().toString());
//                    medicalInfo.setVaccine(vaccine.getText().toString());
//                    medicalInfo.setDosage(Integer.parseInt(dosage.getText().toString()));
//                    medicalInfo.setDosageUnit(dosageUnit.getSelectedItem().toString());
//                    medicalInfo.setTemperature(Integer.parseInt(temperature.getText().toString()));
//                    medicalInfo.setTemperatureUnit(temperatureUnit.getSelectedItem().toString());
//                    medicalInfo.setNotes(notes.getText().toString());
//                    time = Calendar.getInstance().getTimeInMillis();
//                    medicalInfo.setEntryTime(time);
//                    entry.setEntryTime(time);
//                    Log.i("Entry", String.valueOf(time));
//                    entry.setEntryText("Inserted Medical Info for " + dbHelper.getChildName(childID));
//                    entry.setChildID(childID);
//                    dbHelper.addMedical(medicalInfo);
//                    dbHelper.addEntry(entry);
//
//                    Intent intent = new Intent(MedicalActivity.this, ActivityContainer.class);
//                    startActivity(intent);
//                }
//                else if(!height.getText().equals("") && !heightUnit.getSelectedItem().equals("Select") && !weight.getText().equals("")
//                        && !weightUnit.getSelectedItem().equals("Select") && !headSize.getText().equals("") && !headUnit.getSelectedItem().equals("Select")
//                        && doctorDate != 0 && !health.getSelectedItem().equals("Select") && !vaccine.getText().equals("") && !dosage.getText().equals("") && !dosageUnit.getSelectedItem().equals("Select")
//                        && !temperature.getText().equals("") && !temperatureUnit.getSelectedItem().equals("Select") && notes.getText().equals("")) {
//
//                    medicalInfo.setHeight(Integer.parseInt(height.getText().toString()));
//                    medicalInfo.setHeightUnit(heightUnit.getSelectedItem().toString());
//                    medicalInfo.setWeight(Integer.parseInt(weight.getText().toString()));
//                    medicalInfo.setWeightUnit(weightUnit.getSelectedItem().toString());
//                    medicalInfo.setHeadSize(Integer.parseInt(headSize.getText().toString()));
//                    medicalInfo.setHeadSizeUnit(headUnit.getSelectedItem().toString());
//                    medicalInfo.setVaccine(vaccine.getText().toString());
//                    medicalInfo.setDosage(Integer.parseInt(dosage.getText().toString()));
//                    medicalInfo.setDosageUnit(dosageUnit.getSelectedItem().toString());
//                    medicalInfo.setTemperature(Integer.parseInt(temperature.getText().toString()));
//                    medicalInfo.setTemperatureUnit(temperatureUnit.getSelectedItem().toString());
//                    medicalInfo.setNotes("None");
//                    Calendar calendar = Calendar.getInstance();
//                    time = calendar.getTimeInMillis();
//                    entry.setEntryTime(time);
//                    entry.setEntryText("Inserted Medical Info for Jeff");
//                    entry.setChildID(1);
//                    dbHelper.addMedical(medicalInfo);
//                    dbHelper.addEntry(entry);
//                    Intent intent = new Intent(MedicalActivity.this, ActivityContainer.class);
//                    startActivity(intent);
//                }
//                else{
//                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
//                    a.setTitle("Missing Information");
//                    a.setMessage("Please make sure you've filled out the necessary information");
//                    a.show();
//                }
//
//
//            }
//        });
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
        medicalInfo.setDoctorDate(doctorDate);
        doctorDatePicker.setText(month + "/" + day + "/" + year);
    }
}

