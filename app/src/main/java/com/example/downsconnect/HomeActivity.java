package com.example.downsconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
    }
}
