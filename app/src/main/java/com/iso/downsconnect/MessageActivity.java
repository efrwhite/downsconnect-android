package com.iso.downsconnect;

import android.app.AlertDialog;
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
import com.iso.downsconnect.objects.Message;

import java.util.Calendar;
//activity for creating new messages as well as displaying/updating existing ones
public class MessageActivity extends AppCompatActivity {
    private TextView currentTime, history;
    private EditText messageText;
    private Message message;
    private DBHelper db;
    private Button save;
    private Entry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //get messageID to determine if this is a new entry or not
        Intent intent = getIntent();
        String msgID = intent.getStringExtra("msgID");
        int id = Integer.parseInt(msgID);

        //get current childID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        //initialize variables
        final Button back = findViewById(R.id.backButton);
        currentTime = findViewById(R.id.messageTime);
        messageText = findViewById(R.id.messageEdit);
        history = findViewById(R.id.msgHistory);
        message = new Message();
        message.setChildID(childID);
        db = new DBHelper(this);
        save = findViewById(R.id.messageSave);
        entry = new Entry();

        //if it is an existing message entry, display the message's info
        if(id != -1){
            save.setEnabled(false);
            message = db.getMessage(id);
            messageText.setText(message.getMessage());
        }

        //button for saving info to db
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if all the fields have been filled out
                if(!messageText.getText().toString().equals("")){
                    //set info in the object
                    message.setMessage(messageText.getText().toString());

                    //fill in info for the entry object
                    entry.setEntryText("Saved a message for " + db.getChildName(childID));
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    entry.setChildID(childID);
                    entry.setEntryType("Message");

                    //adds the message entry to the db and return it's id
                    long result = db.addMessage(message);

//                    Log.i("msgRes", String.valueOf(result));
                    //save the message entry's id in the entry object and add it to the db
                    entry.setForeignID(result);
                    db.addEntry(entry);

                    //display success message
                    Toast.makeText(getApplicationContext(), "Message infomation saved", Toast.LENGTH_SHORT).show();

                    //navigate back to home screen
                    Intent intent = new Intent(MessageActivity.this, ActivityContainer.class);
                    startActivity(intent);

                }
                else {
                    //display error message
                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });

        //navigates to history page to display all past messages
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this, ListingActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
            }
        });



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

        //button that navigates back to home screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });
    }
}
