package com.example.youseehousing;

import android.support.annotation.NonNull;
import android.util.Log;

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
    private String Uid = "1027 Felspar St, San Diego, CA 92109 – San Diego  Unit: 0.5"; //TODO : Replace me
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
    private static int COUNT = 1;

    public MainHousingListing_PopulateList() {
        // Access a Cloud Firestore instance from your Activity
        queryDatabase();
    }


//    // You can add listings to the list here
//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createListingDetails(i));
//        }
//    }

    private static void addItem(ListingDetails item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    /**
     * This method queries the Cloud Firestore database for COUNT listings.
     * TODO: Paginate data https://firebase.google.com/docs/firestore/query-data/query-cursors
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
                                    Log.d(TAG, document.getId() + " => " + document.getData());
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
     **/
    private void getSnapshotDetails(QueryDocumentSnapshot document) {

    }

    // Create ListingDetails with details here
    //      id, title, details, description
    private ListingDetails createListingDetails(int position) {
        //queryDatabase();
        return new ListingDetails(String.valueOf(position),
                "Listing " + position,
                "Item " + position,
                makeCaption(position),
                "This is the description of listing " + position + "."
        );
    }

    /**
     * @param position : The array index corresponds to the position of the listing thumbnail
     *                 in the list
     *                 TODO: Determine what goes in the caption
     *                 Maybe something like: "$2152/mo 2BR/2BA"
     */
    private static String makeCaption(int position) {
        StringBuilder builder = new StringBuilder();

        // randomly generated rent and bedrooms for fun
        // the rent's too damn high!
        DecimalFormat df = new DecimalFormat("0.00");
        double rent = (Math.random() * 2000.0) + 1000.00;
        int bedroom_count = (int) (Math.random() * 5 + 1);
        int bathroom_count = (int) (Math.random() * 4 + 1);

        builder.append("$").append(df.format(rent)).append("/mo.  "); // Rent
        builder.append(bedroom_count).append("BR/"); // Bedrooms
        builder.append(bathroom_count).append("BA"); // Bedrooms

        return builder.toString();
    }



}

