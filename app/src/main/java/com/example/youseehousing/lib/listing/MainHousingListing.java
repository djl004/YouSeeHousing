package com.example.youseehousing.lib.listing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.youseehousing.R;
import com.example.youseehousing.lib.fragment.MainListingPageFragment;

/**
 *   DEPRECATED!!!!DEPRECATED!!!!DEPRECATED!!!!DEPRECATED!!!!DEPRECATED!!!!DEPRECATED!!!!
 *  Replaced by ActivityFragmentOrigin
    MainHousingListing
    This is the page that has thumbnails of all the listings you can see.

 **/


public class MainHousingListing extends AppCompatActivity implements MainListingPageFragment.OnListFragmentInteractionListener {
    private String TAG = "MainHousingListing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);
    }

    /**
        This function handles selecting a listing thumbnail.
    **/
    public void onListFragmentInteraction(ListingDetails item) {
        // TODO: Switching to an activity from a fragment doesn't work this way
        selectListingFxn(item);
    }

    /**
        This function handles what happens when a listing thumbnail is selected.
     **/
    private void selectListingFxn(ListingDetails item) {
        // Throw an exception if the activity is not found
        // Doesn't crash, just doesn't do anything
        try {
            Intent intent_f = new Intent(MainHousingListing.this, MainListingPage.class);
            // Pass selected listing's data to the next activity
            intent_f.putExtra("parcel_data", item);
            startActivity(intent_f);
        }
        catch (android.content.ActivityNotFoundException e) {
            e.printStackTrace();
            Log.e("exception", "exception: "+e);
        }
    }
}
