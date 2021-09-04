package com.iso.downsconnect.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.MedicalInfo;
import com.iso.downsconnect.objects.VisitInfo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewBornFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewBornFragment extends Fragment {
    private CheckBox yes1, yes2, yes3, yes4, yes5, yes6, yes7, yes8, yes9, yes10,
            no1, no2, no3, no4, no5, no6, no7, no8, no9, no10;
    private EditText date1, date2, date3, date4, date5;
    private Spinner provider1, provider2, provider3, provider4, provider5;
    private VisitInfo visitInfo = new VisitInfo();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewBornFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewBornFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewBornFragment newInstance(String param1, String param2) {
        NewBornFragment fragment = new NewBornFragment();
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
        return inflater.inflate(R.layout.fragment_newborn, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        yes1 = view.findViewById(R.id.checkBoxYes11);
        yes2 = view.findViewById(R.id.checkBoxYes2);
        yes3 = view.findViewById(R.id.checkBoxYes3);
        yes4 = view.findViewById(R.id.checkBoxYes4);
        yes5 = view.findViewById(R.id.checkBoxYes5);
        yes6 = view.findViewById(R.id.checkBoxYes6);
        yes7 = view.findViewById(R.id.checkBoxYes7);
        yes8 = view.findViewById(R.id.checkBoxYes8);
        yes9 = view.findViewById(R.id.checkBoxYes9);
        yes10 = view.findViewById(R.id.checkBoxYes10);

        no1 = view.findViewById(R.id.checkBoxNo);
        no2 = view.findViewById(R.id.checkBoxNo2);
        no3 = view.findViewById(R.id.checkBoxNo3);
        no4 = view.findViewById(R.id.checkBoxNo4);
        no5 = view.findViewById(R.id.checkBoxNo5);
        no6 = view.findViewById(R.id.checkBoxNo6);
        no7 = view.findViewById(R.id.checkBoxNo7);
        no8 = view.findViewById(R.id.checkBoxNo8);
        no9 = view.findViewById(R.id.checkBoxNo9);
        no10 = view.findViewById(R.id.checkBoxNo10);

        date1 = view.findViewById(R.id.assessDate);
        date2 = view.findViewById(R.id.assessDate2);
        date3 = view.findViewById(R.id.assessDate3);
        date4 = view.findViewById(R.id.assessDate4);
        date5 = view.findViewById(R.id.assessDate5);


    }

    //call this method from doctorsvisit in order to save the info......I think :)
    public void saveInfo(){
       int one = selectedCheckbox(yes1, no1);
       int two = selectedCheckbox(yes2, no2);
       int three = selectedCheckbox(yes3, no3);
       int four = selectedCheckbox(yes4, no4);
       int five = selectedCheckbox(yes5, no5);
       int six = selectedCheckbox(yes6, no6);
       int seven = selectedCheckbox(yes7, no7);
       int eight = selectedCheckbox(yes8, no8);
       int nine = selectedCheckbox(yes9, no9);
       int ten = selectedCheckbox(yes10, no10);

       if(!date1.getText().toString().equals("") && !date2.getText().toString().equals("") && !date3.getText().toString().equals("")
            && !date4.getText().toString().equals("") && !date5.getText().toString().equals("")){
           visitInfo.setDates(date1.getText().toString() + ";" + date2.getText().toString() + ";" + date3.getText().toString() + ";"
                   + date4.getText().toString() + ";" + date5.getText().toString());
       }
    }

    public int selectedCheckbox(CheckBox one, CheckBox two){
        if(one.isChecked()){
            if(visitInfo.getAnswers().equals("")){
                visitInfo.setAnswers(one.getText().toString());
            }
            else{
                visitInfo.setAnswers(visitInfo.getAnswers().concat(";").concat(one.getText().toString()));
            }
            return 1;
        }
        else if(two.isChecked()){
            if(visitInfo.getAnswers().equals("")){
                visitInfo.setAnswers(two.getText().toString());
            }
            else{
                visitInfo.setAnswers(visitInfo.getAnswers().concat("; ").concat(two.getText().toString()));
            }
            return 1;
        }
        else{
            return -1;
        }
    }

    public boolean validate(){
        return (yes1.isChecked() || no1.isChecked()) && (yes2.isChecked() || no2.isChecked()) && (yes3.isChecked() || no3.isChecked()) && (yes4.isChecked() || no4.isChecked())
                && (yes5.isChecked() || no5.isChecked()) && (yes6.isChecked() || no6.isChecked()) && (yes7.isChecked() || no7.isChecked()) && (yes8.isChecked() || no8.isChecked())
                && (yes9.isChecked() || no9.isChecked()) && (yes10.isChecked() || no10.isChecked()) && !date1.getText().toString().equals("") && !date2.getText().toString().equals("")
                && !date3.getText().toString().equals("") && !date4.getText().toString().equals("") && !date5.getText().toString().equals("");
    }
}