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

import java.util.ArrayList;

public class EighteenMonthFragment extends Fragment {
    private Spinner provider1, provider2;
    private EditText date1, date2;
    private CheckBox yes1, yes2, yes3, yes4, no1, no2, no3, no4;

    private ArrayList<String> p_names = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private DBHelper dbHelper;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eighteenmonth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(getContext());
        providers = dbHelper.getAllProviders();

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

        setRegularListener(yes1, no1, "yes");
        setRegularListener(yes2, no2, "yes");
        setRegularListener(yes3, no3, "yes");
        setRegularListener(yes4, no4, "yes");

        setRegularListener(no1, yes1, "no");
        setRegularListener(no2, yes2, "no");
        setRegularListener(no3, yes3, "no");
        setRegularListener(no4, yes4, "no");

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
