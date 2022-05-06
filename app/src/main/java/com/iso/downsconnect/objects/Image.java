package com.iso.downsconnect.objects;

//object for holding information for a image entry
public class Image {
    //instance variables
    private int childID, imageID;
    private byte[] image;

    //constructor to create the object
    public Image(){

    }

    //getters and setters for each instance variable
    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
