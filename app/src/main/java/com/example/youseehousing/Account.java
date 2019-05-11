package com.example.youseehousing;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 *  Account class for firebase realtime database setup
 *
 */
@IgnoreExtraProperties
public class Account {
    private String name;
    private String email;
    private String birth;
    private String gender;

    public Account() {
    }

    public Account(String name, String email, String birth, String gender) {
        name = name;
        email = email;
        birth = birth;
        gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        gender = gender;
    }
}
