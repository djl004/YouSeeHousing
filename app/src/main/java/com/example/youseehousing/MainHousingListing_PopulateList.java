package com.example.youseehousing;

import android.content.Context;
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

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class MainHousingListing_PopulateList {
    private FirebaseFirestore db;
    private String TAG = "MainHousingListing";

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

    public MainHousingListing_PopulateList(ItemFragment.OnListFragmentInteractionListener mListener) {
        this.mListener = mListener;
        // Access a Cloud Firestore instance from your Activity
        queryDatabase();
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
     * TODO: Paginate data https://firebase.google.com/docs/firestore/query-data/query-cursors
     * TODO: Querying database probably deserves its own class
     **/
    private void queryDatabase() {
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
        }
        else {
            Log.e("TAG", "db reference is null!");
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

