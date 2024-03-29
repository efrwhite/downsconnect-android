package com.iso.downsconnect;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.Child;
import com.iso.downsconnect.helpers.DateHandler;

import java.util.ArrayList;
import java.util.Calendar;
//activity for creating new child profiles as well as viewing/updating existing child profiles
public class DetailedProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText birthdayPicker, dueDatePicker, allergies, medications, fullName;
    private Spinner gender, bloodType;
    private TextView age;
    private ImageView image;
    private boolean birthday, dueDate;
    private Calendar birthdayDate, due_Date;
    private Button back, save, addCaregiver;
    private DBHelper helper;
    private Child child = new Child();
    private DateHandler dateHandler = new DateHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_profile);

        //declare and initialize variables
        helper = new DBHelper(this);
        birthdayDate = Calendar.getInstance();
        due_Date = Calendar.getInstance();
        age = findViewById(R.id.ageText);
        gender = findViewById(R.id.genderEditText);
        bloodType = findViewById(R.id.bloodSpinner);
        allergies = findViewById(R.id.allergyEditText);
        medications = findViewById(R.id.medicationsEditText);
        fullName = findViewById(R.id.editTextTextPersonName);
        birthdayPicker = findViewById(R.id.birthdayPicker);
        dueDatePicker = findViewById(R.id.dueDatePicker);
        image = findViewById(R.id.profileImageView);
        addCaregiver = findViewById(R.id.addCaregiverButton);
        save = findViewById(R.id.saveButton);
        back = findViewById(R.id.backButton);
        Spinner genderSpinner = findViewById(R.id.genderEditText);


        //get child's name
        final String childName = getIntent().getStringExtra("childName");

        //if a child's name is presen, get their information from the db
        if (!childName.equals("None")) {
            child = helper.getChild(childName);

        //if the child has information in db, fill in fields and display it
        if (child != null) {
            fullName.setText(child.getFirstName() + " " + child.getLastName());
            gender.setSelection(getIndex(gender, child.getGender()));
            Calendar birthday = Calendar.getInstance();
            birthday.setTimeInMillis(child.getBirthday());
            birthdayPicker.setText(dateHandler.writtenDate(birthday.get(Calendar.MONTH),birthday.get(Calendar.DATE) , birthday.get(Calendar.YEAR)));
            Calendar due = Calendar.getInstance();
            due.setTimeInMillis(child.getDueDate());
            dueDatePicker.setText(dateHandler.writtenDate(due.get(Calendar.MONTH),  due.get(Calendar.DATE) , due.get(Calendar.YEAR)));
            bloodType.setSelection(getIndex(bloodType, child.getBloodType()));
            allergies.setText(child.getAllergies());
            medications.setText(child.getMedications());
        }
        }


//        String spinner = genderSpinner.getSelectedItem().toString();

        //button to save child information to db
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if all necessary fields have been filled in
                if (!fullName.getText().toString().equals("") && !gender.getSelectedItem().equals("Select") && !birthdayPicker.getText().toString().equals("")
                        && !bloodType.getSelectedItem().equals("Select")) {
                    String name = fullName.getText().toString();
                    //checks to make sure user has entered both a first and last name for the child, if not display error message
                    if (!fullName.getText().toString().contains(" ")) {
                        AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                        a.setTitle("Child's Name");
                        a.setMessage("Please make sure you've entered your child's first and last name");
                        a.show();
                    } else {
                        //if user has entered both first and last name and all other necessary fields are complete, add info to child object
                        child.setFirstName(name.substring(0, name.indexOf(" ")));
                        child.setGender((String) gender.getSelectedItem());
                        child.setLastName(name.substring(name.indexOf(" ")));
                        child.setBloodType(bloodType.getSelectedItem().toString());

                        //checks if allergy info was filled out and set the info accordingly
                        if (allergies.getText().toString().equals(" ")) {
                            child.setAllergies("None");
                        } else {
                            child.setAllergies(allergies.getText().toString());
                        }

                        //checks if medication info was filled out and set the info accordingly
                        if (medications.getText().toString().equals(" ")) {
                            child.setMedications("None");
                        } else {
                            child.setMedications(medications.getText().toString());
                        }

                        //checks if due date info was filled out and set the info accordingly
                        if(!dueDatePicker.getText().toString().equals("")) {
                            child.setDueDate(due_Date.getTimeInMillis());
                        }
                        else{
                            child.setDueDate(0);
                        }

                        //calls function for updating a child if updating a child's info
                        if(!childName.equals("None")){
                            helper.updateChild(child);
                        }
                        else {
                            //calls function for adding new child if creating new child profile
                            boolean result = helper.addChild(child);
                        }
                        //if only one child present in database, select that child as the one having info saved for
                        ArrayList<Child> children = helper.getAllChildren();
                        if(children.size() == 1){
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            sharedPreferences.edit().putInt("name", children.get(0).getChildID()).commit();
                            Log.i("ctest", String.valueOf(sharedPreferences.getInt("name", 1)));
                        }

                        //display message telling user a profile was added
                        Toast.makeText(getApplicationContext(),"Added Child Profile", Toast.LENGTH_SHORT).show();

                        //navigate back to home page
                        Intent intent = new Intent(DetailedProfileActivity.this, ActivityContainer.class);
                        startActivity(intent);
                    }
                } else {
                    //display error message if any fields haven't been filled out
                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });

        //button for navigating back to home screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedProfileActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        //display date picker for birthday and set birthday flag to true to indicate what the date is being set for
        birthdayPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthday = true;
                dueDate = false;
                showDatePickerDialog();
            }
        });

        //display date picker for due date and set due date flag to true to indicate what the  date is being set for
        dueDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dueDate = true;
                birthday = false;
                showDatePickerDialog();
            }
        });

        //if blue add caregiver button is clicked, navigate to caregiver page
        addCaregiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedProfileActivity.this, CaregiverProfileActivity.class);
                intent.putExtra("care_name", "None");
                startActivity(intent);
            }
        });
    }

    //creates date dialog box to display with current date as teh default
    private void showDatePickerDialog() {
        @SuppressLint("ResourceType") DatePickerDialog datePickerDialog = new DatePickerDialog(this, 2, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        //if datepicker was displayed for birthday, set information for birthday
        if (birthday) {
            birthdayDate.set(year, month, day);
            child.setBirthday(birthdayDate.getTimeInMillis());
            Calendar currentDate = Calendar.getInstance();
            long difference = currentDate.getTimeInMillis() - birthdayDate.getTimeInMillis();

            //calculate age of child an display it
            long days = difference / (24 * 60 * 60 * 1000);
            int months = (int) days / 30;
            int years = (int) days / 365;
            age.setText(years + " year(s), " + months + " month(s), " + days + " day(s)");

            //display selected in the edittext
            birthdayPicker.setText(dateHandler.writtenDate(month, day, year));
        }
        //if datepicker was displayed for due date, set information for due date
        if (dueDate) {
            due_Date.set(year, month, day);
            //display selected in the edittext
            dueDatePicker.setText(dateHandler.writtenDate(month, day, year));
        }
    }

    //get index of selection in a spinner
    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;

    }
}
