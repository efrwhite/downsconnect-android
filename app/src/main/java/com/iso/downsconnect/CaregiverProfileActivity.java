package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.AccountHolder;
//activity for creating new caregiver profiles as well as viewing/updating existing ones
public class CaregiverProfileActivity extends AppCompatActivity {
    private DBHelper helper;
    private EditText name, phone, pass, username;
    private Button back, save;
    private AccountHolder accountHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_profile);

        //get current user information
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final long userID = sharedPreferences.getLong("user", 1);
        final String care_user = getIntent().getStringExtra("care_name");

        //declare and initialize variables
        helper = new DBHelper(this);
        accountHolder = new AccountHolder();
        name = findViewById(R.id.caregiver_name_editText);
        phone = findViewById(R.id.phone_editText);
        pass = findViewById(R.id.pass_editText);
        username = findViewById(R.id.user_editText);
        back = findViewById(R.id.caregiver_back);
        save = findViewById(R.id.user_save);

        //if viewing an already existing account, display the account information
        if(!care_user.equals("None")) {
            accountHolder = helper.getAccountWithName(care_user);
            if (accountHolder != null) {
                String fullName = accountHolder.getFirstName() + " " + accountHolder.getLastName().trim();
                name.setText(fullName);
                phone.setText(accountHolder.getPhone());
                pass.setText(accountHolder.getPassword());
                username.setText(accountHolder.getUsername());
                save.setText("Update");
            }
        }

        //button for navigating back to home screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaregiverProfileActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        //prompts user for their password whenever user clicks on password edittext
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(CaregiverProfileActivity.this);
                    final EditText edittext = new EditText(CaregiverProfileActivity.this);

                    alert.setTitle("Enter your current password");

                    alert.setView(edittext);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if password is correct, display password and allow user to update it
                            if (edittext.getText().toString().equals(helper.getPassword(userID))) {
                                pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            }
                        }
                    });
                    alert.show();
                }
            }
        });

        //button for saving or updating user information
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if any information has been changed for an existing aaccountand update accordingly
                if(save.getText().toString().equals("Update")) {
                    String fullName = accountHolder.getFirstName() + " " + accountHolder.getLastName();
                    if (!fullName.equals(null) && !fullName.equals(name.getText().toString())) {
                        String newName = name.getText().toString();
                        accountHolder.setFirstName(newName.substring(0, newName.indexOf(" ")));
                        accountHolder.setLastName(newName.substring(newName.indexOf(" ")));
                    }
                    if (!accountHolder.getPhone().equals(phone.getText().toString())) {
                        accountHolder.setPhone(phone.getText().toString());
                    }
                    if (!accountHolder.getPassword().equals(pass.getText().toString())) {
                        accountHolder.setPassword(pass.getText().toString());
                    }
                    if (!accountHolder.getUsername().equals(username.getText().toString())) {
                        accountHolder.setUsername(username.getText().toString());
                    }
                    //call db function for updating accounts
                    helper.updateAccount(accountHolder);
                }
                else{
                    //checks that necessary fields have been filled out for a new account
                    if(!name.getText().toString().equals("") && !phone.getText().toString().equals("") && !pass.getText().toString().equals("")
                        && !username.getText().toString().equals("")){
                        //fill in account object with user info
                        String newName = name.getText().toString();
                        accountHolder.setFirstName(newName.substring(0, newName.indexOf(" ")));
                        accountHolder.setLastName(newName.substring(newName.indexOf(" ")));
                        accountHolder.setPhone(phone.getText().toString());
                        accountHolder.setPassword(pass.getText().toString());
                        accountHolder.setUsername(username.getText().toString());

                        //add the new account to db
                        helper.addAccount(accountHolder);
                    }
                }

                //navigate back to home screen
                Intent intent = new Intent(CaregiverProfileActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });
    }
}