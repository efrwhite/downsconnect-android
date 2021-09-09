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
import com.iso.downsconnect.objects.Message;

import java.util.Calendar;

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

        Intent intent = getIntent();
        String msgID = intent.getStringExtra("msgID");
        int id = Integer.parseInt(msgID);

        final Button back = findViewById(R.id.backButton);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        currentTime = findViewById(R.id.messageTime);
        messageText = findViewById(R.id.messageEdit);
        history = findViewById(R.id.msgHistory);
        message = new Message();
        message.setChildID(childID);
        db = new DBHelper(this);
        save = findViewById(R.id.messageSave);
        entry = new Entry();

        if(id != -1){
            save.setEnabled(false);
            message = db.getMessage(id);
            messageText.setText(message.getMessage());
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!messageText.getText().toString().equals("")){
                    message.setMessage(messageText.getText().toString());

                    entry.setEntryText("Saved a message for " + db.getChildName(childID));
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    entry.setChildID(childID);
                    entry.setEntryType("Message");

                    long result = db.addMessage(message);
                    Log.i("msgRes", String.valueOf(result));
                    entry.setForeignID(result);
                    db.addEntry(entry);

                    Toast.makeText(getApplicationContext(), "Message infomation saved", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MessageActivity.this, ActivityContainer.class);
                    startActivity(intent);

                }
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this, ListingActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
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
                Intent intent = new Intent(MessageActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });
    }
}
