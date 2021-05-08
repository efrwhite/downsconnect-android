package com.example.downsconnect;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {
    private DBHelper db;
    private Button signIn;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBHelper(this);
        signIn = findViewById(R.id.signInSignInButton);
        Button cancel = findViewById(R.id.cancelSignInButton);
        final EditText Username = findViewById(R.id.usernameEditText);
        final EditText Password = findViewById(R.id.passwordEditText);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Username.getText().toString();
                String pass = Password.getText().toString();
                    if (db.getAccount(user, pass) != null) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sharedPreferences.edit().putBoolean("signedIn", true).commit();
                        Intent intent = new Intent(SignInActivity.this, ActivityContainer.class);
                        startActivity(intent);
                    }
                else{
                    AlertDialog a = new AlertDialog.Builder(signIn.getContext()).create();
                    a.setTitle("Incorrect Username or Password");
                    a.setMessage("Please provide the correct account credentials.");
                    a.show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        
    }
}
