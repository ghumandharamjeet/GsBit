package com.dharam.gsbit.model;

public class UserInfo {

    private String personName;
    private String password;
    private String phoneNumber;

    public UserInfo() { }

    public UserInfo(String personName, String password, String phoneNumber)
    {
        this.personName = personName;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
