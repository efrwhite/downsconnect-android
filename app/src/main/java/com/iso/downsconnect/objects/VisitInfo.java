package com.iso.downsconnect.objects;

public class VisitInfo {
    private int visitInfoID, childID, medicalInfoID;
    private String answers, dates, providers;

    public VisitInfo(){

    }

    public int getVisitInfoID() {
        return visitInfoID;
    }

    public void setVisitInfoID(int visitInfoID) {
        this.visitInfoID = visitInfoID;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public int getMedicalInfoID() {
        return medicalInfoID;
    }

    public void setMedicalInfoID(int medicalInfoID) {
        this.medicalInfoID = medicalInfoID;
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
}
