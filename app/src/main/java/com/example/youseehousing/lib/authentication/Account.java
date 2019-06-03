package com.example.youseehousing.lib.authentication;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *  Account class for firebase realtime database setup
 *
 */
@IgnoreExtraProperties
public class Account {
    private static final String TAG = "Account";
    //user signup
    private String name;
    private String email;
    private String birth;
    private String gender;
    private ArrayList<String> favorites;
    //user profile
    private String smoking;
    private String noise;
    private String wake;
    private String sleep;
    private String guest;
    private String other;


    public Account() {
    }

    public Account(String name, String email, String birth, String gender, ArrayList<String> favorites) {
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
        this.favorites = favorites;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name",name);
        result.put("email",email);
        result.put("birth", birth);
        result.put("gender", gender);
        result.put("favorite", favorites);
        result.put("smoking", smoking);
        result.put("wake",wake);
        result.put("sleep",sleep);
        result.put("guest",guest);
        result.put("noise",noise);
        result.put("other",other);
        return result;
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
        if (favorites == null) {
            return new ArrayList<String>();
        }
        return favorites;
    }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

    public static ArrayList<String> makeEmptyFavorites() {
        return new ArrayList<String>();
    }

    public String getNoise() {
        return noise;
    }

    public void setNoise(String noise) {
        this.noise = noise;
    }

    public String getWake() {
        return wake;
    }

    public void setWake(String wake) {
        this.wake = wake;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
