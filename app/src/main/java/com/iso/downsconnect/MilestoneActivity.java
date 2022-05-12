package com.iso.downsconnect;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iso.downsconnect.helpers.DBHelper;
import com.iso.downsconnect.helpers.DateHandler;
import com.iso.downsconnect.objects.Entry;
import com.iso.downsconnect.objects.Milestone;

import java.util.Calendar;

public class MilestoneActivity extends AppCompatActivity {
    private EditText roll, walk, stand, sit, val, crawl, nh_walk, jump,
            holds, hands_mouth, passes, pincher, drinks, scribbles, feed_spoon,
            points, emotion, affection, interest, coos, babbles, speaks,
            two_word, sentence, startles, turns;
    private EditText standing, sitting, rolling, walking;
    private DateHandler month_;
    private DBHelper helper;
    private Milestone milestone;
    private Button save;
    private TextView standAge, sitAge, walkAge, rollAge;
    private long childBirthday;
    private Entry entry = new Entry();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestone);

        //get current child ID
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int childID = sharedPreferences.getInt("name", 1);


        //intialize variables and objects
        helper = new DBHelper(this);
        final Button back = findViewById(R.id.backButton);
        turns = findViewById(R.id.editTextDate31);
        startles = findViewById(R.id.editTextDate30);
        sentence = findViewById(R.id.editTextDate29);
        two_word = findViewById(R.id.editTextDate26);
        speaks = findViewById(R.id.editTextDate25);
        babbles = findViewById(R.id.editTextDate24);
        coos = findViewById(R.id.editTextDate23);
        interest = findViewById(R.id.editTextDate19);
        affection = findViewById(R.id.editTextDate18);
        emotion = findViewById(R.id.editTextDate17);
        points = findViewById(R.id.editTextDate16);
        feed_spoon = findViewById(R.id.editTextDate15);
        scribbles = findViewById(R.id.editTextDate14);
        drinks = findViewById(R.id.editTextDate13);
        pincher = findViewById(R.id.editTextDate12);
        passes = findViewById(R.id.editTextDate11);
        hands_mouth = findViewById(R.id.editTextDate10);
        holds = findViewById(R.id.editTextDate9);
        jump = findViewById(R.id.editTextDate8);
        nh_walk = findViewById(R.id.editTextDate7);
        save = findViewById(R.id.saveButton);
        roll = findViewById(R.id.editTextDate2);
        sit = findViewById(R.id.editTextDate3);
        crawl = findViewById(R.id.editTextDate4);
        stand = findViewById(R.id.editTextDate5);
        walk = findViewById(R.id.editTextDate6);

        month_ = new DateHandler();
        milestone = new Milestone();

        //set initial entry object info
        entry.setChildID(childID);
        entry.setEntryText("Updated milestone information for " + helper.getChildName(childID));
        entry.setEntryType("Milestone");

        //set the id of the child you are saving the info for
        milestone.setChildId(childID);

        //check if the child currently has milestone info already
        final Milestone stone = helper.getMilestone(childID);
        //childBirthday = helper.getChildBirthday(childID);

        //if milestone object retrieved from db isn't null, display the fields that have values in the object
        if(stone != null){
            //checks if there is a values and displays it if there is
            if(!stone.getTurns().equals("None")){
                turns.setText(stone.getTurns());
            }
            if(!stone.getStartles().equals("None")){
                startles.setText(stone.getStartles());
            }
            if(!stone.getSentence().equals("None")){
                sentence.setText(stone.getSentence());
            }
            if(!stone.getTwo_word().equals("None")){
                two_word.setText(stone.getTwo_word());
            }
            if(!stone.getSpeaks().equals("None")){
                speaks.setText(stone.getSpeaks());
            }
            if(!stone.getCoos().equals("None")){
                coos.setText(stone.getCoos());
            }
            if(!stone.getInterest().equals("None")){
                interest.setText(stone.getInterest());
            }
            if(!stone.getAffection().equals("None")){
                affection.setText(stone.getAffection());
            }
            if(!stone.getEmotion().equals("None")){
                emotion.setText(stone.getEmotion());
            }
            if(!stone.getPoints().equals("None")){
                points.setText(stone.getPoints());
            }
            if(!stone.getFeed_spoon().equals("None")){
                feed_spoon.setText(stone.getFeed_spoon());
            }
            if(!stone.getScribbles().equals("None")){
                scribbles.setText(stone.getScribbles());
            }
            if(!stone.getDrinks().equals("None")){
                drinks.setText(stone.getDrinks());
            }
            if(!stone.getPincher().equals("None")){
                pincher.setText(stone.getPincher());
            }
            if(!stone.getPasses().equals("None")){
                passes.setText(stone.getPasses());
            }
            if(!stone.getHands_mouth().equals("None")){
                hands_mouth.setText(stone.getHands_mouth());
            }
            if(!stone.getHolds().equals("None")){
                holds.setText(stone.getHolds());
            }
            if(!stone.getJump().equals("None")){
                jump.setText(stone.getJump());
            }
            if(!stone.getNh_walk().equals("None")){
                nh_walk.setText(stone.getNh_walk());
            }
            if(!stone.getRoll().equals("None")){
                roll.setText(stone.getRoll());
            }
            if(!stone.getSit().equals("None")){
                sit.setText(stone.getSit());
            }
            if(!stone.getCrawl().equals("None")){
                crawl.setText(stone.getCrawl());
            }
            if(!stone.getStand().equals("None")){
                stand.setText(stone.getStand());
            }
            if(!stone.getWalk().equals("None")){
                walk.setText(stone.getWalk());
            }

        }

        //save the info to the db
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks if there is anything entered for the field, if there is, save it to the object
                if(!turns.getText().toString().equals("")){
                    milestone.setTurns(turns.getText().toString());
                }
                else{
                    //if nothing was entered, save "none" for it's values
                    milestone.setTurns("None");
                }

                if(!startles.getText().toString().equals("")){
                    milestone.setStartles(startles.getText().toString());
                }
                else{
                    milestone.setStartles("None");
                }

                if(!sentence.getText().toString().equals("")){
                    milestone.setSentence(sentence.getText().toString());
                }
                else{
                    milestone.setSentence("None");
                }

                if(!two_word.getText().toString().equals("")){
                    milestone.setTwo_word(two_word.getText().toString());
                }
                else{
                    milestone.setTwo_word("None");
                }

                if(!speaks.getText().toString().equals("")){
                    milestone.setSpeaks(speaks.getText().toString());
                }
                else{
                    milestone.setSpeaks("None");
                }

                if(!babbles.getText().toString().equals("")){
                    milestone.setBabbles(babbles.getText().toString());
                }
                else{
                    milestone.setBabbles("None");
                }

                if(!coos.getText().toString().equals("")){
                    milestone.setCoos(coos.getText().toString());
                }
                else{
                    milestone.setCoos("None");
                }

                if(!interest.getText().toString().equals("")){
                    milestone.setInterest(interest.getText().toString());
                }
                else{
                    milestone.setInterest("None");
                }

                if(!affection.getText().toString().equals("")){
                    milestone.setAffection(affection.getText().toString());
                }
                else{
                    milestone.setAffection("None");
                }

                if(!emotion.getText().toString().equals("")){
                    milestone.setEmotion(emotion.getText().toString());
                }
                else{
                    milestone.setEmotion("None");
                }

                if(!points.getText().toString().equals("")){
                    milestone.setPoints(points.getText().toString());
                }
                else{
                    milestone.setPoints("None");
                }

                if(!feed_spoon.getText().toString().equals("")){
                    milestone.setFeed_spoon(feed_spoon.getText().toString());
                }
                else{
                    milestone.setFeed_spoon("None");
                }

                if(!scribbles.getText().toString().equals("")){
                    milestone.setScribbles(scribbles.getText().toString());
                }
                else{
                    milestone.setScribbles("None");
                }

                if(!drinks.getText().toString().equals("")){
                    milestone.setDrinks(drinks.getText().toString());
                }
                else{
                    milestone.setDrinks("None");
                }

                if(!pincher.getText().toString().equals("")){
                    milestone.setPincher(pincher.getText().toString());
                }
                else{
                    milestone.setPincher("None");
                }

                if(!passes.getText().toString().equals("")){
                    milestone.setPasses(passes.getText().toString());
                }
                else{
                    milestone.setPasses("None");
                }

                if(!hands_mouth.getText().toString().equals("")){
                    milestone.setHands_mouth(hands_mouth.getText().toString());
                }
                else{
                    milestone.setHands_mouth("None");
                }

                if(!holds.getText().toString().equals("")){
                    milestone.setHolds(holds.getText().toString());
                }
                else{
                    milestone.setHolds("None");
                }

                if(!jump.getText().toString().equals("")){
                    milestone.setJump(jump.getText().toString());
                }
                else{
                    milestone.setJump("None");
                }

                if(!nh_walk.getText().toString().equals("")){
                    milestone.setNh_walk(nh_walk.getText().toString());
                }
                else{
                    milestone.setNh_walk("None");
                }

                if(!roll.getText().toString().equals("")){
                    milestone.setRoll(roll.getText().toString());
                }
                else{
                    milestone.setRoll("None");
                }

                if(!sit.getText().toString().equals("")){
                    milestone.setSit(sit.getText().toString());
                }
                else{
                    milestone.setSit("None");
                }

                if(!crawl.getText().toString().equals("")){
                    milestone.setCrawl(crawl.getText().toString());
                }
                else{
                    milestone.setCrawl("None");
                }

                if(!stand.getText().toString().equals("")){
                    milestone.setStand(stand.getText().toString());
                }
                else{
                    milestone.setStand("None");
                }

                if(!walk.getText().toString().equals("")){
                    milestone.setWalk(walk.getText().toString());
                }
                else{
                    milestone.setWalk("None");
                }

                //if this is an existing entry, store the updated info to the db
                if(stone != null){
                    helper.updateMilestone(milestone);
                }
                else{
                    //if this is a new milestone entry, add it to the db
                    long id = helper.addMilestone(milestone);
                    //set the rest of the entry info and store it
                    entry.setForeignID(id);
                    //                    Log.i("asdf", String.valueOf(id));
                }
                entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
                //adds new entry to db
                helper.addEntry(entry);
                //display success message and navigate back to home page
                Toast.makeText(getApplicationContext(), "Milestone updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MilestoneActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

//old code from before all the other fields were added :(
//        if(stone != null){
//            milestone.setStandingDate(stone.getStandingDate());
//            milestone.setSittingDate(stone.getSittingDate());
//            milestone.setRollingDate(stone.getRollingDate());
//            milestone.setWalkingDate(stone.getWalkingDate());
//            if(stone.getStandingDate() != 0) {
//                Calendar standTime = Calendar.getInstance();
//                standTime.setTimeInMillis(stone.getStandingDate());
//                standing.setText(month_.getMonth(standTime.get(Calendar.MONTH)) + " " + standTime.get(Calendar.DATE) + ", " + standTime.get(Calendar.YEAR));
//                long difference = standTime.getTimeInMillis() - childBirthday;
//                long days = difference / (24 * 60 * 60 * 1000);
//                int months = (int) days / 30;
//                int years = (int) days / 365;
//                if(days <= 547){
//                    standAge.setText(months + " months");
//                }
//                else{
//                    standAge.setText(years + "yrs");
//                }
//            }
//            if(stone.getSittingDate() != 0){
//                Calendar sitTime = Calendar.getInstance();
//                sitTime.setTimeInMillis(stone.getSittingDate());
//                sitting.setText(month_.getMonth(sitTime.get(Calendar.MONTH)) + " " + sitTime.get(Calendar.DATE) + ", " + sitTime.get(Calendar.YEAR));
//                long difference = sitTime.getTimeInMillis() - childBirthday;
//                long days = difference / (24 * 60 * 60 * 1000);
//                int months = (int) days / 30;
//                int years = (int) days / 365;
//                if(days <= 547){
//                    sitAge.setText(months + " months");
//                }
//                else{
//                    sitAge.setText(years + "yrs");
//                }
//            }
//            if(stone.getRollingDate() != 0){
//                Calendar rollTime = Calendar.getInstance();
//                rollTime.setTimeInMillis(stone.getRollingDate());
//                rolling.setText(month_.getMonth(rollTime.get(Calendar.MONTH)) + " " + rollTime.get(Calendar.DATE) + ", " + rollTime.get(Calendar.YEAR));
//                long difference = rollTime.getTimeInMillis() - childBirthday;
//                long days = difference / (24 * 60 * 60 * 1000);
//                int months = (int) days / 30;
//                int years = (int) days / 365;
//                if(days <= 547){
//                    rollAge.setText(months + " months");
//                }
//                else{
//                    rollAge.setText(years + "yrs");
//                }
//            }
//            if(stone.getWalkingDate() != 0){
//                Calendar walkTime = Calendar.getInstance();
//                walkTime.setTimeInMillis(stone.getWalkingDate());
//                walking.setText(month_.getMonth(walkTime.get(Calendar.MONTH)) + " " + walkTime.get(Calendar.DATE) + ", " + walkTime.get(Calendar.YEAR));
//                long difference = childBirthday - walkTime.getTimeInMillis();
//                long days = difference / (24 * 60 * 60 * 1000);
//                int months = (int) days / 30;
//                int years = (int) days / 365;
//                if(days <= 547){
//                    walkAge.setText(months + " months");
//                }
//                else{
//                    walkAge.setText(years + "yrs");
//                }
//            }
//
//        }


//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(rolling.getText().toString().equals("") && walking.getText().toString().equals("") && sitting.getText().toString().equals("") && standing.getText().toString().equals("")){
//                    AlertDialog a = new AlertDialog.Builder(save.getContext()).create();
//                    a.setTitle("Missing Information");
//                    a.setMessage("Please make sure you've filled out the necessary information");
//                    a.show();
//                }
//                else{
//                    if(stone != null){
//                        val = helper.updateMilestone(milestone);
//                        entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
//                    }
//                    else{
//                        helper.addMilestone(milestone);
//                        entry.setEntryTime(Calendar.getInstance().getTimeInMillis());
//                    }
//
//                    Intent intent = new Intent(MilestoneActivity.this, ActivityContainer.class);
//                    startActivity(intent);
//                }
//            }
//        });


        //button for navigating back to home screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MilestoneActivity.this, ActivityContainer.class);
                startActivity(intent);
            }
        });

