package com.iso.downsconnect.objects;

public class Mood {
    private int moodID, childID;
    private String MoodType, time, notes, units;

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
