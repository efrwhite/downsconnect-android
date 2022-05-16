package com.iso.downsconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.AccountHolder;
//activity for creating a new account
public class SignUpActivity extends AppCompatActivity {
    private String first, last, user, phone, pass, confirmPass;
    private DBHelper helper;
    private EditText firstName, lastName, userName, password, phoneNumber ,confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //initialize variables
        helper = new DBHelper(this);
        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        userName = findViewById(R.id.usernameEditText);
        phoneNumber = findViewById(R.id.phoneEditText);
        password = findViewById(R.id.passwordEditText);
        confirmPassword = findViewById(R.id.confirmPasswordEditText);
        final Button signUp = findViewById(R.id.signUpButton);
        Button cancel = findViewById(R.id.cancelButton);

        //inserts a new account into the db
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrieves info from all the fields and checks if they're not empty
                first = firstName.getText().toString();
                last = lastName.getText().toString();
                user = userName.getText().toString();
                pass = password.getText().toString();
                phone = phoneNumber.getText().toString();
                confirmPass = confirmPassword.getText().toString();
                if (!first.equals("") && !last.equals("") && !user.equals("")
                        && !phone.equals("") && !pass.equals("") && !confirmPass.equals("")) {

                    //a check to see if your passwords match
                    if(!pass.equals(confirmPass)){
                        AlertDialog a = new AlertDialog.Builder(signUp.getContext()).create();
                        a.setTitle("Passwords don't match");
                        a.setMessage("The passwords you've entered don't match, please ensure that do before continuing");
                        a.show();
                    }
                    //checks if there is already a user in the db with this username
                    if(helper.checkUsername(user).equals("n")){
                        AlertDialog a = new AlertDialog.Builder(signUp.getContext()).create();
                        a.setTitle("Username taken!");
                        a.setMessage("The username you entered is in use already");
                        a.show();
                    }
                    else {
                        //sets variable in shared preferences to show that the a user is currently logged in
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sharedPreferences.edit().putBoolean("signedIn", true).commit();

                        //create an account object and pass it to the db to insert it
                        AccountHolder accountHolder = new AccountHolder(first, last, user, pass, phone);
                        long id = helper.addAccount(accountHolder);
                        helper.close();
                        sharedPreferences.edit().putLong("user", id).commit();

                        //navigates to the home screen
                        Intent intent = new Intent(SignUpActivity.this, ActivityContainer.class);
//                        intent.putExtra("user", user);
                        startActivity(intent);
                    }
                }
                else{
                    //display error if info is missing
                    AlertDialog a = new AlertDialog.Builder(signUp.getContext()).create();
                    a.setTitle("Invalid/Missing Information");
                    a.setMessage("Please make sure you've filled out all the required fields");
                    a.show();
                }
            }
        });

        //button to navigate back to main screen
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
