package com.iso.downsconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Journal;

import java.util.Calendar;

public class JournalActivity extends AppCompatActivity {
    private TextView currentTime, history;
    private EditText title, notes;
    private Button save;
    private Journal journal;
    private Entry entry;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        final Button back = findViewById(R.id.backButton);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        currentTime = findViewById(R.id.diaryTime);
        title = findViewById(R.id.journalTitle);
        notes = findViewById(R.id.journalNotes);
        save = findViewById(R.id.journalButton);
        history = findViewById(R.id.journalHistory);
        journal = new Journal();
        entry = new Entry();
        db = new DBHelper(this);

        entry.setChildID(childID);
        journal.setChildID(childID);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JournalActivity.this, ListingActivity.class);
                intent.putExtra("type", 3);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!title.getText().toString().equals("") && !notes.getText().toString().equals("")){
                    journal.setTitle(title.getText().toString());
                    journal.setNotes(notes.getText().toString());

                    entry.setEntryText("Saved a journal entry for " + db.getChildName(childID));
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    entry.setEntryType("Journal");

                    long result = db.addJournal(journal);
                    Log.i("journRes", String.valueOf(result));
                    entry.setForeignID(result);
                    db.addEntry(entry);

                    Toast.makeText(getApplicationContext(), "Journal infomation saved", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(JournalActivity.this, ActivityContainer.class);
                    startActivity(intent);
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String realMins;
        if(minute <= 10){
            realMins = "0" + minute;
        }
        else{
            realMins = String.valueOf(minute);
        }
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JournalActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });
    }
}
