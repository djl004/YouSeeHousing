package com.example.youseehousing;

import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.List;
import java.util.Locale;

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

    private static final String TAG = "MainListingPage";
    private ImageView imagesView;
    private TextView addressView;
    private TextView captionView;
    private TextView descriptionView;
    private ListingDetails listing;

    private MapView mapView;
    private MapboxMap mapboxMap;
    private Mapbox mapboxInstance;
    private double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapboxInstance = Mapbox.getInstance(this,
                "pk.eyJ1IjoicW93bnNnbWw5MyIsImEiOiJjanZ5emNrMmwwYWo5NDh0cm56bnpsNG1pIn0.JxZmBYwkXwMLb2w7ZdjQIQ");
        setContentView(R.layout.activity_listing);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        imagesView = (ImageView) findViewById(R.id.image);
        addressView = (TextView) findViewById(R.id.listing_address);
        captionView = (TextView) findViewById(R.id.listing_price);
        descriptionView = (TextView) findViewById(R.id.listing_description);

        // Get data passed from previous activity
        // Check if members are null when setting parameters
        listing = (ListingDetails)
                getIntent().getParcelableExtra("parcel_data");

        // Set parameters
        try {
            String image_0 = listing.getPictures().get(0);
            if(image_0.length() > 0) {
        //        Picasso.get().load(listing.getPictures().get(0)).into(imagesView);   // This throws IllegalArgumentException !!!
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


        /************************************* Map Feature ****************************************/


        String userAddress = listing.getAddress();
        Log.d(TAG, "User address: " + userAddress);

        /*
        tv_check_connection = findViewById(R.id.tv_check_connection);
        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();
*/
        // Convert user address to coordinates (latitude & longitude)
        mapSetup(userAddress);
    } // End of onCreate()

    private void mapSetup(String userAddress) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses;


        try {
            addresses = geocoder.getFromLocationName(userAddress, 1);   // This is causing the internet connection issue
            Address address = addresses.get(0);
            longitude = address.getLongitude();
            latitude = address.getLatitude();
        } catch (Exception e) {
            Log.e(TAG, "Exception thrown while converting address to coordinates: " + e.toString());
        }


        // Create the map
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxArg) {
                MainListingPage.this.mapboxMap = mapboxArg;

                // Setting camera to the user's address
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude)).build();
                try {
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 100);
                    Log.d(TAG, "Camera position changed to (lat: " + latitude + ", lng: " + longitude + ")");
                } catch (Exception e) {
                    Log.e(TAG, "Exception thrown while changing camera position: " + e.toString());
                }

                // Setting the map style
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {

                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                        // Adding a pin to map
                        style.addImage("marker-icon-id",
                                BitmapFactory.decodeResource(
                                        MainListingPage.this.getResources(), R.drawable.mapbox_marker_icon_default));

                        Log.d(TAG, "pin set at (lat: " + latitude + ", lng: " + longitude + ")");

                        GeoJsonSource geoJsonSource = new GeoJsonSource("source-id", Feature.fromGeometry(
                                Point.fromLngLat(longitude, latitude)));
                        style.addSource(geoJsonSource);

                        SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
                        symbolLayer.withProperties(
                                PropertyFactory.iconImage("marker-icon-id")
                        );
                        style.addLayer(symbolLayer);

                    } // End of onStyleLoaded()
                });
            } // End of onMapReady()
        }); // End of .getMapAsync()
    }


} // End of MainListingPage class
