package com.example.youseehousing;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.youseehousing.forlisting.Listing;
import com.example.youseehousing.forlisting.Populate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class MainHousingListing_PopulateList {
        private FirebaseDatabase database;
        private DatabaseReference myRef;
        private String Uid;

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
        setUp();
        for (int i = 1; i <= COUNT; i++) {
            addItem(createListingDetails(i));
        }
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

    private void setUp() {
        //declare all the variables
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("listing");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("in MHL_PL,", "Value is " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("in MHL_PL,", "Failed to read value.", error.toException());
            }
        });
    }


    // Create ListingDetails with details here
    //      id, title, details, description
    private ListingDetails createListingDetails(int position) {
        setUp();
        return new ListingDetails (String.valueOf(position),
                              "Listing " + position,
                            "Item " + position,
                                    makeCaption(position),
                         "This is the description of listing " + position + "."
                                    );
    }

    /**
     * @param position : The array index corresponds to the position of the listing thumbnail
     *                     in the list
     *  TODO: Determine what goes in the caption
     *                 Maybe something like: "$2152/mo 2BR/2BA"
     */
    private static String makeCaption(int position) {
        StringBuilder builder = new StringBuilder();

        // randomly generated rent and bedrooms for fun
        // the rent's too damn high!
        DecimalFormat df = new DecimalFormat("0.00");
        double rent = (Math.random()*2000.0)+1000.00;
        int bedroom_count = (int) (Math.random()*5+1);
        int bathroom_count = (int) (Math.random()*4+1);

        builder.append("$").append(df.format(rent)).append("/mo.  "); // Rent
        builder.append(bedroom_count).append("BR/"); // Bedrooms
        builder.append(bathroom_count).append("BA"); // Bedrooms

        return builder.toString();
    }


    private void showData(DataSnapshot dataSnapshot) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("listing");

//        for(DataSnapshot ds : dataSnapshot.getChildren()){
//            ListingDetails uInfo = new ListingDetails();
//            Log.d(TAG,"Before set name: " + ds.child(Uid).getValue(Account.class).getName());
////            uInfo.setAddress( ds.child(Uid).getValue(Account.class).getName()); //set the name
////            uInfo.setEmail(ds.child(Uid).getValue(Account.class).getEmail()); //set the email
//            Log.d("Debug", "name:" + ds.child(Uid).getValue());
//            //display all the information
//            Log.d(TAG, "showData: name: " + uInfo.getName());
//            Log.d(TAG, "showData: email: " + uInfo.getEmail());
//
////            final TextView name = (TextView) findViewById(R.id.name);
////            name.setText(uInfo.getName());
////            final TextView userEmail = (TextView) findViewById(R.id.email);
////            userEmail.setText(uInfo.getEmail());

        }
    }

