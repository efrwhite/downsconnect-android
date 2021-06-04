package com.example.downsconnect;

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
import android.widget.Spinner;

import com.example.downsconnect.objects.Entry;
import com.example.downsconnect.objects.MedicalInfo;

import java.util.Calendar;

public class DoctorsVisitActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText doctorDatePicker, provider, height, headSize, temperature, visitNum, weight;
    private long doctorDate = 0;
    private Spinner headUnit, tempUnit, weightUnit, heightUnit;
    private MedicalInfo medicalInfo = new MedicalInfo();
    private Button save;
    private Entry entry = new Entry();
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_visit);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 0);

        doctorDatePicker = findViewById(R.id.doctorsDatePicker);
        dbHelper = new DBHelper(this);
        medicalInfo.setChildID(childID);
        save = findViewById(R.id.saveButton);
        provider = findViewById(R.id.providerEditText);
        height = findViewById(R.id.heightEditText);
        headSize = findViewById(R.id.headEditText);
        headUnit = findViewById(R.id.headSpinner);
        tempUnit = findViewById(R.id.temperatureSpinner);
        temperature = findViewById(R.id.temperatureEditText);
        visitNum = findViewById(R.id.visitEditText);
        weight = findViewById(R.id.weightEditText);
        weightUnit = findViewById(R.id.weightSpinner);
        heightUnit = findViewById(R.id.heightSpinner);


        doctorDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!doctorDatePicker.getText().toString().equals("") && !provider.getText().toString().equals("")
                        && !height.getText().toString().equals("") && !headUnit.getSelectedItem().equals("Select")
                        && !tempUnit.getSelectedItem().equals("Select") && !visitNum.getText().toString().equals("") && !weight.getText().toString().equals("")
                        && !heightUnit.getSelectedItem().equals("Select") && !weightUnit.getSelectedItem().equals("Select")){
                    medicalInfo.setProvider(provider.getText().toString());
                    medicalInfo.setHeight(height.getText().toString() + " " + heightUnit.getSelectedItem().toString());
                    medicalInfo.setWeight(weight.getText().toString() + " " + weightUnit.getSelectedItem().toString());
                    medicalInfo.setHeadInfo(headSize.getText().toString() + headUnit.getSelectedItem().toString());
                    medicalInfo.setTemperatureInfo(temperature.getText().toString() + " " + tempUnit.getSelectedItem().toString());
                    medicalInfo.setVisit(visitNum.getText().toString());
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    entry.setChildID(childID);
                    entry.setEntryText("Saved " + medicalInfo.getProvider() + " appointment information for " + dbHelper.getChildName(childID));
                    dbHelper.addEntry(entry);
                    dbHelper.addMedical(medicalInfo);
                    Intent intent = new Intent(DoctorsVisitActivity.this, ActivityContainer.class);
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

    private void showDatePickerDialog() {
        @SuppressLint("ResourceType") DatePickerDialog datePickerDialog = new DatePickerDialog(this, 2, (DatePickerDialog.OnDateSetListener) this,
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