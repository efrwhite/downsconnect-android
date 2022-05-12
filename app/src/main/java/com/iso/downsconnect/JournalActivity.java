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
//activity for creating new journal's and displaying/updating existing journals
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

        //Get journal id to figure out whether this is a new entry of not
        Intent i = getIntent();
        String journID = i.getStringExtra("journID");

        //get current child ID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        //declare and initialize variables
        currentTime = findViewById(R.id.diaryTime);
        title = findViewById(R.id.journalTitle);
        notes = findViewById(R.id.journalNotes);
        save = findViewById(R.id.journalButton);
        history = findViewById(R.id.journalHistory);
        final Button back = findViewById(R.id.backButton);
        journal = new Journal();
        entry = new Entry();
        db = new DBHelper(this);

        //if viewing an already existing activity entry, display the information about that entry
        if(!journID.equals("-1")){
            journal = db.getJournal(Integer.parseInt(journID));
            save.setEnabled(false);
            title.setText(journal.getTitle());
            notes.setText(journal.getNotes());
        }

        //set child ID for both entry and journal objects
        entry.setChildID(childID);
        journal.setChildID(childID);

        //history button for navigating to listing page which display all current journal entries in the db
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JournalActivity.this, ListingActivity.class);
                intent.putExtra("type", 3);
                startActivity(intent);
            }
        });

        //button for saving info to db
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks if all fields are filled out
                if(!title.getText().toString().equals("") && !notes.getText().toString().equals("")){
                    //set info in journal object
                    journal.setTitle(title.getText().toString());
                    journal.setNotes(notes.getText().toString());

                    //set info for entry object
                    entry.setEntryText("Saved a journal entry for " + db.getChildName(childID));
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    entry.setEntryType("Journal");

                    //add journal to db and get the id associated with it
                    long result = db.addJournal(journal);

                    //add new entry to database and navigate back to home page
                    entry.setForeignID(result);
                    db.addEntry(entry);

                    //display message alerting user that the journal was saved and navigate to home page
                    Toast.makeText(getApplicationContext(), "Journal infomation saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(JournalActivity.this, ActivityContainer.class);
                    startActivity(intent);
                }
            }
        });

        //Calculate and display the current time
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

        //button for navigating back to home screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JournalActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });
    }
}
