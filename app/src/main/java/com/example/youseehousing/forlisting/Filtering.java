package com.example.youseehousing.forlisting;


import java.util.ArrayList;         // Want to use ArrayList
import java.util.Map;
import org.json.*;


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
     *           Key: "price",         Value: "500-1000
     *           Key: "distance",      Value: "0-3"
     *           Key: "numRooms",      Value: "2-4"
     *           Key: "size",          Value: "0-1000"
     *           Key: "numBaths",      Value: "1-3"
     *           Key: "numVacancies",  Value: "1-4"
     *           Key: "leaseDuration", Value: "9-12"
     *           Key: "hasWD",         Value: "true"
     *           Key: "hasPets",       Value: "false"
     *           Key: "hasFurniture",  Value: "true"
     *           Key: "hasUtils",      Value: "true"
     * @return the filtered ArrayList of Listings
     */
    public static ArrayList<Listing> filter(Map<String, String> theFilters){
        ArrayList<Listing> results = new ArrayList<Listing>();
        String delims = "[-]+";
        if(theFilters.containsKey("price")){            // should make a maximum default value, so this should always be true
            String[] bounds = theFilters.get("price").split(delims);
            int upper = Integer.parseInt(bounds[0]);    // this is the value that is going to be the upper limit to our results. It comes from the filters applied by the user
            int lower = Integer.parseInt(bounds[1]);    // this is the lower bound
//            results = database.populate(lower,upper);         // not sure how we will grab data, so this is only temporary, will comment out for compilation purposes
        }

        if(theFilters.containsKey("hasWD")){
            for(Listing result: results){   // the if statement in this should give correct filtering if they want it, if they don't want any filtering with this then don't put the key in
                //  logic: if they have it set to wanting one, then the .equals evaluates to true, and the if statement will increment those that are set to true
                //          if they have it set to not want one, then the right statement evaluates to false, and it increments all of the ones that have it set to false
                //          if we don't like this, we can have three separate values that we check for instead to make it more clear
                if(result.isHasWD() == theFilters.get("hasWD").equals("true")) result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
        }

        if(theFilters.containsKey("numRooms")){
            // for every listing that has more than the requested number of rooms, increment the match filter number
            String[] bounds = theFilters.get("numRooms").split(delims);
            int upper = Integer.parseInt(bounds[1]);
            int lower = Integer.parseInt(bounds[0]);
            for(Listing result: results){   // this loop goes through all of the listings, incrementing any that are within the filter's boundaries
                if(result.getNumRooms() >= lower && result.getNumRooms() <= upper)
                    result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
        }

        if(theFilters.containsKey("size")){
            String[] bounds = theFilters.get("size").split(delims);
            double upper = Double.parseDouble(bounds[1]);
            double lower = Double.parseDouble(bounds[0]);
            for(Listing result: results){
                if(result.getSize() >= lower && result.getSize() <= upper) result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
        }

        if(theFilters.containsKey("numBaths")){
            String[] bounds = theFilters.get("numBaths").split(delims);
            int upper = Integer.parseInt(bounds[1]);
            int lower = Integer.parseInt(bounds[0]);
            for(Listing result: results){
                if(result.getNumBaths() >= lower && result.getNumBaths() <= upper) result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
        }

        if(theFilters.containsKey("hasPets")){
            for(Listing result: results){
                if(result.isHasPets() == theFilters.get("hasPets").equals("true")) result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
        }

        if(theFilters.containsKey("hasFurniture")){
            for(Listing result: results){
                if(result.isHasFurniture() == theFilters.get("hasFurniture").equals("true")) result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
        }



        if(theFilters.containsKey("distance")){
            String[] bounds = theFilters.get("distance").split(delims);
            double upper = Double.parseDouble(bounds[1]);
            double lower = Double.parseDouble(bounds[0]);
            for(Listing result: results) {
                if(result.getDistance() >= lower && result.getDistance() <= upper)
                    result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
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















