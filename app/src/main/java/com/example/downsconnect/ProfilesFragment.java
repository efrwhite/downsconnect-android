package com.example.downsconnect;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ProfilesFragment extends Fragment {
    private DBHelper helper;
    private ArrayList<Child> children = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_profiles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button childrenBtn = view.findViewById(R.id.addChildrenButton);
        helper = new DBHelper(getContext());
        addChildren();
        childrenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailedProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    public void addChildren(){
        LinearLayout childLayout = getView().findViewById(R.id.childrenLinearLayout);
        childLayout.setGravity(Gravity.CENTER);
        children = helper.getAllChildren();
        Log.i("size", String.valueOf(children.size()));
        int i = 0;
        for(Child child: children){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 50, 30);
            TextView view = new TextView(getContext());
            view.setGravity(Gravity.CENTER);
            view.setText(child.getFirstName());
            view.setTextColor(Color.BLACK);
            view.setTextSize(20);
            Button button = new Button(getContext());
            //button.setId("Child" + String.valueOf(i) + "btn");
            view.setLayoutParams(layoutParams);
            childLayout.addView(view);

        }
    }
}