package com.example.youseehousing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
    private LinearLayout imageRecyclerView;

    private ListingDetails item;
    private ImageRecyclerFragment imageRecyclerFragment;
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
        imageRecyclerView = (LinearLayout) rootView.findViewById(R.id.listing_imageLayout);

        // Add fragment to page

        imageRecyclerFragment = (ImageRecyclerFragment) getChildFragmentManager().findFragmentById(R.id.image_recycler_fragment);

//        Bundle bundle = new Bundle();
//        bundle.putParcelable("Images", item);
//        fm.beginTransaction().add(R.id.listing_imageLayout, imageRecyclerFragment, "1");


        // Get data passed from previous activity
        // Check if members are null when setting parameters

        if (getArguments() != null) {
            try {
                item = (ListingDetails) getArguments().get("ListingDetails");
                imageRecyclerList = new ImageRecycler_PopulateList(item, imageRecyclerFragment);
                setText();
                setImages();
            }
            catch (ClassCastException e) {
                e.printStackTrace();
            }
        }

        return rootView;
    }

    private void setImages() {

    }

    private void setText() {
        // Set parameters
        try {
            String image_0 = item.getPictures().get(0);
            if(image_0.length() > 0) {
                Picasso.get().load(item.getPictures().get(0)).into(imagesView);
            }
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
