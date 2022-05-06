package com.iso.downsconnect.objects;

//object for holding information for a medication entry
//implements Comparable so medication objects can be sorted
public class Medication implements Comparable<Medication>{
    //instance variables
    private int medID, childID;
    private double dose;
    private String name, doseUnits, reason, frequency;

    //constructor to create the object
    public Medication(){}

    //getters and setters for each instance variable
    public int getMedID() {
        return medID;
    }

    public void setMedID(int medID) {
        this.medID = medID;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoseUnits() {
        return doseUnits;
    }

    public void setDoseUnits(String doseUnits) {
        this.doseUnits = doseUnits;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    //implement compareTo to enable sorting
    //sorts objects by the medications's name
    @Override
    public int compareTo(Medication o) {
        return this.getName().toLowerCase().compareTo(o.getName().toLowerCase());
    }
}
