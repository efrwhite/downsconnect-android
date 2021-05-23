package com.example.downsconnect.objects;

public class Head {
    private int headSize, childAge;

    public Head(){}

    public Head(int head, int age){
        this.headSize = head;
        this.childAge = age;
    }

    public int getHeadSize() {
        return headSize;
    }

    public void setHeadSize(int headSize) {
        this.headSize = headSize;
    }

    public int getChildAge() {
        return childAge;
    }

    public void setChildAge(int childAge) {
        this.childAge = childAge;
    }
}