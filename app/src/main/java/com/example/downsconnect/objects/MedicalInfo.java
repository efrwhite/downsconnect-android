package com.example.downsconnect.objects;

import com.example.downsconnect.MedicalActivity;

public class MedicalInfo {
    private String provider, headInfo, temperatureInfo, visit, height, weight, providerType;
    private int childID, medicalID;
    private long doctorDate;

    public MedicalInfo(){}

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
}
