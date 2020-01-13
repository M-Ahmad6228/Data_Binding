package com.example.combined_task;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class User extends BaseObservable {

    String fullName;
    String emailAddress;
    String password;
    String confirmPassword;

    public User() {
        this.fullName = "";
        this.emailAddress = "";
        this.password = "";
        confirmPassword = "";
    }

    public User(String fullName, String emailAddress, String password, String confirmPassword) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    @Bindable
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String username) {
        this.fullName = username;
        notifyPropertyChanged(BR.fullName);
    }

    @Bindable
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        notifyPropertyChanged(BR.emailAddress);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        notifyPropertyChanged(BR.confirmPassword);
    }
}
