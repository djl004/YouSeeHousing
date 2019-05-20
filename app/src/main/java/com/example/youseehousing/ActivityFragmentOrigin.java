package com.example.youseehousing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class ActivityFragmentOrigin extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    private BottomNavigationView bottomNavigationView;

    private final String TAG = "ActivityFragmentOrigin";

    final Fragment fragment1 = new UserPreferencesFragment();
//    final Fragment fragment2 = new ActivityListPageFragment();
    final ItemFragment fragment2 = new ItemFragment();
    final Fragment fragment3 = new FavoritesFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_origin);

        // Call AsyncTask execute to populate listing list
        MainHousingListing_PopulateList list = new MainHousingListing_PopulateList(ActivityFragmentOrigin.this);
        list.execute();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.bottombaritem_listing);

        fm.beginTransaction().add(R.id.frameLayout, fragment3, "1").hide(fragment1).commit();
        fm.beginTransaction().add(R.id.frameLayout, fragment2, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.frameLayout,fragment1, "2").commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottombaritem_preferences:
                                fm.beginTransaction().hide(active).show(fragment1).commit();
                                active = fragment1;
                                return true;
                            case R.id.bottombaritem_listing:
                                switchToListingView();
                                return true;
                            case R.id.bottombaritem_favorites:
                                fm.beginTransaction().hide(active).show(fragment3).commit();
                                active = fragment3;
                                return true;
                        }
                        return false;
                    }
                });
    }

    private void switchToListingView() {
        fm.beginTransaction().hide(active).show(fragment2).commit();
        active = fragment2;
        redrawList();
    }

    /**
     * Redraws the main housing list.
     * Called by AsyncTask MainHousingList_PopulateList when finished querying database.
     */
    public void redrawList() {
        new Thread() {
            public void run() {
                int i = 0;
//                while (i++ < 1000) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fragment2.refreshList();
                            }
                        });
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//            }
        }.start();
    }

    /**
     * Handle interaction with list fragment items
     * @param item : the selected listing
     */
    @Override
    public void onListFragmentInteraction(ListingDetails item) {
        selectListingFxn(item);
    }

    /**
     * What happens when a listing is selected
     * @param item : the selected listing
     */
    private void selectListingFxn(ListingDetails item) {
        // Throw an exception if the activity is not found
        // Doesn't crash, just doesn't do anything
        try {
            Intent intent_f = new Intent(ActivityFragmentOrigin.this, MainListingPage.class);

            // Pass selected listing's data to the next activity
            intent_f.putExtra("parcel_data", item);

            startActivity(intent_f);
        } catch (android.content.ActivityNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "exception: " + e);
        }
    }
}
