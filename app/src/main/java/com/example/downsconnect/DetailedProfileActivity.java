package com.example.downsconnect;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

public class DetailedProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText birthdayPicker, dueDatePicker, allergies, medications, fullName;
    private Spinner gender, bloodType;
    private TextView age;
    private boolean birthday, dueDate;
    private Calendar birthdayDate, due_Date;
    private Button back, save;
    private DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_profile);

        birthdayDate = Calendar.getInstance();
        due_Date = Calendar.getInstance();
        age = findViewById(R.id.ageText);
        gender = findViewById(R.id.genderEditText);
        bloodType = findViewById(R.id.bloodSpinner);
        allergies = findViewById(R.id.allergyEditText);
        medications = findViewById(R.id.medicationsEditText);
        fullName = findViewById(R.id.editTextTextPersonName);
        helper = new DBHelper(this);

        save = findViewById(R.id.saveButton);
        back = findViewById(R.id.backButton);
        Spinner genderSpinner = findViewById(R.id.genderEditText);

        String spinner = genderSpinner.getSelectedItem().toString();
        birthdayPicker = findViewById(R.id.birthdayPicker);
        dueDatePicker = findViewById(R.id.dueDatePicker);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fullName.getText().toString().equals("") && !gender.getSelectedItem().equals("Select") && !birthdayPicker.getText().toString().equals("")
                       && !dueDatePicker.getText().toString().equals("") && !bloodType.getSelectedItem().equals("Select")){
                    Child child = new Child();
                    String name = fullName.getText().toString();
                    if(!fullName.getText().toString().contains(" ")){
                        AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                        a.setTitle("Child's Name");
                        a.setMessage("Please make sure you've entered your child's first and last name");
                        a.show();
                    }
                    else{
                        child.setFirstName(name.substring(0, name.indexOf(" ")));
                        child.setLastName(name.substring(name.indexOf(" ")));
                        child.setBirthday(birthdayDate.getTimeInMillis());
                        child.setDueDate(due_Date.getTimeInMillis());
                        child.setBloodType(bloodType.getSelectedItem().toString());
                        if(allergies.getText().toString().equals(" ")){
                            child.setAllergies("None");
                        }
                        else{
                            child.setAllergies(allergies.getText().toString());
                        }

                        if(medications.getText().toString().equals(" ")){
                            child.setAllergies("None");
                        }
                        else{
                            child.setAllergies(medications.getText().toString());
                        }
                        helper.addChild(child);
                        Intent intent = new Intent(DetailedProfileActivity.this, ActivityContainer.class);
                        startActivity(intent);
                    }
                }
                else{
                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedProfileActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        birthdayPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthday = true;
                dueDate = false;
                showDatePickerDialog();
            }
        });

        dueDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dueDate = true;
                birthday = false;
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
        if(birthday) {
            birthdayDate.set(year, month, day);
            Calendar currentDate = Calendar.getInstance();
            long difference = currentDate.getTimeInMillis() - birthdayDate.getTimeInMillis();
            long days = difference / (24 * 60 * 60 * 1000);
            int months = (int) days/ 30;
            int years = (int) days/365;
            age.setText(years + " year(s), " + months + " month(s), " + days + " day(s)");
            birthdayPicker.setText(month + "/" + day + "/" + year);
        }
        if(dueDate){
            due_Date.set(year, month, day);
            dueDatePicker.setText(month + "/" + day + "/" + year);
        }
    }
}
