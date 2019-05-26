package com.example.youseehousing;

import android.app.Activity;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListPage {
    private ActivityFragmentOrigin afoActivity;
    private RefreshableListFragmentPage refreshableFragment;

    public ListPageFragment.ListType getListType() {
        return type;
    }

    private ListPageFragment.ListType type = ListPageFragment.ListType.MAIN_LISTING_PAGE;
    public static List<RecyclerViewListItem> ITEMS = new ArrayList<RecyclerViewListItem>();



    public ListPage(Activity activityFragmentOrigin, RefreshableListFragmentPage fragment) {
         // check if activityFragmentOrigin is a bad cast
         if ((afoActivity = validateActivity(activityFragmentOrigin)) == null) {
             throw new ClassCastException();
         }
        setRefreshableFragment(fragment);

     }
    public ActivityFragmentOrigin getActivityFragmentOrigin() {
        return afoActivity;
    }

    public RefreshableListFragmentPage getRefreshableFragment() {
         return refreshableFragment;
     }

    private void setRefreshableFragment(RefreshableListFragmentPage fragment) {
         this.refreshableFragment = fragment;
    }

//    private void addListingToItems(ListingDetails newListing) {
//        ITEMS.add(newListing);
//    }

    /**
     * Verify that activity passed is in fact an ActivityFragmentOrigin object.
     * @param activityFragmentOrigin
     * @return
     */
    private ActivityFragmentOrigin validateActivity(Activity activityFragmentOrigin) {
        if(activityFragmentOrigin != null) {
            try {
                return (ActivityFragmentOrigin) activityFragmentOrigin;
            } catch (ClassCastException e) {
                throw e;
            }
        }
        return null;
     }

    /**
     * Add a listing to ITEMS.
     */
    public void addListingToPage(QueryDocumentSnapshot document) {
        ListingDetails newListing = ListingDetails.makeListingDetailsFromDocumentSnapshot(document);
        if ( newListing != null ) {
            ITEMS.add(newListing);
        }

    }

    /**
     * Clears the list for a new query.
     */
    public void clearList() {
        ITEMS.clear();
        if(refreshableFragment != null) {
            refreshableFragment.refreshPage();
        }
    }
}
