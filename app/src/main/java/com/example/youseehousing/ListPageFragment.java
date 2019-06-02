package com.example.youseehousing;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListPageFragment extends RefreshableListFragmentPage {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    private final String TAG = "MainListingPageFragment";

    private ListType type = ListType.MAIN_LISTING_PAGE;

    public OnListFragmentInteractionListener getmListener() {
        return mListener;
    }

    public void setmListener(OnListFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    // The types of list
    public enum ListType {
        FAVORITES, MAIN_LISTING_PAGE, IMAGE_RECYCLER
    }

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private Bundle savedInstanceState;
    private View createdView;
    private RecyclerView recyclerView;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MainListingPageFragment newInstance(int columnCount) {
        MainListingPageFragment fragment = new MainListingPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            setmColumnCount(getArguments().getInt(ARG_COLUMN_COUNT));
        }
        this.savedInstanceState = savedInstanceState;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createdView = inflater.inflate(R.layout.fragment_list_page, container, false);

        // Set the adapter
        if (createdView instanceof RecyclerView) {
            Context context = createdView.getContext();
            recyclerView = (RecyclerView) createdView;
            if (getmColumnCount() <= 1) {
                recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, getmColumnCount()));
            }
            // TODO: Listings are inserted into the display list here

            // Create a new list page type based on the ListPage.ListType this object has
            switch(this.getListType()) {
                case MAIN_LISTING_PAGE:
                    recyclerView.setAdapter
                            (new MyItemRecyclerViewAdapter(MainHousingListing_PopulateList.ITEMS, getmListener()));
                    break;

                case FAVORITES:
                    recyclerView.setAdapter
                            (new MyItemRecyclerViewAdapter(Favorites_PopulateList.ITEMS, getmListener()));
                    break;

                case IMAGE_RECYCLER:
                default: throw new TypeNotPresentException("Invalid List Type", new Throwable());
            }
        }
        return createdView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            setmListener((OnListFragmentInteractionListener) context);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setmListener(null);
    }

    @Override
    public void refreshPage() {
        if (getActivity() != null) {
            new Thread() {
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "Redrawing " + getListType() + "!!!");
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    });
                }
            }.start();
        }
    }

    @Override
    public ListType getListType() {
        return type;
    }

    public int getmColumnCount() {
        return mColumnCount;
    }

    public void setmColumnCount(int mColumnCount) {
        this.mColumnCount = mColumnCount;
    }

    /**
     * Shows an empty text notice if no results were found.
     */
    public void showEmptyNotice() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ListingDetails item);
    }

}
