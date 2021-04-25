package com.example.downsconnect.objects;

public class Sleep implements Comparable {
    private int sleepID, childID, duration;
    private long sleepTime;
    private String snoring, notes, sleepTreatments, study;

    public Sleep(){

    }

    public Sleep(int childID, long startTime, long endTime, String notes){
        this.childID = childID;
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

    public String getSnoring() {
        return snoring;
    }

    public void setSnoring(String snoring) {
        this.snoring = snoring;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int compareTo(Object o) {
        return this.compareTo(o);
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSleepTreatments() {
        return sleepTreatments;
    }

    public void setSleepTreatments(String sleepTreatments) {
        this.sleepTreatments = sleepTreatments;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }
}
