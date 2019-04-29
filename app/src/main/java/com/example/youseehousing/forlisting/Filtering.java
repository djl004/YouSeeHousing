package com.example.youseehousing.forlisting;


import java.util.ArrayList;         // Want to use ArrayList











public class Filtering{


    /**
     * This static method is used to filter out certain listings
     * @param l: The ArrayList of Listings to filter out:
     * @param param: The general criteria to filter. Can be one of the following:
     *               - "distance" to filter by distance
     *               - "price" to filter by price
     *               - "hasUtils" to filter out listings that do not include utilities in rent cost
     *               - "hasWD" to filter out listings without WD
     *               - "hasFurniture" to filter out listings without furniture
     *               - "numBaths" to filter out listings without right number of baths
     *               - "numRooms" to filter out listings without the right number of rooms
     *               - "numVacancies" to filter by number of vacancies
     *               - "hasPets" to filter out listings that don't allow pets
     *               - "leaseDuration" to filter by lease duraction
     * @param botLimit and topLimit: Some criteria needs additional detail. See below:
     *               - distance needs lower bound and upper bound, inclusive
     *               - price needs lower and upper bound, inclusive
     *               - numBaths needs lower and upper bound, inclusive
     *               - numRooms needs lower rand upper bound, inclusive
     *               - numVacancies needs lower and upper bound, inclusive
     *               - leasDuration needs lowe rnad upper bound, inclusive
     *               Note: If the param doesn't use the botLimit or topLimit, pass in whatever.
     * @return: The filtered list
     */
    public static ArrayList<Listing> filter(ArrayList<Listing> l, String param, int botLimit, int topLimit){


        // Check if this particular method call is using limits or not
        boolean usesLimits = false;
        if(param == "distance" || param == "price" || param == "numBaths" || param == "numRooms"
                || param == "numVacancies" || param == "leaseDuration"){
            usesLimits = true;
        }


        // Filter using a parameter that uses limits
        if(usesLimits){

            // Go through the ArrayList and delete Listings that don't fit
            for(int i = 0; i < l.size(); i++) {

                Listing currentObj = l.get(i);


                // For params that include a botLimit and a topLimit, get the relevant instance variable
                int relevantVar = 0;
                if (param == "distance") {
                    relevantVar = currentObj.getDistance();
                } else if (param == "price") {
                    relevantVar = currentObj.getPrice();
                } else if (param == "numBaths") {
                    relevantVar = currentObj.getNumBaths();
                } else if (param == "numRooms") {
                    relevantVar = currentObj.getNumRooms();
                } else if (param == "numVacancies") {
                    relevantVar = currentObj.getNumVacancies();
                } else if (param == "hasPets") {
                    relevantVar = currentObj.getHasPets();
                } else if (param == "leaseDuration") {
                    relevantVar = currentObj.getLeaseDuration();
                }


                // If the current Listing don't fit in the limits, remove that Listing
                if(relevantVar < botLimit || relevantVar > topLimit) {
                    l.remove(i);           // Remove the element
                    i--;                   // Decrement i to account for the removal of one element
                }


            } // End of going through every element in the list
        } // end of filtering by parameters that use limits





        // Filter using a parameter that doesn't use limits
        else{

            // Go through the ArrayList and delete Listings that don't fit
            for(int i = 0; i < l.size(); i++) {


                Listing currentObj = l.get(i);

                // For params that don't include a botLimit and a topLimit, get the relevant boolean
                boolean relevantVar = false;
                if (param == "hasUtils") {
                    relevantBool = currentObj.isHasUtils;
                } else if (param == "hasWD") {
                    relevantBool = currentObj.isHasWD;
                } else if (param == "hasFurniture") {
                    relevantBool = currentObj.isHasFurniture;
                } else if (param == "hasPets") {
                    relevantBool = currentObj.isHasPets;
                }


                // For the current Listing, remove it if it doesn't qualify
                if(relevantVar == false){
                    l.remove(i);           // Remove the element
                    i--;                   // Decrement i to account for the removal of one element
                }



            } // End of going through every element in the list
        } // end of filtering by parameters that don't use limits







        // The list is filtered, so return it
        return l;
    } // end of filter()







} // end of public class Filtering















