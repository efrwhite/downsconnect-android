package com.example.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.downsconnect.objects.Activity;
import com.example.downsconnect.objects.DateHandler;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityListingActivity extends AppCompatActivity {
    private Button back;
    private DBHelper helper;
    private ArrayList<Activity> activities = new ArrayList<>();
    private LinearLayout linearLayout;
    private DateHandler dateHandler = new DateHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        helper = new DBHelper(this);
        back = findViewById(R.id.backButton);
        linearLayout = findViewById(R.id.activities_layout);

        activities = helper.getAllActivities(childID);

        addActivites();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityListingActivity.this, ActivityActivity.class);
                startActivity(intent);
            }
        });


    }

    private void addActivites(){
        Log.i("a_size", String.valueOf(activities.size()));
        for(Activity activity: activities){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(activity.getEntryTime());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            marginLayoutParams.setMargins(20, 0, 50,10);

            LinearLayout mainLayout = new LinearLayout(this);
            mainLayout.setTag(activity.getActivityID() + "Layout");
            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.setLayoutParams(marginLayoutParams);


            layoutParams.setMargins(200, 0, 0, 30);
            textParams.setMargins(20, 0, 10, 30);

            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setTag(activity.getActivityID() + "Layout");
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setLayoutParams(marginLayoutParams);

            LinearLayout horizontalLayout2 = new LinearLayout(this);
            horizontalLayout2.setTag(activity.getActivityID() + "Layout2");
            horizontalLayout2.setOrientation(LinearLayout.HORIZONTAL);
            //horizontalLayout2.setLayoutParams(marginLayoutParams);

            TextView date = new TextView(this);
            date.setText(dateHandler.writtenDate(calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), calendar.get(Calendar.YEAR)));
            date.setTextSize(15);
            date.setTextColor(Color.BLACK);
            date.setWidth(500);
            date.setLayoutParams(textParams);
            horizontalLayout.addView(date);
            mainLayout.addView(horizontalLayout);

            TextView c_activity = new TextView(this);
            c_activity.setText(activity.getChildActivity());
            c_activity.setTextSize(15);
            c_activity.setWidth(500);
            c_activity.setTextColor(Color.BLACK);
            c_activity.setLayoutParams(textParams);
            horizontalLayout.addView(c_activity);

            linearLayout.addView(mainLayout);
        }
    }
}