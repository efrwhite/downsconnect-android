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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.iso.downsconnect.ActivityContainer;
import com.iso.downsconnect.DBHelper;
import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Feed;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FluidFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FluidFragment extends Fragment {
    private Button saveBtn;
    private EditText notes, fluidFood, quantity, otherText;
    private Spinner foodUnit;
    private DBHelper helper;
    private Feed feed;
    private Entry entry;
    private CheckBox iron, vitamin, other;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FluidFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FluidFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FluidFragment newInstance(String param1, String param2) {
        FluidFragment fragment = new FluidFragment();
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
        return inflater.inflate(R.layout.fragment_fluid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final int childID = sharedPreferences.getInt("name", 1);

        saveBtn = view.findViewById(R.id.saveButton);
        notes = view.findViewById(R.id.editText);
        fluidFood = view.findViewById(R.id.fluidFoodEditText);
        foodUnit = view.findViewById(R.id.unitSpinner);
        quantity = view.findViewById(R.id.quantityEditText);
        helper = new DBHelper(getContext());
        iron = view.findViewById(R.id.ironCheckbox);
        vitamin = view.findViewById(R.id.vitaCheckbox);
        other = view.findViewById(R.id.otherCheckbox);
        otherText = view.findViewById(R.id.otherText);



        feed = new Feed();
        entry = new Entry();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fluidFood.getText().toString().equals("") && !foodUnit.getSelectedItem().equals("Select") && !quantity.getText().toString().equals("")){
                    feed.setChildID(childID);
                    feed.setAmount(Integer.parseInt(quantity.getText().toString()));
                    feed.setSubstance(fluidFood.getText().toString());
                    feed.setFoodUnit(foodUnit.getSelectedItem().toString());
                    Calendar calendar = Calendar.getInstance();
                    feed.setEntryTime(calendar.getTimeInMillis());
                    if(!notes.getText().toString().equals("")){
                        feed.setNotes(notes.getText().toString());
                    }
                    else{
                        feed.setNotes("");
                    }
                    if(vitamin.isChecked()){
                       feed.setVitamin("Yes");
                    }
                    else{
                        feed.setVitamin("No");
                    }
                    if(iron.isChecked()){
                        feed.setIron("Yes");
                    }
                    else{
                        feed.setIron("No");
                    }
                    if(other.isChecked()){
                        feed.setOther(otherText.getText().toString());
                    }
                    else{
                        feed.setOther("None");
                    }
                    entry.setChildID(childID);
                    entry.setEntryText(helper.getChildName(childID) + " drank " + feed.getAmount() + feed.getFoodUnit() + " of " + feed.getSubstance());
                    entry.setEntryTime(calendar.getTimeInMillis());
                    helper.addFeed(feed);
                    helper.addEntry(entry);
                    Intent intent = new Intent(getContext(), ActivityContainer.class);
                    startActivity(intent);
                }
                else{
                    AlertDialog a = new AlertDialog.Builder(saveBtn.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });
    }
}