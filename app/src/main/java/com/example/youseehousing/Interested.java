
package com.example.youseehousing;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
/**
 * This class will contain a get interested and a set interested method
 *
 * **/
/*
public class Interested {

    private String TAG = "interested";
    private final String USERS_PATH = "users/";
    // listingId is address field
    /**
     * This function takes a userId and inputs it into a listing's interested field.
     * **/
    /*
    public void setInterested(final String userId, final String listingId) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USERS_PATH + userId);

        // get userInterestedIds
        final ArrayList<String> interested = getUserInterestedIds(ref);

        // add new listingId
        interested.add(listingId);

        // update userId with new interestedId array
        ref.child("interestedIds").setValue(interested);


        // Set interested in Listing class
        if (db != null) {
            db.collection("listing")
                    .whereEqualTo("address", listingId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // call function to get listing details
                                    ArrayList<String> interested = (ArrayList<String>) document.get("interested");
                                    interested.add(userId);
                                    db.collection("listing").document(listingId).update("interested", interested);
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        // Set interested in user class

    }

    private ArrayList<String> getUserInterestedIds(DatabaseReference ref) {
        final ArrayList<String> interested = new ArrayList<>();

        // add listener to add listing to get current interested Ids
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Account interestedACC = dataSnapshot.getValue(Account.class);
                for(String id: interestedACC.getInterestedIds()){
                    interested.add(id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "The read failed: " + databaseError.getCode());
            }
        });
        return interested;
    }
    */
//}
