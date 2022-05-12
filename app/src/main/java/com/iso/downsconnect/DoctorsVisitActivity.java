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
//activity used to save new medical info to the database
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
    //declare/initialize all the age specific fragments
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

        //get current childID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);
        Log.i("chid", String.valueOf(childID));

        //initialize fragment manager to handle the displaying of the fragments
        final FragmentManager fragmentManager = getSupportFragmentManager();


        //initialize variables
        doctorDatePicker = findViewById(R.id.doctorsDatePicker);
        dbHelper = new DBHelper(this);
        //get provider and medical info from db
        medicalInfos = dbHelper.getAllMedical();
        providers = dbHelper.getAllProviders();
        save = findViewById(R.id.saveButton);

        //if no provider profiles have been added, display alert message
        if (providers.size() == 0 || dbHelper.getAllChildren().size() == 0) {
            new AlertDialog.Builder(DoctorsVisitActivity.this)
                    //prompt user to create the needed profiles and navigate back to home screen
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

        //initialize variables
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

        //disables the visit age spinner
        visitNum.setEnabled(false);


        //Calculate current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        String realMins;
        //fix minutes that are less than 10
        if (minute <= 10) {
            realMins = "0" + minute;
        } else {
            realMins = String.valueOf(minute);
        }
        //detemines whether time was AM or PM
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

        //load the spinner with the provider names
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

        //displays the corresponding age fragment based on which age was picked
        visitNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                //Load appropriate fragment depending on what age the user picks
                switch (selected) {
                    //checks if the passed in string matches any of the strings in the case statements
                    //if they match, set the fragment equal to the associated fragment
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
                //check to make sure that the user select an age to begin with
                if (!selected.equals("Select")) {
                    //display the fragment using the fragment manager
                    fragmentManager.beginTransaction().replace(R.id.ageLayout, fragment).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //display the date dialog picker
        doctorDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //button for navigating back to medical page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorsVisitActivity.this, MedicalActivity.class);
                startActivity(intent);
            }
        });

        //button to save the medical info to the db
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save visit info
                String age = visitNum.getSelectedItem().toString();
                //save the fragment info if the visit spinner is enabled
                if (visitNum.isEnabled()) {
                    switch (age) {
                        //checks for which case statement string matches the input string and run the code for it
                        case "Select":
                            //display error message if visit age isn't selected
                            AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                            a.setTitle("Select Visit age");
                            a.setMessage("Please make you've selected a visit age");
                            a.show();
                            break;
                        case "Newborn":
                            //get the info from the corresponding fragment
                            medicalInfo = newBornFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Two months":
                            //get the info from the corresponding fragment
                            medicalInfo = twoMonthFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Four months":
                            //get the info from the corresponding fragment
                            medicalInfo = fourMonthFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Six months":
                            //get the info from the corresponding fragment
                            medicalInfo = sixMonthFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Nine months":
                            //get the info from the corresponding fragment
                            medicalInfo = nineMonthFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Twelve months":
                            //get the info from the corresponding fragment
                            medicalInfo = twelveMonthFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Fifteen months":
                            //get the info from the corresponding fragment
                            medicalInfo = fifteenMonthFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Eighteen months":
                            //get the info from the corresponding fragment
                            medicalInfo = eighteenMonthFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Thirty months":
                            //get the info from the corresponding fragment
                            medicalInfo = thirtyMonthsFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Two years":
                            //get the info from the corresponding fragment
                            medicalInfo = twoYearFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Three years":
                            //get the info from the corresponding fragment
                            medicalInfo = threeYearFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Four years":
                            //get the info from the corresponding fragment
                            medicalInfo = fourYearFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Five years":
                            //get the info from the corresponding fragment
                            medicalInfo = fiveYearFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Six years":
                            //get the info from the corresponding fragment
                            medicalInfo = sixYearFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Seven years":
                            //get the info from the corresponding fragment
                            medicalInfo = sevenYearFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Eight years":
                            //get the info from the corresponding fragment
                            medicalInfo = eightYearFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Nine years":
                            //get the info from the corresponding fragment
                            medicalInfo = nineYearFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Ten years":
                            //get the info from the corresponding fragment
                            medicalInfo = tenYearFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Eleven years":
                            //get the info from the corresponding fragment
                            medicalInfo = elevenYearFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Twelve years":
                            //get the info from the corresponding fragment
                            medicalInfo = twelveYearFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                        case "Not an age-scheduled visit":
                            //get the info from the corresponding fragment
                            medicalInfo = noAgeFragment.saveInfo();
                            //if the need fragment fields were entered, add the info to the db, if not, display error message
                            if (medicalInfo != null) {
                                addInfo(childID, age);
                            } else {
                                displayErrorMessage("Missing Visit Information", "Please make sure you've filled in the visit information");
                            }
                            break;
                    }
                } else {
                    //if visit age spinner not active, continue to adding a new medical entry
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
                //error message to display if any information is missing
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

                    //display success message and navigate back to home screen
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

    //display date dialog box
    private void showDatePickerDialog() {
        @SuppressLint("ResourceType") DatePickerDialog datePickerDialog = new DatePickerDialog(this, 2, (DatePickerDialog.OnDateSetListener) this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    //save and display the date that the user selected from the dialog
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
        //create an adapter set for the adapter, which displays the array of names
        ArrayAdapter<String> providerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, p_names);
        providerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provider.setAdapter(providerAdapter);
    }

    //function for display error message with info passed in
    public void displayErrorMessage(String title, String message){
        AlertDialog b = new AlertDialog.Builder(save.getContext()).create();
        b.setTitle(title);
        b.setMessage(message);
        b.show();
    }
}