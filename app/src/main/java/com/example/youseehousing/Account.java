package com.example.youseehousing;

/**
 *  Account class for firebase realtime database setup
 *
 */
public class Account {
    String Uid;
    String Name;
    String Email;
    String Birth;
    String Gender;

    public Account() {
    }

    public Account(String name, String email, String birth, String gender, String uid) {
        Name = name;
        Email = email;
        Birth = birth;
        Gender = gender;
        Uid = uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBirth() {
        return Birth;
    }

    public void setBirth(String birth) {
        Birth = birth;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
