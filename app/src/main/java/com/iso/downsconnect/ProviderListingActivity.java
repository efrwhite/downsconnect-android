package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.helpers.DateHandler;
import com.iso.downsconnect.objects.MedicalInfo;

import java.util.ArrayList;
import java.util.Calendar;
//activity for displaying the medical entries for a specific provider type
//displays neck safety page as well
public class ProviderListingActivity extends AppCompatActivity {
    private DBHelper helper;
    private ArrayList<MedicalInfo> medicalInfos = new ArrayList<>();
    private LinearLayout linearLayout;
    private TextView title;
    private DateHandler month = new DateHandler();
    private String childAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get what type of listing this is
        final String type = getIntent().getStringExtra("type");

        //get current child ID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 0);

        childAge = "0";


        //if neck button was clicked, display the appropriate layout
        if(type.equals("Neck")) {
            //display the neck safety layout
            setContentView(R.layout.activity_neck_safety);
            //initialize object
            Button back = findViewById(R.id.back_button);
            //button for navigating back to medical activity
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProviderListingActivity.this, MedicalActivity.class);
                    startActivity(intent);
                }
            });
        }
        //if any other of the provider buttons were clicked, display the provider listing layout
        else {
            setContentView(R.layout.activity_provider_listing);

            //initialize variables
            Button back = findViewById(R.id.back_button);
            linearLayout = findViewById(R.id.listingsLayout);
            linearLayout.setGravity(Gravity.CENTER);
            title = findViewById(R.id.medicalText);
            helper = new DBHelper(this);
            title.setText(type + " visit info for " + helper.getChildName(childID));

            //get all the medical info of a specific type for the child
            medicalInfos = helper.getSpecificProviders(childID, type);

            //for each entry for the specific type of provider, create and display the needed objects
            for (final MedicalInfo medical : medicalInfos) {
                //create layout parameters for each linear layout
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                // set margins for parameters
                marginLayoutParams.setMargins(100, 0, 100,30);
                layoutParams.setMargins(200, 0, 0, 30);
                textParams.setMargins(5, 5, 2, 5);

                //create horizontal layout for holding objects
                final LinearLayout horizontalLayout = new LinearLayout(this);
                horizontalLayout.setId(medical.getChildID());
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLayout.setLayoutParams(marginLayoutParams);
                horizontalLayout.setGravity(Gravity.CENTER);
                //defines a border around the layout
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setStroke(6,getResources().getColor(R.color.colorPrimaryDark));
                //horizontalLayout.setBackgroundColor(Color.LTGRAY);
                horizontalLayout.setBackground(gradientDrawable);
                //horizontalLayout.setPadding(5, 10, 10, 10);

                //define vertical layout for placing objects correctly
                LinearLayout verticalLayout = new LinearLayout(this);
                //sets background color for the layout
                verticalLayout.setBackgroundColor(Color.parseColor("#FFBEBE"));
                verticalLayout.setOrientation(LinearLayout.VERTICAL);
                verticalLayout.setLayoutParams(textParams);

                //textview for displaying the child's age
                TextView age = new TextView(getApplicationContext());
                calcAge(childID, medical, age);
                age.setTextSize(15);
                //age.setLayoutParams(textParams);
                age.setWidth(800);
                age.setTextColor(Color.BLACK);

                //text view for displaying the date of the medical visit
                TextView date = new TextView(this);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(medical.getDoctorDate());
                date.setText("Date: " + month.getMonth(calendar.get(Calendar.MONTH)) + " " +calendar.get(Calendar.DATE) + ", " + calendar.get(Calendar.YEAR));
                date.setTextSize(15);
                date.setWidth(500);
                date.setTextColor(Color.BLACK);

                //textview to display name of the provider who did the medical visit
                TextView name = new TextView(this);
                name.setText("Provider: " + medical.getProvider());
                name.setTextSize(15);
                name.setWidth(500);
                name.setTextColor(Color.BLACK);

                //add each of the textview to the vertical layout
                verticalLayout.addView(date);
                verticalLayout.addView(age);
                verticalLayout.addView(name);

                //when a entry is clicked, navigate to the medical listing page to display more info
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

                //add vertical layout to horizontal layout
                horizontalLayout.addView(verticalLayout);
                //add horizontal layout to the main lienar layout
                linearLayout.addView(horizontalLayout);
            }

            //button for navigating back to medical page
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProviderListingActivity.this, MedicalActivity.class);
                    startActivity(intent);
                }
            });


        }
    }

    //calculate the age of the child during the visit and display it
    private void calcAge(int childID, MedicalInfo medical, TextView text) {
        //calculate the age of the child
        long difference = medical.getDoctorDate() - helper.getChildBirthday(childID);
        long days = difference / (24 * 60 * 60 * 1000);
        int months = (int) days / 30;
        int years = (int) days / 365;

        //display correct unit based on child's age
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