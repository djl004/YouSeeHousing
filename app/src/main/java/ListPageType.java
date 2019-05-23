import com.example.youseehousing.ItemFragment;

public abstract class ListPageType implements ItemFragment.OnListFragmentInteractionListener{
     // The types of list
     public enum ListType {
         FAVORITES, MAINLISTINGPAGE
     }

    public abstract void onListFragmentInteraction();

}
