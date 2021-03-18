package com.example.downsconnect;

public class MedicalInfo {
    private int height, weight, headSize, dosage;
    private long entryTime;
    private String vaccine, doctorVisit, temperature, health;

    public MedicalInfo(){

    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeadSize() {
        return headSize;
    }

    public void setHeadSize(int headSize) {
        this.headSize = headSize;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getDoctorVisit() {
        return doctorVisit;
    }

    public void setDoctorVisit(String doctorVisit) {
        this.doctorVisit = doctorVisit;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }
}
