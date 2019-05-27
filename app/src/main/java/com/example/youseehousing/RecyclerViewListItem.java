package com.example.youseehousing;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class RecyclerViewListItem implements Parcelable {

    // Parcelable methods
    abstract public int describeContents();
    abstract public void writeToParcel(Parcel dest, int flags);


}
