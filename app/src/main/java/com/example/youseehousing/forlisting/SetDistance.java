package com.example.youseehousing.forlisting;


import java.util.ArrayList;

/**
 * This class will contain various static methods designed to:
 *     1. Set the latitude and longitude of our ArrayList of Listings (if they aren't already set)
 *     2. Set the instance variable "distance" of the Listings in our ArrayList based on a
 *        user-entered address.
 */
public class SetDistance {


    // Important variables:
    // If, for whatever reason, a Listing's address could not be converted to coordinates, use -1.
    public static final double NO_LATITUDE = -1;
    public static final double NO_LONGITUDE = -1;

    // Constants used for calculations
    public static final double R = 6372.8;             // In kilometers
    public static final double KM_CONSTANT = 0.621371; // How many miles in a kilometer




    /**
     * Call this function to set the distances in the ArrayList.
     */
    public static void setDistances(ArrayList<Listing> list, String address){


        // First, call setCoords() to make sure the latitudes and longitudes are filled out
        setCoords(list);



    } // end of setDistances()









    /**
     * Function to set the coordinates of all the Listings in the ArrayList. Called from
     * setDistances() automatically, but can also be called directly, if you desire.
     *
     * Note: If a Listing in the ArrayList's coordinates cannot be found, then they will be set to
     *       -1.
     */
    public static void setCoords(ArrayList<Listing> list){


        // Go through list, and fill in Listings
        for(int i = 0; i < list.size(); i++){


            // Get the current Listing for convenience
            Listing curr = list.get(i);

            // If Listing has not been filled (lat and lng == 0), then fill them in.
            if(curr.getLat() == 0 && curr.getLng() == 0){
                double arr[] = getCoords(curr.getAddress());
                curr.setLat(arr[0]);         // Latitude located in the 1st index
                curr.setLng(arr[1]);         // Longitude located in the 2nd index
            }


        }


        return;
    } // end of setCoords()










    /**
     * Helper function for getting coords from an address. If the coordinates cannot be found, then
     * the returned double array will be filled with -1.
     * @return a double array of size 2. First index contains lat, second contains lng
     */
    public static double[] getCoords(String address){

        double arr[] = new double [2];



        return arr;
    } // end of getCoords()













    /**
     * Helper function used to calculate the distance (in miles) between two sets of coordinates
     * Resource: https://rosettacode.org/wiki/Haversine_formula#Java
     *
     *
     * @return the distance, in miles, between the two sets of coordinates
     */
    public static double dBetweenCoords(double lat1, double lng1, double lat2, double lng2){


        // Calculate this and return;
        double distance = 0;


        // Haversine formula to calculate distance
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        distance = R * c;




        // Convert answer in kilometers to miles, and return
        return distance * KM_CONSTANT;

    } // end of dBetweenCoords()




} // end of public class SetDistance
