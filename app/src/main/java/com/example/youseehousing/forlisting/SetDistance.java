package com.example.youseehousing.forlisting;


import java.util.ArrayList;

/**
 * This class will contain various static methods designed to:
 *     1. Set the latitude and longitude of our ArrayList of Listings (if they aren't already set)
 *     2. Set the instance variable "distance" of the Listings in our ArrayList based on a
 *        user-entered address.
 */
public class SetDistance {



    // If, for whatever reason, a Listing's address could not be converted to coordinates, use -1.
    public static final double NO_LATITUDE = -1;
    public static final double NO_LONGITUDE = -1;



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

            // If Listing has not been filled (lat and lng == 0), then fill them in.
            if(list.get(i).getLat() != 0 && list.get(i).getLng() != 0){

            }


        }


        return;
    } // end of setCoords()










    /**
     * Helper function for getting coords from an address
     * @return a double array of size 2. First index contains lat, second contains lng
     */
    public static double[] getCoords(String address){

        double arr[] = new double [2];



        return arr;
    } // end of getCoords()











    /**
     * Helper function used to calculate the distance (in miles) between two sets of coordinates
     *
     * @return the distance, in miles, between the two sets of coordinates
     */
    public static double dBetweenCoords(double firstLat, double firstLng, double secondLat,
                                                                          double secondLng){

        // Calculate this and return;
        double distance = 0;





        return distance;
    } // end of dBetweenCoords()




} // end of public class SetDistance
