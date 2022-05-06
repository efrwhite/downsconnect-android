package com.iso.downsconnect.objects;

import java.util.Comparator;

//object for holding information for a entries
//implements Comparable so entry objects can be sorted
public class Entry implements Comparable<Entry> {
    //instance variables
    private int entryID, childID;
    private String entryText, entryType;
    private long entryTime, foreignID;

    //constructor to create the object
    public Entry(){

    }

    //getters and setters for each instance variable
    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    public long getForeignID() {
        return foreignID;
    }

    public void setForeignID(long foreignID) {
        this.foreignID = foreignID;
    }

    public int getEntryID() {
        return entryID;
    }

    public void setEntryID(int entryID) {
        this.entryID = entryID;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }


    //implement compareTo to enable sorting
    //sorts objects by the entry's date and time
    @Override
    public int compareTo(Entry o) {
        if (this.entryTime > o.entryTime){
            return -1;
        }
        if (this.entryTime < o.entryTime){
            return 1;
        }
        else{
            return 0;
        }
    }
}
