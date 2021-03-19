package com.example.downsconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_home, container, false);
    }
}

//public class HomeFragment extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        DBHelper helper = new DBHelper(this);
//
//        Button feed = findViewById(R.id.feedButton);
//        Button activity = findViewById(R.id.activityButton);
//        Button sleep = findViewById(R.id.sleepButton);
//        Button mood = findViewById(R.id.moodButton);
//        Button resources = findViewById(R.id.resourcesButton);
//        Button medical = findViewById(R.id.medicalButton);
//        Button message = findViewById(R.id.messageButton);
//        Button milestone = findViewById(R.id.milestoneButton);
//        Button photo = findViewById(R.id.photoButton);
//        Button diary = findViewById(R.id.diaryButton);
//        Button more = findViewById(R.id.moreButton);
//        Button signOut = findViewById(R.id.signoutButton);
//
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(HomeFragment.this)
//                        .setTitle("Sign Out")
//                        .setMessage("Are you sure you want to sign out")
//                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                    sharedPreferences.edit().putBoolean("signedIn", false).commit();
//                                    Intent intent = new Intent(HomeFragment.this, MainActivity.class);
//                                    startActivity(intent);
//                            }
//                        })
//                        .setNegativeButton("no", null).show();
//            }
//
//        });
//
//
//        feed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, FeedActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        activity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, ActivityActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        sleep.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, SleepActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        mood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, MoodActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        resources.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, ResourcesActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        medical.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, MedicalActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, MessageActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        milestone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, MilestoneActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, PhotoActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        diary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, DiaryActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeFragment.this, MoreActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//}
