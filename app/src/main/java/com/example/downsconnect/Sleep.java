package com.example.downsconnect;

public class Sleep {
    private int sleepID, childID;
    private String startTime, endTime, sleepType, notes;

    public Sleep(){

    }

    public Sleep(int childID, String startTime, String endTime, String notes){
        this.childID = childID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
    }

    public int getSleepID() {
        return sleepID;
    }

    public void setSleepID(int sleepID) {
        this.sleepID = sleepID;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSleepType() {
        return sleepType;
    }

    public void setSleepType(String sleepType) {
        this.sleepType = sleepType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
