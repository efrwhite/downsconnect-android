package com.iso.downsconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.iso.downsconnect.fragments.EightYearFragment;
import com.iso.downsconnect.fragments.EighteenMonthFragment;
import com.iso.downsconnect.fragments.ElevenYearFragment;
import com.iso.downsconnect.fragments.FifteenMonthFragment;
import com.iso.downsconnect.fragments.FiveYearFragment;
import com.iso.downsconnect.fragments.FourMonthFragment;
import com.iso.downsconnect.fragments.FourYearFragment;
import com.iso.downsconnect.fragments.NewBornFragment;
import com.iso.downsconnect.fragments.NineMonthFragment;
import com.iso.downsconnect.fragments.NineYearFragment;
import com.iso.downsconnect.fragments.NoAgeFragment;
import com.iso.downsconnect.fragments.SevenYearFragment;
import com.iso.downsconnect.fragments.SixMonthFragment;
import com.iso.downsconnect.fragments.SixYearFragment;
import com.iso.downsconnect.fragments.TenYearFragment;
import com.iso.downsconnect.fragments.ThirtyMonthsFragment;
import com.iso.downsconnect.fragments.ThreeYearFragment;
import com.iso.downsconnect.fragments.TwelveMonthFragment;
import com.iso.downsconnect.fragments.TwelveYearFragment;
import com.iso.downsconnect.fragments.TwoMonthFragment;
import com.iso.downsconnect.fragments.TwoYearFragment;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.MedicalInfo;
import com.iso.downsconnect.objects.Provider;

import java.util.ArrayList;
import java.util.Calendar;

public class DoctorsVisitActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText doctorDatePicker, height, headSize, temperature, weight;
    private long doctorDate = 0;
    private Spinner headUnit, tempUnit, weightUnit, heightUnit, providerType, provider, visitNum;
    private MedicalInfo medicalInfo = new MedicalInfo();
    private Fragment fragment;
    private Button save;
    private Entry entry = new Entry();
    private DBHelper dbHelper;
    private TextView ageText, currentTime;
    private Button back;
    private ArrayList<String> p_names = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private FrameLayout ageLayout;
    private String age;
    private NewBornFragment newBornFragment = new NewBornFragment();
    private TwoMonthFragment twoMonthFragment = new TwoMonthFragment();
    private FourMonthFragment fourMonthFragment = new FourMonthFragment();
    private SixMonthFragment sixMonthFragment = new SixMonthFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_visit);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        final FragmentManager fragmentManager = getSupportFragmentManager();


        doctorDatePicker = findViewById(R.id.doctorsDatePicker);
        dbHelper = new DBHelper(this);
        providers = dbHelper.getAllProviders();
        save = findViewById(R.id.saveButton);
        if(providers.size() == 0 || dbHelper.getAllChildren().size() == 0){
                    new AlertDialog.Builder(DoctorsVisitActivity.this)
                            .setTitle("Missing Profiles")
                            .setMessage("Please make sure you have at least one provider and one child in the profiles section")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(DoctorsVisitActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }).show();
                }
        medicalInfo.setChildID(childID);

        //open panel for pediatrican like the sleep panel
        //panel shouldn't open if other doctor type is selected


        provider = findViewById(R.id.p_nameSpinner);
        providerType = findViewById(R.id.providerSpinner);
        height = findViewById(R.id.heightEditText);
        headSize = findViewById(R.id.headEditText);
        headUnit = findViewById(R.id.headSpinner);
        tempUnit = findViewById(R.id.temperatureSpinner);
        temperature = findViewById(R.id.temperatureEditText);
        visitNum = findViewById(R.id.visitSpinner);
        weight = findViewById(R.id.weightEditText);
        weightUnit = findViewById(R.id.weightSpinner);
        heightUnit = findViewById(R.id.heightSpinner);
        ageText = findViewById(R.id.calcAgeText);
        currentTime = findViewById(R.id.current_time_text);
        back = findViewById(R.id.backButton);
        ageLayout = findViewById(R.id.ageLayout);

        visitNum.setEnabled(false);


        //Calculate current time
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

        //calculate child's current age
        long difference = Calendar.getInstance().getTimeInMillis() - dbHelper.getChildBirthday(childID);
        long days = difference / (24 * 60 * 60 * 1000);
        int months = (int) days / 30;
        int years = (int) days / 365;

        if(days < 31){
            ageText.setText(days + " days");
        }
        else if(days >= 31 && days <= 547){
            ageText.setText(months + " months");
        }
        else{
            ageText.setText(years + "yrs");
        }

        //Display questions based on which visit for the pediatrican the child is currenlty on

        loadSpinnerData();

