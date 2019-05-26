package com.example.youseehousing;

import android.content.Context;
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

import com.squareup.picasso.Picasso;

/**
 * This class is the fragment version of the listing details page.
 */
public class ListingDetailsOverlayFragment extends Fragment {

    private ImageView imagesView;
    private TextView addressView;
    private TextView captionView;
    private TextView descriptionView;
    private Button buttonsView;

    private RecyclerView imageRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ListingDetails item;
//    private ImageRecyclerFragment imageRecyclerFragment;
    private ImageRecycler_PopulateList imageRecyclerList;
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
        buttonsView = (Button) rootView.findViewById(R.id.btnCompare);
        imageRecyclerView = (RecyclerView) rootView.findViewById(R.id.image_recycler_view);




        if (getArguments() != null) {
            try {
                item = (ListingDetails) getArguments().get("ListingDetails");
                setupRecyclerView(rootView);
                setText();
            }
            catch (ClassCastException e) {
                e.printStackTrace();
            }
        }

        return rootView;
    }

    /**
     * Set parameters in RecyclerView
     * @param rootView
     */
    private void setupRecyclerView(View rootView) {
        layoutManager = new LinearLayoutManager(getContext());
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

    public void refresh() {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    public void hideButtons(final boolean visible) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (visible) {
                    buttonsView.setVisibility(View.VISIBLE);
                }
                else {
                    buttonsView.setVisibility(View.GONE);
                }
                refresh();
            }
        });

    }
}
