package com.example.youseehousing;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
    MainHousingListing
    This is the page that has thumbnails of all the listings you can see.

 **/


public class MainHousingListing extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {
    private android.support.v4.app.Fragment itemFragment;
    private String TAG = "MainHousingListing";
    private MyItemRecyclerViewAdapter adapter;
    MainHousingListing_PopulateList queryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);

        itemFragment = (Fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list);

//        RecyclerView recyclerView = findViewById(R.id.fragment_list);
//        adapter = new MyItemRecyclerViewAdapter(MainHousingListing_PopulateList.ITEMS, mListener);
//        adapter.setClickListener(this);
//
//        Intent intent = getIntent();
//        String activity = intent.getStringExtra("from");
//
//        if(activity == null) {
//            queryList = new MainHousingListing_PopulateList(this);
//            queryList.execute();
//        }
//        else if (activity == "MainHousingListing") {
//            queryList.cancel(true);
//            Log.i(TAG,"coming from MainHousingListing");
//
//        }
    }

    /**
     * Redraw the activity.
     */
    public void refreshList() {
        finish();
        android.support.v4.app.Fragment newfrag = new ItemFragment();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_list, newfrag);
        transaction.addToBackStack(null);
        Intent i = new Intent(this, MainHousingListing.class);
        i.putExtra("from","MainHousingListing");
        startActivity(i);
    }

    /*
        This function handles selecting a listing thumbnail.
    */
    public void onListFragmentInteraction(ListingDetails item) {
        // TODO: Switching to an activity from a fragment doesn't work this way
        selectListingFxn(item);
    }


    /*
        This function handles what happens when a listing thumbnail is selected.
        // TODO: Implement what happens when a listing is selected, that is, going to a the
            MainListingPage that has the details of the listing.

     */
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
