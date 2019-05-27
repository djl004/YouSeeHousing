package com.example.youseehousing;

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
import android.widget.TextView;

/**
 * This class is the fragment version of the listing details page.
 */
public class ListingDetailsOverlayFragment extends Fragment {

    private ActivityFragmentOrigin parentActivity;

    private ImageView imagesView;
    private TextView addressView;
    private TextView captionView;
    private TextView descriptionView;
    private Button btnCompare;
    private Button btnFavorite;
    private Button btnContact;
    private Button btnMap;
    private RecyclerView imageRecyclerView;


    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ListingDetails item;
    private final FragmentManager fm = getFragmentManager();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_single_overlay, container, false);

        imagesView = (ImageView) rootView.findViewById(R.id.image);
        addressView = (TextView) rootView.findViewById(R.id.title);
        captionView = (TextView) rootView.findViewById(R.id.caption);
        descriptionView = (TextView) rootView.findViewById(R.id.description);
        btnCompare = (Button) rootView.findViewById(R.id.btnCompare);
        btnContact = (Button) rootView.findViewById(R.id.btnContactInfo);
        btnFavorite = (Button) rootView.findViewById(R.id.btnFavorite);
        btnMap = (Button) rootView.findViewById(R.id.btnMap);
        imageRecyclerView = (RecyclerView) rootView.findViewById(R.id.image_recycler_view);

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
                item = (ListingDetails) getArguments().get("ListingDetails");
                setupRecyclerView(rootView);
                setText();
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
            captionView.setText(item.getBath() + " " + item.getBed());
            descriptionView.setText(item.getDesc());
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

    public void hideButtons(final boolean visible) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (visible) {
                    btnCompare.setVisibility(View.VISIBLE);
                }
                else {
                    btnCompare.setVisibility(View.GONE);
                }
                refresh();
            }
        });

    }
}
