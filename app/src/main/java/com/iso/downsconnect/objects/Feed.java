package com.iso.downsconnect.objects;

import java.io.Serializable;

public class Feed implements Serializable {
    private int amount;
    private long entryTime;
    private String substance, foodUnit, notes, iron, vitamin, other;
    private int childID, feedID;

    public Feed(){

    }

    public Feed(int amount, String substance, String timeConsumed){
        this.amount = amount;
        this.substance = substance;
        this.foodUnit = timeConsumed;
    }

    public Feed(int amount, String substance, String timeConsumed, String notes){
        this.amount = amount;
        this.substance = substance;
        this.foodUnit = timeConsumed;
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

    public String getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(String foodUnit) {
        this.foodUnit = foodUnit;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    public String getIron() {
        return iron;
    }

    public void setIron(String iron) {
        this.iron = iron;
    }

    public String getVitamin() {
        return vitamin;
    }

    public void setVitamin(String vitamin) {
        this.vitamin = vitamin;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getFeedID() {
        return feedID;
    }

    public void setFeedID(int feedID) {
        this.feedID = feedID;
    }
}
