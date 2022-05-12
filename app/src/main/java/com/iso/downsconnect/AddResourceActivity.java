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
//Activity that allows users to add a new resource to the db
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

        //get current child ID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);
//        Log.i("chid", String.valueOf(childID));


        //declare and initialize objects
        name = findViewById(R.id.res_name);
        url = findViewById(R.id.res_url);
        add = findViewById(R.id.addRes);
        entry = new Entry();
        helper = new DBHelper(this);
        resource = new Resource();

        //button to save resource info to db
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().equals("") && !url.getText().toString().equals("")){
                    //add info to resource object
                    resource.setName(name.getText().toString());
                    resource.setURL(url.getText().toString());

                    //add entry to db and get the id associated with it
                    long id = helper.addResource(resource);

                    //add info to the entry object and add it to the db
                    entry.setEntryText("Added a new resource");
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    entry.setChildID(childID);
                    entry.setEntryType("Resource");
                    entry.setForeignID(id);
                    helper.addEntry(entry);

                    //display message to let user know that a resource was added
                    Toast.makeText(getApplicationContext(),"Added Resource", Toast.LENGTH_SHORT).show();

                    //navigate back to the home page
                    Intent intent = new Intent(AddResourceActivity.this, ActivityContainer.class);
                    startActivity(intent);
                }
                else {
                    //display an error message if there are missing fields
                    AlertDialog a = new AlertDialog.Builder(add.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });
    }
}