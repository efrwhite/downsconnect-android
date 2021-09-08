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

public class FifteenMonthFragment extends Fragment {
    private CheckBox yes1, yes2, yes3, yes4, yes5,
            no1, no2, no3, no4, no5;
    private MedicalInfo medicalInfo;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fifteenmonth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        medicalInfo = new MedicalInfo();

        yes1 = view.findViewById(R.id.checkBoxYes27);
        no1 = view.findViewById(R.id.checkBoxNo28);
        yes2 = view.findViewById(R.id.checkBoxYes28);
        no2 = view.findViewById(R.id.checkBoxNo29);
        yes3 = view.findViewById(R.id.checkBoxYes29);
        no3 = view.findViewById(R.id.checkBoxNo30);
        yes4 = view.findViewById(R.id.checkBoxYes61);
        no4 = view.findViewById(R.id.checkBoxNo62);
        yes5 = view.findViewById(R.id.checkBoxYes30);
        no5 = view.findViewById(R.id.checkBoxNo31);

        setRegularListener(yes1, no1);
        setRegularListener(yes3, no3);
        setRegularListener(yes5, no5);
        setRegularListener(yes4, no4);
        setRegularListener(yes2, no2);

    }

    public MedicalInfo saveInfo(){
        medicalInfo.setNotes("None");
        int one = selectedCheckbox(yes1, no1, 1);
        int two = selectedCheckbox(yes2, no2, 2);
        int three = selectedCheckbox(yes3, no3, 2);
        int four = selectedCheckbox(yes4, no4, 2);
        int five = selectedCheckbox(yes5, no5, 2);


        if(one == -1 || two == -1 || three == -1 || four == -1 || five == -1){
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
