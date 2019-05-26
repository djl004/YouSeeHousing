package com.example.youseehousing;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ImageRecycler_PopulateList extends ListPage {
    private ActivityFragmentOrigin afoActivity;
    private String TAG = "MainHousingListing";
    private ImageRecycler_PopulateList imageRecyclerList;
    private List<ListingImage> IMAGES = new ArrayList<ListingImage>();

    private static final int COUNT = 5; // Max number of listings to query at once from database.
    private final ListPageFragment.ListType TYPE = ListPageFragment.ListType.IMAGE_RECYCLER;
    private final ListingDetails item;

    public ImageRecycler_PopulateList(ListingDetails item, RefreshableListFragmentPage fragment) {
        super(fragment);
        this.item = item;

        if(item != null) {
            populateList();
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


    public void addImageToPage(ListingImage image) {
        if ( image != null ) {
            ITEMS.add(image);
        }
    }

    private boolean populateList() {
        ArrayList<String> images = item.getPictures();

        if(item == null) {
            Log.e(TAG, "Item is null!");
            return false;
        }
        if (images == null) {
            Log.e(TAG, "item.getPictures() is null!");
            return false;
        }
        if (images.size() <= 0) {
            Log.e(TAG, "item.getPictures() is size 0!");
            return false;
        }

        Log.i(TAG, "Loading images!");
        for( String imageURL : item.getPictures() ) {
            ListingImage image = ListingImage.makeListingImage(imageURL);
            addImageToPage(image);
        }

        return true;
    }

//    @Override
//    public List<RecyclerViewListItem> getITEMS() {
//        return ITEMS;
//    }
}

