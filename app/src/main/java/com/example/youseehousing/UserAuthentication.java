package com.example.youseehousing;

import android.app.LauncherActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;

public class UserAuthentication {
    /**
     * Constructor
     */
    private UserAuthentication() {
    }

    public ArrayList<String> getFavorites() {
        return null;
    }

    public static boolean addToFavorites(ListingDetails item) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        return false;

    }

    public static String getUid(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            for(UserInfo profile : user.getProviderData()) {
                return profile.getUid();
            }
        }
        return "";
    }
}
