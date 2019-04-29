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
     * @return: The filtered list
     */
    public static ArrayList<Listing> filter(ArrayList<Listing> l, String param, int botLimit, int topLimit){





        // The list is filtered, so return it
        return l;
    } // end of filer()







} // end of public class Filtering