//        providerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selected = parent.getItemAtPosition(position).toString();
//                //check to see if selected item is pediatrician
//
//                if(selected.equals("Pediatrician")){
//                    ageLayout.setVisibility(View.VISIBLE);
//                    age = ageText.getText().toString();
//                    if(age.contains("days") || age.equals(" 1 month")){
//                        age = "Newborn";
//                    }
//                    //set fragment class to appropriate fragment depending on child's age
//                    switch (age){
//                        case "Newborn":
//                            fragment = newBornFragment;
//                            break;
//                        case "2 months":
//                            fragment = new TwoMonthFragment();
//                            break;
//                        case "4 months":
//
//                    }
//                    //create new instance of the fragment and display it to the user
//                    fragmentManager.beginTransaction().replace(R.id.ageLayout, fragment).commit();
//                }
//            }
        providerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if(selected.equals("Pediatrician")){
                    visitNum.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        visitNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                //check to see if selected item is pediatrician
                switch (selected) {
                    case "Newborn":
                        fragment = newBornFragment;
                        break;
                    case "Two months":
                        fragment = twoMonthFragment;
                        break;
                    case "Four months":
                        fragment = fourMonthFragment;
                        break;
                    case "Six months":
                        fragment = sixMonthFragment;
                        break;
                    case "Nine months":
                        fragment = new NineMonthFragment();
                        break;
                    case "Twelve months":
                        fragment = new TwelveMonthFragment();
                        break;
                    case "Fifteen months":
                        fragment = new FifteenMonthFragment();
                        break;
                    case "Eighteen months":
                        fragment = new EighteenMonthFragment();
                        break;
                    case "Two years":
                        fragment = new TwoYearFragment();
                        break;
                    case "Thirty months":
                        fragment = new ThirtyMonthsFragment();
                        break;
                    case "Three years":
                        fragment = new ThreeYearFragment();
                        break;
                    case "Four years":
                        fragment = new FourYearFragment();
                        break;
                    case "Five years":
                        fragment = new FiveYearFragment();
                        break;
                    case "Six years":
                        fragment = new SixYearFragment();
                        break;
                    case "Seven years":
                        fragment = new SevenYearFragment();
                        break;
                    case "Eight years":
                        fragment = new EightYearFragment();
                        break;
                    case "Nine years":
                        fragment = new NineYearFragment();
                        break;
                    case "Ten years":
                        fragment = new TenYearFragment();
                        break;
                    case "Eleven years":
                        fragment = new ElevenYearFragment();
                        break;
                    case "Twelve years":
                        fragment = new TwelveYearFragment();
                        break;
                    case "Not an age-scheduled visit":
                        fragment = new NoAgeFragment();
                        break;

                }
                if(!selected.equals("Select")) {
                    fragmentManager.beginTransaction().replace(R.id.ageLayout, fragment).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        doctorDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorsVisitActivity.this, MedicalActivity.class);
                startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save visit info
                boolean validate = false;
                if(age.equals("Newborn")){
                    validate = newBornFragment.validate();
                }
                if(validate) {
                    if (!doctorDatePicker.getText().toString().equals("") && !provider.getSelectedItem().toString().equals("Select")
                            && !height.getText().toString().equals("") && !headUnit.getSelectedItem().equals("Select")
                            && !tempUnit.getSelectedItem().equals("Select") && !weight.getText().toString().equals("")
                            && !heightUnit.getSelectedItem().equals("Select") && !weightUnit.getSelectedItem().equals("Select") && !providerType.getSelectedItem().toString().equals("Select")) {
                        medicalInfo.setProvider(provider.getSelectedItem().toString());
                        medicalInfo.setHeight(height.getText().toString() + " " + heightUnit.getSelectedItem().toString());
                        medicalInfo.setProviderType(providerType.getSelectedItem().toString());
                        medicalInfo.setWeight(weight.getText().toString() + " " + weightUnit.getSelectedItem().toString());
                        medicalInfo.setHeadInfo(headSize.getText().toString() + " " + headUnit.getSelectedItem().toString());
                        medicalInfo.setTemperatureInfo(temperature.getText().toString() + " " + tempUnit.getSelectedItem().toString());
//                        medicalInfo.setVisit(visitNum.getText().toString());

                        entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                        entry.setChildID(childID);
                        entry.setEntryText("Saved " + medicalInfo.getProviderType() + " appointment information for " + dbHelper.getChildName(childID));

                        long id = dbHelper.addMedical(medicalInfo);
                        entry.setForeignID(id);
                        dbHelper.addEntry(entry);

                        ageLayout.setVisibility(View.VISIBLE);
                        String age = ageText.getText().toString();
                        if (age.contains("days") || age.equals(" 1 month")) {
                            age = "Newborn";
                        }

                        switch (age) {
                            case "Newborn":
                                NewBornFragment newBornFragment = new NewBornFragment();
                                newBornFragment.saveInfo();
                                break;
                        }


                        Intent intent = new Intent(DoctorsVisitActivity.this, ActivityContainer.class);
                        startActivity(intent);
                    }
                }
                else{
                    Log.i("Valley2", String.valueOf(validate));
                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });

    }

    private void showDatePickerDialog() {
        @SuppressLint("ResourceType") DatePickerDialog datePickerDialog = new DatePickerDialog(this, 2, (DatePickerDialog.OnDateSetListener) this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        doctorDate = calendar.getTimeInMillis();
        medicalInfo.setDoctorDate(doctorDate);
        doctorDatePicker.setText(month + "/" + day + "/" + year);
    }

    public void loadSpinnerData(){
        //loads all the providers currently saved in db
        p_names.add("Select");
        for(Provider provide: providers){
            p_names.add(provide.getName());
        }
        ArrayAdapter<String> providerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, p_names);
        providerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provider.setAdapter(providerAdapter);
    }
}