package com.example.downsconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private String first, last, user, phone, pass, confirmPass;
    private DBHelper helper;
    private EditText firstName, lastName, userName, password, phoneNumber ,confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        helper = new DBHelper(this);

        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        userName = findViewById(R.id.usernameEditText);
        phoneNumber = findViewById(R.id.phoneEditText);
        password = findViewById(R.id.passwordEditText);
        confirmPassword = findViewById(R.id.confirmPasswordEditText);

        final Button signUp = findViewById(R.id.signUpButton);
        Button cancel = findViewById(R.id.cancelButton);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first = firstName.getText().toString();
                last = lastName.getText().toString();
                user = userName.getText().toString();
                pass = password.getText().toString();
                phone = phoneNumber.getText().toString();
                confirmPass = confirmPassword.getText().toString();
                if (!first.equals("") && !last.equals("") && !user.equals("")
                        && !phone.equals("") && !pass.equals("") && !confirmPass.equals("") && pass.equals(confirmPass)) {
                    AccountHolder accountHolder = new AccountHolder(first, last, user, pass, phone);
                    //will add to database you dbhelper when that function is written
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            else{
                    AlertDialog a = new AlertDialog.Builder(signUp.getContext()).create();
                    a.setTitle("Invalid/Missing Information");
                    a.setMessage("Please make sure you've filled out all the required fields");
                    a.show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
