package com.example.youseehousing;

import android.os.Bundle;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

public class ImageRecyclerFragment extends ListPageFragment {

    private ListType type = ListPageFragment.ListType.IMAGE_RECYCLER;
    private ListingDetails item;

    // Number of images
    private int columnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private Bundle savedInstanceState;
    private View createdView;
    private RecyclerView recyclerView;
    private SnapHelper snapHelper;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public ImageRecyclerFragment() {
        super();
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(getRecyclerView());
    }

    @Override
    public ListType getListType() {
        return type;
    }

    @Override
    public int getmColumnCount() {
        return columnCount;
    }

}
