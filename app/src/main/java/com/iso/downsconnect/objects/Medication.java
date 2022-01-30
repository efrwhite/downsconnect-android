package com.iso.downsconnect.objects;

public class Medication {
    private int medID, childID;
    private double dose;
    private String name, doseUnits, reason, frequency;

    public Medication(){}

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
}
