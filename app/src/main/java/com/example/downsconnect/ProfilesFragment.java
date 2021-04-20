package com.example.downsconnect;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;

import com.example.downsconnect.objects.Child;

import java.util.ArrayList;

public class ProfilesFragment extends Fragment {
    private DBHelper helper;
    private ArrayList<Child> children = new ArrayList<>();
    private LinearLayout childLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_profiles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        childLayout = getView().findViewById(R.id.childrenLinearLayout);
        childLayout.setGravity(Gravity.CENTER);

        Button childrenBtn = view.findViewById(R.id.addChildrenButton);
        helper = new DBHelper(getContext());
        addChildren();
        childrenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailedProfileActivity.class);
                intent.putExtra("childName", "None");
                startActivity(intent);
            }
        });

    }

    public void addChildren(){
        children = helper.getAllChildren();
        Log.i("size", String.valueOf(children.size()));
        int i = 0;
        for(Child child: children){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            marginLayoutParams.setMargins(50, 0, 50,10);
            LinearLayout horizontalLayout = new LinearLayout(getContext());
            Guideline guideline =  new Guideline(getContext());
            horizontalLayout.setTag(child.getFirstName() + "Layout");
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setLayoutParams(marginLayoutParams);

            layoutParams.setMargins(200, 0, 0, 30);
            final TextView view = new TextView(getContext());
            view.setGravity(Gravity.CENTER_HORIZONTAL);
            view.setText(child.getFirstName());
            view.setTextColor(Color.BLACK);
            view.setTextSize(15);
            view.setLayoutParams(layoutParams);

            final Button button = new Button(getContext());
            button.setLayoutParams(layoutParams);
            button.setText("Delete");
            button.setHeight(10);
            button.setWidth(20);
            button.setId(child.getChildID());
            button.setTag(child.getFirstName());
            horizontalLayout.addView(view);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteChild(button);
                }
            });
            final Button edit = new Button(getContext());
            edit.setText("Edit");
            edit.setHeight(10);
            edit.setWidth(20);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = (String) view.getText();
                    //Child child = helper.getChild(name);
                    Intent intent = new Intent(getContext(), DetailedProfileActivity.class);
                    intent.putExtra("childName", name);
                    startActivity(intent);
                }
            });

            horizontalLayout.addView(button);
            horizontalLayout.addView(edit);
            childLayout.addView(horizontalLayout);

        }
    }

    public void deleteChild(final Button button){
        //getView().findViewWithTag(button.getId());
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Child")
                .setMessage("Are you sure you want to this child account")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        helper.deleteEntry(button.getId(), "Child");
                        helper.close();
                        childLayout.removeView(getView().findViewWithTag(button.getTag() + "Layout"));
                    }
                })
                .setNegativeButton("no", null).show();
    }
}