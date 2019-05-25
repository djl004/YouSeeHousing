package com.example.youseehousing;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

/**
 *
 *  This class handles querying the database and populating the list of items
 *
 */
public class Favorites_PopulateList extends ListPage {
    private ActivityFragmentOrigin afoActivity;
    private String TAG = "Favorites";
    private FirebaseAuth mAuth;
    private String USERS_PATH = "users/";

    private ArrayList<String> userFavorites;


    private static final int COUNT = 5; // Max number of listings to query at once from database.

    private final ListPageFragment.ListType TYPE = ListPageFragment.ListType.FAVORITES;


    /**
     * Constructor
     *
     * @param activityFragmentOrigin : the activity to which this object belongs
     */
    public Favorites_PopulateList(Activity activityFragmentOrigin, RefreshableListFragmentPage fragment) {
        super(activityFragmentOrigin, fragment);
        afoActivity = super.getActivityFragmentOrigin();
        super.clearList();
        queryUserFavorites();
    }

    /**
     * This method queries the Cloud Firestore database for COUNT listings.
     * And calls addListingToPage for each retrieved
     * Returns true if query was successful
     * Returns false otherwise
     * TODO: Paginate data https://firebase.google.com/docs/firestore/query-data/query-cursors
     * TODO: Querying database probably deserves its own class
     **/
    private void queryDatabase() {
        // favorites has not been set
        if (userFavorites == null) {
            Log.e(TAG, "userFavorites is null!");
            return;
        }
        if (userFavorites.isEmpty()) {
            Log.e(TAG, "userFavorites is not null and empty!");
            // do something nice with empty favorites here
            // "You have no favorites! :'("
            return;
        }

        // query for favorite
        // each string is an ADDRESS
        for (String favoriteString : userFavorites) {
                queryForFavorite(favoriteString);
        }
    }

    private boolean queryForFavorite(final String favoriteString) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference listingRef = db.collection("listing");
        Query query = listingRef.whereEqualTo("address", favoriteString);
        if (db != null) {
                query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // call function to add listing details
                                    addListingToPage(document);
                                    getRefreshableFragment().refreshPage();
                                }
                            } else {
                                Log.w(TAG, "Error getting address: " + favoriteString, task.getException());
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


    private void queryUserFavorites() {
        // Set reference to the user's directory in the database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USERS_PATH + UserAuthentication.getUid());
        Log.e(TAG, "User path: " + ref.toString());

        // Attach a listener to read the data at our users reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> favorites = dataSnapshot.getValue(Account.class).getFavorites();
                userFavorites = favorites;
                returnUserFavorites(favorites);

                // callback to populate list when done
                queryDatabase();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private ArrayList<String> returnUserFavorites(ArrayList<String> favorites) {
        Log.e(TAG, favorites.toString());
        return favorites;
    }


}





//}

