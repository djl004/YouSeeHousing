package com.example.youseehousing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.youseehousing.ListPageFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * This class handles populating and displaying the list of housing in MainHousingListing.
 * Note: Class ListingDetailsViewHolder is what sets the text of the listing thumbnail.
 *
 * Template Description:
 * {@link RecyclerView.Adapter} that can display a {@link ListingDetails} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class GenericItemRecyclerViewAdapter extends RecyclerView.Adapter<GenericItemRecyclerViewAdapter.ViewHolder> {

    private final List<RecyclerViewListItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final String TAG = "ItemRecyclerViewAdapter";

    public GenericItemRecyclerViewAdapter(List<RecyclerViewListItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public List<RecyclerViewListItem> getList() {
        return mValues;
    }

    public OnListFragmentInteractionListener getmListener() {
        return mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_item, parent, false);
        return new ViewHolder(view);

    }

    /**
     * Sets the text and image in the listing item on the main listing page.
     *
     * @param holder: the listing thumbnail object
     * @param position: the position in the array
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

//    private void setThumbnailImage(ListingDetailsViewHolder holder) {
//        //        String imageURL = "https://i.imgur.com/vrmywgE.jpg";
//        ListingDetails item = holder.mItem;
//        String imageURL = getImageURL(item, 0);
//        if (!imageURL.isEmpty()) {
//            Log.i(TAG, "Loading image " + imageURL);
//            Picasso.get().load(imageURL).resize(holder.mThumbnailView.getWidth(),500).centerCrop().into(holder.mThumbnailView);
//        }
//    }

    @Override
    public int getItemCount() {
        return getmValues().size();
    }

    public List<RecyclerViewListItem> getmValues() {
        return mValues;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        // TODO: Parameters for listing thumbnail here
        public ViewHolder(View view) {
            super(view);
            mView = view;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
