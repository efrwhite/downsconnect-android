package com.example.downsconnect;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.downsconnect.objects.Milestone;
import com.example.downsconnect.objects.Month;

import java.util.Calendar;

public class MilestoneActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private boolean roll, walk, stand, sit, val;
    private EditText standing, sitting, rolling, walking;
    private Month month_;
    private DBHelper helper;
    private Milestone milestone;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestone);

        final Button back = findViewById(R.id.backButton);
        save = findViewById(R.id.saveButton);
        standing = findViewById(R.id.standingDatePicker);
        sitting = findViewById(R.id.sittingDatePicker);
        rolling = findViewById(R.id.rolledDatePicker);
        walking = findViewById(R.id.walkingDatePicker);
        month_ = new Month();
        milestone = new Milestone();
        
        milestone.setChildId(0);
        milestone.setWalkingDate(0);
        milestone.setRollingDate(0);
        milestone.setSittingDate(0);
        milestone.setStandingDate(0);

        helper = new DBHelper(this);
        final Milestone stone = helper.getMilestone(0);

        if(stone != null){
            milestone.setStandingDate(stone.getStandingDate());
            milestone.setSittingDate(stone.getSittingDate());
            milestone.setRollingDate(stone.getRollingDate());
            milestone.setWalkingDate(stone.getWalkingDate());
            if(stone.getStandingDate() != 0) {
                Calendar standTime = Calendar.getInstance();
                standTime.setTimeInMillis(stone.getStandingDate());
                standing.setText(month_.getMonth(standTime.get(Calendar.MONTH)) + " " + standTime.get(Calendar.DATE) + ", " + standTime.get(Calendar.YEAR));
            }
            if(stone.getSittingDate() != 0){
                Calendar sitTime = Calendar.getInstance();
                sitTime.setTimeInMillis(stone.getSittingDate());
                sitting.setText(month_.getMonth(sitTime.get(Calendar.MONTH)) + " " + sitTime.get(Calendar.DATE) + ", " + sitTime.get(Calendar.YEAR));
            }
            if(stone.getRollingDate() != 0){
                Calendar rollTime = Calendar.getInstance();
                rollTime.setTimeInMillis(stone.getRollingDate());
                rolling.setText(month_.getMonth(rollTime.get(Calendar.MONTH)) + " " + rollTime.get(Calendar.DATE) + ", " + rollTime.get(Calendar.YEAR));
            }
            if(stone.getWalkingDate() != 0){
                Calendar walkTime = Calendar.getInstance();
                walkTime.setTimeInMillis(stone.getWalkingDate());
                walking.setText(month_.getMonth(walkTime.get(Calendar.MONTH)) + " " + walkTime.get(Calendar.DATE) + ", " + walkTime.get(Calendar.YEAR));
            }

        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rolling.getText().toString().equals("") && walking.getText().toString().equals("") && sitting.getText().toString().equals("") && standing.getText().toString().equals("")){
                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
                else{
                    if(stone != null){
                        val = helper.updateMilestone(milestone);
                    }
                    else{
                        helper.addMilestone(milestone);
                    }

                    Intent intent = new Intent(MilestoneActivity.this, ActivityContainer.class);
                    startActivity(intent);
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MilestoneActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        standing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stand = true;
                roll = false;
                walk = false;
                sit = false;
                showDatePickerDialog();
            }
        });

        sitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sit = true;
                roll = false;
                walk = false;
                stand = false;
                showDatePickerDialog();
            }
        });

        rolling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roll = true;
                walk = false;
                stand = false;
                sit = false;
                showDatePickerDialog();
            }
        });

        walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walk = true;
                roll = false;
                stand = false;
                sit = false;
                showDatePickerDialog();
            }
        });
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
        }
        else if(sit){
            calendar.set(year, month, dayOfMonth);
            milestone.setSittingDate(calendar.getTimeInMillis());
            sitting.setText(month_.getMonth(month) + " " +dayOfMonth + ", " + year);
        }
        else if(roll){
            calendar.set(year, month, dayOfMonth);
            milestone.setRollingDate(calendar.getTimeInMillis());
            rolling.setText(month_.getMonth(month) + " " +dayOfMonth + ", " + year);
        }
        else{
            calendar.set(year, month, dayOfMonth);
            milestone.setWalkingDate(calendar.getTimeInMillis());
            walking.setText(month_.getMonth(month) + " " +dayOfMonth + ", " + year);
        }
    }
}
