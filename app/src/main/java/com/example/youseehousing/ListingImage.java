package com.example.youseehousing;

import android.os.Parcel;
import android.provider.ContactsContract;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListingImage extends RecyclerViewListItem {
    private String imageURL;

    public ListingImage() {}

    public ListingImage(String imageURL) {
        setImageURL(imageURL);
    }

    protected ListingImage(Parcel in) {
      this.imageURL = in.readString();
    }

    // Begin Parcelable methods
    public static final Creator<ListingImage> CREATOR = new Creator<ListingImage>() {
        @Override
        public ListingImage createFromParcel(Parcel in) {
            return new ListingImage(in);
        }

        @Override
        public ListingImage[] newArray(int size) {
            return new ListingImage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageURL);
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public static ListingImage makeListingImage(String imageURL) {
        return new ListingImage(imageURL);
    }

    /**
     * Given a list of RecyclerViewListItems, returns only the ListingImages
     * @param list
     * @return
     */
    public static ArrayList<ListingImage> getListingImages(List<RecyclerViewListItem> list) {
        ArrayList<ListingImage> outList = new ArrayList<ListingImage>();
        for (RecyclerViewListItem item : list) {
            if(item instanceof ListingImage) {
                outList.add((ListingImage) item);
            }
        }
        return outList;
    }

    public static void loadImage(ListingImage image, ImageView imageView) {
        String imageURL = image.getImageURL();
        // Load placeholder image if URL is bad
        if(imageURL.length() <= 0) {
            Picasso.get().load(R.drawable.placeholder).into(imageView);
        }
        else {
            Picasso.get().load(imageURL).error(R.drawable.placeholder).into(imageView);
        }
    }
}
