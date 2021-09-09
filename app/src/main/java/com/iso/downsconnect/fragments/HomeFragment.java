package com.iso.downsconnect.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iso.downsconnect.ActivityActivity;
import com.iso.downsconnect.BathroomActivity;
import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.FeedActivity;
import com.iso.downsconnect.JournalActivity;
import com.iso.downsconnect.MainActivity;
import com.iso.downsconnect.MedicalActivity;
import com.iso.downsconnect.MessageActivity;
import com.iso.downsconnect.MilestoneActivity;
import com.iso.downsconnect.MoodActivity;
import com.iso.downsconnect.MoreActivity;
import com.iso.downsconnect.R;
import com.iso.downsconnect.ResourcesActivity;
import com.iso.downsconnect.SleepActivity;
import com.iso.downsconnect.helpers.DateHandler;
import com.iso.downsconnect.objects.Entry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private LinearLayout entryLayout;
    private DBHelper helper;
    private ArrayList<Entry> entries;
    private DateHandler month = new DateHandler();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.activity_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helper = new DBHelper(getContext());
        entries = new ArrayList<>();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final int childID = sharedPreferences.getInt("name", 1);


        Button feed = view.findViewById(R.id.feedButton);
        Button activity = view.findViewById(R.id.activityButton);
        Button sleep = view.findViewById(R.id.sleepButton);
        Button mood = view.findViewById(R.id.moodButton);
        Button resources = view.findViewById(R.id.resourcesButton);
        Button medical = view.findViewById(R.id.medicalButton);
        Button message = view.findViewById(R.id.messageButton);
        Button milestone = view.findViewById(R.id.milestoneButton);
        Button bathroom = view.findViewById(R.id.bathroomButton);
        Button diary = view.findViewById(R.id.diaryButton);
        Button more = view.findViewById(R.id.moreButton);
        Button signOut = view.findViewById(R.id.signoutButton);
        entryLayout = view.findViewById(R.id.entryLayout);

        addEntries();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Sign Out")
                        .setMessage("Are you sure you want to sign out")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                    sharedPreferences.edit().putBoolean("signedIn", false).commit();
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                            }
                        })
                        .setNegativeButton("no", null).show();
            }

        });


        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FeedActivity.class);
                intent.putExtra("feedID", "-1");
                startActivity(intent);
            }
        });

        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityActivity.class);
                intent.putExtra("activityID", "-1");
                startActivity(intent);
            }
        });

        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SleepActivity.class);
                intent.putExtra("sleepID", "-1");
                startActivity(intent);
            }
        });

        mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MoodActivity.class);
                intent.putExtra("moodID", "-1");
                startActivity(intent);
            }
        });

        resources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ResourcesActivity.class);
                startActivity(intent);
            }
        });

        medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MedicalActivity.class);
                startActivity(intent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("msgID", "-1");
                startActivity(intent);
            }
        });

        milestone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MilestoneActivity.class);
                startActivity(intent);
            }
        });

        bathroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BathroomActivity.class);
                startActivity(intent);
            }
        });

        diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), JournalActivity.class);
                intent.putExtra("journID", "-1");
                startActivity(intent);
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MoreActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addEntries(){
        ExecutorService service = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        service.execute(new Runnable() {
            @Override
            public void run() {
                entries = helper.getAllEntries();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for(Entry entry: entries){
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            marginLayoutParams.setMargins(10, 0, 0,30);
                            LinearLayout horizontalLayout = new LinearLayout(getContext());
                            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                            horizontalLayout.setLayoutParams(marginLayoutParams);
                            layoutParams.setMargins(15, 0, 10, 30);


                            TextView entryText = new TextView(getContext());
                            entryText.setText(entry.getEntryText() + " ");
                            entryText.setTextSize(15);
                            entryText.setWidth(600);
                            entryText.setLayoutParams(layoutParams);

                            TextView entryDate = new TextView(getContext());
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(entry.getEntryTime());
                            Date date = calendar.getTime();
                            DateFormat formatter = new SimpleDateFormat("h:mm a");
                            String time = formatter.format(date);

                            entryDate.setText(month.getMonth(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.DATE) + ", " + calendar.get(Calendar.YEAR) + " at: " + time);
                            entryDate.setTextSize(15);
                            horizontalLayout.addView(entryText);
                            horizontalLayout.addView(entryDate);
                            entryLayout.addView(horizontalLayout);
                        }
                    }
                });
            }
        });

    }
}