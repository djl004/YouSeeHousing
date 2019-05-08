package com.example.youseehousing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.youseehousing.dummy.DummyContent.DummyItem;

public class MainHousingListing extends FragmentActivity implements ItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);

    }

    // TODO: Implement what happens when a listing is selected
    public void onListFragmentInteraction(DummyItem item) {
        // TODO: Switching to an activity from a fragment doesn't work this way
//        selectListingFxn();
    }
    private void selectListingFxn() {
        Intent intent_f = new Intent(MainHousingListing.this, MainListingPage.class);
        startActivity(intent_f);
    }
}
