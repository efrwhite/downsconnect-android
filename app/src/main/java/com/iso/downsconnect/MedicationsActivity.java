package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Medication;

import java.util.Calendar;
//activity for creating new medication as well as viewing/updating existing ones
public class MedicationsActivity extends AppCompatActivity {
    private Button back, add, pastMed;
    private EditText name, dose, frequency, reason;
    private Spinner doseUnits;
    private Entry entry = new Entry();
    private Medication med = new Medication();
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        int medID = getIntent().getIntExtra("mediID", -1);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        med.setChildID(childID);

        dbHelper = new DBHelper(this);

        //get objects on layout
        back = findViewById(R.id.medBack);
        name = findViewById(R.id.medName);
        dose = findViewById(R.id.medDose);
        frequency = findViewById(R.id.medFrequency);
        reason = findViewById(R.id.medReason);
        doseUnits = findViewById(R.id.doseUnits);
        add = findViewById(R.id.addBtn);
        pastMed = findViewById(R.id.addMed);

        //if medicalID is not -1, get the medical entry and display the info in the proper fields
        if(medID != -1){
            //display medication information if id is in database
            med = dbHelper.getMedication(medID);
            //changes name of submit button to "update" so any changes made to the entry will be update in the db
            add.setText("Update");
            name.setText(med.getName());
            dose.setText(String.valueOf(med.getDose()));
            doseUnits.setSelection(getIndex(doseUnits, med.getDoseUnits()));
            frequency.setText(med.getFrequency());
            reason.setText(med.getReason());
        }

        //button to navigate back to the medications listing page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicationsActivity.this, MedicationHistoryActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks if you are adding a new entry or updating an entry
                //actions that are run if you are adding an entry
                if (add.getText().toString().equals("Add")) {
                    //checks if all the necessary fields have been filled out
                    if (!name.getText().toString().equals("") && !dose.getText().toString().equals("") && !frequency.getText().toString().equals("")
                            && !reason.getText().toString().equals("") && !doseUnits.getSelectedItem().toString().equals("Select")) {
                        //store info into medication object
                        med.setName(name.getText().toString());
                        med.setDose(Double.parseDouble(dose.getText().toString()));
                        med.setFrequency(frequency.getText().toString());
                        med.setReason(reason.getText().toString());
                        med.setDoseUnits(doseUnits.getSelectedItem().toString());
                        //call add medication method and get id of newly inserted row
                        long id = dbHelper.addMedication(med);

                        //populate entry object and add it to database
                        entry.setEntryType("Medication");
                        entry.setForeignID(id);
                        entry.setEntryText("Saved " + med.getName() + " medication for " + dbHelper.getChildName(childID));
                        entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                        entry.setChildID(childID);
                        dbHelper.addEntry(entry);

                        //navigate back to Home Screen and display success message
                        Toast.makeText(getApplicationContext(), "Medication Information saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MedicationsActivity.this, ActivityContainer.class);
                        startActivity(intent);
                    } else {
                        //display error message
                        AlertDialog a = new AlertDialog.Builder(add.getContext()).create();
                        a.setTitle("Missing Information");
                        a.setMessage("Please make sure you've filled out the necessary information");
                        a.show();
                    }
                }
                //actions that are run if you are updating an entry
                else{
                    //checks if all the necessary fields have been filled out
                    if (!name.getText().toString().equals("") && !dose.getText().toString().equals("") && !frequency.getText().toString().equals("")
                            && !reason.getText().toString().equals("") && !doseUnits.getSelectedItem().toString().equals("Select")) {
                        //adds updated info into the new medication object
                        med.setName(name.getText().toString());
                        med.setDose(Double.parseDouble(dose.getText().toString()));
                        med.setFrequency(frequency.getText().toString());
                        med.setReason(reason.getText().toString());
                        med.setDoseUnits(doseUnits.getSelectedItem().toString());
                        //adds the updated info for the entry to the db
                        dbHelper.updateMedication(med);
                        //display a success message and navigate back to home screen
                        Toast.makeText(getApplicationContext(), "Medication Information updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MedicationsActivity.this, ActivityContainer.class);
                        startActivity(intent);
                    } else {
                        //display error message
                        AlertDialog a = new AlertDialog.Builder(add.getContext()).create();
                        a.setTitle("Missing Information");
                        a.setMessage("Please make sure you've filled out the necessary information");
                        a.show();
                    }
                }
            }
        });


    }

    //get index of selection in a spinner
    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;

    }
}