package com.example.youseehousing.forlisting;


import java.util.ArrayList;         // Want to use ArrayList
import java.util.Map;


public class Filtering{


    private static final int MAX_RADIUS = 10;   // max radius for the "location"

    // This static method is called to sort the ArrayList of Listing objects
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

        if(theFilters.get("fruniture").equals("true")){
            for(Listing result: results){
                if(result.isHasFurniture()) result.setNumOfFilterMatching(result.getNumOfFilterMatching() + 1);
            }
        }



        if(theFilters.containsKey("location")){         // TODO: this is a lot more complicated than meets the eye

        }


        return results;
    }







}















