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

public class ThreeYearFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_threeyears, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getContext());
        providers = dbHelper.getAllProviders();

        yes1 = view.findViewById(R.id.checkBoxYes64);
        no1 = view.findViewById(R.id.checkBoxNo65);
        yes2 = view.findViewById(R.id.checkBoxYes65);
        no2 = view.findViewById(R.id.checkBoxNo66);
        yes3 = view.findViewById(R.id.checkBoxYes66);
        no3 = view.findViewById(R.id.checkBoxNo67);
        yes4 = view.findViewById(R.id.checkBoxYes67);
        no4 = view.findViewById(R.id.checkBoxNo68);
        yes5 = view.findViewById(R.id.checkBoxYes76);
        no5 = view.findViewById(R.id.checkBoxNo77);
        yes6 = view.findViewById(R.id.checkBoxYes77);
        no6 = view.findViewById(R.id.checkBoxNo78);
        yes7 = view.findViewById(R.id.checkBoxYes78);
        no7 = view.findViewById(R.id.checkBoxNo79);

        date1 = view.findViewById(R.id.assessDate22);
        date2 = view.findViewById(R.id.assessDate23);
        date3 = view.findViewById(R.id.assessDate24);
        date4 = view.findViewById(R.id.assessDate25);

        provider1 = view.findViewById(R.id.Spin3_1);
        provider2 = view.findViewById(R.id.Spin3_2);
        provider3 = view.findViewById(R.id.Spin3_3);
        provider4 = view.findViewById(R.id.Spin3_4);

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
