package com.example.youseehousing;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

import java.util.ArrayList;

public class ActivityFragmentOrigin extends AppCompatActivity implements ListPageFragment.OnListFragmentInteractionListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_origin);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        // Setup Navigation Drawer Layout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


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
                                switchToView(fragment2);
                                invalidateOptionsMenu();
                                return true;
                            case R.id.bottombaritem_favorites:
                                switchToView(fragment3);
                                invalidateOptionsMenu();
                                return true;
                        }
                        return false;
                    }
                });
    }


    private void openUserPreferencesPage() {
        fm.beginTransaction().hide(active).show(fragment1).commit();
        active = fragment1;
        toggleListingOverlay(false); // testing
        toggleCompareOverlay(false); // testing
    }

    /**
     * Shows/hides the listing overlay
     * @param visible - true to show, false to hide
     */
    private void toggleListingOverlay(boolean visible) {
            showOverlayFragment(visible, fragment4);
    }

    private void toggleCompareOverlay(boolean visible) {
            showOverlayFragment(visible, compare_bottom);
            showOverlayFragment(visible, compare_top);
    }

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
        if (checkIfFragmentIsVisible(fragment4)) {
            toggleListingOverlay(false);
        }

        if (checkIfFragmentIsVisible(compare_bottom) || checkIfFragmentIsVisible(compare_top)) {
            toggleCompareOverlay(false);
        }

        else {
            super.onBackPressed();
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
    private void switchToView(ListPageFragment pageFragment) {
        fm.beginTransaction().hide(active).show(pageFragment).commit();
        active = pageFragment;
        createAndPopulateListingPage(pageFragment.getListType());
        toggleListingOverlay(false); // testing
        toggleCompareOverlay(false); // testing
    }

    /**
     * Creates a new ListPage object.
     */
    private void createAndPopulateListingPage(ListPageFragment.ListType TYPE) {
        // Call AsyncTask execute to populate listing list
        switch(TYPE) {
            case MAIN_LISTING_PAGE:
                activeList = new MainHousingListing_PopulateList(ActivityFragmentOrigin.this, fragment2);
                break;
            case FAVORITES:
                favoritesList = new Favorites_PopulateList(ActivityFragmentOrigin.this, fragment3);
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
     * What happens when a listing is selected
     * @param item : the selected listing
     */
    private void selectListingFxn(ListingDetails item) {
//        makeCompare(item, item); // pass this two different items to compare
        makeListingPage(item); // single item listing
    }

    private void makeListingPage(ListingDetails item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("ListingDetails", item);

        fragment4.setArguments(bundle);
        fragment4.hideButtons(true);
        fragment4.refresh();

        toggleListingOverlay(true); // testing
    }

    private void makeCompare(ListingDetails item1, ListingDetails item2) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("ListingDetails", item1);

        compare_bottom.setArguments(bundle);
        compare_top.setArguments(bundle);

        compare_bottom.refresh();
        compare_top.refresh();

        toggleCompareOverlay(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.action_dropdown_location:
                mBuilder = new AlertDialog.Builder(ActivityFragmentOrigin.this);
                mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                mBuilder.setTitle("Set Location Filter");
                mSpinner = (Spinner) ((View) mView).findViewById(R.id.spinner);
                adapter = new ArrayAdapter<String>(ActivityFragmentOrigin.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.locationList));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("alpha…")){

                            Toast.makeText(ActivityFragmentOrigin.this,
                                    mSpinner.getSelectedItem().toString(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
                return true;

            case R.id.action_dropdown_price:
                mBuilder = new AlertDialog.Builder(ActivityFragmentOrigin.this);
                mView = getLayoutInflater().inflate(R.layout.dialog_doublespinner, null);
                mBuilder.setTitle("Set Price (MIN/MAX) Filter");
                mSpinner = (Spinner) ((View) mView).findViewById(R.id.spinner1);
                adapter = new ArrayAdapter<String>(ActivityFragmentOrigin.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.sizeList));
                mSpinner.setAdapter(adapter);

                mSpinner2 = (Spinner) ((View) mView).findViewById(R.id.spinner2);
                adapter2 = new ArrayAdapter<String>(ActivityFragmentOrigin.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.bedList));
                mSpinner2.setAdapter(adapter2);

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("alpha…")){

                            Toast.makeText(ActivityFragmentOrigin.this,
                                    mSpinner.getSelectedItem().toString(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();

                return true;
            case R.id.action_dropdown_washerdryer:
                mBuilder = new AlertDialog.Builder(ActivityFragmentOrigin.this);
                mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                mBuilder.setTitle("Set Washer/Dryer Filter");
                mSpinner = (Spinner) ((View) mView).findViewById(R.id.spinner);
                adapter = new ArrayAdapter<String>(ActivityFragmentOrigin.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.washdryList));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("alpha…")){

                            Toast.makeText(ActivityFragmentOrigin.this,
                                    mSpinner.getSelectedItem().toString(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();

                return true;
            case R.id.action_dropdown_furnishedaccommodations:
                mBuilder = new AlertDialog.Builder(ActivityFragmentOrigin.this);
                mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                mBuilder.setTitle("Set Accommodations Filter");
                mSpinner = (Spinner) ((View) mView).findViewById(R.id.spinner);
                adapter = new ArrayAdapter<String>(ActivityFragmentOrigin.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.furnishedList));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("alpha…")){

                            Toast.makeText(ActivityFragmentOrigin.this,
                                    mSpinner.getSelectedItem().toString(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();

                return true;
            case R.id.action_dropdown_sizebedsbathrooms:
                mBuilder = new AlertDialog.Builder(ActivityFragmentOrigin.this);
                mView = getLayoutInflater().inflate(R.layout.dialog_multispinner, null);
                mBuilder.setTitle("Set Size/Bed/Bathroom Filter");
                mSpinner = (Spinner) ((View) mView).findViewById(R.id.spinner1);
                adapter = new ArrayAdapter<String>(ActivityFragmentOrigin.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.sizeList));
                mSpinner.setAdapter(adapter);

                mSpinner2 = (Spinner) ((View) mView).findViewById(R.id.spinner2);
                adapter2 = new ArrayAdapter<String>(ActivityFragmentOrigin.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.bedList));
                mSpinner2.setAdapter(adapter2);

                mSpinner3 = (Spinner) ((View) mView).findViewById(R.id.spinner3);
                adapter3 = new ArrayAdapter<String>(ActivityFragmentOrigin.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.bathroomList));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner3.setAdapter(adapter3);

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("alpha…")){

                            Toast.makeText(ActivityFragmentOrigin.this,
                                    mSpinner.getSelectedItem().toString(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();

                return true;
            case R.id.action_dropdown_petsallowed:
                mBuilder = new AlertDialog.Builder(ActivityFragmentOrigin.this);
                mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                mBuilder.setTitle("Set Pets Filter");
                mSpinner = (Spinner) ((View) mView).findViewById(R.id.spinner);
                adapter = new ArrayAdapter<String>(ActivityFragmentOrigin.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.petsList));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("alpha…")){

                            Toast.makeText(ActivityFragmentOrigin.this,
                                    mSpinner.getSelectedItem().toString(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();

                return true;
            case R.id.action_dropdown_vacancies:
                mBuilder = new AlertDialog.Builder(ActivityFragmentOrigin.this);
                mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                mBuilder.setTitle("Set Vacancies Filter");
                mSpinner = (Spinner) ((View) mView).findViewById(R.id.spinner);
                adapter = new ArrayAdapter<String>(ActivityFragmentOrigin.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.vacanciesList));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("alpha…")){

                            Toast.makeText(ActivityFragmentOrigin.this,
                                    mSpinner.getSelectedItem().toString(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();

                return true;
            case R.id.action_dropdown_utilities:
                final String[] values = {" First Item "," Second Item "," Third Item "," Fourth Item ", " Fifth Item", "Sixth Item", "Seventh Item"};
                final ArrayList itemsSelected = new ArrayList();
                mBuilder = new AlertDialog.Builder(ActivityFragmentOrigin.this);
                mBuilder.setTitle("Select Your Choice");
                mBuilder.setMultiChoiceItems(values, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedItemId,
                                                boolean isSelected) {
                                if (isSelected) {
                                    itemsSelected.add(selectedItemId);
                                } else if (itemsSelected.contains(selectedItemId)) {
                                    itemsSelected.remove(Integer.valueOf(selectedItemId));
                                }
                            }
                        })
                        .setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //Your logic when OK button is clicked
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                dialog = mBuilder.create();
                dialog.show();

                return true;
            case R.id.action_dropdown_lease:
                mBuilder = new AlertDialog.Builder(ActivityFragmentOrigin.this);
                mView = getLayoutInflater().inflate(R.layout.dialog_doublespinner, null);
                mBuilder.setTitle("Set Lease Duration (MIN/MAX) Filter");
                mSpinner = (Spinner) ((View) mView).findViewById(R.id.spinner1);
                adapter = new ArrayAdapter<String>(ActivityFragmentOrigin.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.durationList));
                mSpinner.setAdapter(adapter);

                mSpinner2 = (Spinner) ((View) mView).findViewById(R.id.spinner2);
                adapter2 = new ArrayAdapter<String>(ActivityFragmentOrigin.this, android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.durationList));
                mSpinner2.setAdapter(adapter2);

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("alpha…")){

                            Toast.makeText(ActivityFragmentOrigin.this,
                                    mSpinner.getSelectedItem().toString(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!active.equals(fragment2)) {
            menu.removeItem(R.id.action_filters);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}


