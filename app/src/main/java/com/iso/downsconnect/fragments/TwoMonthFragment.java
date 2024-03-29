package com.iso.downsconnect.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.MedicalInfo;
import com.iso.downsconnect.objects.Provider;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TwoMonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoMonthFragment extends Fragment {
    private CheckBox yes1, yes2, yes3, yes4, yes5, yes6, yes7, yes8, yes9, no1, no2, no3, no4, no5, no6, no7, no8, no9;
    private EditText date;
    private Spinner provider;

    private ArrayList<String> p_names = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private DBHelper dbHelper;
    private MedicalInfo medicalInfo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TwoMonthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TwoMonthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TwoMonthFragment newInstance(String param1, String param2) {
        TwoMonthFragment fragment = new TwoMonthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twomonth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //create db object and get all providers
        dbHelper = new DBHelper(getContext());
        providers = dbHelper.getAllProviders();
        medicalInfo = new MedicalInfo();

        //initialize all layout objects
        yes1 = view.findViewById(R.id.checkBoxYes11);
        no1 = view.findViewById(R.id.checkBoxNo11);
        yes2 = view.findViewById(R.id.checkBoxYes);
        no2 = view.findViewById(R.id.checkBoxNo12);
        yes3 = view.findViewById(R.id.checkBoxYes12);
        no3 = view.findViewById(R.id.checkBoxNo13);
        yes4 = view.findViewById(R.id.checkBoxYes13);
        no4 = view.findViewById(R.id.checkBoxNo14);
        yes5 = view.findViewById(R.id.checkBoxYes14);
        no5 = view.findViewById(R.id.checkBoxNo15);
        yes6 = view.findViewById(R.id.checkBoxYes15);
        no6 = view.findViewById(R.id.checkBoxNo16);
        yes7 = view.findViewById(R.id.checkBoxYes16);
        no7 = view.findViewById(R.id.checkBoxNo17);
        yes8 = view.findViewById(R.id.checkBoxYes17);
        no8 = view.findViewById(R.id.checkBoxNo18);
        yes9 = view.findViewById(R.id.checkBoxYes18);
        no9 = view.findViewById(R.id.checkBoxNo19);

        date = view.findViewById(R.id.editTextDate);
        provider = view.findViewById(R.id.twoMSpinner);

        //creates listeners for each checkbox
        setRegularListener(yes1, no1);
        setRegularListener(yes2, no2);
        setRegularListener(yes3, no3);
        setRegularListener(yes4, no4);
        setRegularListener(yes5, no5);
        setRegularListener(yes6, no6);
        setRegularListener(yes7, no7);
        setRegularListener(yes8, no8);

        //creates listeners for each checkbox
        setToggleListener(yes9, no9, date, provider);

        //loads spinners with the provider names
        loadSpinnerData();
    }

    //Called from within DoctorsVisitActivity in order to save visit info
    public MedicalInfo saveInfo(){
        //checks which checkbox is checked
        int one = selectedCheckbox(yes1, no1, 1);
        int two = selectedCheckbox(yes2, no2, 2);
        int three = selectedCheckbox(yes3, no3, 2);
        int four = selectedCheckbox(yes4, no4, 2);
        int five = selectedCheckbox(yes5, no5, 2);
        int six = selectedCheckbox(yes6, no6, 2);
        int seven = selectedCheckbox(yes7, no7, 2);
        int eight = selectedCheckbox(yes8, no8, 2);
        int nine = selectedCheckbox(yes9, no9, 2);

        //if a set of checkboxes is unchecked, return null
        if(one == -1 || two == -1 || three == -1 || four == -1 || five == -1 || six == -1 || seven == -1 || eight == -1 || nine == -1){
            return null;
        }

        boolean written = false;
        if(yes9.isChecked()){
            //check if required info is filled out if yes is checked
            if(!date.getText().toString().equals("") && !provider.getSelectedItem().toString().equals("Select")){
                if(!written) {
                    //set object values if filled out
                    medicalInfo.setDates(date.getText().toString());
                    medicalInfo.setProviders(provider.getSelectedItem().toString());
                    written = true;
                }
            }
            else{
                return null;
            }
        }
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

        //create an adapter set for each adapter, which displays the array of names
        ArrayAdapter<String> providerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, p_names);
        providerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provider.setAdapter(providerAdapter);

    }

    //create an adapter set for each adapter, which displays the array of names
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
}