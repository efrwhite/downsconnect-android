package com.iso.downsconnect.objects;

public class AccountHolder implements Comparable<AccountHolder>{
    private String firstName, lastName, username, password, phone;
    private int accountID;
    public AccountHolder(){

    }
    public AccountHolder(String firstName, String lastName, String username, String password, String phone){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

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

    @Override
    public int compareTo(AccountHolder o) {
        String thisName = this.firstName + " " + this.lastName;
        String thatName = o.firstName + " " + o.lastName;
        thisName = thisName.toLowerCase();
        thatName = thatName.toLowerCase();
        return thisName.compareTo(thatName);
    }
}
