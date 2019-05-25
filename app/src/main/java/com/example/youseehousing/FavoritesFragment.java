package com.example.youseehousing;


import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends ListPageFragment {

    private ListType type = ListPageFragment.ListType.FAVORITES;

    public FavoritesFragment() {
        super();
    }

    @Override
    public ListType getListType() {
        return type;
    }
}
