package com.iso.downsconnect.objects;

//object for holding information for a resource entry
public class Resource {
    //instance variables
    private int resourceID;
    private String name, URL;

    //getters and setters for each instance variable
    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
