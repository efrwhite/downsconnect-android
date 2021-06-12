package com.example.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.downsconnect.objects.MedicalInfo;
import com.example.downsconnect.objects.Month;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class ProviderListingActivity extends AppCompatActivity {
    private DBHelper helper;
    private ArrayList<MedicalInfo> medicalInfos = new ArrayList<>();
    private LinearLayout linearLayout;
    private TextView title;
    private Month month = new Month();
    private String childAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String type = getIntent().getStringExtra("type");
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
            linearLayout.setGravity(Gravity.CENTER);
            title = findViewById(R.id.medicalText);
            helper = new DBHelper(this);
            title.setText(type + " visit info for " + helper.getChildName(childID));
            medicalInfos = helper.getSpecificProviders(childID, type);
            Log.i("mSize", String.valueOf(childID));

            for (final MedicalInfo medical : medicalInfos) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                marginLayoutParams.setMargins(100, 0, 0,30);
                layoutParams.setMargins(200, 0, 0, 30);
                textParams.setMargins(5, 5, 2, 5);

                final LinearLayout horizontalLayout = new LinearLayout(this);
                horizontalLayout.setId(medical.getChildID());
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLayout.setLayoutParams(marginLayoutParams);
                horizontalLayout.setGravity(Gravity.CENTER);
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setStroke(6,getResources().getColor(R.color.colorPrimaryDark));
                //horizontalLayout.setBackgroundColor(Color.LTGRAY);
                horizontalLayout.setBackground(gradientDrawable);
                //horizontalLayout.setPadding(5, 10, 10, 10);

                LinearLayout verticalLayout = new LinearLayout(this);
                verticalLayout.setBackgroundColor(Color.LTGRAY);
                verticalLayout.setOrientation(LinearLayout.VERTICAL);
                verticalLayout.setLayoutParams(textParams);

                verticalLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProviderListingActivity.this, MedicalListingsActivity.class);
                        intent.putExtra("medical", medical.getMedicalID());
                        intent.putExtra("age", childAge);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    }
                });

                TextView age = new TextView(getApplicationContext());
                calcAge(childID, medical, age);
                age.setTextSize(15);
                //age.setLayoutParams(textParams);
                age.setWidth(800);
                age.setTextColor(Color.BLACK);

                TextView date = new TextView(this);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(medical.getDoctorDate());
                date.setText("Date: " + month.getMonth(calendar.get(Calendar.MONTH)) + " " +calendar.get(Calendar.DATE) + ", " + calendar.get(Calendar.YEAR));
                date.setTextSize(15);
                date.setWidth(500);
                date.setTextColor(Color.BLACK);

                TextView name = new TextView(this);
                name.setText("Provider: " + medical.getProvider());
                name.setTextSize(15);
                name.setWidth(500);
                name.setTextColor(Color.BLACK);

                verticalLayout.addView(date);
                verticalLayout.addView(age);
                verticalLayout.addView(name);
                horizontalLayout.addView(verticalLayout);
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

    private void calcAge(int childID, MedicalInfo medical, TextView text) {
        long difference = medical.getDoctorDate() - helper.getChildBirthday(childID);
        long days = difference / (24 * 60 * 60 * 1000);
        int months = (int) days / 30;
        int years = (int) days / 365;

        if(days < 31){
            childAge = "Age: " + days + " days";
            text.setText(childAge);
        }
        else if(days >= 31 && days <= 547){
            childAge = "Age: " + months + " months";
            text.setText(childAge);
        }
        else{
            childAge = "Age: " + years + "yrs";
            text.setText("Age: " + years + "yrs");
        }
    }
}