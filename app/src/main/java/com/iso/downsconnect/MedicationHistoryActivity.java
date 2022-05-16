package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.Medication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
//Activity that display a list of the current medications in the db
public class MedicationHistoryActivity extends AppCompatActivity {
    //declare variables
    ArrayList<Medication> medications = new ArrayList<>();
    LinearLayout med;
    DBHelper dbHelper;
    private Button addMedication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_history);

        //get medicalID to figure out whether this is an existing entry or a new one
        final int medID = getIntent().getIntExtra("mediID", -1);


        //get currently stored childID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        //initialize objects on the layout
        med = findViewById(R.id.medLinear);
        dbHelper = new DBHelper(this);
        addMedication = findViewById(R.id.addMed);

        //get all the current medications in the db
        medications = dbHelper.getAllMedication(childID);

        //button that navigates to activity for adding a new medication
        addMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicationHistoryActivity.this, MedicationsActivity.class);
                intent.putExtra("mediID", medID);
                startActivity(intent);
            }
        });
        //sorts the medications
        Collections.sort(medications);

        //loop through all the medications and display their info on the screen
        for(final Medication medication: medications){
            //create layout parameters for each linear layout
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            // set margins for parameters
            marginLayoutParams.setMargins(20, 0, 0,10);
            layoutParams.setMargins(0, 0, 0, 30);
            textParams.setMargins(0, 30, 0, 30);

            //create horizontal linear layouts for nesting in main linear layout
            //allows for items to be placed next to each other
            LinearLayout mainLayout = new LinearLayout(this);
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.setLayoutParams(marginLayoutParams);

            //create secondary horizontal layout for holding objects
            final LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setLayoutParams(marginLayoutParams);
            horizontalLayout.setId(medication.getMedID());

//            LinearLayout horizontalLayout2 = new LinearLayout(this);
//            horizontalLayout2.setOrientation(LinearLayout.HORIZONTAL);

            //create text view to display name of medication
            TextView medName = new TextView(this);
            medName.setText(medication.getName());
            medName.setTextSize(15);
            medName.setTextColor(Color.BLACK);
            medName.setWidth(300);
            medName.setLayoutParams(textParams);
            horizontalLayout.addView(medName);

            //Button to display more information about the medication
            Button info = new Button(getApplicationContext());
            info.setText("Edit");
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = medication.getMedID();
                    //navigates to medication page with the id of the medication you want to look at
                    Intent intent = new Intent(MedicationHistoryActivity.this, MedicationsActivity.class);
                    intent.putExtra("mediID", id);
                    startActivity(intent);
                }
            });
            info.setWidth(1);
            info.setLayoutParams(layoutParams);
            //add textview to layout
            horizontalLayout.addView(info);

            //Button to delete the medication
            final Button delete = new Button(getApplicationContext());
            delete.setText("Delete");
            delete.setWidth(1);
            delete.setId(medication.getMedID());
            delete.setLayoutParams(layoutParams);
            horizontalLayout.addView(delete);
            //creates a dialog when the button is clicked asking if the user wants to continue with deleting
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(MedicationHistoryActivity.this)
                            .setTitle("Delete Medication")
                            .setMessage("Are you sure you want to delete this medication?")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //if yes is clicked, deletes the entry from the db and deletes the layout containing it's info
                                    dbHelper.deleteEntry(medication.getMedID(),"Med");
                                    med.removeView((View) horizontalLayout.getParent());
                                }
                            })
                            .setNegativeButton("no", null).show();
                }
            });


            //add nested layouts to their parent layouts
            mainLayout.addView(horizontalLayout);
            med.addView(mainLayout);

        }
    }
}