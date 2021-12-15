package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.AccountHolder;

public class CaregiverProfileActivity extends AppCompatActivity {
    private DBHelper helper;
    private EditText name;
    private Button back;
    AccountHolder accountHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_profile);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String user = sharedPreferences.getString("user", "");

        String care_user = getIntent().getStringExtra("care_name");

        helper = new DBHelper(this);
        accountHolder = new AccountHolder();
        name = findViewById(R.id.caregiver_name_editText);
        back = findViewById(R.id.caregiver_back);

        if(!care_user.equals("None")) {
            accountHolder = helper.getAccountWithName(care_user);
            if (accountHolder != null) {
                name.setText(accountHolder.getFirstName() + " " + accountHolder.getLastName());
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaregiverProfileActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });
    }
}