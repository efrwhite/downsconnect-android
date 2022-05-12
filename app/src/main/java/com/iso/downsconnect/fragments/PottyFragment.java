package com.iso.downsconnect.fragments;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.Spinner;

import com.iso.downsconnect.ActivityContainer;
import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.Bathroom;
import com.iso.downsconnect.objects.Entry;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PottyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//fragment for displaying/saving info for a bathroom entry that is of type potty
public class PottyFragment extends Fragment {
    private Bathroom bathroom = new Bathroom();
    private Entry entry = new Entry();
    private EditText notes, duration;
    private Spinner units, accident;
    private DBHelper helper;
    private Button save;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PottyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PottyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PottyFragment newInstance(String param1, String param2) {
        PottyFragment fragment = new PottyFragment();
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
        if(getArguments() != null) {
            //get the bathroom entry that was clicked in the view entries panel
            bathroom = (Bathroom) getArguments().getSerializable("bathroom");
        }
        return inflater.inflate(R.layout.fragment_potty, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get current child id from shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final int childID = sharedPreferences.getInt("name", 1);

        //preset unused bathroom values with None
        if(bathroom.getBathroomType() == null) {
            bathroom.setBathroomType("None");
            bathroom.setChildID(childID);
            bathroom.setDiaperCream("None");
            bathroom.setOpenAir("None");
            bathroom.setLeak("None");
            bathroom.setQuantity("None");
            bathroom.setDateOfLastStool(-1);
            bathroom.setTreatmentPlan("None");
        }

        //Declare and initialize layout objects and variables
        helper = new DBHelper(getContext());
        notes =  view.findViewById(R.id.editText);
        accident = view.findViewById(R.id.accidentEditText);
        units = view.findViewById(R.id.unitsSpinner);
        duration = view.findViewById(R.id.durationEditText);
        save = view.findViewById(R.id.saveButton);

        //Display bathroom entry that was click in the view entries if type is potty
        if(bathroom.getBathroomType().equals("Potty")){
            setInfo();
        }

        //saves info to database when the button is clicked
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if all required fields have been filled out
                if(!accident.getSelectedItem().equals("") && !duration.getText().toString().equals("")
                        && !units.getSelectedItem().equals("Select")){
                    //set values in the bathroom object
                    bathroom.setBathroomType("Potty");
                    bathroom.setDuration(duration.getText().toString() + " " + units.getSelectedItem().toString());
                    bathroom.setPottyAccident(accident.getSelectedItem().toString());

                    //Description text for the potty entry
                    String entryText = helper.getChildName(childID) + " went potty for " + bathroom.getDuration();
                    //If accident button is checked, set entry text accordingly
                    if(accident.getSelectedItem().equals("Yes")){
                        entryText = entryText + ". It was an accident";
                    }
                    else{
                        entryText = entryText + ". It wasn't an accident";
                    }

                    //create entry object for the bathroom entry
                    entry.setEntryText(entryText);
                    entry.setChildID(childID);
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    entry.setEntryType("Bathroom");

                    //add bathroom entry to database and get it's bathroom id
                    long id = helper.addBathroom(bathroom);

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

    public void setInfo(){
        //prefill the fields based on the information from the bathroom entry that was clicked in the view entries panel
        save.setEnabled(false);
        duration.setText(bathroom.getDuration().substring(0, bathroom.getDuration().indexOf(" ")));
        units.setSelection(getIndex(units, bathroom.getDuration().substring(bathroom.getDuration().indexOf(" ") + 1)));
        accident.setSelection(getIndex(accident, bathroom.getPottyAccident()));

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