//        standing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                stand = true;
//                roll = false;
//                walk = false;
//                sit = false;
//                showDatePickerDialog();
//            }
//        });
//
//        sitting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sit = true;
//                roll = false;
//                walk = false;
//                stand = false;
//                showDatePickerDialog();
//            }
//        });
//
//        rolling.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                roll = true;
//                walk = false;
//                stand = false;
//                sit = false;
//                showDatePickerDialog();
//            }
//        });
//
//        walking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                walk = true;
//                roll = false;
//                stand = false;
//                sit = false;
//                showDatePickerDialog();
//            }
//        });
    }

//    private void showDatePickerDialog() {
//        @SuppressLint("ResourceType") DatePickerDialog datePickerDialog = new DatePickerDialog(this, 2,  this,
//                Calendar.getInstance().get(Calendar.YEAR),
//                Calendar.getInstance().get(Calendar.MONTH),
//                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//        datePickerDialog.show();
//    }

//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        Calendar calendar = Calendar.getInstance();
//        if(stand){
//            calendar.set(year, month, dayOfMonth);
//            milestone.setStandingDate(calendar.getTimeInMillis());
//            standing.setText(month_.getMonth(month) + " " + dayOfMonth + ", " + year);
//            long difference = calendar.getTimeInMillis() - childBirthday;
//            long days = difference / (24 * 60 * 60 * 1000);
//            int months = (int) days / 30;
//            int years = (int) days / 365;
//            if(days <= 547){
//                standAge.setText(months + " months");
//            }
//            else{
//                standAge.setText(years + "yrs");
//            }
//        }
//        else if(sit){
//            calendar.set(year, month, dayOfMonth);
//            milestone.setSittingDate(calendar.getTimeInMillis());
//            sitting.setText(month_.getMonth(month) + " " +dayOfMonth + ", " + year);
//            long difference = calendar.getTimeInMillis() - childBirthday;
//            long days = difference / (24 * 60 * 60 * 1000);
//            int months = (int) days / 30;
//            int years = (int) days / 365;
//            if(days <= 547){
//                sitAge.setText(months + " months");
//            }
//            else{
//                sitAge.setText(years + "yrs");
//            }
//        }
//        else if(roll){
//            calendar.set(year, month, dayOfMonth);
//            milestone.setRollingDate(calendar.getTimeInMillis());
//            rolling.setText(month_.getMonth(month) + " " +dayOfMonth + ", " + year);
//            long difference = calendar.getTimeInMillis() - childBirthday;
//            long days = difference / (24 * 60 * 60 * 1000);
//            int months = (int) days / 30;
//            int years = (int) days / 365;
//            if(days <= 547){
//                rollAge.setText(months + " months");
//            }
//            else{
//                rollAge.setText(years + "yrs");
//            }
//        }
//        else{
//            calendar.set(year, month, dayOfMonth);
//            milestone.setWalkingDate(calendar.getTimeInMillis());
//            walking.setText(month_.getMonth(month) + " " +dayOfMonth + ", " + year);
//            long difference = calendar.getTimeInMillis() - childBirthday;
//            long days = difference / (24 * 60 * 60 * 1000);
//            int months = (int) days / 30;
//            int years = (int) days / 365;
//            if(days <= 547){
//                walkAge.setText(months + " months");
//            }
//            else{
//                walkAge.setText(years + "yrs");
//            }
//        }
//    }
}
