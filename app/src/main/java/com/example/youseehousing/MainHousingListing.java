package com.example.youseehousing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.youseehousing.dummy.DummyContent.DummyItem;

public class MainHousingListing extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);

    }

    // TODO: Implement what happens when a listing is selected
    public void onListFragmentInteraction(DummyItem item) {
        // TODO: Switching to an activity from a fragment doesn't work this way
        selectListingFxn();
    }
    private void selectListingFxn() {
        try {
            Intent intent_f = new Intent(getApplicationContext(), MainListingPage.class);
            startActivity(intent_f);
        }
        catch (android.content.ActivityNotFoundException e) {
            e.printStackTrace();
            Log.e("exception", "exception: "+e);
        }

    }
}
