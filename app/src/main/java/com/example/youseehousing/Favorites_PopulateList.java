package com.example.youseehousing;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *  This class handles querying the database and populating the list of items
 *
 */
public class Favorites_PopulateList extends ListPage {
    private ActivityFragmentOrigin afoActivity;
    private String TAG = "Favorites";

    private static final int COUNT = 5; // Max number of listings to query at once from database.
    private final ListPageFragment.ListType TYPE = ListPageFragment.ListType.FAVORITES;

    /**
     * Constructor
     * @param activityFragmentOrigin : the activity to which this object belongs
     */
    public Favorites_PopulateList(Activity activityFragmentOrigin, RefreshableListFragmentPage fragment) {
        super(activityFragmentOrigin, fragment);
        afoActivity = super.getActivityFragmentOrigin();
        super.clearList();
        queryDatabase();

    }

//    /**
//     * This method takes as input a document snapshot from the database and adds a listing to the
//     * page.
//     * TODO: Paginate data https://firebase.google.com/docs/firestore/query-data/query-cursors
//     * TODO: Querying database probably deserves its own class
//     **/
//    private void addListingToPage(QueryDocumentSnapshot document) {
//            ListingDetails newListing = ListingDetails.makeListingDetailsFromDocumentSnapshot(document);
//            if ( newListing != null ) {
//                ITEMS.add(newListing);
//                super.getRefreshableFragment().refreshPage();
//            }
//    }
//
//    /**
//     * Clears the list for a new query.
//     */
//    private void clearList() {
//        ITEMS.clear();
//        if (afoActivity != null) {
//            afoActivity.redrawLists();
//        }
//    }

    /**
     * This method queries the Cloud Firestore database for COUNT listings.
     * And calls addListingToPage for each retrieved
     * Returns true if query was successful
     * Returns false otherwise
     * TODO: Paginate data https://firebase.google.com/docs/firestore/query-data/query-cursors
     * TODO: Querying database probably deserves its own class
     **/
    private boolean queryDatabase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (db != null) {
            db.collection("listing")
                    .limit(COUNT)
                    .orderBy("address")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // call function to get listing details
//                                    addListingToPage(document);
                                    addListingToPage(document);
                                    getRefreshableFragment().refreshPage();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
            return true;
        }
        else {
            Log.e(TAG, "db reference is null!");
            return false;
        }
    }


}

