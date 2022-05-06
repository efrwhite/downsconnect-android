package com.iso.downsconnect.objects;

//object for holding information for a point that will be displayed on a chart
public class Point {
    //instance variables
    private float x, y;
    private String unit;

    //constructor to create the object
    public Point(){}

    //getters and setters for each instance variable
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
