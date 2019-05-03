package com.example.youseehousing.forlisting;


import java.util.ArrayList;         // Want to use ArrayList
import java.util.Map;


public class Filtering{


    private static final int MAX_RADIUS = 10;   // max radius for the "location"





    // This static method is called to sort the ArrayList of Listing objects

    /**
     * This static method is called to sort the ArrayList of Listing objects
     * @param theFilters: A Map of String keys to String values. Should look like this:
     *           In general, there are two types of filters.
     *                  1. Filters that check a boolean value. The value should be either the string
     *                         "true" representing the boolean true, or the string "false"
     *                         representing the boolean false.
     *                  2. Filters that check the instance variables to be inside a range. They are
     *                         formatted as such. lowBound + "-" + highBound. If there is no
     *                         lowBound, pass in 0. If there is no highBound, pass in a really large
     *                         number, say MAX_VALUE or something.
     *
     *           Examples:
     *           Key: "price",    Value: "500-1000
     *           Key: "distance", Value: "0-3"
     *           Key: "numRooms", Value: "2-4"
     *           Key: "size", Value: "0-1000"
     *           Key: "numBaths", Value: "1-3"
     *           Key: "numVacancies", Value: "1-4"
     *           Key: "leaseDuration", Value: "9-12"
     *           Key: "hasWD", Value: "true"
     *           Key: "hasPets", Value: "false"
     *           Key: "hasFurniture", Value: "true"
     *           Key: "hasUtils", Value: "true"
     * @return the filtered ArrayList of Listings
     */
    public static ArrayList<Listing> filter(Map<String, String> theFilters){
        ArrayList<Listing> results = new ArrayList<Listing>();
        if(theFilters.containsKey("price")){            // should make a maximum default value, so this should always be true
            int value = Integer.parseInt(theFilters.get("price"));  // this is the value that is going to be the upper limit to our results. It comes from the filters applied by the user
//            results = database.populate(value);         // not sure how we will grab data, so this is only temporary, will comment out for compilation purposes
        }

        if(theFilters.get("hasWD").equals("true")){
            for(Listing result: results){
                if(result.isHasWD()) result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
        }

        if(theFilters.containsKey("numRooms")){
            // for every listing that has more than the requested number of rooms, increment the math filter number
            for(Listing result: results){
                if(result.getNumRooms() >= Integer.parseInt(theFilters.get("numRooms"))) result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
        }

        if(theFilters.containsKey("size")){
            for(Listing result: results){
                if(result.getSize() >= Double.parseDouble(theFilters.get("size"))) result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
        }

        if(theFilters.containsKey("numBaths")){
            for(Listing result: results){
                if(result.getNumBaths() >= Integer.parseInt(theFilters.get("numBaths"))) result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
        }

        if(theFilters.get("hasPets").equals("true")){
            for(Listing result: results){
                if(result.isHasPets()) result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
        }

        if(theFilters.get("hasFurniture").equals("true")){
            for(Listing result: results){
                if(result.isHasFurniture()) result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
        }



        if(theFilters.containsKey("location")){         // TODO: this is a lot more complicated than meets the eye

        }


        return results;
    }






    /**
     * One implementation of setDistances using google maps to calculate the distances between
     * the listing's address and the user-entered address. It may take too long, in which case we
     * will swap this implementation for another one.
     *
     * @param listings: the listings whose distance to "address" will be calculated
     * @param address: the user-entered address
     */
    public void setDistancesGoogleMaps(ArrayList<Listing> listings, String address){


        return;
    } // end of public void setDistances()










}















