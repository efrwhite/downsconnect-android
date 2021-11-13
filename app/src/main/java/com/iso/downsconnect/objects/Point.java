package com.iso.downsconnect.objects;

public class Point {
    private float y, x;

    public Point(){}

    public Point(float x, float y){
        this.y = y;
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
