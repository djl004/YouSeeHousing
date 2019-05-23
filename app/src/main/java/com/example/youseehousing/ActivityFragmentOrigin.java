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

public class ActivityFragmentOrigin extends AppCompatActivity implements ListPageFragment.OnListFragmentInteractionListener {

    private BottomNavigationView bottomNavigationView;

    private final String TAG = "ActivityFragmentOrigin";

    private ListPage activeList;
    private ListPage favoritesList;

    final Fragment fragment1 = new UserPreferencesFragment();
    final ListPageFragment fragment2 = new MainListingPageFragment();
    final ListPageFragment fragment3 = new FavoritesFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_origin);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.bottombaritem_listing);

        fm.beginTransaction().add(R.id.frameLayout, fragment3, "1").hide(fragment1).commit();
        fm.beginTransaction().add(R.id.frameLayout, fragment2, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.frameLayout,fragment1, "2").commit();

        // Create listing pages
        activeList = new MainHousingListing_PopulateList(ActivityFragmentOrigin.this, fragment2);
        favoritesList = new MainHousingListing_PopulateList(ActivityFragmentOrigin.this, fragment2);

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
                                switchToView(fragment2);
                                return true;
                            case R.id.bottombaritem_favorites:
                                switchToView(fragment3);
                                return true;
                        }
                        return false;
                    }
                });
    }

    /**
     * Changes the current displayed fragment to the main housing listing page, and
     * redraws the page.
     */
    private void switchToView(ListPageFragment pageFragment) {
        fm.beginTransaction().hide(active).show(pageFragment).commit();
        active = pageFragment;
        createAndPopulateListingPage(pageFragment.getListType());
    }

    /**
     * Creates a new ListPage object.
     */
    private void createAndPopulateListingPage(ListPageFragment.ListType TYPE) {
        // Call AsyncTask execute to populate listing list
        switch(TYPE) {
            case MAIN_LISTING_PAGE:
                activeList = new MainHousingListing_PopulateList(ActivityFragmentOrigin.this, fragment2);
//                activeList =
//                        new MainHousingListing_PopulateList(ActivityFragmentOrigin.this, fragment2);
                break;
            case FAVORITES:
                favoritesList = new Favorites_PopulateList(ActivityFragmentOrigin.this, fragment3);
//                activeList =
//                        new Favorites_PopulateList(ActivityFragmentOrigin.this, fragment3);
                break;

        }
//        activeList = new MainHousingListing_PopulateList(ActivityFragmentOrigin.this, fragment2);
//        favoritesList = new Favorites_PopulateList(ActivityFragmentOrigin.this, fragment3);
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
        // Doesn't crash, just doesn't do anything :(
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
