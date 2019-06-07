package com.example.youseehousing.lib.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.youseehousing.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityListPageFragment extends Fragment {


    public ActivityListPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainListingPageFragment fragment = new MainListingPageFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_holder, fragment).commit();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity_list_page, container, false);
    }

}
