package com.iso.downsconnect.objects;

import java.util.Comparator;

public class Entry implements Comparable<Entry> {
    private int entryID, childID;
    private String entryText, entryType;
    private long entryTime, foreignID;


    public Entry(){

    }


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
