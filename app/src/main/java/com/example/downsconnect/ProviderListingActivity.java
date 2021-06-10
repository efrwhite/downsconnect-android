package com.example.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.downsconnect.objects.MedicalInfo;

import java.util.ArrayList;

public class ProviderListingActivity extends AppCompatActivity {
    private DBHelper helper;
    private ArrayList<MedicalInfo> medicalInfos = new ArrayList<>();
    private LinearLayout linearLayout;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String type = getIntent().getStringExtra("type");
        Log.i("Tye", type);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 0);


        if(type.equals("Neck")) {
            setContentView(R.layout.activity_neck_safety);
            Button back = findViewById(R.id.back_button);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProviderListingActivity.this, MedicalActivity.class);
                    startActivity(intent);
                }
            });
        }
        else {
            setContentView(R.layout.activity_provider_listing);

            Button back = findViewById(R.id.back_button);
            linearLayout = findViewById(R.id.listingsLayout);
            title = findViewById(R.id.medicalText);
            helper = new DBHelper(this);
            title.setText(type + " visit info for " + helper.getChildName(childID));
            medicalInfos = helper.getSpecificProviders(childID, type);
            Log.i("mSize", String.valueOf(medicalInfos.size()));

            for (MedicalInfo medical : medicalInfos) {
                final LinearLayout horizontalLayout = new LinearLayout(this);
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                marginLayoutParams.setMargins(50, 0, 50, 10);
                textParams.setMargins(50, 0, 10, 30);

                horizontalLayout.setLayoutParams(marginLayoutParams);
                TextView age = new TextView(getApplicationContext());
                age.setText("Age: " + (medical.getDoctorDate() - helper.getChildBirthday(childID)));
                age.setTextSize(15);
                horizontalLayout.addView(age);
                linearLayout.addView(horizontalLayout);
            }

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProviderListingActivity.this, MedicalActivity.class);
                    startActivity(intent);
                }
            });


        }
    }
}