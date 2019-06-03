package com.example.youseehousing.lib.listing;

import android.util.Log;

import com.example.youseehousing.lib.ui.ImageRecyclerViewAdapter;

import java.util.ArrayList;

public class ImageRecycler_PopulateList {
//    private ActivityFragmentOrigin afoActivity;
    private String TAG = "MainHousingListing";
    private ImageRecyclerViewAdapter adapter;

//    private final ListPageFragment.ListType TYPE = ListPageFragment.ListType.IMAGE_RECYCLER;
    private final ListingDetails item;

    public ImageRecycler_PopulateList(ImageRecyclerViewAdapter adapter, ListingDetails item) {
        this.adapter = adapter;
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
            adapter.getImageList().add(image);
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
//            getRefreshableFragment().refreshPage();
        }

        return true;
    }

//    @Override
//    public List<RecyclerViewListItem> getITEMS() {
//        return ITEMS;
//    }
}

