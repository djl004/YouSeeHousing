package com.example.youseehousing;


import android.content.ClipData;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends ListPageFragment {

    private ListPage.ListType type = ListPage.ListType.FAVORITES;

    public FavoritesFragment() {
        super();
    }

    @Override
    public ListPage.ListType getListType() {
        return type;
    }



}
