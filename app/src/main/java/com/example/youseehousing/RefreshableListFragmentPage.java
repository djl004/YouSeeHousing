package com.example.youseehousing;

import android.support.v4.app.Fragment;

public abstract class RefreshableListFragmentPage extends Fragment {

    abstract public void refreshPage();

    abstract public ListPageFragment.ListType getListType();
}
