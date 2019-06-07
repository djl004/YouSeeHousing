package com.example.youseehousing.lib.fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.youseehousing.lib.listing.Favorites_PopulateList;
import com.example.youseehousing.lib.ui.FilterButtonActions;
import com.example.youseehousing.lib.listing.ListPage;
import com.example.youseehousing.lib.listing.ListingButtonActions;
import com.example.youseehousing.lib.listing.ListingDetails;
import com.example.youseehousing.lib.listing.MainHousingListing_PopulateList;
import com.example.youseehousing.R;

import java.util.List;

public class ActivityFragmentOrigin extends AppCompatActivity implements ListPageFragment.OnListFragmentInteractionListener {

    public static final String BUNDLE_TAG = "ListingDetails";
    public static final String BUNDLE_VISIBILITY = "buttons";

    private BottomNavigationView bottomNavigationView;

    private final String TAG = "ActivityFragmentOrigin";

    private ListPage activeList;
    private ListPage favoritesList;

    final Fragment fragment1 = new UserPreferencesFragment();
    final ListPageFragment fragment2 = new MainListingPageFragment();
    final ListPageFragment fragment3 = new FavoritesFragment();
    final ListingDetailsOverlayFragment fragment4 = new ListingDetailsOverlayFragment();
    final ListingDetailsOverlayFragment compare_top = new ListingDetailsOverlayFragment();
    final ListingDetailsOverlayFragment compare_bottom = new ListingDetailsOverlayFragment();

    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment2;

    public static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private ListingDetails previousSelectedItem = null;
    private ListingDetails currentSelectedItem = null;

