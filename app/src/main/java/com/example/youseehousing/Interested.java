package com.example.youseehousing;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
/**
 * This class will contain a get interested and a set interested method
 *
 * **/
public class Interested {

    private String TAG = "interested";

    // listingId is address field
    /**
     * This function takes a userId and inputs it into a listing's interested field.
     * **/
    public void setInterested(final String userId, final String listingId) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

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
    }
}
