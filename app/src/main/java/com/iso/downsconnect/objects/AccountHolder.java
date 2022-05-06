package com.iso.downsconnect.objects;

//object for holding information for an account entry
//implements Comparable so child objects can be sorted
public class AccountHolder implements Comparable<AccountHolder>{
    //instance variables
    private String firstName, lastName, username, password, phone;
    private int accountID;

    //constructor to create the object
    public AccountHolder(){

    }

    //constructor to create the object with parameters
    public AccountHolder(String firstName, String lastName, String username, String password, String phone){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    //implement compareTo to enable sorting
    //sorts objects by the account's full name
    @Override
    public int compareTo(AccountHolder o) {
        String thisName = this.firstName + " " + this.lastName;
        String thatName = o.firstName + " " + o.lastName;
        thisName = thisName.toLowerCase();
        thatName = thatName.toLowerCase();
        return thisName.compareTo(thatName);
    }
}
