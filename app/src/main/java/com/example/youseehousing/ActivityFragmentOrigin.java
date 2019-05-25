package com.example.youseehousing;

import android.content.DialogInterface;
import android.content.Intent;
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
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
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

        fm.beginTransaction().add(R.id.frameLayout, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.frameLayout, fragment1, "1").hide(fragment1).commit();
        fm.beginTransaction().add(R.id.frameLayout,fragment2, "2").commit();


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


