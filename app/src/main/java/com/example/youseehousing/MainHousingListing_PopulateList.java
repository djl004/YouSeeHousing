package com.example.youseehousing;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class MainHousingListing_PopulateList {

    /**
     * An array of listing thumbnails
     */
    public static final List<ListingDetails> ITEMS = new ArrayList<ListingDetails>();

    /**
     * A map of  listing details items, by ID.
     */
    public static final Map<String, ListingDetails> ITEM_MAP = new HashMap<String, ListingDetails>();

    // Max listings to add to page. This can be the number of items from the database that fit
    // the search query for example.
    private static final int COUNT = 20;

    // You can add listings to the list here
    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createListingDetails(i));
        }
    }

    private static void addItem(ListingDetails item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    // Create ListingDetails with details here
    //      id, title, details, description
    private static ListingDetails createListingDetails(int position) {
        return new ListingDetails (String.valueOf(position),
                              "Listing " + position,
                            "Item " + position,
                                    makeCaption(position),
                         "This is the description of listing " + position + "."
                                    );
    }

    /**
     * @param position : The array index corresponds to the position of the listing thumbnail
     *                     in the list
     *  TODO: Determine what goes in the caption
     *                 Maybe something like: "$2152/mo 2BR/2BA"
     */
    private static String makeCaption(int position) {
        StringBuilder builder = new StringBuilder();

        // randomly generated rent and bedrooms for fun
        // the rent's too damn high!
        DecimalFormat df = new DecimalFormat("0.00");
        double rent = (Math.random()*2000.0)+1000.00;
        int bedroom_count = (int) (Math.random()*5+1);
        int bathroom_count = (int) (Math.random()*4+1);

        builder.append("$").append(df.format(rent)).append("/mo.  "); // Rent
        builder.append(bedroom_count).append("BR/"); // Bedrooms
        builder.append(bathroom_count).append("BA"); // Bedrooms

        return builder.toString();
    }

    /**
     * A list item representing a piece of content.
     * This class implements Parcelable so it can pass its data to the MainListingPage activity.
     * TODO: This definitely deserves its own class where it dynamically pulls content from the database
     *        based on its id and populates these parameters with that information.
     */

    public static class ListingDetails implements Parcelable {
        public final String id;
        public final String title;
        public final String content;
        public final String caption;
        public final String description;

        public ListingDetails(String id, String title, String content,
                              String caption, String description) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.caption = caption;
            this.description = description;
        }

        protected ListingDetails(Parcel in) {
            id = in.readString();
            title = in.readString();
            content = in.readString();
            caption = in.readString();
            description = in.readString();
        }

        @Override
        public String toString() {
            return content;
        }

        // Begin implemented Parcelable methods

        public static final Creator<ListingDetails> CREATOR = new Creator<ListingDetails>() {
            @Override
            public ListingDetails createFromParcel(Parcel in) {
                return new ListingDetails(in);
            }

            @Override
            public ListingDetails[] newArray(int size) {
                return new ListingDetails[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(title);
            dest.writeString(content);
            dest.writeString(caption);
            dest.writeString(description);
        }

        // End implemented Parcelable methods

    }
}