    private Menu optionsMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_origin);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.bottombaritem_listing);

        fm.beginTransaction().add(R.id.compare_bottom, compare_bottom, "6").hide(compare_bottom).commit(); //overlay
        fm.beginTransaction().add(R.id.compare_top, compare_top, "5").hide(compare_top).commit(); //overlay
        fm.beginTransaction().add(R.id.overlayLayout, fragment4, "4").hide(fragment4).commit(); //overlay
        fm.beginTransaction().add(R.id.frameLayout, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.frameLayout, fragment1, "1").hide(fragment1).commit();
        fm.beginTransaction().add(R.id.frameLayout,fragment2, "2").commit();


        // Create listing pages
        activeList = new MainHousingListing_PopulateList(ActivityFragmentOrigin.this, fragment2);




        // Bottom nav buttons
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottombaritem_preferences:
                                openUserPreferencesPage();
                                invalidateOptionsMenu();
                                return true;
                            case R.id.bottombaritem_listing:
                                openMainHousingListingPage();
                                invalidateOptionsMenu();
                                return true;
                            case R.id.bottombaritem_favorites:
                                openFavoritesPage();
                                invalidateOptionsMenu();
                                return true;
                        }
                        return false;
                    }
                });
    }

    /**
     * Open MainHousingListing page
     */
    private void openMainHousingListingPage() {
        toggleListingOverlay(false); // testing
        toggleCompareOverlay(false); // testing
        showFragment(fragment2);
        switchToView(ListPageFragment.ListType.MAIN_LISTING_PAGE);
        showOptions(ListPageFragment.ListType.MAIN_LISTING_PAGE);
//        switchToView(fragment2);
    }

    /**
     * Open favorites page
     */
    private void openFavoritesPage() {
        toggleListingOverlay(false); // testing
        toggleCompareOverlay(false); // testing
        showFragment(fragment3);
        switchToView(ListPageFragment.ListType.FAVORITES);
        showOptions(ListPageFragment.ListType.FAVORITES);
//        switchToView(fragment3);
    }

    private void openUserPreferencesPage() {
        showFragment(fragment1);
        toggleListingOverlay(false); // testing
        toggleCompareOverlay(false); // testing
        if(isCompareModeEnabled()) {
            disableCompareMode();
        }
    }

    /**
     * Shows/hides the listing overlay
     * @param visible - true to show, false to hide
     */
    public void toggleListingOverlay(boolean visible) {
//            ListingDetailsOverlayFragment.hideButtons(fragment4, !visible);
            showOverlayFragment(visible, fragment4);
    }

    public void toggleCompareOverlay(boolean visible) {
            showOverlayFragment(visible, compare_bottom);
            showOverlayFragment(visible, compare_top);
    }

    /**
     * Toggles visibility of a fragment.
     * @param visible
     * @param fragment
     */
    private void showOverlayFragment(boolean visible, Fragment fragment) {
        if(visible) {
            fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .show(fragment)
                    .commit();
        }
        else {
            fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .hide(fragment)
                    .commit();
        }
    }

    /**
     * If listing overlay is visible, back button hides listing overlay.
     * Else, does what it would normally do.
     */
    @Override
    public void onBackPressed() {
        // Check to see if single overlay is open
        if (checkIfFragmentIsVisible(fragment4)) {
            toggleListingOverlay(false);
        }
        // Check to see if compare overlay is open
        if (checkIfFragmentIsVisible(compare_bottom) || checkIfFragmentIsVisible(compare_top)) {
            toggleCompareOverlay(false);
        }
        // Cancel compare mode
        if (isCompareModeEnabled()) {
            disableCompareMode();
        }

        else {
//            super.onBackPressed();
        }
    }

    /**
     * Check if an arbitrary fragment is visible to the user.
     * @param fragment - the fragment to check
     * @return true if visible, false otherwise
     */
    private boolean checkIfFragmentIsVisible(Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        else if (fragment.isAdded() && fragment.isVisible() && fragment.getUserVisibleHint()) {
            return true;
        }
        else return false;
    }

    /**
     * Changes the current displayed fragment to the main housing listing page, and
     * redraws the page.
     */
    private void showFragment(Fragment pageFragment) {
        fm.beginTransaction().hide(active).show(pageFragment).commit();
        active = pageFragment;
//        createAndPopulateListingPage(pageFragment.getListType());
//        toggleListingOverlay(false); // testing
//        toggleCompareOverlay(false); // testing
    }
    private void switchToView(ListPageFragment.ListType type) {
//        fm.beginTransaction().hide(active).show(pageFragment).commit();
//        active = pageFragment;
        createAndPopulateListingPage(type);
        toggleListingOverlay(false); // testing
        toggleCompareOverlay(false); // testing
    }

    /**
     * Creates a new ListPage object.
     */
    public void createAndPopulateListingPage(ListPageFragment.ListType TYPE) {

        switch(TYPE) {
            case MAIN_LISTING_PAGE:
                activeList.clearList();
                activeList = new MainHousingListing_PopulateList(ActivityFragmentOrigin.this, fragment2);
                break;
            case FAVORITES:
                activeList.clearList();
                activeList = new Favorites_PopulateList(ActivityFragmentOrigin.this, fragment3);
                break;
        }
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
     * Used for compare function.
     * @param previousSelectedItem - the item that was selected to compare
     */
    public void setPreviousSelectedItem(ListingDetails previousSelectedItem) {
        this.previousSelectedItem = previousSelectedItem;
    }

    /**
     * Returns the previously selected listing.
     * @return
     */
    public ListingDetails getPreviousSelectedItem() {
        return previousSelectedItem;
    }

    /**
     * What happens when a listing is selected
     * @param item : the selected listing
     */
    private void selectListingFxn(ListingDetails item) {
        // pass this two different items to compare
        if(isCompareModeEnabled()) {
            makeCompare(previousSelectedItem, item);
        }
        else {
            makeListingPage(item); // single item listing
        }
    }

    /**
     * Shows the listing page of the listing selected.
     * @param item - the listing selected.
     */
    private void makeListingPage(ListingDetails item) {
        Bundle bundle = new Bundle();

        bundle.putParcelable(BUNDLE_TAG, item);
        bundle.putParcelable(BUNDLE_VISIBILITY, new ListingDetails());

        fragment4.setArguments(bundle);

        fragment4.refresh();

        toggleListingOverlay(true); // testing
    }

    /**
     * Opens an overlay with two Listings to compares
     * @param item1
     * @param item2
     */
    public void makeCompare(ListingDetails item1, ListingDetails item2) {
        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();

        bundle1.putParcelable(BUNDLE_TAG, (ListingDetails) item1.clone());
        bundle2.putParcelable(BUNDLE_TAG, (ListingDetails) item2.clone());

        compare_top.setArguments(bundle1);
        compare_bottom.setArguments(bundle2);

        compare_top.refresh();
        compare_bottom.refresh();

        toggleCompareOverlay(true);
        disableCompareMode(true);
    }

    /**
     * Returns true if the compare button has been pressed and in compare mode.
     * @return
     */
    public boolean isCompareModeEnabled() {
        // Set previousSelectedItem to null to cancel compare mode
        if (previousSelectedItem == null) {
            return false;
        }
        else return true;
    }

    /**
     * Cancels compare mode. If silent is true, it won't make a text prompt
     */
    public void disableCompareMode() {
        // Set previousSelectedItem to null to cancel compare mode
        disableCompareMode(false);
    }

    public void disableCompareMode(boolean silent) {
        // Set previousSelectedItem to null to cancel compare mode

        previousSelectedItem = null;
        if(!silent) {
            Toast.makeText(getApplicationContext(), ListingButtonActions.STRING_COMPARE_CANCEL, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Begin filter options menu methods
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        optionsMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        AlertDialog.Builder mBuilder;
        View mView;
        final Spinner mSpinner, mSpinner2, mSpinner3;
        ArrayAdapter<String> adapter, adapter2, adapter3;
        AlertDialog dialog;


        switch (item.getItemId()) {
            case R.id.action_dropdown_distance:
                FilterButtonActions.setDistanceFilter(this);
                return true;

            case R.id.action_dropdown_price:
                FilterButtonActions.setPriceFilter(this);
                return true;

            case R.id.action_dropdown_bedsbathrooms:
                FilterButtonActions.setBedsBathsFilter(this);
                return true;

            case R.id.action_dropdown_extras:
                FilterButtonActions.setExtrasFilter(this);
                return true;

            case R.id.action_dropdown_lease:
                FilterButtonActions.setLeaseLengthFilter(this);
                return true;

            case R.id.action_dropdown_sqft:
                FilterButtonActions.setSquareFtFilter(this);
                return true;

            case R.id.action_logout:
                FilterButtonActions.setLogOut(this, getApplicationContext());
                return true;

            case R.id.action_clear_filters:
                FilterButtonActions.clearFilters(this);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(active.equals(fragment1)){
            menu.findItem(R.id.action_filters).setVisible(false);
            menu.findItem(R.id.action_logout).setVisible(true);
        }
        if(active.equals(fragment2)) {
            // MainListingPage
            menu.findItem(R.id.action_filters).setVisible(true);
            menu.findItem(R.id.action_logout).setVisible(false);
        }
         if(active.equals(fragment3)) {
            // Favorites page
                menu.findItem(R.id.action_filters).setVisible(false);
                menu.findItem(R.id.action_logout).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void showOptions(ListPageFragment.ListType type) {
        if(optionsMenu == null) {
            Log.e(TAG, "optionsMenu is null!");
            return;
        }
        if (type == ListPageFragment.ListType.MAIN_LISTING_PAGE) {
            optionsMenu.findItem(R.id.action_filters).setVisible(true);
            optionsMenu.findItem(R.id.action_logout).setVisible(false);
        }
        else {
            optionsMenu.findItem(R.id.action_filters).setVisible(false);
            optionsMenu.findItem(R.id.action_logout).setVisible(false);
        }
    }

    /**
     * End filter options menu methods
     */


}


