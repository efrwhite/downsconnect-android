package com.example.downsconnect.objects;

public class Point {
    private int y, x;

    public Point(){}

    public Point(int x, int y){
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
