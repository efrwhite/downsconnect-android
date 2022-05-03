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

public class EighteenMonthFragment extends Fragment {
    //declare variables
    private Spinner provider1, provider2;
    private EditText date1, date2;
    private CheckBox yes1, yes2, yes3, yes4, no1, no2, no3, no4;
    private MedicalInfo medicalInfo;

    private ArrayList<String> p_names = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private DBHelper dbHelper;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eighteenmonth, container, false);
    }

    //fragment constructor
    public EighteenMonthFragment(){

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //create db object and get all provides
        dbHelper = new DBHelper(getContext());
        providers = dbHelper.getAllProviders();

        medicalInfo = new MedicalInfo();

        //intialize all layout objects
        yes1 = view.findViewById(R.id.checkBoxYes47);
        no1 = view.findViewById(R.id.checkBoxNo48);
        yes2 = view.findViewById(R.id.checkBoxYes48);
        no2 = view.findViewById(R.id.checkBoxNo49);
        yes3 = view.findViewById(R.id.checkBoxYes49);
        no3 = view.findViewById(R.id.checkBoxNo50);
        yes4 = view.findViewById(R.id.checkBoxYes50);
        no4 = view.findViewById(R.id.checkBoxNo51);

        date1 = view.findViewById(R.id.assessDate14);
        date2 = view.findViewById(R.id.assessDate15);

        provider1 = view.findViewById(R.id.spin18_1);
        provider2 = view.findViewById(R.id.spin18_2);

        //creates listeners for each checkbox
        setRegularListener(yes1, no1);
        setRegularListener(yes2, no2);
        setRegularListener(yes3, no3);
        setRegularListener(yes4, no4);

        //loads spinners with the provider names
        loadSpinnerData();
    }

    //Called from within DoctorsVisitActivity in order to save visit info
    public MedicalInfo saveInfo(){
        //sets the information in the medicalinfo object
        medicalInfo.setNotes("None");
        //checks if required fields have been filled out and set info accordingly
        if(!date1.getText().toString().equals("") && !provider1.getSelectedItem().toString().equals("Select")){
            medicalInfo.setDates(date1.getText().toString());
            medicalInfo.setProviders(provider1.getSelectedItem().toString());
        }
        else{
            return null;
        }

        //checks if required fields have been filled out
        if(!date2.getText().toString().equals("") && !provider2.getSelectedItem().toString().equals("Select")){
            medicalInfo.setDates(medicalInfo.getDates() + "," + date2.getText().toString());
            medicalInfo.setProviders(medicalInfo.getProviders() + "," + provider2.getSelectedItem().toString());
        }

        //checks which checkbox is checked
        int one = selectedCheckbox(yes1, no1, 1);
        int two = selectedCheckbox(yes2, no2, 2);
        int three = selectedCheckbox(yes3, no3, 2);
        int four = selectedCheckbox(yes4, no4, 2);

        //if a set of checkboxes is unchecked, return null
        if(one == -1 || two == -1 || three == -1 || four == -1 ){
            return null;
        }
        //return object to DoctorsVisitActivity to add to db
        return medicalInfo;
    }

    //sets the necessary info based on which checkcbox is selected
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
        //clear spinner to prevent duplicated
        p_names.clear();

        //add values to spinner array
        p_names.add("Select");
        for(Provider provide: providers){
            p_names.add(provide.getName());
        }
        //create an adapter to set for each spinner
        ArrayAdapter<String> providerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, p_names);
        providerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provider1.setAdapter(providerAdapter);
        provider2.setAdapter(providerAdapter);

    }

    //disables and enables fields based on which checkbox is checked when tied to spinner and textfields
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

    //deselects other checkbox
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

//    public void setData(MedicalInfo info){
//        String[] answers = info.getAnswers().split(",");
//        String[] dates = info.getDates().split(",");
//        String[] providers = info.getProviders().split(",");
//        if(answers[0].equals("yes")){
//            yes1.setChecked(true);
//        }
//        else{
//            no1.setChecked(true);
//        }
//        if(answers[1].equals("yes")){
//            yes2.setChecked(true);
//        }
//        else{
//            no2.setChecked(true);
//        }
//        if(answers[2].equals("yes")){
//            yes3.setChecked(true);
//        }
//        else{
//            no3.setChecked(true);
//        }
//        if(answers[3].equals("yes")){
//            yes4.setChecked(true);
//        }
//        else{
//            no4.setChecked(true);
//        }
//
//        date1.setText(dates[0]);
//        date2.setText(dates[1]);
//
//        provider1.setSelection(getIndex(provider1, providers[0]));
//        provider2.setSelection(getIndex(provider2, providers[1]));
//    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;

    }
}
