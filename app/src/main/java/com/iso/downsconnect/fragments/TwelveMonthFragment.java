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

public class TwelveMonthFragment extends Fragment {
    private CheckBox yes1, yes2, yes3, yes4, yes5, yes6, yes7, yes8, yes9, yes10,
            no1, no2, no3, no4, no5, no6, no7, no8, no9, no10;
    private EditText date1, date2, date3, date4, date5;
    private Spinner provider1, provider2, provider3, provider4, provider5;
    private VisitInfo visitInfo = new VisitInfo();
    private ArrayList<String> p_names = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private DBHelper dbHelper;
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

        setRegularListener(yes1, no1, "yes");
        setRegularListener(yes3, no3, "yes");
        setRegularListener(yes5, no5, "yes");
        setRegularListener(yes7, no7, "yes");
        setRegularListener(yes9, no9, "yes");
        setRegularListener(yes8, no8, "yes");
        setRegularListener(yes10, no10, "yes");


        setRegularListener(no1, yes1, "no");
        setRegularListener(no3, yes3, "no");
        setRegularListener(no5, yes5, "no");
        setRegularListener(no7, yes7, "no");
        setRegularListener(no8, yes8, "no");
        setRegularListener(no9, yes9, "no");
        setRegularListener(no10, yes10, "no");

        setToggleListener(yes2, no2, "yes", date3, provider3);
        setToggleListener(yes4, no4, "yes", date4, provider4);
        setToggleListener(yes6, no6, "yes", date5, provider5);

        setToggleListener(no2, yes2, "no", date3, provider3);
        setToggleListener(no4, yes4, "no", date4, provider4);
        setToggleListener(no6, yes6, "no", date5, provider5);

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
        provider5.setAdapter(providerAdapter);

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
