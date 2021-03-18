package com.example.downsconnect;

public class Mood {
    private int moodID;
    private long entryTime;
    private String MoodType, time, notes;

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

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }
}
