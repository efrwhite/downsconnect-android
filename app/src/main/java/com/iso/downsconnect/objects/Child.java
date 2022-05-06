package com.iso.downsconnect.objects;

//object for holding information for a child entry
//implements Comparable so child objects can be sorted
public class Child implements Comparable<Child>{
    //instance variables
    private String firstName, lastName, allergies, bloodType, gender, medications;
    private int childID;
    private long birthday, dueDate;

    //constructor to create the object with parameters
    public Child(String firstName, String lastName, long birthday, String gender){
       this.firstName = firstName;
       this.lastName = lastName;
       this.birthday = birthday;
       this.gender = gender;
   }

    //constructor to create the object
    public Child(){}


    //getters and setters for each instance variable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    //implement compareTo to enable sorting
    //sorts objects by the child's full name
    @Override
    public int compareTo(Child o) {
        String thisName = this.firstName + " " + this.lastName;
        String thatName = o.firstName + " " + o.lastName;
        thisName = thisName.toLowerCase();
        thatName = thatName.toLowerCase();
        return thisName.compareTo(thatName);
    }
}
