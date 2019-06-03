package com.example.youseehousing.lib.fragment;

import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.youseehousing.lib.ui.ImageRecyclerViewAdapter;
import com.example.youseehousing.lib.listing.ListingButtonActions;
import com.example.youseehousing.lib.listing.ListingDetails;
import com.example.youseehousing.R;
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

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

/**
 * This class is the fragment version of the listing details page.
 */
public class ListingDetailsOverlayFragment extends Fragment {

    private final String TAG = "ListingDetailsFrag";
    private ActivityFragmentOrigin parentActivity;
    private ImageView imagesView;
    private TextView addressView;
    private TextView priceView;
    private TextView dimensionsView;
    private TextView petsView;
    private TextView bedsView;
    private TextView bathsView;
    private TextView parkingView;
    private TextView furnishedView;
    private TextView buildingLeaseView;
    private TextView contactInfoView;
    private TextView descriptionView;
    private Button btnCompare;
    private Button btnFavorite;
    private Button btnContact;
    private Button btnMap;
    private LinearLayout buttonsLayoutView;
    private LinearLayout mapLayoutView;
    private LinearLayout imageLayoutView;
    private LinearLayout descriptionLayoutView;
    private RecyclerView imageRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ListingDetails item;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private Mapbox mapboxInstance;
    private double longitude, latitude;



