package com.example.youseehousing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/*
    MainHousingListing
    This is the page that has thumbnails of all the listings you can see.


 */


public class MainHousingListing extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);
    }


    /*
        This function handles selecting a listing thumbnail.
    */
    public void onListFragmentInteraction(MainHousingListing_PopulateList.ListingDetails item) {
        // TODO: Switching to an activity from a fragment doesn't work this way
        selectListingFxn(item);
    }


    /*
        This function handles what happens when a listing thumbnail is selected.
        // TODO: Implement what happens when a listing is selected, that is, going to a the
            MainListingPage that has the details of the listing.

     */
    private void selectListingFxn(MainHousingListing_PopulateList.ListingDetails item) {
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
