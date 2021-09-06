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

import com.iso.downsconnect.ActivityContainer;
import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.Bathroom;
import com.iso.downsconnect.helpers.DateHandler;
import com.iso.downsconnect.objects.Entry;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConstipationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConstipationFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private EditText lastStoolDate, treatment, notes;
    private Button save;
    private Bathroom bathroom = new Bathroom();
    private DBHelper helper;
    private Entry entry;

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
        return inflater.inflate(R.layout.fragment_constipation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final int childID = sharedPreferences.getInt("name", 1);

        bathroom.setBathroomType("Constipation");
        bathroom.setChildID(childID);
        bathroom.setDiaperCream("None");
        bathroom.setOpenAir("None");
        bathroom.setLeak("None");
        bathroom.setQuantity("None");
        bathroom.setPottyAccident("None");
        bathroom.setDuration("None");

        lastStoolDate = view.findViewById(R.id.stoolDateEditText);
        lastStoolDate.setFocusable(false);
        treatment = view.findViewById(R.id.quantityEditText);
        notes = view.findViewById(R.id.editText);
        save = view.findViewById(R.id.saveButton);
        helper = new DBHelper(getContext());
        entry = new Entry();

        lastStoolDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!lastStoolDate.getText().toString().equals("") && !treatment.getText().toString().equals("")){
                    if(!notes.getText().toString().equals("")){
                        bathroom.setNotes(notes.getText().toString());
                    }
                    bathroom.setTreatmentPlan(treatment.getText().toString());
                    helper.addBathroom(bathroom);
                    entry.setChildID(childID);
                    entry.setEntryText(helper.getChildName(childID) + " was constipated, treated with " + bathroom.getTreatmentPlan());
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    helper.addEntry(entry);
                    Intent intent = new Intent(getContext(), ActivityContainer.class);
                    startActivity(intent);
                }
                else{
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
        DateHandler month_ = new DateHandler();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        bathroom.setDateOfLastStool(calendar.getTimeInMillis());
        lastStoolDate.setText(month_.getMonth(month) + " " + dayOfMonth + ", " + year);
    }

//    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
//
//        // when dialog box is closed, below method will be called.
//        public void onDateSet(DatePicker view, int selectedYear,
//                              int selectedMonth, int selectedDay) {
//            DateHandler month_ = new DateHandler();
//            lastStoolDate.setText(month_.getMonth(selectedMonth) + " " + selectedDay + ", " + selectedYear);
//
//        }
//    };
}