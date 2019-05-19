package com.example.youseehousing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.data.DataBufferObserver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class MainHousingListing_PopulateList extends AsyncTask<Void, Integer, Void> {
    private MainActivity callingActivity;
    private FirebaseFirestore db;
    private String TAG = "MainHousingListing";

    // Async task methods
    @Override
    protected Void doInBackground(Void... voids) {
        // Access a Cloud Firestore instance from your Activity
        queryDatabase();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        // Code to refresh list
        callingActivity.changeToMainHousingListingPage();
    }
    // End Async Task methods

    /**
     * An array of listing thumbnails
     */
    public static List<ListingDetails> ITEMS = new ArrayList<ListingDetails>();

    /**
     * A map of  listing details items, by ID.
     */
    public static Map<String, ListingDetails> ITEM_MAP = new HashMap<String, ListingDetails>();

    // Max listings to add to page. This can be the number of items from the database that fit
    // the search query for example.
    private static int COUNT = 10;
    ItemFragment.OnListFragmentInteractionListener mListener;

    public MainHousingListing_PopulateList(MainActivity callingActivity) {
        this.callingActivity = callingActivity;
    }


    /**
     * This method takes as input a document snapshot from the database and adds a listing to the
     * page.
     * TODO: Paginate data https://firebase.google.com/docs/firestore/query-data/query-cursors
     * TODO: Querying database probably deserves its own class
     **/
    private void addListingToPage(QueryDocumentSnapshot document) {
            ListingDetails newListing = getSnapshotDetails(document);
            if ( newListing != null ) {
                ITEMS.add(newListing);
            }
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
                    .orderBy("address")
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
            Log.e("TAG", "db reference is null!");
            return false;
        }
    }

    /**
     *  Breaks the QueryDocumentSnapshot into its component parts
     *  Returns a ListingDetails object populated with the details
     **/
    private ListingDetails getSnapshotDetails(QueryDocumentSnapshot document) {
        if (document.exists()) {
            String address = document.get("address").toString(); // Address
            String bath = document.get("bath").toString(); // Bath
            String bed = document.get("bed").toString(); // bed
            String buildingLease = document.get("buildingLease").toString(); // Bath
            String contact = document.get("contact").toString(); // contact
            String desc = document.get("desc").toString(); // desc
            String dim = document.get("dim").toString(); // dim
            String link = document.get("link").toString(); // link
            String parking = document.get("parking").toString(); // parking
            // TODO: Pass a default array with a length > 0 if this is null
            ArrayList<String> pictures = (ArrayList<String>) document.get("pictures"); // parking
            String pet = document.get("pet").toString(); // pet
            String price = document.get("price").toString(); // dim
            String unitLease = document.get("unitLease").toString(); // Bath
            String washerDryer = document.get("washerDryer").toString(); // Bath

/*
        Listing details constructor
        public ListingDetails(String address,
                    String bath,
                    String bed,
                    String buildingLease,
                    String contact,
                    String desc,
                    String dim,
                    String link,
                    String parking,
                    ArrayList<String> pictures,
                    String pet,
                    String price,
                    String unitLease,
                    String washerDryer) { ... }
*/

            return new ListingDetails(address, bath, bed, buildingLease, contact,
                    desc, dim, link, parking, pictures, pet, price, unitLease, washerDryer);
        }
        else {
            return null;
        }
    }

//    /**
//     *
//     *  Returns a ListingDetails object populated with the details
//     **/
//    private ListingDetails createListingDetails(int position) {
//        //queryDatabase();
//        return new ListingDetails(String.valueOf(position),
//                "Listing " + position,
//                "Item " + position,
//                makeCaption(position),
//                "This is the description of listing " + position + "."
//        );
//    }
//
//    /**
//     * @param position : The array index corresponds to the position of the listing thumbnail
//     *                 in the list
//     *                 TODO: Determine what goes in the caption
//     *                 Maybe something like: "$2152/mo 2BR/2BA"
//     */
//    private static String makeCaption(int position) {
//        StringBuilder builder = new StringBuilder();
//
//        // randomly generated rent and bedrooms for fun
//        // the rent's too damn high!
//        DecimalFormat df = new DecimalFormat("0.00");
//        double rent = (Math.random() * 2000.0) + 1000.00;
//        int bedroom_count = (int) (Math.random() * 5 + 1);
//        int bathroom_count = (int) (Math.random() * 4 + 1);
//
//        builder.append("$").append(df.format(rent)).append("/mo.  "); // Rent
//        builder.append(bedroom_count).append("BR/"); // Bedrooms
//        builder.append(bathroom_count).append("BA"); // Bedrooms
//
//        return builder.toString();
//    }



}

