package com.example.finalprojectlabmcs_2301865842;

public class TransactionHistory{

    int transID;
    String transDate;
    int userID;
    int prodID;

    public TransactionHistory(int transID, String transDate, int userID, int prodID) {
        this.transID = transID;
        this.transDate = transDate;
        this.userID = userID;
        this.prodID = prodID;
    }

    public int getTransID() {
        return transID;
    }

    public void setTransID(int transID) {
        this.transID = transID;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProdID() {
        return prodID;
    }

    public void setProdID(int prodID) {
        this.prodID = prodID;
    }
}