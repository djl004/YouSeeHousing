package com.example.youseehousing;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.youseehousing.ListPageFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles scrollable ImageViews.
 * Note: Class ImageViewHolder is what sets the text of the listing thumbnail.
 *
 * Template Description:
 * {@link RecyclerView.Adapter} that can display a {@link ListingDetails} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageViewHolder> {

    private final String TAG = "ItemRecyclerViewAdapter";
    private List<ListingImage> IMAGES = new ArrayList<ListingImage>();
    private ImageRecycler_PopulateList populateList;

    public ImageRecyclerViewAdapter(ListingDetails item) {
        //TODO: Initialize IMAGES
        populateList = new ImageRecycler_PopulateList(this, item);
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    @Override
    public ImageRecyclerViewAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView view = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);
        ImageViewHolder vh = new ImageViewHolder(view);
        return vh;
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     *
     * @param holder: the image object
     * @param position: the position in the array
     */
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ListingImage.loadImage(IMAGES.get(position), holder.mImageView);
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    @Override
    public int getItemCount() {
        return getImageList().size();
    }

    public List<ListingImage> getImageList() {
        return IMAGES;
    }

    /**
     * Provide a reference to the views for each data item
     */
    public class ImageViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
        public final ImageView mImageView; // mImageView: image

        // TODO: Parameters for listing thumbnail here
        public ImageViewHolder(ImageView view) {
            super(view);
            mImageView = view;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
