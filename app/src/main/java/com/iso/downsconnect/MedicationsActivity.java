package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Medication;

import java.util.Calendar;

public class MedicationsActivity extends AppCompatActivity {
    private Button back, add;
    private EditText name, dose, frequency, reason;
    private Spinner doseUnits;
    private Entry entry = new Entry();
    private Medication med = new Medication();
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicationsActivity.this, MedicalActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if all fields are filled out
                if(!name.getText().toString().equals("") && !dose.getText().toString().equals("") && !frequency.getText().toString().equals("")
                    && !reason.getText().toString().equals("") && !doseUnits.getSelectedItem().toString().equals("Select")){
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
                }
                else{
                    AlertDialog a = new AlertDialog.Builder(add.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });


    }
}