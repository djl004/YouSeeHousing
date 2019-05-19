package com.example.youseehousing;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * A list item representing a piece of content.
 * This class implements Parcelable so it can pass its data to the MainListingPage activity.
 * DONE: This definitely deserves its own class where it dynamically pulls content from the database
 *        based on its id and populates these parameters with that information. DONE!
 */

public class ListingDetails implements Parcelable {

//    private String id;
    private String address;
    private String bath;
    private String bed;
    private String buildingLease;
    private String contact;
    private String desc;
    private String dim;
    private String link;
    private String parking;
    private ArrayList<String> pictures;
    private String pet;
    private String price;
    private String unitLease;
    private String washerDryer;

    public ListingDetails() {}

    public ListingDetails(String address,
                          String bath,
                          String bed,
                          String buildingLease,
                          String contact,
                          String desc,
                          String dim,
                          String link,
                          String parking,
                          ArrayList<String> pictures,
                          String pet,
                          String price,
                          String unitLease,
                          String washerDryer) {
//        this.id = id;
        this.address = address;
        this.bath = bath;
        this.bed = bed;
        this.buildingLease = buildingLease;
        this.contact = contact;
        this.desc = desc;
        this.dim = dim;
        this.link = link;
        this.parking = parking;
        this.pictures = pictures;
        this.pet = pet;
        this.price = price;
        this.unitLease = unitLease;
        this.washerDryer = washerDryer;
    }

    protected ListingDetails(Parcel in) {
//        id = in.readString();
        address = in.readString();
//        this.id = in.readString();
        this.address = in.readString();
        this.bath = in.readString();
        this.bed = in.readString();
        this.buildingLease = in.readString();
        this.contact = in.readString();
        this.desc = in.readString();
        this.dim = in.readString();
        this.link = in.readString();
        this.parking = in.readString();
        this.pictures = (ArrayList<String>) in.readSerializable();
        this.pet = in.readString();
        this.price = in.readString();
        this.unitLease = in.readString();
        this.washerDryer =in.readString();
    }

    @Override
    public String toString() {
        return address;
    }

    //getters
//    public String getId() {
//        return this.id;
//    }
    public String getAddress() {
        return this.address;
    }
    public String getBath() {
        return bath;
    }
    public String getBed() {
        return bed;
    }
    public String getBuildingLease() {
        return buildingLease;
    }
    public String getContact() {
        return contact;
    }
    public String getDesc() {
        return desc;
    }
    public String getDim() {
        return dim;
    }
    public String getLink() {
        return link;
    }
    public String getParking() {
        return parking;
    }
    public ArrayList<String> getPictures() {
        return pictures;
    }
    public String getPet() {
        return pet;
    }
    public String getPrice() {
        return price;
    }
    public String getUnitLease() {
        return unitLease;
    }
    public String getWasherDryer() {
        return washerDryer;
    }

    //setters
//    public void setId(String id) {
//        this.id = id;
//    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setBath(String bath) {
        this.bath = bath;
    }
    public void setBed(String bed) {
        this.bed = bed;
    }
    public void setBuildingLease(String buildingLease) {
        this.buildingLease = buildingLease;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setDim(String dim) {
        this.dim = dim;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setParking(String parking) {
        this.parking = parking;
    }
    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }
    public void setPet(String pet) {
        this.pet = pet;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setUnitLease(String unitLease) {
        this.unitLease = unitLease;
    }
    public void setWasherDryer(String washerDryer) {
        this.washerDryer = washerDryer;
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
//        dest.writeString(id);
        dest.writeString(address);
        dest.writeString(bath);
        dest.writeString(bed);
        dest.writeString(buildingLease);
        dest.writeString(contact);
        dest.writeString(desc);
        dest.writeString(dim);
        dest.writeString(link);
        dest.writeString(parking);
        dest.writeSerializable(pictures);
        dest.writeString(pet);
        dest.writeString(price);
        dest.writeString(unitLease);
        dest.writeString(washerDryer);

    }

    // End implemented Parcelable methods

}
