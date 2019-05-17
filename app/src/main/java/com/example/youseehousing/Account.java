package com.example.youseehousing;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 *  Account class for firebase realtime database setup
 *
 */
@IgnoreExtraProperties
public class Account {
    private static final String TAG = "Account";
    private String name;
    private String email;
    private String birth;
    private String gender;

    public Account() {
    }

    public Account(String name, String email, String birth, String gender) {
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        //Log.d(TAG,"Ctor name: " + this.name + ", passed in name:" + name);
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
