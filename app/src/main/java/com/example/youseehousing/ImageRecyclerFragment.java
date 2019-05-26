package com.example.youseehousing;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/***
 * DEPRECATED!!!DEPRECATED!!!DEPRECATED!!!DEPRECATED!!!DEPRECATED!!!DEPRECATED!!!
 */
public class ImageRecyclerFragment extends ListPageFragment {

    private ListType type = ListPageFragment.ListType.IMAGE_RECYCLER;
    private ListingDetails item;

    // Number of images
    private int columnCount = 1;
    private View createdView;
    private SnapHelper snapHelper;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public ImageRecyclerFragment() {
        super();
    }

    @Override
    public ListType getListType() {
        return type;
    }

    @Override
    public int getmColumnCount() {
        return columnCount;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        createdView = inflater.inflate(R.layout.fragment_image_recycler, container, false);
//        if (createdView instanceof RecyclerView && createdView != null) {
//            Context context = createdView.getContext();
//            setRecyclerView((RecyclerView) createdView);
////            if (getmColumnCount() <= 1) {
////                getRecyclerView().setLayoutManager(new WrapContentLinearLayoutManager(context));
////            } else {
////                getRecyclerView().setLayoutManager(new GridLayoutManager(context, getmColumnCount()));
////            }
////             TODO: Listings are inserted into the display list here
//
////            // Create a new list page type based on the ListPage.ListType this object has
////            switch(this.getListType()) {
////                case MAIN_LISTING_PAGE:
////                    recyclerView.setAdapter
////                            (new MyItemRecyclerViewAdapter(MainHousingListing_PopulateList.ITEMS, mListener));
////                    break;
////
////                case FAVORITES:
////                    recyclerView.setAdapter
////                            (new MyItemRecyclerViewAdapter(Favorites_PopulateList.ITEMS, mListener));
////                    break;
////
////                case IMAGE_RECYCLER:
////            getRecyclerView().setAdapter(
////                            (new ImageRecyclerViewAdapter(ImageRecycler_PopulateList.ITEMS, getmListener())));
//
//                    // Code for sticky images (snaps to center of screen)
//                    WrapContentLinearLayoutManager layout = new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
//                    getRecyclerView().setLayoutManager(layout);
////                    getRecyclerView().setHasFixedSize(true);
//                    SnapHelper helper = new LinearSnapHelper();
//                    helper.attachToRecyclerView(getRecyclerView());
//
//
////                    break;
//
////                default: throw new TypeNotPresentException("Invalid List Type", new Throwable());
//            }
////        }
        return createdView;
    }
}
