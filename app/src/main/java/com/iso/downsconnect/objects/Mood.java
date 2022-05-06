package com.iso.downsconnect.objects;

//object for holding information for a mood entry
public class Mood {
    //instance variables
    private int moodID, childID;
    private String MoodType, time, notes, units;

    //getters and setters for each instance variable
    public String getMoodType() {
        return MoodType;
    }

    public void setMoodType(String moodType) {
        MoodType = moodType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getMoodID() {
        return moodID;
    }

    public void setMoodID(int moodID) {
        this.moodID = moodID;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }
}
