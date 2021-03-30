package com.example.downsconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class BathroomActivity extends AppCompatActivity {

//    TabLayout tabLayout;
//    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bathroom);

        final Button back = findViewById(R.id.backButton);
        TextView currentTime = findViewById(R.id.current_time_text);
//        tabLayout = findViewById(R.id.tab_layout);
//        viewPager = findViewById(R.id.view_pager);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if(hour >= 12){
            hour = hour - 12;
            currentTime.setText("Today " + String.valueOf(hour) + ":" + String.valueOf(minute) + "PM");
        }
        else{
            currentTime.setText("Today " + String.valueOf(hour) + ":" + String.valueOf(minute) + "AM");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BathroomActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

//        ArrayList<String> arrayList = new ArrayList<>();
//
//        arrayList.add("Diaper");
//        arrayList.add("Potty");
//        arrayList.add("Constipation");
//
//        prepareViewPager(viewPager,arrayList);
//
//        tabLayout.setupWithViewPager(viewPager);
//    }
//
//    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
//        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
//
//        BathroomFragment fragment = new BathroomFragment();
//
//        for (int i=0; i<arrayList.size(); i++){
//            Bundle bundle = new Bundle();
//            bundle.putString("title",arrayList.get(i));
//            fragment.setArguments(bundle);
//            adapter.addFragment(fragment,arrayList.get(i));
//            fragment = new BathroomFragment();
//        }
//
//        viewPager.setAdapter(adapter);
//    }
//
//    private class MainAdapter extends FragmentPagerAdapter {
//        ArrayList<String> arrayList = new ArrayList<>();
//        List<Fragment> fragmentList = new ArrayList<>();
//
//        public void addFragment (Fragment fragment, String title){
//            arrayList.add(title);
//            fragmentList.add(fragment);
//        }
//
//        public MainAdapter(@NonNull FragmentManager fm) {
//            super(fm);
//        }
//
//        @NonNull
//        @Override
//        public Fragment getItem(int position) {
//            return fragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragmentList.size();
//        }
//
//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return arrayList.get(position);
//        }
    }
}