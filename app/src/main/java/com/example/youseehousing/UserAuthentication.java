package com.example.youseehousing;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAuthentication {
    private static String TAG = "UserAuthentication";
    private static String USERS_PATH = "users/";

    /**
     * Constructor
     */
    private UserAuthentication() {
    }

    /**
     * Returns true if successfully added to favorites or already in favorites. False, otherwise.
     *  TODO: Add functionality for favorites. This sorta works
     * @param item
     * @return
     */
    public static void addToFavorites(final ListingDetails item) {
        final DatabaseReference ref = getUserDirectory();
        final ArrayList<String> current_favorites = new ArrayList<String>();

        if (ref != null && item != null) {
            Log.e(TAG, "Adding address " + item.getAddress() + "to favorites!");
            // Set reference to the user's directory in the database
            final ArrayList<String> favorites = new ArrayList<String>();
            Log.e(TAG, "User path: " + ref.toString());

            // Attach a listener to read the data at our users reference
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<String> favs = dataSnapshot.getValue(Account.class).getFavorites();
                    if (favs == null) {
                        return;
                    }
                    for (String fav : favs) {
                        if (fav == null) {
                            continue;
                        }
                        if( fav.equals(item.getAddress()) ) {
                            Log.i(TAG, "Favorite exists already");
                            return;
                        }
                        current_favorites.add(fav);
                    }
                    // If no duplicates, push to database
                    Map<String, Object> favorite_map = new HashMap<>();
                    favorite_map.put(Integer.toString(current_favorites.size()), item.getAddress());

                    ref.child("favorites").updateChildren(favorite_map, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                System.out.println("Data could not be saved " + databaseError.getMessage());
                            } else {
                                System.out.println("Data saved successfully.");
                            }
                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "The read failed: " + databaseError.getCode());
                }
            });
        }
    }



    /**
     * Returns a list of strings with the user's favorites.
     * @return
     */
    public static ArrayList<String> getFavorites() {
        Log.e(TAG, "Getting user favorites!");
        // Set reference to the user's directory in the database
        final ArrayList<String> favorites = new ArrayList<String>();
        DatabaseReference ref = UserAuthentication.getUserDirectory();
        Log.e(TAG, "User path: " + ref.toString());

        // Attach a listener to read the data at our users reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> favs = dataSnapshot.getValue(Account.class).getFavorites();
                for(String fav : favs ) {
                    Log.e(TAG, "Retrieved favorite: " + fav);
                    favorites.add(fav);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "The read failed: " + databaseError.getCode());
            }
        });
        return favorites;
    }

    /**
     * Queries database for an address
     * Returns true if it exists
     * Returns false otherwise
     * @param address
     */
    public boolean queryForAddress(final String address) {
        final List<ListingDetails> found_list = new ArrayList<ListingDetails>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference listingRef = db.collection("listing");
        Query query = listingRef.whereEqualTo("address", address);
        if (db != null) {
            query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Compare addresses returned to query address
                                    ListingDetails foundItem = ListingDetails.makeListingDetailsFromDocumentSnapshot(document);
                                    if(foundItem.getAddress().equals(address)) {
                                        found_list.add(foundItem);
                                    }
                                }
                            } else {
                                Log.w(TAG, "Error getting address: " + address, task.getException());
                            }
                        }
                    });
            // If any items were added to found_list, return true, query found address.
            if( found_list.size() > 0 ) {
                return true;
            }
            else return false;
        }
        else {
            Log.e(TAG, "db reference is null!");
            return false;
        }
    }

    /**
     * Returns a DatabaseReference that can be used to read/write to users profile.
     * @return
     */
    public static DatabaseReference getUserDirectory() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USERS_PATH + UserAuthentication.getUid());
        return ref;
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
