package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Resource;

import java.util.ArrayList;
import java.util.Calendar;

public class AddResourceActivity extends AppCompatActivity {
    private EditText name, url;
    private Button add;
    private Resource resource;
    private DBHelper helper;
    private Entry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);
        Log.i("chid", String.valueOf(childID));

        name = findViewById(R.id.res_name);
        url = findViewById(R.id.res_url);
        add = findViewById(R.id.addRes);

        entry = new Entry();
        helper = new DBHelper(this);

        resource = new Resource();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().equals("") && !url.getText().toString().equals("")){
                    resource.setName(name.getText().toString());
                    resource.setURL(url.getText().toString());
                    long id = helper.addResource(resource);
                    entry.setEntryText("Added a new resource");
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    entry.setChildID(childID);
                    entry.setEntryType("Resource");
                    entry.setForeignID(id);
                    helper.addEntry(entry);
                    Toast.makeText(getApplicationContext(),"Added Resource", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddResourceActivity.this, ActivityContainer.class);
                    startActivity(intent);
                }
                else {
                    AlertDialog a = new AlertDialog.Builder(add.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });
    }
}