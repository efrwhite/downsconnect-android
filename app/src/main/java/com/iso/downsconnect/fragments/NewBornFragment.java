package com.iso.downsconnect.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.R;
import com.iso.downsconnect.helpers.FragmentData;
import com.iso.downsconnect.objects.MedicalInfo;
import com.iso.downsconnect.objects.Provider;
import com.iso.downsconnect.objects.VisitInfo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewBornFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewBornFragment extends Fragment {
    private CheckBox yes1, yes2, yes3, yes4, yes5, yes6, yes7, yes8, yes9, yes10,
            no1, no2, no3, no4, no5, no6, no7, no8, no9, no10;
    private EditText date1, date2, date3, date4, date5;
    private Spinner provider1, provider2, provider3, provider4, provider5;
    private ArrayList<String> p_names = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private DBHelper dbHelper;
    private MedicalInfo medicalInfo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewBornFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewBornFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewBornFragment newInstance(String param1, String param2) {
        NewBornFragment fragment = new NewBornFragment();
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
        return inflater.inflate(R.layout.fragment_newborn, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getContext());
        providers = dbHelper.getAllProviders();

        medicalInfo = new MedicalInfo();

        yes1 = view.findViewById(R.id.checkBoxYes11);
        yes2 = view.findViewById(R.id.checkBoxYes2);
        yes3 = view.findViewById(R.id.checkBoxYes3);
        yes4 = view.findViewById(R.id.checkBoxYes4);
        yes5 = view.findViewById(R.id.checkBoxYes5);
        yes6 = view.findViewById(R.id.checkBoxYes6);
        yes7 = view.findViewById(R.id.checkBoxYes7);
        yes8 = view.findViewById(R.id.checkBoxYes8);
        yes9 = view.findViewById(R.id.checkBoxYes9);
        yes10 = view.findViewById(R.id.checkBoxYes10);

        no1 = view.findViewById(R.id.checkBoxNo);
        no2 = view.findViewById(R.id.checkBoxNo2);
        no3 = view.findViewById(R.id.checkBoxNo3);
        no4 = view.findViewById(R.id.checkBoxNo4);
        no5 = view.findViewById(R.id.checkBoxNo5);
        no6 = view.findViewById(R.id.checkBoxNo6);
        no7 = view.findViewById(R.id.checkBoxNo7);
        no8 = view.findViewById(R.id.checkBoxNo8);
        no9 = view.findViewById(R.id.checkBoxNo9);
        no10 = view.findViewById(R.id.checkBoxNo10);

        date1 = view.findViewById(R.id.assessDate);
        date2 = view.findViewById(R.id.assessDate2);
        date3 = view.findViewById(R.id.assessDate3);
        date4 = view.findViewById(R.id.assessDate4);
        date5 = view.findViewById(R.id.assessDate5);

        provider1 = view.findViewById(R.id.provider1);
        provider2 = view.findViewById(R.id.provider2);
        provider3 = view.findViewById(R.id.provider3);
        provider4 = view.findViewById(R.id.provider4);
        provider5 = view.findViewById(R.id.provider5);

        //set click listeners for each checkbox
        setRegularListener(yes1, no1);
        setRegularListener(yes3, no3);
        setRegularListener(yes5, no5);
        setRegularListener(yes7, no7);
        setRegularListener(yes9, no9);


        setToggleListener(yes2, no2, date1, provider1);
        setToggleListener(yes4, no4, date2, provider2);
        setToggleListener(yes6, no6,  date3, provider3);
        setToggleListener(yes8, no8,  date4, provider4);
        setToggleListener(yes10, no10,  date5, provider5);



        //load list of providers for all spinners
        loadSpinnerData();

    }

    //Called from within doctorsvisitactivity in order to save visit info
    public MedicalInfo saveInfo(){
        medicalInfo.setNotes("None");
        //check which checkbox is checked
       int one = selectedCheckbox(yes1, no1, 1);
       int two = selectedCheckbox(yes2, no2, 2);
       int three = selectedCheckbox(yes3, no3, 2);
       int four = selectedCheckbox(yes4, no4, 2);
       int five = selectedCheckbox(yes5, no5, 2);
       int six = selectedCheckbox(yes6, no6, 2);
       int seven = selectedCheckbox(yes7, no7, 2);
       int eight = selectedCheckbox(yes8, no8, 2);
       int nine = selectedCheckbox(yes9, no9, 2);
       int ten = selectedCheckbox(yes10, no10, 2);

       //if a set of checkboxes is unchecked, return null
       if(one == -1 || two == -1 || three == -1 || four == -1 || five == -1 || six == -1 || seven == -1 || eight == -1 || nine == -1 || ten == -1){
           return null;
       }

       //check to see if a checkbox with an appoinment date and provider has been filled out
       boolean written = false;
       if(yes2.isChecked()){
            if(!date1.getText().toString().equals("") && !provider1.getSelectedItem().toString().equals("Select")){
                if(!written) {
                    medicalInfo.setDates(date1.getText().toString());
                    medicalInfo.setProviders(provider1.getSelectedItem().toString());
                    written = true;
                }
            }
            else{
                return null;
            }
       }
       if(yes4.isChecked()){
            if(!date2.getText().toString().equals("") && !provider2.getSelectedItem().toString().equals("Select")){
                if(!written) {
                    medicalInfo.setDates(date2.getText().toString());
                    medicalInfo.setProviders(provider2.getSelectedItem().toString());
                    written = true;
                }
                else {
                    medicalInfo.setDates(medicalInfo.getDates() + "," + date2.getText().toString());
                    medicalInfo.setProviders(medicalInfo.getProviders() + "," + provider2.getSelectedItem().toString());
                }
            }
            else{
                return null;
            }
       }
       if(yes6.isChecked()){
            if(!date3.getText().toString().equals("") && !provider3.getSelectedItem().toString().equals("Select")){
                if(!written) {
                    medicalInfo.setDates(date3.getText().toString());
                    medicalInfo.setProviders(provider3.getSelectedItem().toString());
                    written = true;
                }
                else {
                    medicalInfo.setDates(medicalInfo.getDates() + "," + date3.getText().toString());
                    medicalInfo.setProviders(medicalInfo.getProviders() + "," + provider3.getSelectedItem().toString());
                }
            }
            else{
                return null;
            }
       }
       if(yes8.isChecked()){
            if(!date4.getText().toString().equals("") && !provider4.getSelectedItem().toString().equals("Select")){
                if(!written) {
                    medicalInfo.setDates(date4.getText().toString());
                    medicalInfo.setProviders(provider4.getSelectedItem().toString());
                    written = true;
                }
                else {
                    medicalInfo.setDates(medicalInfo.getDates() + "," + date4.getText().toString());
                    medicalInfo.setProviders(medicalInfo.getDates() + "," + provider4.getSelectedItem().toString());
                }
            }
            else{
                return null;
            }
       }
        if(yes10.isChecked()){
            if(!date5.getText().toString().equals("") && !provider5.getSelectedItem().toString().equals("Select")){
                if(!written) {
                    medicalInfo.setDates(date5.getText().toString());
                    medicalInfo.setProviders(provider5.getSelectedItem().toString());
                    written = true;
                }
                else {
                    medicalInfo.setDates(medicalInfo.getDates() + "," + date5.getText().toString());
                    medicalInfo.setProviders(medicalInfo.getDates() + "," + provider5.getSelectedItem().toString());
                }
            }
            else{
                return null;
            }
        }
       return medicalInfo;
    }

    public int selectedCheckbox(CheckBox one, CheckBox two, int first){
        //if yes is checked
        if(one.isChecked()){
            if(first == 1){
                medicalInfo.setAnswers(one.getText().toString());
            }
            else{
                medicalInfo.setAnswers(medicalInfo.getAnswers().concat(",").concat(one.getText().toString()));
            }
            return 1;
        }
        //if no is checked
        else if(two.isChecked()){
            if(first == 1){
                medicalInfo.setAnswers(two.getText().toString());
            }
            else{
                medicalInfo.setAnswers(medicalInfo.getAnswers().concat(",").concat(two.getText().toString()));
            }
            return 2;
        }
        //-1 if neither is checked
        else{
            return -1;
        }
    }

    public void loadSpinnerData(){
        //loads all the providers currently saved in db
        p_names.add("Select");
        for(Provider provide: providers){
            p_names.add(provide.getName());
        }
        ArrayAdapter<String> providerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, p_names);
        providerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provider1.setAdapter(providerAdapter);
        provider2.setAdapter(providerAdapter);
        provider3.setAdapter(providerAdapter);
        provider4.setAdapter(providerAdapter);
        provider5.setAdapter(providerAdapter);

    }

    public void setToggleListener(final CheckBox checkBox1, final CheckBox checkBox2, final EditText date, final Spinner provider){
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox2.setChecked(false);
                date.setEnabled(true);
                provider.setEnabled(true);
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox1.setChecked(false);
                date.setEnabled(false);
                provider.setEnabled(false);
            }
        });
    }

    private void setRegularListener(final CheckBox checkBox1, final CheckBox checkBox2){
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox2.setChecked(false);
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox1.setChecked(false);
            }
        });
    }
}