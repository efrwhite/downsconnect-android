package com.example.downsconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MedicalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);

        final Button back = findViewById(R.id.backButton);
        TextView currentTime = findViewById(R.id.current_time_text);

        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if(hour >= 12){
            hour = hour - 12;
            currentTime.setText("Today " + String.valueOf(hour) + ":" + String.valueOf(minute) + "PM");
        }
        else{
            currentTime.setText("Today " + String.valueOf(hour) + ":" + String.valueOf(minute) + "AM");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalActivity.this, HomeFragment.class);
                startActivity(intent);
            }
        });
    }
}
