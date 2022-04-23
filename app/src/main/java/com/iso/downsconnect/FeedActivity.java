package com.iso.downsconnect;

import android.content.Intent;
import android.os.Bundle;
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
import com.iso.downsconnect.fragments.FluidFragment;
import com.iso.downsconnect.fragments.SolidFragment;
import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.objects.Feed;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    private DBHelper helper;
    private Feed feed = new Feed();
    private SolidFragment solidFragment = new SolidFragment();
    private FluidFragment fluidFragment = new FluidFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        final Button back = findViewById(R.id.backButton);
        TextView currentTime = findViewById(R.id.current_time_text);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        helper = new DBHelper(this);

        Intent intent = getIntent();
        String msgID = intent.getStringExtra("feedID");
        int id = Integer.parseInt(msgID);

        String type = intent.getStringExtra("type");

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Liquid");
        arrayList.add("Solid");

        if(id != -1){
            feed = helper.getFeed(id);
            Bundle bun = new Bundle();
            bun.putSerializable("feed", feed);
            solidFragment.setArguments(bun);
            fluidFragment.setArguments(bun);
        }

        prepareViewPager(viewPager,arrayList);

        tabLayout.setupWithViewPager(viewPager);

        if(id != -1) {
            if (type.equals("Liquid")) {
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.select();
            }
            else if (type.equals("Solid")){
                TabLayout.Tab tab = tabLayout.getTabAt(1);
                tab.select();
            }
        }
    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        FeedActivity.MainAdapter adapter = new FeedActivity.MainAdapter(getSupportFragmentManager());

        //FeedFragment fragment = new FeedFragment();

        adapter.addFragment(fluidFragment, "Fluid");
        adapter.addFragment(solidFragment, "Solid");

//        for (int i=0; i<arrayList.size(); i++){
//            Bundle bundle = new Bundle();
//            bundle.putString("title",arrayList.get(i));
//            fragment.setArguments(bundle);
//            adapter.addFragment(fragment,arrayList.get(i));
//            fragment = new FeedFragment();
//        }

        viewPager.setAdapter(adapter);
    }

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
