package com.example.youseehousing.forlisting;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Populate {
    public static ArrayList<Listing> populateTheList(){

        FirebaseDatabase poppingDB = FirebaseDatabase.getInstance();
        DatabaseReference poppingRef = poppingDB.getReference("message");

        return null;

    }
}
