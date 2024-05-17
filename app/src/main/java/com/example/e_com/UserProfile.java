package com.example.e_com;

public class UserProfile {
    String email,name,address;
    long number;

    public UserProfile(String email, String name, String address, long number) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.number = number;
    }

    public UserProfile(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
