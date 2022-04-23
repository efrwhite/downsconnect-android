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
import android.widget.Toast;

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
import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.helpers.DateHandler;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.MedicalInfo;
import com.iso.downsconnect.objects.Provider;

import java.util.ArrayList;
import java.util.Calendar;

public class DoctorsVisitActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText doctorDatePicker, height, headSize, temperature, weight;
    private long doctorDate = 0;
    private Spinner headUnit, tempUnit, weightUnit, heightUnit, providerType, provider, visitNum;
    private MedicalInfo medicalInfo;
    private Fragment fragment;
    private Button save;
    private Entry entry = new Entry();
    private DBHelper dbHelper;
    private TextView ageText, currentTime;
    private Button back;
    private ArrayList<String> p_names = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private ArrayList<MedicalInfo> medicalInfos = new ArrayList<>();
    private FrameLayout ageLayout;
    private NewBornFragment newBornFragment = new NewBornFragment();
    private TwoMonthFragment twoMonthFragment = new TwoMonthFragment();
    private FourMonthFragment fourMonthFragment = new FourMonthFragment();
    private SixMonthFragment sixMonthFragment = new SixMonthFragment();
    private NineMonthFragment nineMonthFragment = new NineMonthFragment();
    private TwelveMonthFragment twelveMonthFragment = new TwelveMonthFragment();
    private FifteenMonthFragment fifteenMonthFragment = new FifteenMonthFragment();
    private EighteenMonthFragment eighteenMonthFragment = new EighteenMonthFragment();
    private ThirtyMonthsFragment thirtyMonthsFragment = new ThirtyMonthsFragment();
    private TwoYearFragment twoYearFragment = new TwoYearFragment();
    private ThreeYearFragment threeYearFragment = new ThreeYearFragment();
    private FourYearFragment fourYearFragment = new FourYearFragment();
    private FiveYearFragment fiveYearFragment = new FiveYearFragment();
    private SixYearFragment sixYearFragment = new SixYearFragment();
    private SevenYearFragment sevenYearFragment = new SevenYearFragment();
    private EightYearFragment eightYearFragment = new EightYearFragment();
    private NineYearFragment nineYearFragment = new NineYearFragment();
    private TenYearFragment tenYearFragment = new TenYearFragment();
    private ElevenYearFragment elevenYearFragment = new ElevenYearFragment();
    private TwelveYearFragment twelveYearFragment = new TwelveYearFragment();
    private NoAgeFragment noAgeFragment = new NoAgeFragment();
    private DateHandler dateHandler = new DateHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_visit);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);
        Log.i("chid", String.valueOf(childID));


        final FragmentManager fragmentManager = getSupportFragmentManager();


        doctorDatePicker = findViewById(R.id.doctorsDatePicker);
        dbHelper = new DBHelper(this);
        medicalInfos = dbHelper.getAllMedical();
        providers = dbHelper.getAllProviders();
        save = findViewById(R.id.saveButton);
        //if no provider profiles have been added, display alert message
        if (providers.size() == 0 || dbHelper.getAllChildren().size() == 0) {
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
        medicalInfo = new MedicalInfo();


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
        final int minute = calendar.get(Calendar.MINUTE);
        String realMins;
        if (minute <= 10) {
            realMins = "0" + minute;
        } else {
            realMins = String.valueOf(minute);
        }
        if (hour > 12) {
            hour = hour - 12;
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + "PM");
        } else if (hour == 12) {
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + "PM");
        } else {
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + "AM");
        }

        //calculate child's current age
        long difference = Calendar.getInstance().getTimeInMillis() - dbHelper.getChildBirthday(childID);
        long days = difference / (24 * 60 * 60 * 1000);
        int months = (int) days / 30;
        int years = (int) days / 365;

        if (days < 31) {
            ageText.setText(days + " days");
        } else if (days >= 31 && days <= 547) {
            ageText.setText(months + " months");
        } else {
            ageText.setText(years + "yrs");
        }

        //Display questions based on which visit for the pediatrican the child is currenlty on

        loadSpinnerData();

        providerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Enable the age spinner only if the visit is of type "Pediatrician"
                String selected = parent.getItemAtPosition(position).toString();
                if (selected.equals("Pediatrician")) {
                    visitNum.setEnabled(true);
                } else {
                    visitNum.setEnabled(false);
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
                //Load appropriate fragment depending on what age the user picks
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
                        fragment = nineMonthFragment;
                        break;
                    case "Twelve months":
                        fragment = twelveMonthFragment;
                        break;
                    case "Fifteen months":
                        fragment = fifteenMonthFragment;
                        break;
                    case "Eighteen months":
                        fragment = eighteenMonthFragment;
                        break;
                    case "Two years":
                        fragment = twoYearFragment;
                        break;
                    case "Thirty months":
                        fragment = thirtyMonthsFragment;
                        break;
                    case "Three years":
                        fragment = threeYearFragment;
                        break;
                    case "Four years":
                        fragment = fourYearFragment;
                        break;
                    case "Five years":
                        fragment = fiveYearFragment;
                        break;
                    case "Six years":
                        fragment = sixYearFragment;
                        break;
                    case "Seven years":
                        fragment = sevenYearFragment;
                        break;
                    case "Eight years":
                        fragment = eightYearFragment;
                        break;
                    case "Nine years":
                        fragment = nineYearFragment;
                        break;
                    case "Ten years":
                        fragment = tenYearFragment;
                        break;
                    case "Eleven years":
                        fragment = elevenYearFragment;
                        break;
                    case "Twelve years":
                        fragment = twelveYearFragment;
                        break;
                    case "Not an age-scheduled visit":
                        fragment = noAgeFragment;
                        break;

                }
                if (!selected.equals("Select")) {
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
                String age = visitNum.getSelectedItem().toString();
                if (visitNum.isEnabled()) {
                    switch (age) {
                        case "Select":
                            AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                            a.setTitle("Select Visit age");
                            a.setMessage("Please make you've selected a visit age");
                            a.show();
                            break;
                        case "Newborn":
                            medicalInfo = newBornFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Two months":
                            medicalInfo = twoMonthFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Four months":
                            medicalInfo = fourMonthFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Six months":
                            medicalInfo = sixMonthFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Nine months":
                            medicalInfo = nineMonthFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Twelve months":
                            medicalInfo = twelveMonthFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Fifteen months":
                            medicalInfo = fifteenMonthFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Eighteen months":
                            medicalInfo = eighteenMonthFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Thirty months":
                            medicalInfo = thirtyMonthsFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Two years":
                            medicalInfo = twoYearFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Three years":
                            medicalInfo = threeYearFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Four years":
                            medicalInfo = fourYearFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Five years":
                            medicalInfo = fiveYearFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Six years":
                            medicalInfo = sixYearFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Seven years":
                            medicalInfo = sevenYearFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Eight years":
                            medicalInfo = eightYearFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Nine years":
                            medicalInfo = nineYearFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Ten years":
                            medicalInfo = tenYearFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Eleven years":
                            medicalInfo = elevenYearFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Twelve years":
                            medicalInfo = twelveYearFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Not an age-scheduled visit":
                            medicalInfo = noAgeFragment.saveInfo();
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                    }
                } else {
                    addInfo(childID, age);
                }
            }
        });
    }

    public void addInfo(int childID, String age) {
        //if provider is a pediatrician or a cardiologist, require user to input height, weight, head circumference, and temperature information
        if (providerType.getSelectedItem().toString().equals("Pediatrician") || providerType.getSelectedItem().toString().equals("Cardio")) {
            //check to make sure necessary information has been filled out
            if (!doctorDatePicker.getText().toString().equals("") && !provider.getSelectedItem().toString().equals("Select")
                    && !height.getText().toString().equals("") && !headUnit.getSelectedItem().equals("Select")
                    && !tempUnit.getSelectedItem().equals("Select") && !weight.getText().toString().equals("")
                    && !heightUnit.getSelectedItem().equals("Select") && !weightUnit.getSelectedItem().equals("Select") && !providerType.getSelectedItem().toString().equals("Select")) {
                //add information to medical object
                medicalInfo.setChildID(childID);
                medicalInfo.setProvider(provider.getSelectedItem().toString());
                medicalInfo.setHeight(height.getText().toString() + " " + heightUnit.getSelectedItem().toString());
                medicalInfo.setProviderType(providerType.getSelectedItem().toString());
                medicalInfo.setWeight(weight.getText().toString() + " " + weightUnit.getSelectedItem().toString());
                medicalInfo.setHeadInfo(headSize.getText().toString() + " " + headUnit.getSelectedItem().toString());
                medicalInfo.setTemperatureInfo(temperature.getText().toString() + " " + tempUnit.getSelectedItem().toString());
                medicalInfo.setDoctorDate(doctorDate);
                if (visitNum.isEnabled()) {
                    medicalInfo.setVisit(visitNum.getSelectedItem().toString());
                }
                else{
                    medicalInfo.setVisit("N/A");
                }
                if (medicalInfo.getAnswers() == null) {
                    medicalInfo.setAnswers("None");
                }
                if (medicalInfo.getDates() == null) {
                    medicalInfo.setDates("None");
                }
                if (medicalInfo.getProviders() == null) {
                    medicalInfo.setProviders("None");
                }

                //create entry object for this visit
                entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                entry.setChildID(childID);
                entry.setEntryType("Medical");
                entry.setEntryText("Saved " + medicalInfo.getProviderType() + " appointment information for " + dbHelper.getChildName(childID));

                //insert information into database
                long id = dbHelper.addMedical(medicalInfo);
                entry.setForeignID(id);
                dbHelper.addEntry(entry);

                ageLayout.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Doctor Visit information saved", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DoctorsVisitActivity.this, ActivityContainer.class);
                startActivity(intent);
                //alert to display if any information is missing
            } else {
                AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                a.setTitle("Missing Doctor Visit Information");
                a.setMessage("Please make sure you've filled out the necessary information");
                a.show();
            }
        } else {
            if (!doctorDatePicker.getText().toString().equals("") && !provider.getSelectedItem().toString().equals("Select")
                    && !providerType.getSelectedItem().toString().equals("Select")) {
                medicalInfo.setChildID(childID);
                medicalInfo.setProvider(provider.getSelectedItem().toString());
                medicalInfo.setProviderType(providerType.getSelectedItem().toString());
                medicalInfo.setDoctorDate(doctorDate);


                //check if height has been filled out
                if (!height.getText().toString().equals("") && !heightUnit.getSelectedItem().equals("Select")) {
                    medicalInfo.setHeight(height.getText().toString() + " " + heightUnit.getSelectedItem().toString());
                } else {
                    //if not, save "N/A" for the data
                    medicalInfo.setHeight("N/A");
                }

                //check if weight has been filled out
                if (!weight.getText().toString().equals("") && !weightUnit.getSelectedItem().equals("Select")) {
                    medicalInfo.setWeight(weight.getText().toString() + " " + weightUnit.getSelectedItem().toString());
                } else {
                    //if not, save "N/A" for the data
                    medicalInfo.setWeight("N/A");
                }

                //check if head circumference has been filled out
                if (!headSize.getText().toString().equals("") && !headUnit.getSelectedItem().toString().equals("Select")) {
                    medicalInfo.setHeadInfo(headSize.getText().toString() + " " + headUnit.getSelectedItem().toString());
                } else {
                    //if not, save "N/A" for the data
                    medicalInfo.setHeadInfo("N/A");
                }

                //check if temperature has been filled out
                if (!temperature.getText().toString().equals("") && !tempUnit.getSelectedItem().toString().equals("Select")) {
                    medicalInfo.setTemperatureInfo(temperature.getText().toString() + " " + tempUnit.getSelectedItem().toString());
                } else {
                    //if not, save "N/A" for the data
                    medicalInfo.setTemperatureInfo("N/A");
                }

                //check if visit is a Pediatrician visit, if so document visit age
                if (visitNum.isEnabled()) {
                    medicalInfo.setVisit(visitNum.getSelectedItem().toString());
                } else {
                    medicalInfo.setVisit("N/A");
                }
                if (medicalInfo.getAnswers() == null) {
                    medicalInfo.setAnswers("None");
                }
                if (medicalInfo.getDates() == null) {
                    medicalInfo.setDates("None");
                }
                if (medicalInfo.getProviders() == null) {
                    medicalInfo.setProviders("None");

                    //make entry object for visit
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    entry.setChildID(childID);
                    entry.setEntryType("Medical");
                    entry.setEntryText("Saved " + medicalInfo.getProviderType() + " appointment information for " + dbHelper.getChildName(childID));

                    //add information to database
                    long id = dbHelper.addMedical(medicalInfo);
                    entry.setForeignID(id);
                    dbHelper.addEntry(entry);

                    ageLayout.setVisibility(View.VISIBLE);

                    Toast.makeText(getApplicationContext(), "Doctor Visit information saved", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(DoctorsVisitActivity.this, ActivityContainer.class);
                    startActivity(intent);

                    //alert to display if any information is missing
                } else {
                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                    a.setTitle("Missing Doctor Visit Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        }
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
        doctorDatePicker.setText(dateHandler.writtenDate(month, day, year));
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

    public void displayErrorMessage(String title, String message){
        AlertDialog b = new AlertDialog.Builder(save.getContext()).create();
        b.setTitle(title);
        b.setMessage(message);
        b.show();
    }
}