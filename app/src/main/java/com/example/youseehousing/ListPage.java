package com.example.youseehousing;

import android.app.Activity;

public class ListPage {
     // The types of list
     public enum ListType {
         FAVORITES, MAIN_LISTING_PAGE
     }
     private ActivityFragmentOrigin afoActivity;

     public ActivityFragmentOrigin getActivityFragmentOrigin() {
         return afoActivity;
     }

    private void setAfoActivity(ActivityFragmentOrigin afoActivity) {
        this.afoActivity = afoActivity;
    }

    public ListPage(Activity activityFragmentOrigin) {
         // check if activityFragmentOrigin is a bad cast
         if ((afoActivity = validateActivity(activityFragmentOrigin)) == null) {
             throw new ClassCastException();
         }
     }

     private ActivityFragmentOrigin validateActivity(Activity activityFragmentOrigin) {
        if(activityFragmentOrigin != null) {
            try {
                return (ActivityFragmentOrigin) activityFragmentOrigin;
            } catch (ClassCastException e) {
                throw e;
            }
        }
        return null;
     }






}
