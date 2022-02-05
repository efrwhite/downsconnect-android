package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

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

public class MedicationHistoryActivity extends AppCompatActivity {
    ArrayList<Medication> medications = new ArrayList<>();
    LinearLayout med;
    DBHelper dbHelper;
    private Button addMedication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_history);

        final int medID = getIntent().getIntExtra("mediID", -1);


        //get currently stored childID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        med = findViewById(R.id.medLinear);
        dbHelper = new DBHelper(this);
        addMedication = findViewById(R.id.addMed);
        medications = dbHelper.getAllMedication(childID);

        addMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicationHistoryActivity.this, MedicationsActivity.class);
                intent.putExtra("mediID", medID);
                startActivity(intent);
            }
        });

        for(final Medication medication: medications){
            //create layout parameters for each linear layout
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            // set margins for parameters
            marginLayoutParams.setMargins(20, 0, 50,10);

            //create horizontal linear layouts for nesting in main linear layout
            //allows for items to be placed next to each other
            LinearLayout mainLayout = new LinearLayout(this);
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.setLayoutParams(marginLayoutParams);

            layoutParams.setMargins(200, 0, 0, 30);
            textParams.setMargins(10, 30, 100, 30);

            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setLayoutParams(marginLayoutParams);

            LinearLayout horizontalLayout2 = new LinearLayout(this);
            horizontalLayout2.setOrientation(LinearLayout.HORIZONTAL);

            //create text view to display name of medication
            TextView medName = new TextView(this);
            medName.setText(medication.getName());
            medName.setTextSize(15);
            medName.setTextColor(Color.BLACK);
            medName.setWidth(500);
            medName.setLayoutParams(textParams);
            horizontalLayout.addView(medName);

            //Button to display more information about the medication
            Button info = new Button(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                info.setBackground(getDrawable(R.drawable.right_arrow));
            }
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = medication.getMedID();
                    Intent intent = new Intent(MedicationHistoryActivity.this, MedicationsActivity.class);
                    intent.putExtra("mediID", id);
                    startActivity(intent);
                }
            });
            info.setWidth(1);
            info.setLayoutParams(textParams);
            horizontalLayout.addView(info);

            //add nested layouts to their parent layouts
            mainLayout.addView(horizontalLayout);
            med.addView(mainLayout);

        }
    }
}