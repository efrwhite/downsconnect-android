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

import com.iso.downsconnect.DBHelper;
import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.Provider;
import com.iso.downsconnect.objects.VisitInfo;

import java.util.ArrayList;

public class FiveYearFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_fiveyears, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getContext());
        providers = dbHelper.getAllProviders();

        yes1 = view.findViewById(R.id.checkBoxYes75);
        no1 = view.findViewById(R.id.checkBoxNo76);
        yes2 = view.findViewById(R.id.checkBoxYes79);
        no2 = view.findViewById(R.id.checkBoxNo80);
        yes3 = view.findViewById(R.id.checkBoxYes80);
        no3 = view.findViewById(R.id.checkBoxNo81);
        yes4 = view.findViewById(R.id.checkBoxYes81);
        no4 = view.findViewById(R.id.checkBoxNo82);
        yes5 = view.findViewById(R.id.checkBoxYes82);
        no5 = view.findViewById(R.id.checkBoxNo83);
        yes6 = view.findViewById(R.id.checkBoxYes83);
        no6 = view.findViewById(R.id.checkBoxNo84);
        yes7 = view.findViewById(R.id.checkBoxYes84);
        no7 = view.findViewById(R.id.checkBoxNo85);

        date1 = view.findViewById(R.id.assessDate31);
        date2 = view.findViewById(R.id.assessDate32);
        date3 = view.findViewById(R.id.assessDate33);
        date4 = view.findViewById(R.id.assessDate34);
        date5 = view.findViewById(R.id.assessDate35);

        provider1 = view.findViewById(R.id.Spin5_1);
        provider2 = view.findViewById(R.id.Spin5_2);
        provider3 = view.findViewById(R.id.Spin5_3);
        provider4 = view.findViewById(R.id.Spin5_4);
        provider5 = view.findViewById(R.id.Spin5_5);

        setToggleListener(yes2, no2, "yes", date4, provider4);
        setToggleListener(yes4, no4, "yes", date5, provider5);
        setToggleListener(no2, yes2, "no", date4, provider4);
        setToggleListener(no4, yes4, "no", date5, provider5);

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
