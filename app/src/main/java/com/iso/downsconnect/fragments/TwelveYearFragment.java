package com.iso.downsconnect.fragments;

import android.os.Bundle;
import android.provider.MediaStore;
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

public class TwelveYearFragment extends Fragment {
    private CheckBox yes1, no1;
    private EditText date1, date2;
    private Spinner provider1, provider2;

    private ArrayList<String> p_names = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private DBHelper dbHelper;
    private MedicalInfo medicalInfo;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twelveyears, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getContext());
        providers = dbHelper.getAllProviders();
        medicalInfo = new MedicalInfo();

        yes1 = view.findViewById(R.id.checkBoxYes127);
        no1 = view.findViewById(R.id.checkBoxNo128);

        date1 = view.findViewById(R.id.assessDate61);
        provider1 = view.findViewById(R.id.Spin12_1);
        date2 = view.findViewById(R.id.assessDate62);
        provider2 = view.findViewById(R.id.Spin12_2);

        setToggleListener(yes1, no1, date2, provider2);



        loadSpinnerData();
    }

    public MedicalInfo saveInfo(){
        medicalInfo.setNotes("None");
        //check which checkbox is checked
        int one = selectedCheckbox(yes1, no1, 1);

        //if a set of checkboxes is unchecked, return null
        if(one == -1){
            return null;
        }

        //check to see if a checkbox with an appoinment date and provider has been filled out
        boolean written = false;
        if(yes1.isChecked()){
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

}
