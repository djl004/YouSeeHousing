package com.example.youseehousing;

import android.os.Parcel;

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
}
