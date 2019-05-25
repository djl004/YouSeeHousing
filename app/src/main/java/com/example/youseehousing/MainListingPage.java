package com.example.youseehousing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * This is the page that has a detailed description of a listing.
 * There will be a swipeable image list, title and description, and buttons
 * Buttons: Add to favorites, compare listings, get contact info, website
 *
 * For the this page to communicate with the previous activity, either:
 * this activity can connect to the database and grab the information again
 * or
 * the previous activity can pass the information to populate this page to this page
 *
 * Related links:
 * http://sohailaziz05.blogspot.com/2012/04/passing-custom-objects-between-android.html
 * https://www.techjini.com/blog/passing-objects-via-intent-in-android/
 *
 */
public class MainListingPage extends Activity {

    private ImageView imagesView;
    private TextView addressView;
    private TextView captionView;
    private TextView descriptionView;
    private ListingDetails listing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        imagesView = (ImageView) findViewById(R.id.image);
        addressView = (TextView) findViewById(R.id.title);
        captionView = (TextView) findViewById(R.id.caption);
        descriptionView = (TextView) findViewById(R.id.description);

        // Get data passed from previous activity
        // Check if members are null when setting parameters
        listing = (ListingDetails)
                getIntent().getParcelableExtra("parcel_data");

        // Set parameters
        try {
            String image_0 = listing.getPictures().get(0);
            if(image_0.length() > 0) {
                Picasso.get().load(listing.getPictures().get(0)).into(imagesView);
            }
            addressView.setText(listing.getAddress());
            captionView.setText(listing.getBath() + " " + listing.getBed());
            descriptionView.setText(listing.getDesc());
        }
        catch (NullPointerException e1) {
            // Nothing was passed from previous activity
            e1.printStackTrace();
            Log.e("exception", "exception: no parcel was passed from previous activity!"
                                            + e1);
        }
    }


}
