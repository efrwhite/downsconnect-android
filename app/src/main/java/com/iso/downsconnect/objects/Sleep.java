package com.iso.downsconnect.objects;

//object for holding information for a sleep entry
//implements Comparable so sleep objects can be sorted
public class Sleep implements Comparable {
    //instance variables
    private int sleepID, childID, duration;
    private long sleepTime;
    private String snoring, notes, medication, study, supplements, CPAP, other, unit, sleepDate;

    //constructor to create the object
    public Sleep(){

    }

    //constructor to create the object with parameters
    public Sleep(int childID, long startTime, long endTime, String notes){
        this.childID = childID;
        this.notes = notes;
    }

    //getters and setters for each instance variable
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

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public String getSupplements() {
        return supplements;
    }

    public void setSupplements(String supplements) {
        this.supplements = supplements;
    }

    public String getCPAP() {
        return CPAP;
    }

    public void setCPAP(String CPAP) {
        this.CPAP = CPAP;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSleepDate() {
        return sleepDate;
    }

    public void setSleepDate(String sleepDate) {
        this.sleepDate = sleepDate;
    }
}
