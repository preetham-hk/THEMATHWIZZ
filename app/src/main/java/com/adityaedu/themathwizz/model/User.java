package com.adityaedu.themathwizz.model;

/**
 * Created by Preetham on 1/17/2018.
 */

public class User {

    private int id;
    private  String email;
    private String password;
    private String dob;

    public int getId(){return id;}

    public  void setId(int id) {this.id=id;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public  String getDob(){return dob;}

    public void setDob(String dob) {this.dob=dob;}
}
