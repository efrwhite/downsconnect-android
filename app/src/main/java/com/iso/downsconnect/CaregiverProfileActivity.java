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

public class CaregiverProfileActivity extends AppCompatActivity {
    private DBHelper helper;
    private EditText name, phone, pass, username;
    private Button back, save;
    private AccountHolder accountHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_profile);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final long userID = sharedPreferences.getLong("user", 1);

        final String care_user = getIntent().getStringExtra("care_name");

        helper = new DBHelper(this);
        accountHolder = new AccountHolder();
        name = findViewById(R.id.caregiver_name_editText);
        phone = findViewById(R.id.phone_editText);
        pass = findViewById(R.id.pass_editText);
        username = findViewById(R.id.user_editText);
        back = findViewById(R.id.caregiver_back);
        save = findViewById(R.id.user_save);

        //set user information for logged in account
        if(!care_user.equals("None")) {
            accountHolder = helper.getAccountWithName(care_user);
            if (accountHolder != null) {
                String fullName = accountHolder.getFirstName() + " " + accountHolder.getLastName().trim();
                name.setText(fullName);
                phone.setText(accountHolder.getPhone());
                pass.setText(accountHolder.getPassword());
                username.setText(accountHolder.getUsername());
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaregiverProfileActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

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
                            if (edittext.getText().toString().equals(helper.getPassword(userID))) {
                                pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            }
                        }
                    });
                    alert.show();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if any information has been changed and update accordingly
                String fullName = accountHolder.getFirstName() + " " + accountHolder.getLastName();
                if(!fullName.equals(null) && !fullName.equals(name.getText().toString())){
                    String newName = name.getText().toString();
                    accountHolder.setFirstName(newName.substring(0, newName.indexOf(" ")));
                    accountHolder.setLastName(newName.substring(newName.indexOf(" ")));
                }
                if(!accountHolder.getPhone().equals(phone.getText().toString())){
                    accountHolder.setPhone(phone.getText().toString());
                }
                if(!accountHolder.getPassword().equals(pass.getText().toString())){
                    accountHolder.setPassword(pass.getText().toString());
                }
                if(!accountHolder.getUsername().equals(username.getText().toString())){
                    accountHolder.setUsername(username.getText().toString());
                }
                helper.updateAccount(accountHolder);

                Intent intent = new Intent(CaregiverProfileActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });
    }
}