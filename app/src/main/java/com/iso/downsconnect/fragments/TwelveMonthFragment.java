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
import com.iso.downsconnect.objects.VisitInfo;

import java.util.ArrayList;

public class TwelveMonthFragment extends Fragment {
    private CheckBox yes1, yes2, yes3, yes4, yes5, yes6, yes7, yes8, yes9, yes10,
            no1, no2, no3, no4, no5, no6, no7, no8, no9, no10;
    private EditText date1, date2, date3, date4, date5;
    private Spinner provider1, provider2, provider3, provider4, provider5;
    private VisitInfo visitInfo = new VisitInfo();
    private ArrayList<String> p_names = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private DBHelper dbHelper;
    private MedicalInfo medicalInfo;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twelvemonth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getContext());
        providers = dbHelper.getAllProviders();
        medicalInfo = new MedicalInfo();

        yes1 = view.findViewById(R.id.checkBoxYes31);
        no1 = view.findViewById(R.id.checkBoxNo32);
        yes2 = view.findViewById(R.id.checkBoxYes32);
        no2 = view.findViewById(R.id.checkBoxNo33);
        yes3 = view.findViewById(R.id.checkBoxYes33);
        no3 = view.findViewById(R.id.checkBoxNo34);
        yes4 = view.findViewById(R.id.checkBoxYes34);
        no4 = view.findViewById(R.id.checkBoxNo35);
        yes5 = view.findViewById(R.id.checkBoxYes41);
        no5 = view.findViewById(R.id.checkBoxNo42);
        yes6 = view.findViewById(R.id.checkBoxYes42);
        no6 = view.findViewById(R.id.checkBoxNo43);
        yes7 = view.findViewById(R.id.checkBoxYes43);
        no7 = view.findViewById(R.id.checkBoxNo44);
        yes8 = view.findViewById(R.id.checkBoxYes44);
        no8 = view.findViewById(R.id.checkBoxNo45);
        yes9 = view.findViewById(R.id.checkBoxYes45);
        no9 = view.findViewById(R.id.checkBoxNo46);
        yes10 = view.findViewById(R.id.checkBoxYes46);
        no10 = view.findViewById(R.id.checkBoxNo47);

        date1 = view.findViewById(R.id.assessDate9);
        date2 = view.findViewById(R.id.assessDate10);
        date3 = view.findViewById(R.id.assessDate11);
        date4 = view.findViewById(R.id.assessDate12);
        date5 = view.findViewById(R.id.assessDate13);

        provider1 = view.findViewById(R.id.Spin1_1);
        provider2 = view.findViewById(R.id.Spin1_2);
        provider3 = view.findViewById(R.id.Spin1_3);
        provider4 = view.findViewById(R.id.Spin1_4);
        provider5 = view.findViewById(R.id.Spin1_5);

        setRegularListener(yes1, no1);
        setRegularListener(yes3, no3);
        setRegularListener(yes5, no5);
        setRegularListener(yes7, no7);
        setRegularListener(yes9, no9);
        setRegularListener(yes8, no8);
        setRegularListener(yes10, no10);

        setToggleListener(yes2, no2, date3, provider3);
        setToggleListener(yes4, no4, date4, provider4);
        setToggleListener(yes6, no6, date5, provider5);

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
        int four = selectedCheckbox(yes4, no4, 2);
        int five = selectedCheckbox(yes5, no5, 2);
        int six = selectedCheckbox(yes6, no6, 2);
        int seven = selectedCheckbox(yes7, no7, 2);

        if(one == -1 || two == -1 || three == -1 || four == -1 || five == -1 || six == -1 || seven == -1){
            return null;
        }

        boolean written = false;
        if(yes2.isChecked()){
            if(!date3.getText().toString().equals("") && !provider3.getSelectedItem().toString().equals("Select")){
                if(!written) {
                    medicalInfo.setDates(date3.getText().toString());
                    medicalInfo.setProviders(provider3.getSelectedItem().toString());
                    written = true;
                }
            }
            else{
                return null;
            }
        }
        if(yes4.isChecked()){
            if(!date4.getText().toString().equals("") && !provider4.getSelectedItem().toString().equals("Select")){
                if(!written) {
                    medicalInfo.setDates(date4.getText().toString());
                    medicalInfo.setProviders(provider4.getSelectedItem().toString());
                    written = true;
                }
                else{
                    medicalInfo.setDates(medicalInfo.getDates() + "," + date4.getText().toString());
                    medicalInfo.setProviders(medicalInfo.getProviders() + "," + provider4.getSelectedItem().toString());
                }
            }
            else{
                return null;
            }
        }
        if(yes6.isChecked()){
            if(!date5.getText().toString().equals("") && !provider5.getSelectedItem().toString().equals("Select")){
                if(!written) {
                    medicalInfo.setDates(date5.getText().toString());
                    medicalInfo.setProviders(provider5.getSelectedItem().toString());
                    written = true;
                }
                else {
                    medicalInfo.setDates(medicalInfo.getDates() + "," + date5.getText().toString());
                    medicalInfo.setProviders(medicalInfo.getProviders() + "," + provider5.getSelectedItem().toString());
                }
            }
            else{
                return null;
            }
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
