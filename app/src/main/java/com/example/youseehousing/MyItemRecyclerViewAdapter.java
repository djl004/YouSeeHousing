package com.example.youseehousing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youseehousing.ItemFragment.OnListFragmentInteractionListener;
import com.example.youseehousing.ListingDetails;

import java.util.List;

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
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<ListingDetails> items, OnListFragmentInteractionListener listener) {
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
     * Sets the text in the listing thumbnail.
     *
     * @param holder: the listing thumbnail object
     * @param position: the position in the array
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        // Set text here
//        holder.mIdView.setText(mValues.get(position).getId());
        holder.mTitleView.setText(mValues.get(position).getAddress());
        holder.mDetailsView.setText(mValues.get(position).getBath());

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

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mTitleView;
        public final TextView mDetailsView;
        public ListingDetails mItem;

        /**
         * @param mIdView: unseen id # of item
         * @param mTitleView: title of listing thumbnail
         * @param mDetailsView: caption of listing thumbnail
         *
         */
        // TODO: Parameters for listing thumbnail here
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mTitleView = (TextView) view.findViewById(R.id.title);
            mDetailsView = (TextView) view.findViewById(R.id.details);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
