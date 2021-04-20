package com.example.downsconnect.objects;

public class Bathroom {
    private String bathroomType, treatmentPlan, notes;
    private int childID;
    private long dateOfLastStool;
    public Bathroom(){

    }

    public Bathroom(String bathroomType, String treatmentPlan, String notes, long dateOfLastStool){
        this.bathroomType = bathroomType;
        this.treatmentPlan = treatmentPlan;
        this.notes = notes;
        this.dateOfLastStool = dateOfLastStool;
    }

    public String getBathroomType() {
        return bathroomType;
    }

    public void setBathroomType(String bathroomType) {
        this.bathroomType = bathroomType;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getDateOfLastStool() {
        return dateOfLastStool;
    }

    public void setDateOfLastStool(long dateOfLastStool) {
        this.dateOfLastStool = dateOfLastStool;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }
}
