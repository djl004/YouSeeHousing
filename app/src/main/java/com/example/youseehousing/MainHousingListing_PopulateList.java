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
public class MainHousingListing_PopulateList extends ListPage {
    private ActivityFragmentOrigin afoActivity;
    private String TAG = "MainHousingListing";

//    public static List<ListingDetails> ITEMS = new ArrayList<ListingDetails>();

    private static final int COUNT = 5; // Max number of listings to query at once from database.
    private final ListPageFragment.ListType TYPE = ListPageFragment.ListType.MAIN_LISTING_PAGE;

    /**
     * Constructor
     * @param activityFragmentOrigin : the activity to which this object belongs
     */
    public MainHousingListing_PopulateList(Activity activityFragmentOrigin, RefreshableListFragmentPage fragment) {
        super(activityFragmentOrigin, fragment);
        afoActivity = super.getActivityFragmentOrigin();
        super.clearList();
        queryDatabase();
    }

//    /**
//     * This method takes as input a document snapshot from the database and adds a listing to the
//     * page.
//     * TODO: Paginate data https://firebase.google.com/docs/firestore/query-data/query-cursors
//     **/
//    private void addListingToPage(QueryDocumentSnapshot document) {
//            ListingDetails newListing = ListingDetails.makeListingDetailsFromDocumentSnapshot(document);
//            if ( newListing != null ) {
//                ITEMS.add(newListing);
//            }
//
//        if (afoActivity != null) {
//            afoActivity.redrawLists();
//        }
//    }
//
//    /**
//     * Clears the list for a new query.
//     */
//    private void clearList() {
//        super.ITEMS.clear();
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
                    .orderBy("price")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // call function to get listing details
                                    addListingToPage(document);
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
            getRefreshableFragment().refreshPage();
            return true;
        }
        else {
            Log.e(TAG, "db reference is null!");
            return false;
        }
    }
}

