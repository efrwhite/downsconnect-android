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
import com.iso.downsconnect.objects.Provider;
import com.iso.downsconnect.objects.VisitInfo;

import java.util.ArrayList;

public class SevenYearFragment extends Fragment {
    private CheckBox yes1, yes2, yes3, yes4, yes5, yes6, yes7,
            no1, no2, no3, no4, no5, no6, no7;
    private EditText date1, date2, date3, date4;
    private Spinner provider1, provider2, provider3, provider4;
    private VisitInfo visitInfo = new VisitInfo();
    private ArrayList<String> p_names = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private DBHelper dbHelper;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sevenyears, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getContext());
        providers = dbHelper.getAllProviders();

        yes1 = view.findViewById(R.id.checkBoxYes92);
        no1 = view.findViewById(R.id.checkBoxNo93);
        yes2 = view.findViewById(R.id.checkBoxYes93);
        no2 = view.findViewById(R.id.checkBoxNo94);
        yes3 = view.findViewById(R.id.checkBoxYes94);
        no3 = view.findViewById(R.id.checkBoxNo95);
        yes4 = view.findViewById(R.id.checkBoxYes95);
        no4 = view.findViewById(R.id.checkBoxNo96);
        yes5 = view.findViewById(R.id.checkBoxYes96);
        no5 = view.findViewById(R.id.checkBoxNo97);
        yes6 = view.findViewById(R.id.checkBoxYes97);
        no6 = view.findViewById(R.id.checkBoxNo98);
        yes7 = view.findViewById(R.id.checkBoxYes98);
        no7 = view.findViewById(R.id.checkBoxNo99);

        date1 = view.findViewById(R.id.assessDate42);
        date2 = view.findViewById(R.id.assessDate43);
        date3 = view.findViewById(R.id.assessDate44);
        date4 = view.findViewById(R.id.assessDate45);

        provider1 = view.findViewById(R.id.Spin7_1);
        provider2 = view.findViewById(R.id.Spin7_2);
        provider3 = view.findViewById(R.id.Spin7_3);
        provider4 = view.findViewById(R.id.Spin7_4);

        setToggleListener(yes2, no2, "yes", date3, provider3);
        setToggleListener(yes4, no4, "yes", date4, provider4);
        setToggleListener(no2, yes2, "no", date3, provider3);
        setToggleListener(no4, yes4, "no", date4, provider4);

        setRegularListener(yes1, no1, "yes");
        setRegularListener(yes3, no3, "yes");
        setRegularListener(yes5, no5, "yes");
        setRegularListener(yes6, no6, "yes");
        setRegularListener(yes7, no7, "yes");

        setRegularListener(no1, yes1, "no");
        setRegularListener(no3, yes3, "no");
        setRegularListener(no5, yes5, "no");
        setRegularListener(no6, yes6, "no");
        setRegularListener(no7, yes7, "no");

        loadSpinnerData();

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

    }

    public void setToggleListener(final CheckBox checkBox1, final CheckBox checkBox2, String type, final EditText date, final Spinner provider){
        if(type.equals("yes")){
            checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox2.setChecked(false);
                    date.setEnabled(true);
                    provider.setEnabled(true);
                }
            });
        }
        else if(type.equals("no")){
            checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox2.setChecked(false);
                    date.setEnabled(false);
                    provider.setEnabled(false);
                }
            });
        }
    }

    private void setRegularListener(final CheckBox checkBox1, final CheckBox checkBox2, String type){
        if(type.equals("yes")){
            checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox2.setChecked(false);
                }
            });
        }
        else if(type.equals("no")){
            checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox2.setChecked(false);
                }
            });
        }
    }
}
