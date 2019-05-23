package com.example.youseehousing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class RefreshableListFragmentPage extends Fragment {

    abstract public void refreshPage();

    abstract public ListPage.ListType getListType();
}
