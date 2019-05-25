package com.example.youseehousing;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

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
    private ArrayList<String> favorites;

    public Account() {
    }

    public Account(String name, String email, String birth, String gender, ArrayList<String> favorites) {
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
        this.favorites = favorites;
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

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

    public static ArrayList<String> makeEmptyFavorites() {
        return new ArrayList<String>();
    }
}
