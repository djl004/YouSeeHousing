package com.example.youseehousing;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *  This class handles querying the database and populating the list of items
 *
 */
public class MainHousingListing_PopulateList extends AsyncTask<Void, Integer, Void> {
    private MainActivity callingActivity;
    private ActivityFragmentOrigin afoActivity;
    private ItemFragment listFragment;
    private FirebaseFirestore db;
    private String TAG = "MainHousingListing";

    public static List<ListingDetails> ITEMS = new ArrayList<ListingDetails>();

     private static final int COUNT = 15; // Max number of listings to query at once from database.

    /**
     * Constructor
     * @param activityFragmentOrigin : the activity to which this object belongs
     */
    public MainHousingListing_PopulateList(Activity activityFragmentOrigin) {
        if(activityFragmentOrigin != null) {
            try {
                this.afoActivity = (ActivityFragmentOrigin) activityFragmentOrigin;
            } catch (ClassCastException e) {
                this.afoActivity = null;
                throw e;
            }
        }
    }

    /**
     * This method takes as input a document snapshot from the database and adds a listing to the
     * page.
     * TODO: Paginate data https://firebase.google.com/docs/firestore/query-data/query-cursors
     * TODO: Querying database probably deserves its own class
     **/
    private void addListingToPage(QueryDocumentSnapshot document) {
            ListingDetails newListing = ListingDetails.makeListingDetailsFromDocumentSnapshot(document);
            if ( newListing != null ) {
                ITEMS.add(newListing);
            }

        if (afoActivity != null) {
            afoActivity.redrawList();
        }
    }

    /**
     * Clears the list for a new query.
     */
    private void clearList() {
        ITEMS.clear();
        if (afoActivity != null) {
            afoActivity.redrawList();
        }
    }

    /**
     * Gets ready for a new database query
     */
    private void newQuery() {

    }

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
//                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    // call function to get listing details
                                    addListingToPage(document);
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

    /**
     *      Async task methods
     **/
    @Override
    protected Void doInBackground(Void... voids) {
        if (isCancelled()) { return null; }
        Log.i(TAG,"Querying database!");
        queryDatabase();
        return null;
    }

    // This doesn't get called for a reason I can't figure out...
    @Override
    protected void onPostExecute(Void aVoid) {
//         try redrawing list from ActivtyFragmentOrigin
        if (afoActivity != null) {
            afoActivity.redrawList();
        }
    }
    /**
     *      End Async task methods
     **/

}

