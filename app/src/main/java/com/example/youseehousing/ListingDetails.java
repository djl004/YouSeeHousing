package com.example.youseehousing;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A list item representing a piece of content.
 * This class implements Parcelable so it can pass its data to the MainListingPage activity.
 * TODO: This definitely deserves its own class where it dynamically pulls content from the database
 *        based on its id and populates these parameters with that information.
 */

public class ListingDetails implements Parcelable {

    public String id;
    public String address;
    public String content;
    public String caption;
    public String description;

    public ListingDetails() {}

    public ListingDetails(String id, String address, String content,
                          String caption, String description) {
        this.id = id;
        this.address = address;
        this.content = content;
        this.caption = caption;
        this.description = description;
    }

    protected ListingDetails(Parcel in) {
        id = in.readString();
        address = in.readString();
        content = in.readString();
        caption = in.readString();
        description = in.readString();
    }


    //getters
    public String getId() {
        return this.id;
    }
    public String getAddress() {
        return this.address;
    }


    //setters
    public void setId(String id) {
        this.id = id;
    }
    public void setAddress(String address) {
        this.address = address;
    }


//        private void showData(DataSnapshot dataSnapshot) {
//            for(DataSnapshot ds : dataSnapshot.getChildren()){
//                Account uInfo = new Account();
//                // Log.d(TAG,"Before set name: " + ds.child(Uid).getValue(Account.class).getName());
//                uInfo.setName( ds.child(Uid).getValue(Account.class).getName()); //set the name
//                uInfo.setEmail(ds.child(Uid).getValue(Account.class).getEmail()); //set the email
//                Log.d(TAG, "name:" + ds.child(Uid).getValue());
//                //display all the information
//                Log.d(TAG, "showData: name: " + uInfo.getName());
//                Log.d(TAG, "showData: email: " + uInfo.getEmail());
//
//                final TextView name = (TextView) findViewById(R.id.name);
//                name.setText(uInfo.getName());
//                final TextView userEmail = (TextView) findViewById(R.id.email);
//                userEmail.setText(uInfo.getEmail());
//
//            }
//        }

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
        dest.writeString(address);
        dest.writeString(content);
        dest.writeString(caption);
        dest.writeString(description);
    }

    // End implemented Parcelable methods

}
