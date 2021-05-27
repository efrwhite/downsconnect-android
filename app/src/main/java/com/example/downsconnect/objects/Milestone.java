package com.example.downsconnect.objects;

public class Milestone {
    private int childId, milestoneId;
    private long rollingDate, sittingDate, walkingDate, standingDate;

    public Milestone(){}

    public long getRollingDate() {
        return rollingDate;
    }

    public void setRollingDate(long rollingDate) {
        this.rollingDate = rollingDate;
    }

    public long getSittingDate() {
        return sittingDate;
    }

    public void setSittingDate(long sittingDate) {
        this.sittingDate = sittingDate;
    }

    public long getWalkingDate() {
        return walkingDate;
    }

    public void setWalkingDate(long walkingDate) {
        this.walkingDate = walkingDate;
    }

    public long getStandingDate() {
        return standingDate;
    }

    public void setStandingDate(long standingDate) {
        this.standingDate = standingDate;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }
}
