package com.example.finalprojectlabmcs_2301865842;

public class UserData {

    int id;
    String username;
    String password;
    String phoneNumber;
    String gender;
    int wallet;
    String dob;

    public UserData(int id, String username, String password, String dob, String phoneNumber, String gender, int wallet) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.wallet = wallet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }
}