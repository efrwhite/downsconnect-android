package com.iso.downsconnect.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.MedicalInfo;
import com.iso.downsconnect.objects.Provider;

import java.util.ArrayList;

public class ThirtyMonthsFragment extends Fragment {
    private CheckBox yes1, yes2, yes3,
            no1, no2, no3;
    private EditText date1, date2;
    private Spinner provider1, provider2;
    private ArrayList<String> p_names = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private DBHelper dbHelper;
    private MedicalInfo medicalInfo;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thirtymonth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getContext());
        providers = dbHelper.getAllProviders();
        medicalInfo = new MedicalInfo();

        yes1 = view.findViewById(R.id.checkBoxYes55);
        no1 = view.findViewById(R.id.checkBoxNo56);
        yes2 = view.findViewById(R.id.checkBoxYes56);
        no2 = view.findViewById(R.id.checkBoxNo57);
        yes3 = view.findViewById(R.id.checkBoxYes63);
        no3 = view.findViewById(R.id.checkBoxNo64);

        date1 = view.findViewById(R.id.assessDate20);
        date2 = view.findViewById(R.id.assessDate21);

        provider1 = view.findViewById(R.id.Spin30_1);
        provider2 = view.findViewById(R.id.Spin30_2);


        setRegularListener(yes1, no1);
        setRegularListener(yes3, no3);
        setRegularListener(yes2, no2);
        loadSpinnerData();

    }

    public MedicalInfo saveInfo(){
        medicalInfo.setNotes("None");
        if(!date1.getText().toString().equals("") && !provider1.getSelectedItem().toString().equals("Select")){
            medicalInfo.setDates(date1.getText().toString());
            medicalInfo.setProviders(provider1.getSelectedItem().toString());
        }
        else{
            return null;
        }

        if(!date2.getText().toString().equals("") && !provider2.getSelectedItem().toString().equals("Select")){
            medicalInfo.setDates(medicalInfo.getDates() + "," + date2.getText().toString());
            medicalInfo.setProviders(medicalInfo.getProviders() + "," + provider2.getSelectedItem().toString());
        }
        else{
            return null;
        }
        int one = selectedCheckbox(yes1, no1, 1);
        int two = selectedCheckbox(yes2, no2, 2);
        int three = selectedCheckbox(yes3, no3, 2);

        if(one == -1 || two == -1 || three == -1){
            return null;
        }
        return medicalInfo;
    }

    public int selectedCheckbox(CheckBox one, CheckBox two, int first){
        if(one.isChecked()){
            if(first == 1){
                medicalInfo.setAnswers(one.getText().toString());
            }
            else{
                medicalInfo.setAnswers(medicalInfo.getAnswers().concat(",").concat(one.getText().toString()));
            }
            return 1;
        }
        else if(two.isChecked()){
            if(first == 1){
                medicalInfo.setAnswers(two.getText().toString());
            }
            else{
                medicalInfo.setAnswers(medicalInfo.getAnswers().concat(",").concat(two.getText().toString()));
            }
            return 2;
        }
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
