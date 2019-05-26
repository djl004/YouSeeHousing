package com.example.youseehousing;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youseehousing.ListPageFragment.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.youseehousing.ListingDetails.getImageURL;

/**
 * This class handles scrollable ImageViews.
 * Note: Class ImageViewHolder is what sets the text of the listing thumbnail.
 *
 * Template Description:
 * {@link RecyclerView.Adapter} that can display a {@link ListingDetails} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ImageRecyclerViewAdapter extends GenericItemRecyclerViewAdapter {

    private final String TAG = "ItemRecyclerViewAdapter";

    public ImageRecyclerViewAdapter(List<RecyclerViewListItem> items, OnListFragmentInteractionListener listener) {
        super(items, listener);
    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_image_item, parent, false);
        return new ImageViewHolder(view);
    }


    /**
     * Sets the text and image in the listing item on the main listing page.
     *
     * @param holder: the listing thumbnail object
     * @param position: the position in the array
     */
    public void onBindViewHolder(final GenericItemRecyclerViewAdapter.ViewHolder holder, int position) {
        ImageViewHolder new_holder = (ImageViewHolder) holder;
        List<RecyclerViewListItem> list = getList();

        // TODO: Figure out why non ImageViewHolders get added to the list
        try {
            new_holder.mItem = (ListingImage) list.get(position);
        }
        catch( ClassCastException e ) {
            e.printStackTrace();
            return;
        }

        setImage(new_holder); // Set image

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != getmListener()) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    getmListener().onListFragmentInteraction(new_holder.mItem);
                }
            }
        });
    }



    private void setImage(ImageViewHolder holder) {
        if (holder != null) {
            String imageURL = holder.mItem.getImageURL();
            if (imageURL != null) {
                Log.i(TAG, "Loading image " + imageURL);
                Picasso.get().load(imageURL).resize(holder.mThumbnailView.getWidth(), 500).centerCrop().into(holder.mThumbnailView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    public class ImageViewHolder extends GenericItemRecyclerViewAdapter.ViewHolder {
//        public final View mView;
        public final ImageView mThumbnailView; // mThumbnailView: image
        public ListingImage mItem;

        // TODO: Parameters for listing thumbnail here
        public ImageViewHolder(View view) {
            super(view);
            mThumbnailView = (ImageView) view.findViewById(R.id.card_image);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
