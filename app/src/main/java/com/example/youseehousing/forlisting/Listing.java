package com.example.youseehousing.forlisting;


import java.util.ArrayList;         // Want to use ArrayList
import java.lang.String;            // To use Strings








/**
 * Summary: The Listing object represents a single listing. Some of the instance variables can be
 *          imported straight from the database while others may need to be calculated on-the-fly.
 *          The Listings will be sorted or filtered as the user desired and then displayed.
  */
public class Listing{

    // A list of animals allowed
    public static final String DOG = "dog";
    public static final String CAT = "cat";


    // Instance variables (General info)
    public int id;                  // Maybe we would like a way to refer to a specific object
    public String address;          // TODO: REVIEW: String? Maybe another formatting for address?
    public String phoneNum;         // A phone-number to contact landlord. Format: (XXX)-XXX-XXXX
    public String email;            // An email address to contact the landlord
    public int numOfFilterMatching; // meant for organization of results, “fuzzy search”

    // Instance variables (Moderation)
    public int spam = 0;            // Number of times listing has been flagged as spam
    public int badListing = 0;      // Number of times listing has been flagged as a bad Listing
    public boolean hide;            // Whether or not to hide (should be unique to user)
    public boolean pets;            // whether or not they allow pets
    public ArrayList <String> animals = new ArrayList();    // What pets are allowed.

    // Instance variables (filtering and sorting)
    public double distance;          // Distance from user (should be unique to user)
    public double price;             // Rent per month
    public boolean hasUtils;         // Are the utilities factored into the rent?
    public boolean hasWD;            // has a washer/dryer
    public boolean hasFurniture;     // Whether or not it’s furnished
    public int numBaths;             // number of bathrooms
    public int numRooms;             // number of rooms
    public int numVacancies;         // number of vacancies
    public boolean hasPets;          // Whether or not pets are allowed
    public int leaseDuration;        // The duration of the lease, in months


    /**
     * Constructor.
     * Notes: I think we should create the object first and then fill in whatever parameters are
     *        necessary afterwards. I highly doubt that all the instance variables can be filled in.
     */
    public Listing(){
    }



} // end of public class Listing


























