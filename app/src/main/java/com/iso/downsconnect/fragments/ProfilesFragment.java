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

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.DetailedProfileActivity;
import com.iso.downsconnect.ProvidersActivity;
import com.iso.downsconnect.R;
import com.iso.downsconnect.objects.AccountHolder;
import com.iso.downsconnect.objects.Child;
import com.iso.downsconnect.objects.Provider;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfilesFragment extends Fragment {
    private DBHelper helper;
    private ArrayList<Child> children = new ArrayList<>();
    private ArrayList<AccountHolder> accounts = new ArrayList<>();
    private ArrayList<Provider> providers = new ArrayList<>();
    private LinearLayout childLayout, caregiverLayout, providerLayout;
    private Intent childIntent;

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

        caregiverLayout = getView().findViewById(R.id.careGiversLayout);
        caregiverLayout.setGravity(Gravity.CENTER);

        providerLayout = getView().findViewById(R.id.providersLayout);
        providerLayout.setGravity(Gravity.CENTER);

//        TextView text = new TextView(getContext());
//        text.setText("hi");
//        providerLayout.addView(text);

        Button childrenBtn = view.findViewById(R.id.addChildrenButton);
        helper = new DBHelper(getContext());
        addChildren();
        addCareGivers();
        addProviders();
        childrenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailedProfileActivity.class);
                intent.putExtra("childName", "None");
                startActivity(intent);
            }
        });

        Button providerBtn = view.findViewById(R.id.addProviderButton);
        providerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProvidersActivity.class);
                intent.putExtra("p_name", -1);
                startActivity(intent);
            }
        });

    }

    public void addChildren(){
        final ExecutorService service = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        service.execute(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        children = helper.getAllChildren();
                        int i = 0;
                        for(Child child: children){
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            marginLayoutParams.setMargins(50, 0, 50,10);
                            final LinearLayout horizontalLayout = new LinearLayout(getContext());
                            horizontalLayout.setTag(child.getFirstName() + "Layout");
                            horizontalLayout.setId(child.getChildID());
                            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                            horizontalLayout.setLayoutParams(marginLayoutParams);

                            layoutParams.setMargins(200, 0, 0, 30);
                            textParams.setMargins(50, 0, 10, 30);

                            final TextView view = new TextView(getContext());
                            view.setGravity(Gravity.CENTER_HORIZONTAL);
                            view.setText(child.getFirstName());
                            view.setTextColor(Color.BLACK);
                            view.setTextSize(15);
                            view.setWidth(250);
                            view.setLayoutParams(textParams);

                            final Button button = new Button(getContext());
                            button.setLayoutParams(layoutParams);
                            button.setText("Delete");
                            button.setHeight(10);
                            button.setWidth(10);
                            button.setId(child.getChildID());
                            button.setTag(child.getFirstName());

                            horizontalLayout.addView(view);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    delete(button, "Child");
                                }
                            });
                            final Button edit = new Button(getContext());
                            edit.setText("Edit");
                            edit.setHeight(10);
                            edit.setWidth(10);
                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String name = (String) view.getText();
                                    Intent intent = new Intent(getContext(), DetailedProfileActivity.class);
                                    intent.putExtra("childName", name);
                                    startActivity(intent);
                                }
                            });

                            horizontalLayout.addView(button);
                            horizontalLayout.addView(edit);
                            horizontalLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new AlertDialog.Builder(getContext())
                                            .setTitle("Setting Child")
                                            .setMessage("Are you sure you want to select " + view.getText().toString())
                                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                                    sharedPreferences.edit().putInt("name", horizontalLayout.getId()).commit();

                                                }
                                            })
                                            .setNegativeButton("no", null).show();
                                }
                            });
                            childLayout.addView(horizontalLayout);

                        }
                    }
                });
            }
        });
    }

    public void delete(final Button button, final String type){
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Profile")
                .setMessage("Are you sure you want to delete this " + type + " account")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(type.equals("Child")) {
                            helper.deleteEntry(button.getId(), "Child");
                            childLayout.removeView(getView().findViewWithTag(button.getTag() + "Layout"));
                        }
//                        else if (type.equals("Account")){
//                            helper.deleteEntry(button.getId(), "Account");
//                            caregiverLayout.removeView(getView().findViewById(button.getId()));
//                        }
                        else if (type.equals("Provider")){
                            helper.deleteEntry(button.getId(), "Provider");
                            providerLayout.removeView(getView().findViewById(button.getId()));
                        }
                    }
                })
                .setNegativeButton("no", null).show();
    }

    public void addCareGivers(){
        ExecutorService service = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        service.execute(new Runnable() {
            @Override
            public void run() {
                accounts = helper.getAllAccounts();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for(AccountHolder account: accounts){
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            marginLayoutParams.setMargins(50, 0, 50,10);
                            LinearLayout horizontalLayout = new LinearLayout(getContext());
                            horizontalLayout.setTag(account.getFirstName() + "Layout");
                            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                            horizontalLayout.setLayoutParams(marginLayoutParams);

                            layoutParams.setMargins(200, 0, 0, 30);
                            textParams.setMargins(50, 0, 10, 30);

                            TextView cg_name = new TextView(getContext());
                            cg_name.setText(account.getFirstName());
                            cg_name.setTextSize(15);
                            cg_name.setWidth(250);
                            cg_name.setGravity(Gravity.CENTER_HORIZONTAL);
                            cg_name.setTextColor(Color.BLACK);
                            cg_name.setLayoutParams(textParams);
                            horizontalLayout.addView(cg_name);

                            final Button delete = new Button(getContext());
                            delete.setText("Delete");
                            delete.setWidth(10);
                            delete.setHeight(10);
                            delete.setId(account.getAccountID());
                            delete.setLayoutParams(layoutParams);
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    delete(delete, "Account");
                                }
                            });
                            horizontalLayout.addView(delete);

                            Button edit = new Button(getContext());
                            edit.setText("Edit");
                            edit.setWidth(10);
                            edit.setHeight(10);
                            horizontalLayout.addView(edit);

                            caregiverLayout.addView(horizontalLayout);
                        }
                    }
                });
            }
        });

    }

    public void addProviders(){
        final ExecutorService service = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        service.execute(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        providers = helper.getAllProviders();
                        for(final Provider provider: providers){
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            marginLayoutParams.setMargins(50, 0, 50,10);

                            LinearLayout horizontalLayout = new LinearLayout(getContext());
                            horizontalLayout.setTag(provider.getName() + "Layout");
                            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                            horizontalLayout.setLayoutParams(marginLayoutParams);
                            horizontalLayout.setId(provider.getProviderID());

                            layoutParams.setMargins(200, 0, 0, 30);
                            textParams.setMargins(50, 0, 10, 30);

                            TextView name = new TextView(getContext());
                            name.setText(provider.getName());
                            name.setTextSize(15);
                            name.setWidth(250);
                            name.setGravity(Gravity.CENTER_HORIZONTAL);
                            name.setTextColor(Color.BLACK);
                            name.setLayoutParams(textParams);
                            horizontalLayout.addView(name);

                            final Button remove = new Button(getContext());
                            remove.setText("Delete");
                            remove.setWidth(10);
                            remove.setHeight(10);
                            remove.setId(provider.getProviderID());
                            remove.setLayoutParams(layoutParams);
                            remove.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    delete(remove, "Provider");
                                }
                            });
                            horizontalLayout.addView(remove);

                            final Button change = new Button(getContext());
                            change.setText("Edit");
                            change.setHeight(10);
                            change.setWidth(10);
                            change.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getContext(), ProvidersActivity.class);
                                    intent.putExtra("p_name", provider.getProviderID());
                                    startActivity(intent);
                                }
                            });
                            horizontalLayout.addView(change);

                            providerLayout.addView(horizontalLayout);
                        }
                    }
                });
            }
        });
    }
}