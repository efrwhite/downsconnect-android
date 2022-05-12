package com.iso.downsconnect.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.iso.downsconnect.ActivityContainer;
import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.Bathroom;
import com.iso.downsconnect.helpers.DateHandler;
import com.iso.downsconnect.objects.Entry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConstipationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//fragment for displaying/saving info for a bathroom entry that is of type constipation
public class ConstipationFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private EditText lastStoolDate, treatment, notes;
    private Button save;
    private Bathroom bathroom = new Bathroom();
    private DBHelper helper;
    private Entry entry;
    private DateHandler handler = new DateHandler();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConstipationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConstipationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConstipationFragment newInstance(String param1, String param2) {
        ConstipationFragment fragment = new ConstipationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments() != null) {
            //get the bathroom entry that was clicked in the view entries panel
            bathroom = (Bathroom) getArguments().getSerializable("bathroom");
        }
        return inflater.inflate(R.layout.fragment_constipation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get current child id from shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final int childID = sharedPreferences.getInt("name", 1);

        //preset unused bathroom values with None
        if (bathroom.getBathroomType() == null) {
            bathroom.setBathroomType("None");
            bathroom.setChildID(childID);
            bathroom.setDiaperCream("None");
            bathroom.setOpenAir("None");
            bathroom.setLeak("None");
            bathroom.setQuantity("None");
            bathroom.setPottyAccident("None");
            bathroom.setDuration("None");
        }

        //Declare and initialize layout objects and variables
        lastStoolDate = view.findViewById(R.id.stoolDateEditText);
        lastStoolDate.setFocusable(false);
        treatment = view.findViewById(R.id.quantityEditText);
        notes = view.findViewById(R.id.editText);
        save = view.findViewById(R.id.saveButton);
        helper = new DBHelper(getContext());
        entry = new Entry();

        //Display bathroom entry that was click in the view entries if type is constipation
        if (bathroom.getBathroomType().equals("Constipation")) {
            setInfo();
        }

        //click listener for displaying date dialog when edit text is clicked
        lastStoolDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //saves info to database when the button is clicked
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if all required fields have been filled out
                if(!lastStoolDate.getText().toString().equals("") && !treatment.getText().toString().equals("")){
                    //save notes info if there are notes present
                    if(!notes.getText().toString().equals("")){
                        bathroom.setDuration(notes.getText().toString());
                    }
                    else{
                        bathroom.setDuration("None");
                    }
                    //set values in the bathroom object
                    bathroom.setBathroomType("Constipation");
                    bathroom.setTreatmentPlan(treatment.getText().toString());

                    //add bathroom entry to database and get it's bathroom id
                    long id = helper.addBathroom(bathroom);

                    //create entry object for the bathroom entry
                    entry.setChildID(childID);
                    entry.setEntryType("Bathroom");
                    entry.setEntryText(helper.getChildName(childID) + " was constipated, treated with " + bathroom.getTreatmentPlan());
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    //set the corresponding bathroom id for the entry
                    entry.setForeignID(id);
                    //add new entry to database and navigate back to home page
                    helper.addEntry(entry);
                    Intent intent = new Intent(getContext(), ActivityContainer.class);
                    startActivity(intent);
                }
                else{
                    //display error if required fields not filled out
                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar cal = Calendar.getInstance();
        //creates date dialog box to display
        @SuppressLint("ResourceType") DatePickerDialog datePicker = new DatePickerDialog(getContext(), 2,
                this,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.setTitle("Select the date");
        datePicker.show();
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //get user's selected date and store it into the object and display it
        DateHandler month_ = new DateHandler();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        //saves date info
        bathroom.setDateOfLastStool(calendar.getTimeInMillis());
        //displays date info
        lastStoolDate.setText(month_.getMonth(month) + " " + dayOfMonth + ", " + year);
    }

    public void setInfo(){
        //prefill the fields based on the information from the bathroom entry that was clicked in the view entries panel
        save.setEnabled(false);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(bathroom.getDateOfLastStool());
        lastStoolDate.setText(handler.getMonth(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.DATE) + ", " + calendar.get(Calendar.YEAR));
        if(!bathroom.getDuration().equals("None")){
            notes.setText(bathroom.getDuration());
        }
        treatment.setText(bathroom.getTreatmentPlan());

    }

    //function for find index of a value in a spinner
    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;

    }
}