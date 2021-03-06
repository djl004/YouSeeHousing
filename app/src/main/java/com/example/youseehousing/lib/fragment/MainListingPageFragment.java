package com.example.youseehousing.lib.fragment;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MainListingPageFragment extends ListPageFragment {

    private ListType type = ListPageFragment.ListType.MAIN_LISTING_PAGE;

    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MainListingPageFragment() {
        super();
    }

    @Override
    public ListType getListType() {
        return type;
    }
}
