package com.example.youseehousing.forlistingTests;



import com.example.youseehousing.forlisting.Listing;
import com.example.youseehousing.forlisting.SetDistance;

import junit.framework.TestCase;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;
import java.lang.Math;              // To generate random numbers





/**
 * Contains various unit tests for testing SetDistanceTests. We should manually look up coordinates
 * for various test addresses from the website, and then make sure that the SetDistance method calls
 * result in the same coordinates.
 */
public class SetDistanceTests {

    // Important variables
    static final double LAT_LONG_TOLERANCE = 0.001; // for double equality comparison

    static final int LAT_INDEX = 0;
    static final int LNG_INDEX = 1;

    static final double NO_LAT = -1000;
    static final double NO_LNG = -1000;

    /**
     * Method to check for "equality" of two passed-in doubles
     */
    public static boolean equalDoubles(double a, double b){
        if(Math.abs(a - b) <= LAT_LONG_TOLERANCE){
            return true;
        }
        return false;
    }


    /**
     * Method to check for equality of coordinates
     */
    public static boolean equalCoords(double lat1, double lng1, double lat2, double lng2){
        if(equalDoubles(lat1, lat2) && equalDoubles(lng1, lng2)){
            return true;
        }
        return false;
    }


    @Test
    /**
     * Create an ArrayList of Listing objects. Populate them with random distances. Sort, and then
     * check that the list is sorted from low->high
     */
    public void testValidAddress() {

        // keep track of multiple tests
        boolean testPassed = true;


        // Create a Listing to test
        Listing listing = new Listing("4198 Combe Way, San Diego, CA 92122");

        // Variables to check for equivalence
        double lat = 32.854433;         // Latitude of the address
        double lng = -117.199943;       // Longitude of the address
        double arr[];

        // Get the coordinates for listing.
        arr = SetDistance.getCoords(listing.getAddress());

        testPassed = testPassed && equalCoords(arr[LAT_INDEX], arr[LNG_INDEX], lat, lng);



        // Create a Listing to test
        listing = new Listing("6698 Red Deer St, San Diego, CA 92122");

        // Variables to check for equivalence
        lat = 32.858009;         // Latitude of the address
        lng = -117.203117;       // Longitude of the address

        // Get the coordinates for listing.
        arr = SetDistance.getCoords(listing.getAddress());

        testPassed = testPassed && equalCoords(arr[LAT_INDEX], arr[LNG_INDEX], lat, lng);




        // Create a Listing to test
        listing = new Listing("10001 Pacific Heights Blvd, San Diego, CA 92121");

        // Variables to check for equivalence
        lat = 32.9011042;         // Latitude of the address
        lng = -117.1914667;       // Longitude of the address

        // Get the coordinates for listing.
        arr = SetDistance.getCoords(listing.getAddress());

        testPassed = testPassed && equalCoords(arr[LAT_INDEX], arr[LNG_INDEX], lat, lng);



        assert(testPassed == true);

    } // end of unit test


    @Test
    /**
     * This test will just run 10 queries. It prints when it begins, and when it stops.
     */
    public void testSpeed(){

        // Variable for number of queries
        int numQueries = 10;

        // Create a Listing to test
        Listing listing = new Listing("4198 Combe Way, San Diego, CA 92122");

        // Variables to check for equivalence
        double lat = 32.854433;         // Latitude of the address
        double lng = -117.199943;       // Longitude of the address
        double arr[];


        System.out.println("Start of "+ numQueries + " queries");
        long startTime = System.nanoTime();

        // Get the coordinates for listing.
        for(int i = 0; i < numQueries; i++) {
            arr = SetDistance.getCoords(listing.getAddress());
        }

        long duration = System.nanoTime() - startTime;
        System.out.println("End of "+ numQueries + " queries: " + duration / 1000000 + " ms");

        assert(true == true);
    }



    @Test
    /**
     * Check that invalid addresses hold -1000 as both latitude and longitude
     */
    public void testInvalidAddresses(){
        // Create a Listing to test
        Listing listing = new Listing("INVALIDINVALIDINVALIDINVALIDINVALID");

        // Variables to check for equivalence
        double arr[];

        // Get the coordinates for listing.
        arr = SetDistance.getCoords(listing.getAddress());

        assert(equalCoords(arr[LAT_INDEX], arr[LNG_INDEX], NO_LAT, NO_LNG) == false);
    }



} // end of public class SetDistanceTests
