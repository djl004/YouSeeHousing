package com.example.youseehousing;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class ActivityFragmentOrigin extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    private BottomNavigationView bottomNavigationView;

    final Fragment fragment1 = new UserPreferencesFragment();
//    final Fragment fragment2 = new ActivityListPageFragment();
    final Fragment fragment2 = new ItemFragment();
    final Fragment fragment3 = new FavoritesFragment();
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
                                fm.beginTransaction().hide(active).show(fragment2).commit();
                                active = fragment2;
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

    @Override
    public void onListFragmentInteraction(ListingDetails item) {

    }
}
