package com.iso.downsconnect.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.MedicalInfo;

public class NoAgeFragment extends Fragment {
    private EditText notes;
    private MedicalInfo medicalInfo;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notagescheduled, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notes = view.findViewById(R.id.noAgeNotes);
        medicalInfo = new MedicalInfo();
    }

    public MedicalInfo saveInfo(){
        if(!notes.getText().toString().equals("")){
            medicalInfo.setNotes(notes.getText().toString());
        }
        else {
            medicalInfo.setNotes("None");
        }
        return medicalInfo;
    }
}
