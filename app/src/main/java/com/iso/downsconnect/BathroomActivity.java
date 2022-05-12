package com.iso.downsconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.iso.downsconnect.fragments.ConstipationFragment;
import com.iso.downsconnect.fragments.DiaperFragment;
import com.iso.downsconnect.fragments.PottyFragment;
import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.helpers.FragmentData;
import com.iso.downsconnect.objects.Bathroom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//activity to control/display the different tabs for the bathroom page
public class BathroomActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    ViewPager viewPager;
    private Bathroom bathroom;
    private DBHelper helper;
    private PottyFragment pottyFragment = new PottyFragment();
    private DiaperFragment diaperFragment = new DiaperFragment();
    private ConstipationFragment constipationFragment = new ConstipationFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bathroom);

        //declare and initialize variables
        final Button back = findViewById(R.id.backButton);
        TextView currentTime = findViewById(R.id.current_time_text);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        helper = new DBHelper(this);


        //Get bathroom id to figure out whether this is a new entry of not
        Intent intent = getIntent();
        String msgID = intent.getStringExtra("bathID");
        int id = Integer.parseInt(msgID);

        //get current child ID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);

        //Calculate and display the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String realMins;
        if(minute <= 10){
            realMins = "0" + minute;
        }
        else{
            realMins = String.valueOf(minute);
        }
        if(hour > 12){
            hour = hour - 12;
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + "PM");
        }
        else if(hour == 12){
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + "PM");
        }
        else{
            currentTime.setText("Today " + String.valueOf(hour) + ":" + realMins + "AM");
        }

        //button for navigating back to home screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BathroomActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        //arraylist containing the names of the tabs for the bathroom activity
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Diaper");
        arrayList.add("Potty");
        arrayList.add("Constipation");

        //if viewing an already existing entry, send fragment info to each tab
        if(id != -1){
            bathroom = helper.getBathroom(id);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bathroom", bathroom);
            pottyFragment.setArguments(bundle);
            constipationFragment.setArguments(bundle);
            diaperFragment.setArguments(bundle);
        }

        //set up the tablayout on the bathroom activity
        prepareViewPager(viewPager,arrayList);
        tabLayout.setupWithViewPager(viewPager);

        //if viewing an existing bathroom entry, select the correct tab based on which bathroom type it is
        if(id != -1){
            if(bathroom.getBathroomType().equals("Potty")){
                TabLayout.Tab tab = tabLayout.getTabAt(1);
                tab.select();
            }
            else if(bathroom.getBathroomType().equals("Diaper")){
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.select();
            }
            else if(bathroom.getBathroomType().equals("Constipation")){
                TabLayout.Tab tab = tabLayout.getTabAt(2);
                tab.select();
            }
        }


    }

    //method for adding a fragment to its corresponding tab
    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
        adapter.addFragment(diaperFragment, "Diaper");
        adapter.addFragment(pottyFragment, "Potty");
        adapter.addFragment(constipationFragment, "Constipation");
        viewPager.setAdapter(adapter);
    }

    //private class used to manage the fragments being used in the tablayout
    private class MainAdapter extends FragmentPagerAdapter {
        ArrayList<String> arrayList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();


        public void addFragment (Fragment fragment, String title){
            arrayList.add(title);
            fragmentList.add(fragment);
        }

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return arrayList.get(position);
        }
    }
}