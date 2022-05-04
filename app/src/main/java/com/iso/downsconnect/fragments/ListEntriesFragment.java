package com.iso.downsconnect.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.iso.downsconnect.DoctorsVisitActivity;
import com.iso.downsconnect.FeedActivity;
import com.iso.downsconnect.JournalActivity;
import com.iso.downsconnect.MessageActivity;
import com.iso.downsconnect.MilestoneActivity;
import com.iso.downsconnect.MoodActivity;
import com.iso.downsconnect.R;
import com.iso.downsconnect.SleepActivity;
import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.helpers.DateHandler;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Journal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Displays all current entries in the db
public class ListEntriesFragment extends Fragment {

    private DBHelper helper;
    private ArrayList<Entry> entries = new ArrayList<>();
    private DateHandler dateHandler = new DateHandler();
    private LinearLayout linearLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_list_entries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get all entries from db and sort them
        helper = new DBHelper(getContext());
        entries = helper.getAllEntries();
        Collections.sort(entries);

        //initialize linearlayout object
        linearLayout = view.findViewById(R.id.entriesLayout);

        //Thread for handling displaying of entries so it doesn't interfere with UI thread
        final ExecutorService service = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());

        //run thread
        service.execute(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //loop through each entry and create objects to add to entry list
                        for(final Entry entry: entries){
                            //defines spacing/parameters for any layout with these params
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            marginLayoutParams.setMargins(20, 0, 50,10);

                            //create horizontal layout
                            LinearLayout mainLayout = new LinearLayout(getContext());
                            mainLayout.setTag(entry.getEntryID() + "Layout");
                            mainLayout.setOrientation(LinearLayout.HORIZONTAL);
                            mainLayout.setLayoutParams(marginLayoutParams);

                            layoutParams.setMargins(200, 0, 0, 30);
                            textParams.setMargins(20, 30, 10, 30);

                            //create secondary horizontal layout
                            LinearLayout horizontalLayout = new LinearLayout(getContext());
                            horizontalLayout.setTag(entry.getEntryID()  + "Layout");
                            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                            horizontalLayout.setLayoutParams(marginLayoutParams);

                            //create horizontal layout
                            LinearLayout horizontalLayout2 = new LinearLayout(getContext());
                            horizontalLayout2.setTag(entry.getEntryID()  + "Layout2");
                            horizontalLayout2.setOrientation(LinearLayout.VERTICAL);

                            //create textview for displaying date
                            TextView date = new TextView(getContext());
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(entry.getEntryTime());
                            Date dat = calendar.getTime();
                            //formate date
                            DateFormat formatter = new SimpleDateFormat("h:mm a");
                            String time = formatter.format(dat);
                            date.setText(dateHandler.getMonth(calendar.get(Calendar.MONTH)) + " " + calendar.get(Calendar.DATE) + ", " + calendar.get(Calendar.YEAR) + " at: " + time);
                            date.setTextSize(15);
                            date.setTextColor(Color.BLACK);
                            date.setWidth(300);
                            date.setLayoutParams(textParams);
                            //add date to secondary horizontal layout
                            horizontalLayout.addView(date);
                            //add date to main horizontal layout
                            mainLayout.addView(horizontalLayout);

                            //create textview for display entry activity information
                            TextView c_activity = new TextView(getContext());
                            c_activity.setText(entry.getEntryText());
                            c_activity.setTextSize(15);
                            c_activity.setWidth(350);
                            c_activity.setTextColor(Color.BLACK);
                            c_activity.setLayoutParams(textParams);
                            horizontalLayout.addView(c_activity);

                            //create button to view entry
                            Button button = new Button(getContext());
                            button.setText("View");
                            button.setWidth(20);
                            button.setHeight(10);
                            button.setLayoutParams(textParams);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //switch statements which to take you to the corresponding activity based on what you click
                                    switch (entry.getEntryType()){
//                        case "Feed":
//                            Intent intent = new Intent(getContext(), FeedActivity.class);
//                            intent.putExtra("feedID", String.valueOf(entry.getForeignID()));
//                            startActivity(intent);
//                            break;
                                        case "Activity":
                                            Intent intent2 = new Intent(getContext(), ActivityActivity.class);
                                            //passes the ID of the entry you're viewing to the next activity
                                            intent2.putExtra("activityID", String.valueOf(entry.getForeignID()));
                                            startActivity(intent2);
                                            break;
                                        case "Sleep":
                                            Intent intent3 = new Intent(getContext(), SleepActivity.class);
                                            //passes the ID of the entry you're viewing to the next activity
                                            intent3.putExtra("sleepID", String.valueOf(entry.getForeignID()));
                                            startActivity(intent3);
                                            break;
                                        case "Mood":
                                            Intent intent4 = new Intent(getContext(), MoodActivity.class);
                                            //passes the ID of the entry you're viewing to the next activity
                                            intent4.putExtra("moodID", String.valueOf(entry.getForeignID()));
                                            startActivity(intent4);
                                            break;
                                        case "Message":
                                            Intent intent6 = new Intent(getContext(), MessageActivity.class);
                                            //passes the ID of the entry you're viewing to the next activity
                                            intent6.putExtra("msgID", String.valueOf(entry.getForeignID()));
                                            startActivity(intent6);
                                            break;
                                        case "Milestone":
                                            Intent intent7 = new Intent(getContext(), MilestoneActivity.class);
                                            //passes the ID of the entry you're viewing to the next activity
                                            intent7.putExtra("devID", String.valueOf(entry.getForeignID()));
                                            startActivity(intent7);
                                            break;
                                        case "Bathroom":
                                            Intent intent8 = new Intent(getContext(), BathroomActivity.class);
                                            //passes the ID of the entry you're viewing to the next activity
                                            intent8.putExtra("bathID", String.valueOf(entry.getForeignID()));
                                            startActivity(intent8);
                                            break;
                                        case "Journal":
                                            Intent intent9 = new Intent(getContext(), JournalActivity.class);
                                            //passes the ID of the entry you're viewing to the next activity
                                            intent9.putExtra("journID", String.valueOf(entry.getForeignID()));
                                            startActivity(intent9);
                                            break;
                                        case "Feed":
                                            Intent intent10 = new Intent(getContext(), FeedActivity.class);
                                            //passes the ID of the entry you're viewing to the next activity
                                            intent10.putExtra("feedID", String.valueOf(entry.getForeignID()));
                                            //check to decide what type of feed it is
                                            if(entry.getEntryText().contains("drank")){
                                                intent10.putExtra("type", "Fluid");
                                            }
                                            else{
                                                intent10.putExtra("type", "Solid");
                                            }
                                            startActivity(intent10);
                                            break;
                                    }
                                }
                            });

                            //add button to secondary
                            horizontalLayout.addView(button);
                            linearLayout.addView(mainLayout);
                        }
                    }
                });
            }
        });

    }
}