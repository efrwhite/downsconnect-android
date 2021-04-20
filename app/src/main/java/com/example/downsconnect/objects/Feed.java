package com.example.downsconnect.objects;

public class Feed {
    private int amount;
    private long entryTime;
    private String substance, timeConsumed, notes;
    private int ChildID;

    public Feed(){

    }

    public Feed(int amount, String substance, String timeConsumed){
        this.amount = amount;
        this.substance = substance;
        this.timeConsumed = timeConsumed;
    }

    public Feed(int amount, String substance, String timeConsumed, String notes){
        this.amount = amount;
        this.substance = substance;
        this.timeConsumed = timeConsumed;
        this.notes = notes;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSubstance() {
        return substance;
    }

    public void setSubstance(String substance) {
        this.substance = substance;
    }

    public String getTimeConsumed() {
        return timeConsumed;
    }

    public void setTimeConsumed(String timeConsumed) {
        this.timeConsumed = timeConsumed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getChildID() {
        return ChildID;
    }

    public void setChildID(int childID) {
        ChildID = childID;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }
}
