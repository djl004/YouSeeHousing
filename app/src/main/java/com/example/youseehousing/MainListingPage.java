package com.example.youseehousing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

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
public class MainListingPage extends AppCompatActivity {

    private ImageView imagesView;
    private TextView titleView;
    private TextView descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        imagesView = (ImageView) findViewById(R.id.image);
        titleView = (TextView) findViewById(R.id.title);
        descriptionView = (TextView) findViewById(R.id.description);
    }


}
