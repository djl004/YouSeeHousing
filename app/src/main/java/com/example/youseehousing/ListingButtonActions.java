package com.example.youseehousing;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ListingButtonActions {
    private final static String TAG = "ListingButtonActions";
    public final static String STRING_COMPARE_PROMPT = "Select another listing to compare\nBack button to cancel";
    public final static String STRING_COMPARE_CANCEL = "Compare cancelled";

    public static void addToFavorites(ListingDetails item) {
        Log.i(TAG, "Adding item to favorites!");
        UserAuthentication.addToFavorites(item);
    }
    public static void compare(ActivityFragmentOrigin activity, ListingDetails item) {
        Log.i(TAG, "Doing compare!");
        activity.setPreviousSelectedItem(item);
        activity.toggleListingOverlay(false);
        activity.toggleCompareOverlay(false);
        Toast.makeText(activity, STRING_COMPARE_PROMPT, Toast.LENGTH_LONG).show();
    }
    public static void openContactInfo(ListingDetails item) {
        Log.i(TAG, "Opening contact info!");
    }
    public static void openMap(ListingDetails item) {
        Log.i(TAG, "Opening map!");
    }
}
