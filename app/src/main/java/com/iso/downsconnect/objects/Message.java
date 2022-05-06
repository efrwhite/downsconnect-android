package com.iso.downsconnect.objects;

//object for holding information for a message entry
public class Message {
    //instance variables
    private int messageID, childID;
    private String message;

    //constructor to create the object
    public Message(){}

    //getters and setters for each instance variable
    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
