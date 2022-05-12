package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.helpers.DateHandler;
import com.iso.downsconnect.objects.MedicalInfo;

import java.util.Calendar;
//Activity to display medical info in more detail
public class MedicalListingsActivity extends AppCompatActivity {
    private TextView date, name, p_type, visit, height, weight, head, temp, age;
    private Button back;
    private MedicalInfo info = new MedicalInfo();
    private DBHelper helper;
    private DateHandler month = new DateHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_listings);

        //get current child ID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        //get medicalID of the entry you're looking at
        int medicalID = getIntent().getIntExtra("medical", 0);

        //get child's age and the type of provider that the visit was for
        String childAge = getIntent().getStringExtra("age");
        final String type = getIntent().getStringExtra("type");

        //initialize objects
        date = findViewById(R.id.dateText);
        name = findViewById(R.id.nameText);
        p_type = findViewById(R.id.typeText);
        height = findViewById(R.id.heightText);
        visit = findViewById(R.id.visitText);
        weight = findViewById(R.id.weightText);
        head = findViewById(R.id.headText);
        temp = findViewById(R.id.tempText);
        back = findViewById(R.id.backButton);
        age = findViewById(R.id.ageText);
        helper = new DBHelper(this);
        TextView currentTime = findViewById(R.id.current_time_text);

        //get current time and display it it in the textview
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String realMins;
        //adjust minute string if minutes are less than 10
        if(minute <= 10){
            realMins = "0" + minute;
        }
        else{
            realMins = String.valueOf(minute);
        }
        //determines whether the time is in AM or PM
        if(hour > 12){
            hour = hour - 12;
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + "PM");
        }
        else if(hour == 12){
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + "PM");
        }
        else{
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + "AM");
        }


        //get medical entry's info
        info = helper.getMedical(medicalID);

        //object to store the date the medical visit occurred
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(info.getDoctorDate());

        //display child's age
//        age.setText(childAge);

        //display visit date
        date.setText(month.getMonth(cal.get(Calendar.MONTH)) + " " + cal.get(Calendar.DATE) + ", " + cal.get(Calendar.YEAR));

        //display the rest of the visit's info
        name.setText(info.getProvider());
        p_type.setText(info.getProviderType());
        height.setText(info.getHeight());
        visit.setText(info.getVisit());
        weight.setText(info.getWeight());
        head.setText(info.getHeadInfo());
        temp.setText(info.getTemperatureInfo());

        //calculate the age of the child during the visit and display it
        calcAge(childID, info);

        //button for navigating back to home screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalListingsActivity.this, ProviderListingActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });

    }

    //calculate the age of the child during the visit and display it
    private void calcAge(int childID, MedicalInfo medical) {
        //calculate the age of the child
        long difference = medical.getDoctorDate() - helper.getChildBirthday(childID);
        long days = difference / (24 * 60 * 60 * 1000);
        int months = (int) days / 30;
        int years = (int) days / 365;

        //display correct unit based on child's age
        if(days < 31){
            age.setText(days + " days");
        }
        else if(days >= 31 && days <= 547){
            age.setText(months + " months");
        }
        else{
            age.setText(years + "yrs");
        }
    }
}