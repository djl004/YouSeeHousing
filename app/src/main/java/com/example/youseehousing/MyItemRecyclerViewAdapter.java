package com.example.youseehousing;

import android.support.v7.widget.RecyclerView;
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
 * This class handles populating and displaying the list of housing in MainHousingListing.
 * Note: Class ViewHolder is what sets the text of the listing thumbnail.
 *
 * Template Description:
 * {@link RecyclerView.Adapter} that can display a {@link ListingDetails} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<ListingDetails> mValues;
    private final ListPageFragment.OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<ListingDetails> items, ListPageFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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
        holder.mItem = mValues.get(position);

        setThumbnailImage(holder); // Set image
        holder.mTitleView.setText(mValues.get(position).getAddress()); // Set title "100 Addr Pl."
        holder.mDetailsView.setText(mValues.get(position).getPrice()); // Set detail text "$1,959"

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    private void setThumbnailImage(ViewHolder holder) {
        //        String imageURL = "https://i.imgur.com/vrmywgE.jpg";
        ListingDetails item = holder.mItem;
        String imageURL = getImageURL(item, 0);
        if (!imageURL.isEmpty()) {
            Picasso.get().load(imageURL).resize(holder.mThumbnailView.getWidth(),500).centerCrop().into(holder.mThumbnailView);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageView mThumbnailView;
        public final TextView mTitleView;
        public final TextView mDetailsView;
        public ListingDetails mItem;

        /**
         * @param mIdView: unseen id # of item
         * @param mThumbnailView: image
         * @param mTitleView: title of listing thumbnail
         * @param mDetailsView: caption of listing thumbnail
         *
         */
        // TODO: Parameters for listing thumbnail here
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mThumbnailView = (ImageView) view.findViewById(R.id.thumbnail);
            mTitleView = (TextView) view.findViewById(R.id.title);
            mDetailsView = (TextView) view.findViewById(R.id.details);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
