package com.iso.downsconnect.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.iso.downsconnect.ActivityContainer;
import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.Bathroom;
import com.iso.downsconnect.objects.Entry;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiaperFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiaperFragment extends Fragment {
    private Bathroom bathroom = new Bathroom();
    private DBHelper helper;
    private Entry entry = new Entry();
    private EditText notes;
    private Spinner diaperLeak, openAir, cream, quantity;
    private Button save;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DiaperFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiaperFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiaperFragment newInstance(String param1, String param2) {
        DiaperFragment fragment = new DiaperFragment();
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
        return inflater.inflate(R.layout.fragment_diaper, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final int childID = sharedPreferences.getInt("name", 1);

        bathroom.setBathroomType("Diaper");
        bathroom.setChildID(childID);
        bathroom.setDateOfLastStool(-1);
        bathroom.setTreatmentPlan("None");
        bathroom.setPottyAccident("None");
        bathroom.setDuration("None");

        diaperLeak = view.findViewById(R.id.leakSpinner);
        openAir = view.findViewById(R.id.openAirSpinner);
        cream = view.findViewById(R.id.diaperCreamSpinner);
        quantity = view.findViewById(R.id.quantitySpinner);
        notes = view.findViewById(R.id.editText);
        save = view.findViewById(R.id.saveButton);
        helper = new DBHelper(getContext());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!diaperLeak.getSelectedItem().equals("Select") && !openAir.getSelectedItem().equals("Select")
                    && !cream.getSelectedItem().equals("Select") && !quantity.getSelectedItem().equals("Select")){
                    if(!notes.getText().toString().equals("")){
                        bathroom.setNotes(notes.getText().toString());
                    }
                    else{
                        bathroom.setNotes("None");
                    }
                    bathroom.setDiaperCream(cream.getSelectedItem().toString());
                    bathroom.setLeak(diaperLeak.getSelectedItem().toString());
                    bathroom.setOpenAir(openAir.getSelectedItem().toString());
                    bathroom.setQuantity(quantity.getSelectedItem().toString());
                    entry.setEntryText(helper.getChildName(childID) + " had an accident in their diaper");
                    entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                    entry.setChildID(childID);
                    entry.setEntryType("Bathroom");


                    long id = helper.addBathroom(bathroom);
                    entry.setForeignID(id);
                    helper.addEntry(entry);
                    Intent intent = new Intent(getContext(), ActivityContainer.class);
                    startActivity(intent);
                }
                else{
                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
                    a.setTitle("Missing Information");
                    a.setMessage("Please make sure you've filled out the necessary information");
                    a.show();
                }
            }
        });
    }
}