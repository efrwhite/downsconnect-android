package com.iso.downsconnect.objects;

//object for holding information for a provider entry
//implements Comparable so provider objects can be sorted
public class Provider implements Comparable<Provider>{
    //instance variables
    private String name, prac_name, specialty, phone, fax, email, website, address, state, city, zip;
    private int providerID;

    //getters and setters for each instance variable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrac_name() {
        return prac_name;
    }

    public void setPrac_name(String prac_name) {
        this.prac_name = prac_name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public int getProviderID() {
        return providerID;
    }

    public void setProviderID(int providerID) {
        this.providerID = providerID;
    }

    //implement compareTo to enable sorting
    //sorts objects by the provider's name
    @Override
    public int compareTo(Provider o) {
        return this.name.compareTo(o.name);
    }
}
