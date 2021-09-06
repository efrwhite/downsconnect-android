package com.iso.downsconnect;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.helpers.DateHandler;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Milestone;

import java.util.Calendar;

public class MilestoneActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private boolean roll, walk, stand, sit, val;
    private EditText standing, sitting, rolling, walking;
    private DateHandler month_;
    private DBHelper helper;
    private Milestone milestone;
    private Button save;
    private TextView standAge, sitAge, walkAge, rollAge;
    private long childBirthday;
    private Entry entry = new Entry();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestone);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);
        helper = new DBHelper(this);


        final Button back = findViewById(R.id.backButton);
        entry.setChildID(childID);
        entry.setEntryText("Updated milestone information for " + helper.getChildName(childID));

        save = findViewById(R.id.saveButton);
//        standing = findViewById(R.id.standingDatePicker);
//        sitting = findViewById(R.id.sittingDatePicker);
//        rolling = findViewById(R.id.rolledDatePicker);
//        walking = findViewById(R.id.walkingDatePicker);
//        standAge = findViewById(R.id.calcStandingAge);
//        sitAge = findViewById(R.id.calcSittingAge);
//        rollAge = findViewById(R.id.calcRolledOverAge);
//        walkAge = findViewById(R.id.calcWalkingAge);
        month_ = new DateHandler();
        milestone = new Milestone();


        milestone.setChildId(childID);
        milestone.setWalkingDate(0);
        milestone.setRollingDate(0);
        milestone.setSittingDate(0);
        milestone.setStandingDate(0);

        final Milestone stone = helper.getMilestone(childID);
        childBirthday = helper.getChildBirthday(childID);


        if(stone != null){
            milestone.setStandingDate(stone.getStandingDate());
            milestone.setSittingDate(stone.getSittingDate());
            milestone.setRollingDate(stone.getRollingDate());
            milestone.setWalkingDate(stone.getWalkingDate());
            if(stone.getStandingDate() != 0) {
                Calendar standTime = Calendar.getInstance();
                standTime.setTimeInMillis(stone.getStandingDate());
                standing.setText(month_.getMonth(standTime.get(Calendar.MONTH)) + " " + standTime.get(Calendar.DATE) + ", " + standTime.get(Calendar.YEAR));
                long difference = standTime.getTimeInMillis() - childBirthday;
                long days = difference / (24 * 60 * 60 * 1000);
                int months = (int) days / 30;
                int years = (int) days / 365;
                if(days <= 547){
                    standAge.setText(months + " months");
                }
                else{
                    standAge.setText(years + "yrs");
                }
            }
            if(stone.getSittingDate() != 0){
                Calendar sitTime = Calendar.getInstance();
                sitTime.setTimeInMillis(stone.getSittingDate());
                sitting.setText(month_.getMonth(sitTime.get(Calendar.MONTH)) + " " + sitTime.get(Calendar.DATE) + ", " + sitTime.get(Calendar.YEAR));
                long difference = sitTime.getTimeInMillis() - childBirthday;
                long days = difference / (24 * 60 * 60 * 1000);
                int months = (int) days / 30;
                int years = (int) days / 365;
                if(days <= 547){
                    sitAge.setText(months + " months");
                }
                else{
                    sitAge.setText(years + "yrs");
                }
            }
            if(stone.getRollingDate() != 0){
                Calendar rollTime = Calendar.getInstance();
                rollTime.setTimeInMillis(stone.getRollingDate());
                rolling.setText(month_.getMonth(rollTime.get(Calendar.MONTH)) + " " + rollTime.get(Calendar.DATE) + ", " + rollTime.get(Calendar.YEAR));
                long difference = rollTime.getTimeInMillis() - childBirthday;
                long days = difference / (24 * 60 * 60 * 1000);
                int months = (int) days / 30;
                int years = (int) days / 365;
                if(days <= 547){
                    rollAge.setText(months + " months");
                }
                else{
                    rollAge.setText(years + "yrs");
                }
            }
            if(stone.getWalkingDate() != 0){
                Calendar walkTime = Calendar.getInstance();
                walkTime.setTimeInMillis(stone.getWalkingDate());
                walking.setText(month_.getMonth(walkTime.get(Calendar.MONTH)) + " " + walkTime.get(Calendar.DATE) + ", " + walkTime.get(Calendar.YEAR));
                long difference = childBirthday - walkTime.getTimeInMillis();
                long days = difference / (24 * 60 * 60 * 1000);
                int months = (int) days / 30;
                int years = (int) days / 365;
                if(days <= 547){
                    walkAge.setText(months + " months");
                }
                else{
                    walkAge.setText(years + "yrs");
                }
            }

        }


