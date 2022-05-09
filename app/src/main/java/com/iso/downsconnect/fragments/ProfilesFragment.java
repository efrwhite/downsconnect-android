package com.iso.downsconnect.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iso.downsconnect.CaregiverProfileActivity;
import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.DetailedProfileActivity;
import com.iso.downsconnect.ProvidersActivity;
import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.AccountHolder;
import com.iso.downsconnect.objects.Child;
import com.iso.downsconnect.objects.Provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfilesFragment extends Fragment {
    private DBHelper helper;
    private ArrayList<Child> children = new ArrayList<>();
    private ArrayList<AccountHolder> accounts = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private LinearLayout childLayout, caregiverLayout, providerLayout;
    private Intent childIntent;
    private Button careButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_profiles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get linear layouts for each section on the page
        childLayout = getView().findViewById(R.id.childrenLinearLayout);
        childLayout.setGravity(Gravity.LEFT);

        caregiverLayout = getView().findViewById(R.id.careGiversLayout);
        caregiverLayout.setGravity(Gravity.LEFT);

        providerLayout = getView().findViewById(R.id.providersLayout);
        providerLayout.setGravity(Gravity.LEFT);

//        TextView text = new TextView(getContext());
//        text.setText("hi");
//        providerLayout.addView(text);

        Button childrenBtn = view.findViewById(R.id.addChildrenButton);
        careButton = view.findViewById(R.id.addParentsButton);
        helper = new DBHelper(getContext());

        //functions for adding objects to each corresponding layout
        addChildren();
        addCareGivers();
        addProviders();

        //listeners to move to the corresponding profile page
        childrenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailedProfileActivity.class);
                //indicates that user wants to create a new profile
                intent.putExtra("childName", "None");
                startActivity(intent);
            }
        });

        Button providerBtn = view.findViewById(R.id.addProviderButton);
        providerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProvidersActivity.class);
                //indicates that user wants to create a new profile
                intent.putExtra("p_name", -1);
                startActivity(intent);
            }
        });

        careButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CaregiverProfileActivity.class);
                //indicates that user wants to create a new profile
                intent.putExtra("care_name", "None");
                startActivity(intent);
            }
        });

    }
    //adds all existing child profiles to linear layout
    public void addChildren(){
        //Thread for handling the displaying of the entries
        final ExecutorService service = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        //start thread for adding UI elements
        service.execute(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //get all children in db and sort them
                        children = helper.getAllChildren();
                        Collections.sort(children);
                        int i = 0;
                        //for each child, creates a horizontal layout, adds objects to linear layout
                        // and displays info it
                        for(Child child: children){
                            //define layout spacing/parameters for objects
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            //controls the margin for whatever object has these parameters
                            marginLayoutParams.setMargins(50, 0, 50,10);
                            layoutParams.setMargins(75, 0, 0, 30);
                            textParams.setMargins(30, 0, 10, 30);

                            //creates horizontal layout for placing object correctly on screen
                            final LinearLayout horizontalLayout = new LinearLayout(getContext());
                            horizontalLayout.setTag(child.getFirstName() + "CLayout");
                            horizontalLayout.setId(child.getChildID());
                            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                            horizontalLayout.setLayoutParams(marginLayoutParams);

                            //Text view for display child's first name
                            final TextView view = new TextView(getContext());
                            view.setGravity(Gravity.LEFT);
                            view.setText(child.getFirstName());
                            view.setTextColor(Color.BLACK);
                            view.setTextSize(15);
                            view.setWidth(200);
                            view.setLayoutParams(textParams);

                            //Button that will delete child from db
                            final Button button = new Button(getContext());
                            button.setText("Delete");
                            button.setHeight(10);
                            button.setWidth(10);
                            button.setId(child.getChildID());
                            button.setTag(child.getFirstName());

                            //add textview to horizontal layout
                            horizontalLayout.addView(view);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //when delete button is clicked, call delete function
                                    delete(button, "Child");
                                }
                            });

                            //Button that allows you to edit a child
                            final Button edit = new Button(getContext());
                            edit.setText("Edit");
                            edit.setHeight(10);
                            edit.setWidth(10);
                            edit.setLayoutParams(layoutParams);
                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Go to child profile page for editing
                                    String name = (String) view.getText();
                                    Intent intent = new Intent(getContext(), DetailedProfileActivity.class);
                                    //pass name of child to DetailedProfileActivity
                                    intent.putExtra("childName", name);
                                    startActivity(intent);
                                }
                            });
                            // add button to layout
                            horizontalLayout.addView(edit);
                            horizontalLayout.addView(button);
                            //Method of selecting which child you're using the app for
                            horizontalLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new AlertDialog.Builder(getContext())
                                            //message dialog asking if you want to select this child as the child you're entering data for
                                            .setTitle("Setting Child")
                                            .setMessage("Are you sure you want to select " + view.getText().toString())
                                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    //save currently selected child's ID to sharedpreferences for future use
                                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                                    sharedPreferences.edit().putInt("name", horizontalLayout.getId()).commit();

                                                }
                                            })
                                            .setNegativeButton("no", null).show();
                                }
                            });
                            //add horizontal layout to main child layout
                            childLayout.addView(horizontalLayout);

                        }
                    }
                });
            }
        });
    }

    public void delete(final Button button, final String type){
        //dialog asking if you want to commit to deleting the profile
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Profile")
                .setMessage("Are you sure you want to delete this " + type + " account")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //deletes a child profile
                        if(type.equals("Child")) {
                            //get childId from button and remove entry from db
                            helper.deleteEntry(button.getId(), "Child");
                            //Remove deleted entry from UI
                            childLayout.removeView(getView().findViewWithTag(button.getTag() + "CLayout"));
                            Toast.makeText(getContext(), "Deleted a child profile", Toast.LENGTH_SHORT).show();
                        }
                        //deletes a caregiver profile
                        else if (type.equals("Account")){
                            //get accountId from button and remove entry from db
                            helper.deleteEntry(button.getId(), "Account");
                            //Remove deleted entry from UI
                            caregiverLayout.removeView(getView().findViewWithTag(button.getTag() + "GLayout" ));
                            Toast.makeText(getContext(), "Deleted an account", Toast.LENGTH_SHORT).show();
                        }
                        //deletes a provider profile
                        else if (type.equals("Provider")){
                            //get providerId from button and remove entry from db
                            helper.deleteEntry(button.getId(), "Provider");
                            //Remove deleted entry from UI
                            providerLayout.removeView(getView().findViewWithTag(button.getTag() + "PLayout"));
                            Toast.makeText(getContext(), "Deleted a provider profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("no", null).show();
    }

    //adds all existing caregiver profiles to linear layout
    public void addCareGivers(){
        //Thread for handling the displaying of the account entries
        ExecutorService service = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        //start thread for adding UI elements
        service.execute(new Runnable() {
            @Override
            public void run() {
                //get all accounts in db and sort them
                accounts = helper.getAllAccounts();
                Collections.sort(accounts);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //for each account, creates a horizontal layout, adds objects to linear layout
                        // and displays info it
                        for(final AccountHolder account: accounts){
                            //define layout spacing/parameters for objects
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            //controls the margin for whatever object has these parameters
                            layoutParams.setMargins(75, 0, 0, 30);
                            textParams.setMargins(30, 0, 10, 30);
                            marginLayoutParams.setMargins(50, 0, 50,10);

                            //creates horizontal layout for placing object correctly on screen
                            LinearLayout horizontalLayout = new LinearLayout(getContext());
                            horizontalLayout.setTag(account.getFirstName() + "GLayout");
                            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                            horizontalLayout.setLayoutParams(marginLayoutParams);


                            //Text view for display account's first name
                            TextView cg_name = new TextView(getContext());
                            cg_name.setText(account.getFirstName());
                            cg_name.setTextSize(15);
                            cg_name.setWidth(200);
                            cg_name.setGravity(Gravity.LEFT);
                            cg_name.setTextColor(Color.BLACK);
                            cg_name.setLayoutParams(textParams);

                            //add textview to horizontal layout
                            horizontalLayout.addView(cg_name);

                            //Button that will delete account from db
                            final Button delete = new Button(getContext());
                            delete.setText("Delete");
                            delete.setWidth(10);
                            delete.setHeight(10);
                            delete.setId(account.getAccountID());
                            delete.setTag(account.getFirstName());
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //when delete button is clicked, call delete function
                                    delete(delete, "Account");
                                }
                            });
                            //add button to horizontal layout

                            //Button that allows you to edit an account
                            Button edit = new Button(getContext());
                            edit.setText("Edit");
                            edit.setWidth(10);
                            edit.setHeight(10);
                            edit.setLayoutParams(layoutParams);
                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Go to account profile page for editing
                                    Intent intent = new Intent(getContext(), CaregiverProfileActivity.class);
                                    //pass name of child to DetailedProfileActivity
                                    intent.putExtra("care_name", account.getFirstName());
                                    startActivity(intent);
                                }
                            });
                            // add button to layout
                            horizontalLayout.addView(edit);
                            horizontalLayout.addView(delete);
                            // add horizontal layout to main layout
                            caregiverLayout.addView(horizontalLayout);
                        }
                    }
                });
            }
        });

    }

    //adds all existing provider profiles to linear layout
    public void addProviders(){
        //Thread for handling the displaying of the entries
        final ExecutorService service = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        //start thread for adding UI elements
        service.execute(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //get all providers in db and sort them
                        providers = helper.getAllProviders();
                        Collections.sort(providers);
                        //for each provider, creates a horizontal layout, adds objects to linear layout
                        // and displays info it
                        for(final Provider provider: providers){
                            //define layout spacing/parameters for objects
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            //controls the margin for whatever object has these parameters
                            marginLayoutParams.setMargins(50, 0, 50,10);
                            layoutParams.setMargins(75, 0, 0, 30);
                            textParams.setMargins(30, 0, 10, 30);

                            //creates horizontal layout for placing object correctly on screen
                            LinearLayout horizontalLayout = new LinearLayout(getContext());
                            horizontalLayout.setTag(provider.getName() + "PLayout");
                            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                            horizontalLayout.setLayoutParams(marginLayoutParams);
                            horizontalLayout.setId(provider.getProviderID());

                            //Text view for display child's first name
                            TextView name = new TextView(getContext());
                            name.setText(provider.getName());
                            name.setTextSize(15);
                            name.setWidth(200);
                            name.setGravity(Gravity.CENTER_HORIZONTAL);
                            name.setTextColor(Color.BLACK);
                            name.setLayoutParams(textParams);
                            //add textview to horizontal layout
                            horizontalLayout.addView(name);

                            //Button that will delete child from db
                            final Button remove = new Button(getContext());
                            remove.setText("Delete");
                            remove.setWidth(10);
                            remove.setHeight(10);
                            remove.setId(provider.getProviderID());
                            remove.setTag(provider.getName());
                            remove.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //when delete button is clicked, call delete function
                                    delete(remove, "Provider");
                                }
                            });

                            //Button that allows you to edit a provider
                            final Button change = new Button(getContext());
                            change.setText("Edit");
                            change.setHeight(10);
                            change.setWidth(10);
                            change.setLayoutParams(layoutParams);
                            change.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Go to provider profile page for editing
                                    Intent intent = new Intent(getContext(), ProvidersActivity.class);
                                    //pass name of provider to DetailedProfileActivity
                                    intent.putExtra("p_name", provider.getProviderID());
                                    startActivity(intent);
                                }
                            });
                            // add button to layout
                            horizontalLayout.addView(change);
                            horizontalLayout.addView(remove);

                            //add horizontal layout to main provider layout
                            providerLayout.addView(horizontalLayout);
                        }
                    }
                });
            }
        });
    }
}