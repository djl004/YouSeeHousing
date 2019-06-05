package com.example.youseehousing.lib.listing;

import android.app.Activity;

import com.example.youseehousing.lib.ui.RecyclerViewListItem;
import com.example.youseehousing.lib.fragment.ActivityFragmentOrigin;
import com.example.youseehousing.lib.fragment.ListPageFragment;
import com.example.youseehousing.lib.fragment.RefreshableListFragmentPage;
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

    private List<RecyclerViewListItem> ITEMS_BUFFER = new ArrayList<RecyclerViewListItem>();



    public ListPage(Activity activityFragmentOrigin, RefreshableListFragmentPage fragment) {
         // check if activityFragmentOrigin is a bad cast
         if ((afoActivity = validateActivity(activityFragmentOrigin)) == null) {
             throw new ClassCastException();
         }
        setRefreshableFragment(fragment);
     }

     public ListPage(RefreshableListFragmentPage fragment) {
        setRefreshableFragment(fragment);
     }

     public ListPage() {}

    public ActivityFragmentOrigin getActivityFragmentOrigin() {
        return afoActivity;
    }

    public RefreshableListFragmentPage getRefreshableFragment() {
         return refreshableFragment;
     }

    private void setRefreshableFragment(RefreshableListFragmentPage fragment) {
         this.refreshableFragment = fragment;
    }

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
        if(getListType() != ListPageFragment.ListType.IMAGE_RECYCLER) {
            ListingDetails newListing = ListingDetails.makeListingDetailsFromDocumentSnapshot(document);
            // TODO: add filter here
            if (newListing != null) {
                ITEMS.add(newListing);
            }
        }
    }

    /**
     * Adds a listing to a temporary buffer for later processing
     * This is an attempted fix for the index out of bounds Recycler View error
     * See https://medium.com/@nhancv/android-fix-java-lang-indexoutofboundsexception-inconsistency-detected-invalid-item-70e9b3b489a2
     * @param item
     */
    public void addListingToTemporaryBuffer(ListingDetails item) {
        if (item != null) {
            ITEMS_BUFFER.add(item);

        }
    }

    /**
     * Assign the temporary buffer of listings to the actual list and refresh the page
     * This is an attempted fix for the index out of bounds Recycler View error
     */
    public void assignNewItemsList() {
        clearList();
        if(ITEMS_BUFFER == null) {
            ITEMS_BUFFER = new ArrayList<RecyclerViewListItem>();
        }
        ITEMS.addAll(ITEMS_BUFFER);
        getRefreshableFragment().refreshPage();
        ITEMS_BUFFER.clear();
    }

    public void addListingToPage(ListingDetails item) {
        if(getListType() != ListPageFragment.ListType.IMAGE_RECYCLER) {
            // TODO: add filter here
            if (item != null) {
                ITEMS.add(item);
            }
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

    /**
     * Returns number of items in ITEMS
     * @return
     */
    public int size() {
        return ITEMS.size();
    }
}
