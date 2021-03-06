package com.example.downsconnect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DBHelper helper = new DBHelper(this);

        Button feed = findViewById(R.id.feedButton);
        Button activity = findViewById(R.id.activityButton);
        Button sleep = findViewById(R.id.sleepButton);
        Button mood = findViewById(R.id.moodButton);
        Button resources = findViewById(R.id.resourcesButton);
        Button medical = findViewById(R.id.medicalButton);
        Button message = findViewById(R.id.messageButton);
        Button milestone = findViewById(R.id.milestoneButton);
        Button signout = findViewById(R.id.signoutButton);
        Button photo = findViewById(R.id.photoButton);
        Button diary = findViewById(R.id.diaryButton);
        Button more = findViewById(R.id.moreButton);
        Button signOut = findViewById(R.id.signoutButton);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Sign Out")
                        .setMessage("Are you sure you want to sign out")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    sharedPreferences.edit().putBoolean("signedIn", false).commit();
                                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                                    startActivity(intent);
                            }
                        })
                        .setNegativeButton("no", null).show();
            }

        });


        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FeedActivity.class);
                startActivity(intent);
            }
        });

        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ActivityActivity.class);
                startActivity(intent);
            }
        });

        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SleepActivity.class);
                startActivity(intent);
            }
        });

        mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MoodActivity.class);
                startActivity(intent);
            }
        });

        resources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ResourcesActivity.class);
                startActivity(intent);
            }
        });

        medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MedicalActivity.class);
                startActivity(intent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });

        milestone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MilestoneActivity.class);
                startActivity(intent);
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PhotoActivity.class);
                startActivity(intent);
            }
        });

        diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MoreActivity.class);
                startActivity(intent);
            }
        });
    }
}