    private final FragmentManager fm = getFragmentManager();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapboxInstance = Mapbox.getInstance(getContext(),
                "pk.eyJ1IjoicW93bnNnbWw5MyIsImEiOiJjanZ5emNrMmwwYWo5NDh0cm56bnpsNG1pIn0.JxZmBYwkXwMLb2w7ZdjQIQ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_listing, container, false);

        // deprecated image field
        imagesView = (ImageView) rootView.findViewById(R.id.image);

        // text fields
        addressView = (TextView) rootView.findViewById(R.id.listing_address);
        priceView = (TextView) rootView.findViewById(R.id.listing_price);
        dimensionsView = (TextView) rootView.findViewById(R.id.listing_dimensions);
        petsView = (TextView) rootView.findViewById(R.id.listing_pets);
        bedsView = (TextView) rootView.findViewById(R.id.listing_beds);
        bathsView = (TextView) rootView.findViewById(R.id.listing_baths);
        parkingView = (TextView) rootView.findViewById(R.id.listing_parking);
        furnishedView = (TextView) rootView.findViewById(R.id.listing_furnished);
        buildingLeaseView = (TextView) rootView.findViewById(R.id.listing_buildingLease);
        contactInfoView = (TextView) rootView.findViewById(R.id.listing_contactInfo);
        descriptionView = (TextView) rootView.findViewById(R.id.listing_description);

        // buttons
        btnCompare = (Button) rootView.findViewById(R.id.btnCompare);
        btnContact = (Button) rootView.findViewById(R.id.btnContactInfo);
        btnFavorite = (Button) rootView.findViewById(R.id.btnFavorite);
        btnMap = (Button) rootView.findViewById(R.id.btnMap);

        // hideable layouts
        buttonsLayoutView = (LinearLayout) rootView.findViewById(R.id.listing_buttonsLayout);
        mapLayoutView = (LinearLayout) rootView.findViewById(R.id.listing_mapLayout);
        imageLayoutView = (LinearLayout) rootView.findViewById(R.id.listing_imageLayout);
        descriptionLayoutView = (LinearLayout) rootView.findViewById(R.id.listing_descriptionLayout);

        // swipeable images
        imageRecyclerView = (RecyclerView) rootView.findViewById(R.id.image_recycler_view);

        // map
        mapView = (MapView) rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        try {
            parentActivity = (ActivityFragmentOrigin) getActivity();
        }
        catch (ClassCastException e) {
            throw e;
        }


        // Take the arguments passed to this from ActivityFragmentOrigin and set up the
        // details based on that ListingDetails object
        if (getArguments() != null) {
            try {
                ListingDetails visibility = (ListingDetails) getArguments().get(ActivityFragmentOrigin.BUNDLE_VISIBILITY);
                if (visibility != null) {
                    hideDetailsForCompare(this, true);
                }
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                item = (ListingDetails) getArguments().get("ListingDetails");
                setupRecyclerView(rootView);
                setText();
                mapSetup(item.getAddress());
                setupButtons();
            }
            catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return rootView;
    }

    private void setupButtons() {
        // Favorite button
        handleButtonPress(btnCompare);
        handleButtonPress(btnFavorite);
        handleButtonPress(btnContact);
        handleButtonPress(btnMap);
    }

    /**
     * Handles button actions
     * @param btn
     */
    private void handleButtonPress(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Compare button
                if(btn == btnCompare) {
                    ListingButtonActions.compare(parentActivity, item);
                }
                // Contact button
                if(btn == btnContact) {
                    ListingButtonActions.openContactInfo(item);
                }
                // Favorite button
                if(btn == btnFavorite) {
                    ListingButtonActions.addToFavorites(item);
                }
                // Favorite button
                if(btn == btnMap) {
                    ListingButtonActions.openMap(item);
                }
            }
        });
    }

    /**
     * Set parameters in RecyclerView
     * @param rootView
     */
    private void setupRecyclerView(View rootView) {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        imageRecyclerView.setLayoutManager(layoutManager);

        adapter = new ImageRecyclerViewAdapter(item);
        imageRecyclerView.setAdapter(adapter);

        // Sticky images (stays at center)
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(imageRecyclerView);

        imageRecyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * Sets descriptive text in the listing page.
     */
    private void setText() {
        try {
            addressView.setText(item.getAddress());
            priceView.setText(item.getPrice());
            descriptionView.setText(item.getDesc());
            dimensionsView.setText(item.getDim());
            petsView.setText(item.getPet());
            bedsView.setText(item.getBed());
            bathsView.setText(item.getBath());
            parkingView.setText(item.getParking());
//            furnishedView.setText(item.); // TODO: I forgot to add furnished...
            buildingLeaseView.setText(item.getBuildingLease());
            contactInfoView.setText(item.getContact());
        }
        catch (NullPointerException e1) {
            // Nothing was passed from previous activity
            e1.printStackTrace();
            Log.e("exception", "exception: no parcel was passed from previous activity!"
                    + e1);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Effectively "refreshes" the fragment
     */
    public void refresh() {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    /**
     * Hides information for the compare functionality to work better
     * @param fragment
     * @param visible
     */
    private static void hideDetailsForCompare(final ListingDetailsOverlayFragment fragment, final boolean visible) {
                if (visible) {
                    fragment.buttonsLayoutView.setVisibility(View.VISIBLE);
                    fragment.imageLayoutView.setVisibility(View.VISIBLE);
                    fragment.mapLayoutView.setVisibility(View.VISIBLE);
                    fragment.descriptionLayoutView.setVisibility(View.VISIBLE);
                }
                else {
                    fragment.buttonsLayoutView.setVisibility(View.GONE);
                    fragment.imageLayoutView.setVisibility(View.GONE);
                    fragment.mapLayoutView.setVisibility(View.GONE);
                    fragment.descriptionLayoutView.setVisibility(View.GONE);
                }
    }

    private static void hidePictures(final ListingDetailsOverlayFragment fragment, final boolean visible) {
        if (visible) {
//            fragment.imagesView.setVisibility(View.VISIBLE);
            fragment.imageLayoutView.setVisibility(View.VISIBLE);
//            fragment.imageRecyclerView.setVisibility(View.VISIBLE);
        }
        else {
//            fragment.imagesView.setVisibility(View.GONE);
            fragment.imageLayoutView.setVisibility(View.GONE);
//            fragment.imageRecyclerView.setVisibility(View.GONE);
        }
    }

    private static void hideMap(final ListingDetailsOverlayFragment fragment, final boolean visible) {
        if (visible) {
            fragment.mapLayoutView.setVisibility(View.VISIBLE);
        }
        else {
            fragment.mapLayoutView.setVisibility(View.GONE);
        }
    }

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
                ListingDetailsOverlayFragment.this.mapboxMap = mapboxArg;

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
                                        ListingDetailsOverlayFragment.this.getResources(), R.drawable.mapbox_marker_icon_default));

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
}
