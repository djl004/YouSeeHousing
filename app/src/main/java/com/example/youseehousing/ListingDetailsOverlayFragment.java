package com.example.youseehousing;

import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ListingDetailsOverlayFragment extends Fragment {

    private ImageView imagesView;
    private TextView addressView;
    private TextView captionView;
    private TextView descriptionView;
    private ListingDetails item;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_compare_overlay, container, false);

        imagesView = (ImageView) rootView.findViewById(R.id.image);
        addressView = (TextView) rootView.findViewById(R.id.title);
        captionView = (TextView) rootView.findViewById(R.id.caption);
        descriptionView = (TextView) rootView.findViewById(R.id.description);
        // Get data passed from previous activity
        // Check if members are null when setting parameters

        if (getArguments() != null) {
            try {
                item = (ListingDetails) getArguments().get("ListingDetails");
                setText();
            }
            catch (ClassCastException e) {
                e.printStackTrace();
            }
        }

        return rootView;
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
}
