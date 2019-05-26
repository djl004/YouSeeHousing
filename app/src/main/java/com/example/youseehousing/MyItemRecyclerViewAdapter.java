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
 * This class handles populating and displaying the listing details in MainHousingListing.
 * Note: Class ListingDetailsViewHolder is what sets the text of the listing thumbnail.
 *
 * Template Description:
 * {@link RecyclerView.Adapter} that can display a {@link ListingDetails} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends GenericItemRecyclerViewAdapter {

//    private final List<RecyclerViewListItem> mValues;
//    private final OnListFragmentInteractionListener mListener;
    private final String TAG = "ItemRecyclerViewAdapter";

    public MyItemRecyclerViewAdapter(List<RecyclerViewListItem> items, OnListFragmentInteractionListener listener) {
        super(items, listener);
    }

    @Override
    public ListingDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_item, parent, false);
        return new ListingDetailsViewHolder(view);

    }


    /**
     * Sets the text and image in the listing item on the main listing page.
     *
     * @param holder: the listing thumbnail object
     * @param position: the position in the array
     */
    public void onBindViewHolder(final GenericItemRecyclerViewAdapter.ViewHolder holder, int position) {
        final ListingDetailsViewHolder new_holder = (ListingDetailsViewHolder) holder;
        List<RecyclerViewListItem> list = getList();

        new_holder.mItem = (ListingDetails) list.get(position);

        setThumbnailImage(new_holder); // Set image
        new_holder.mTitleView.setText(new_holder.mItem.getAddress()); // Set title "100 Addr Pl."
        new_holder.mDetailsView.setText(new_holder.mItem.getPrice()); // Set detail text "$1,959"

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != getmListener()) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    getmListener().onListFragmentInteraction(new_holder.mItem);
                }
            }
        });
    }

    private void setThumbnailImage(ListingDetailsViewHolder holder) {
        //        String imageURL = "https://i.imgur.com/vrmywgE.jpg";
        ListingDetails item = holder.mItem;
        String imageURL = getImageURL(item, 0);
        if (!imageURL.isEmpty()) {
            Log.i(TAG, "Loading image " + imageURL);
            Picasso.get().load(imageURL).resize(holder.mThumbnailView.getWidth(),500).centerCrop().into(holder.mThumbnailView);
        }
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    public class ListingDetailsViewHolder extends GenericItemRecyclerViewAdapter.ViewHolder {
//        public final View mView;
        public final TextView mIdView; // unseen id # of item
        public final ImageView mThumbnailView; // mThumbnailView: image
        public final TextView mTitleView; // title of listing thumbnail
        public final TextView mDetailsView; // caption of listing thumbnail
        public ListingDetails mItem;

        // TODO: Parameters for listing thumbnail here
        public ListingDetailsViewHolder(View view) {
            super(view);
//            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mThumbnailView = (ImageView) view.findViewById(R.id.thumbnail);
            mTitleView = (TextView) view.findViewById(R.id.title);
            mDetailsView = (TextView) view.findViewById(R.id.thumbnail_caption);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
