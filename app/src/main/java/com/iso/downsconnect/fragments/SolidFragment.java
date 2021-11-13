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
import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Feed;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SolidFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolidFragment extends Fragment {
    private Button saveBtn;
    private EditText notes, quantity, otherText;
    private Spinner foodUnit, solidFood, mode;
    private Entry entry;
    private Feed feed = new Feed();;
    private DBHelper helper;
    private CheckBox iron, vitamin, other;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SolidFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SolidFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SolidFragment newInstance(String param1, String param2) {
        SolidFragment fragment = new SolidFragment();
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
            feed = (Feed) getArguments().getSerializable("feed");
        }
        return inflater.inflate(R.layout.fragment_solid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final int childID = sharedPreferences.getInt("name", 1);

        saveBtn = view.findViewById(R.id.saveButton);
        notes = view.findViewById(R.id.editText);
        solidFood = view.findViewById(R.id.solidFoodEditText);
        quantity = view.findViewById(R.id.quantityEditText);
        foodUnit = view.findViewById(R.id.unitSpinner);
        iron = view.findViewById(R.id.ironCheck);
        vitamin = view.findViewById(R.id.vitaCheck);
        other = view.findViewById(R.id.otherCheck);
        otherText = view.findViewById(R.id.o_text);
        mode = view.findViewById(R.id.modeEatingSpinner2);
        helper = new DBHelper(getContext());
        entry = new Entry();

    if(feed.getSubstance() != null){
            setInfo();
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!solidFood.getSelectedItem().toString().equals("") && !quantity.getText().toString().equals("") && !foodUnit.getSelectedItem().equals("Select") && !mode.getSelectedItem().equals("Select")){
                    Calendar calendar = Calendar.getInstance();
                    feed.setChildID(childID);
                    feed.setSubstance(solidFood.getSelectedItem().toString());
                    feed.setAmount(Integer.parseInt(quantity.getText().toString()));
                    feed.setFoodUnit(foodUnit.getSelectedItem().toString());
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
                    feed.setEntryTime(calendar.getTimeInMillis());
                    entry.setChildID(childID);
                    entry.setEntryTime(calendar.getTimeInMillis());
                    entry.setEntryText(helper.getChildName(childID) + " ate " + feed.getAmount() + feed.getFoodUnit() + " of " + feed.getSubstance());
                    long result = helper.addFeed(feed);
                    entry.setForeignID(result);
                    entry.setEntryType("Feed");
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

    public void setInfo(){
        saveBtn.setEnabled(false);
        if(!feed.getNotes().equals("")){
            notes.setText(feed.getNotes());
        }
        quantity.setText(String.valueOf(feed.getAmount()));
        if(!feed.getOther().equals("None")) {
            otherText.setText(feed.getOther());
            other.setChecked(true);
        }
        foodUnit.setSelection(getIndex(foodUnit, feed.getFoodUnit()));
        mode.setSelection(getIndex(mode, feed.getFeedMode()));
        solidFood.setSelection(getIndex(solidFood, feed.getSubstance()));
        if(feed.getIron().equals("Yes")){
            iron.setChecked(true);
        }
        if(feed.getVitamin().equals("Yes")){
            vitamin.setChecked(true);
        }

    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }
}