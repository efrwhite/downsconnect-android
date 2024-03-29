package com.iso.downsconnect.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.MedicalInfo;

public class NineMonthFragment extends Fragment {
    private CheckBox yes1, yes2, yes3, yes4,
            no1, no2, no3, no4;
    private MedicalInfo medicalInfo;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ninemonth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        medicalInfo = new MedicalInfo();

        //initialize all layout objects
        yes1 = view.findViewById(R.id.checkBoxYes23);
        no1 = view.findViewById(R.id.checkBoxNo24);
        yes2 = view.findViewById(R.id.checkBoxYes24);
        no2 = view.findViewById(R.id.checkBoxNo25);
        yes3 = view.findViewById(R.id.checkBoxYes25);
        no3 = view.findViewById(R.id.checkBoxNo26);
        yes4 = view.findViewById(R.id.checkBoxYes26);
        no4 = view.findViewById(R.id.checkBoxNo27);

        //set click listeners for each checkbox
        setRegularListener(yes1, no1);
        setRegularListener(yes3, no3);
        setRegularListener(yes2, no2);
        setRegularListener(yes4, no4);

    }

    //Called from within DoctorsVisitActivity in order to save visit info
    public MedicalInfo saveInfo(){
        medicalInfo.setNotes("None");

        //check which checkbox is checked
        int one = selectedCheckbox(yes1, no1, 1);
        int two = selectedCheckbox(yes2, no2, 2);
        int three = selectedCheckbox(yes3, no3, 2);
        int four = selectedCheckbox(yes4, no4, 2);

        //if a set of checkboxes is unchecked, return null
        if(one == -1 || two == -1 || three == -1 || four == -1){
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
        else{
            //-1 if neither is checked
            return -1;
        }
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
}
