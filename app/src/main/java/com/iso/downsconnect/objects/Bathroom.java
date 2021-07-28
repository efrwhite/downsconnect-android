package com.iso.downsconnect.objects;

public class Bathroom {
    private String bathroomType, treatmentPlan, notes, leak, openAir, diaperCream, quantity, pottyAccident, duration;
    private int childID, bathroomID;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLeak() {
        return leak;
    }

    public void setLeak(String leak) {
        this.leak = leak;
    }

    public String getOpenAir() {
        return openAir;
    }

    public void setOpenAir(String openAir) {
        this.openAir = openAir;
    }

    public String getDiaperCream() {
        return diaperCream;
    }

    public void setDiaperCream(String diaperCream) {
        this.diaperCream = diaperCream;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPottyAccident() {
        return pottyAccident;
    }

    public void setPottyAccident(String pottyAccident) {
        this.pottyAccident = pottyAccident;
    }

    public int getBathroomID() {
        return bathroomID;
    }

    public void setBathroomID(int bathroomID) {
        this.bathroomID = bathroomID;
    }
}
