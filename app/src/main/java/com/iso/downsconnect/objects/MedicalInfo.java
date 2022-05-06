package com.iso.downsconnect.objects;

//object for holding information for a medical visit entry
public class MedicalInfo {
    //instance variables
    private String provider, headInfo, temperatureInfo, visit, height, weight, providerType, answers, dates, providers, notes;
    private int childID, medicalID;
    private long doctorDate;

    //constructor to create the object
    public MedicalInfo(){}

    //getters and setters for each instance variable
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getHeadInfo() {
        return headInfo;
    }

    public void setHeadInfo(String headInfo) {
        this.headInfo = headInfo;
    }

    public String getTemperatureInfo() {
        return temperatureInfo;
    }

    public void setTemperatureInfo(String temperatureInfo) {
        this.temperatureInfo = temperatureInfo;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public int getMedicalID() {
        return medicalID;
    }

    public void setMedicalID(int medicalID) {
        this.medicalID = medicalID;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public long getDoctorDate() {
        return doctorDate;
    }

    public void setDoctorDate(long doctorDate) {
        this.doctorDate = doctorDate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getProviders() {
        return providers;
    }

    public void setProviders(String providers) {
        this.providers = providers;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
