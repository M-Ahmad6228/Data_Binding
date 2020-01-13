package com.example.combined_task;

import com.orm.SugarRecord;

public class UserData extends SugarRecord {
    public UserData() {
    }
    String fullName;
    String emailAddress;
    String password;
    String picture;
    boolean isPictureSaved;

    public boolean getIsPictureSaved() {
        return isPictureSaved;
    }

    public void setIsPictureSaved(boolean isPictureSaved) {
        this.isPictureSaved = isPictureSaved;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public UserData(String fullName, String emailAddress, String password) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.password = password;
        picture = "";
        isPictureSaved = false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}