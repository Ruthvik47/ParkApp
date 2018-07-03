package com.abcexample.parkapp;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;


public class AccountInfo {

    private String accountid;
    private String FirstName;
    private String LastName;
    private String PhoneNumber;
    private String DateofBirth;
    private String Gender;

    public AccountInfo(){

    }

    public AccountInfo(String accountid, String firstName, String lastName, String phoneNumber, String dateofBirth, String gender) {
        this.accountid = accountid;
        FirstName = firstName;
        LastName = lastName;
        PhoneNumber = phoneNumber;
        DateofBirth = dateofBirth;
        Gender = gender;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getDateofBirth() {
        return DateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        DateofBirth = dateofBirth;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