//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(rolling.getText().toString().equals("") && walking.getText().toString().equals("") && sitting.getText().toString().equals("") && standing.getText().toString().equals("")){
//                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
//                    a.setTitle("Missing Information");
//                    a.setMessage("Please make sure you've filled out the necessary information");
//                    a.show();
//                }
//                else{
//                    if(stone != null){
//                        val = helper.updateMilestone(milestone);
//                        entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
//                    }
//                    else{
//                        helper.addMilestone(milestone);
//                        entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
//                    }
//
//                    Intent intent = new Intent(MilestoneActivity.this, ActivityContainer.class);
//                    startActivity(intent);
//                }
//            }
//        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MilestoneActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

//        standing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                stand = true;
//                roll = false;
//                walk = false;
//                sit = false;
//                showDatePickerDialog();
//            }
//        });
//
//        sitting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sit = true;
//                roll = false;
//                walk = false;
//                stand = false;
//                showDatePickerDialog();
//            }
//        });
//
//        rolling.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                roll = true;
//                walk = false;
//                stand = false;
//                sit = false;
//                showDatePickerDialog();
//            }
//        });
//
//        walking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                walk = true;
//                roll = false;
//                stand = false;
//                sit = false;
//                showDatePickerDialog();
//            }
//        });
    }

    private void showDatePickerDialog() {
        @SuppressLint("ResourceType") DatePickerDialog datePickerDialog = new DatePickerDialog(this, 2,  this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        if(stand){
            calendar.set(year, month, dayOfMonth);
            milestone.setStandingDate(calendar.getTimeInMillis());
            standing.setText(month_.getMonth(month) + " " + dayOfMonth + ", " + year);
            long difference = calendar.getTimeInMillis() - childBirthday;
            long days = difference / (24 * 60 * 60 * 1000);
            int months = (int) days / 30;
            int years = (int) days / 365;
            if(days <= 547){
                standAge.setText(months + " months");
            }
            else{
                standAge.setText(years + "yrs");
            }
        }
        else if(sit){
            calendar.set(year, month, dayOfMonth);
            milestone.setSittingDate(calendar.getTimeInMillis());
            sitting.setText(month_.getMonth(month) + " " +dayOfMonth + ", " + year);
            long difference = calendar.getTimeInMillis() - childBirthday;
            long days = difference / (24 * 60 * 60 * 1000);
            int months = (int) days / 30;
            int years = (int) days / 365;
            if(days <= 547){
                sitAge.setText(months + " months");
            }
            else{
                sitAge.setText(years + "yrs");
            }
        }
        else if(roll){
            calendar.set(year, month, dayOfMonth);
            milestone.setRollingDate(calendar.getTimeInMillis());
            rolling.setText(month_.getMonth(month) + " " +dayOfMonth + ", " + year);
            long difference = calendar.getTimeInMillis() - childBirthday;
            long days = difference / (24 * 60 * 60 * 1000);
            int months = (int) days / 30;
            int years = (int) days / 365;
            if(days <= 547){
                rollAge.setText(months + " months");
            }
            else{
                rollAge.setText(years + "yrs");
            }
        }
        else{
            calendar.set(year, month, dayOfMonth);
            milestone.setWalkingDate(calendar.getTimeInMillis());
            walking.setText(month_.getMonth(month) + " " +dayOfMonth + ", " + year);
            long difference = calendar.getTimeInMillis() - childBirthday;
            long days = difference / (24 * 60 * 60 * 1000);
            int months = (int) days / 30;
            int years = (int) days / 365;
            if(days <= 547){
                walkAge.setText(months + " months");
            }
            else{
                walkAge.setText(years + "yrs");
            }
        }
    }
}
