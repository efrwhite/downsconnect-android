package com.iso.downsconnect.objects;

//object for holding information for a journal entry
public class Journal {
    //instance variables
    private int journalID, childID;
    private String title, notes;

    //constructor to create the object
    public Journal(){}

    //getters and setters for each instance variable
    public int getJournalID() {
        return journalID;
    }

    public void setJournalID(int journalID) {
        this.journalID = journalID;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
