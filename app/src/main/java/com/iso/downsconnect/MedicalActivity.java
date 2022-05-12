package com.iso.downsconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.MedicalInfo;

import java.util.Calendar;
//Activity for navigating to the different medical pages
public class MedicalActivity extends AppCompatActivity{
    private EditText doctorDatePicker;
    private long doctorDate = 0;
    private long time;
    private MedicalInfo medicalInfo = new MedicalInfo();
    private Button back, save, doctorVisitBtn, OTBtn, PTBtn, opthBtn, speech, hearing, dental, cardio, neck, height, weight, circumference, vaccine, medications;
    private DBHelper dbHelper;
    private TextView allergyText, guidelines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);

        //get current child ID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        //initialize variables
        dbHelper = new DBHelper(this);
        back = findViewById(R.id.backButton);
        save = findViewById(R.id.saveButton);
        TextView currentTime = findViewById(R.id.current_time_text);
        doctorVisitBtn = findViewById(R.id.doctorVisitButton);
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
        allergyText = findViewById(R.id.allergyListTextView);
        vaccine = findViewById(R.id.vaccineButton);
        guidelines = findViewById(R.id.guidelinesHyperlink);
        medications = findViewById(R.id.medicationButton);

        //get child's allergies and display them
        String allergies = dbHelper.getAllergies(childID);
        if(allergies != null && !allergies.equals("")) {
            allergyText.setText(allergies);
        }
        else{
            allergyText.setText("No Allergies");
        }



        //buttons for navigating to the different medical provider pages for listing the visit info
        OTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                //defines which type of provider visits you want to see
                intent.putExtra("type", "OT");
                startActivity(intent);
            }
        });

        PTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                //defines which type of provider visits you want to see
                intent.putExtra("type", "PT");
                startActivity(intent);
            }
        });

        opthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                //defines which type of provider visits you want to see
                intent.putExtra("type", "Ophthalmology");
                startActivity(intent);
            }
        });

        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                //defines which type of provider visits you want to see
                intent.putExtra("type", "Speech");
                startActivity(intent);
            }
        });

        hearing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                //defines which type of provider visits you want to see
                intent.putExtra("type", "Hearing");
                startActivity(intent);
            }
        });

        dental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                //defines which type of provider visits you want to see
                intent.putExtra("type", "Dental");
                startActivity(intent);
            }
        });

        cardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                //defines which type of provider visits you want to see
                intent.putExtra("type", "Cardio");
                startActivity(intent);
            }
        });

        neck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ProviderListingActivity.class);
                //defines which type of provider visits you want to see
                intent.putExtra("type", "Neck");
                startActivity(intent);
            }
        });

        //button for navigating to activity for inserting medical visit info
        doctorVisitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, DoctorsVisitActivity.class);
                //defines that this is a new entry and not one that already exist
                intent.putExtra("medID", -1);
                startActivity(intent);
            }
        });

        //buttons for display the child's various measurements throughout the years
        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ChartActivity.class);
                //defines which type of measurement you are looking at
                intent.putExtra("chart", "Height");
                startActivity(intent);
            }
        });

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ChartActivity.class);
                //defines which type of measurement you are looking at
                intent.putExtra("chart", "Weight");
                startActivity(intent);
            }
        });

        circumference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ChartActivity.class);
                //defines which type of measurement you are looking at
                intent.putExtra("chart", "HeadSize");
                startActivity(intent);
            }
        });

        //navigates to the vaccine activity for adding and updating vaccine info
        vaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, VaccineActivity.class);
                startActivity(intent);
            }
        });

        //hyperlink that navigates to phone's browser for guideline info
        guidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://pediatrics.aappublications.org/content/128/2/393"));
                startActivity(intent);
            }
        });

        //navigates to the vaccine activity for adding and updating medication info
        medications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, MedicationHistoryActivity.class);
                intent.putExtra("mediID", "-1");
                startActivity(intent);
            }
        });

        //get current time and display it it in the textview
        Calendar calendar = Calendar.getInstance();
        time = calendar.getTimeInMillis();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String realMins;
        //adjust minute string if minutes are less than 10
        if(minute < 10){
            realMins = "0" + minute;
        }
        else{
            realMins = String.valueOf(minute);
        }
        //determines whether the time is in AM or PM
        if(hour >= 12){
            hour = hour - 12;
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + " PM");
        }
        else{
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + " AM");
        }

        //button that navigates back to home screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });
    }
}

