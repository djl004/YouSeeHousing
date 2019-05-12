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
    public void testGetCoordsSpeed(){

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

        assert(equalCoords(arr[LAT_INDEX], arr[LNG_INDEX], NO_LAT, NO_LNG) == true);
    }




    @Test
    /**
     * Test that setDistances() finds the correct distance instance variable
     */
    public void testCorrectDistances(){

        // Test variables

        // Create an ArrayList of two listings to test
        ArrayList<Listing> list = new ArrayList<>();
        list.add( new Listing("4198 Combe Way, San Diego, CA 92122"));
        list.add(new Listing("6698 Red Deer St, San Diego, CA 92122"));

        // Create a user-entered address to test against
        String userAddress = "10001 Pacific Heights Blvd, San Diego, CA 92121";

        // Check on google maps for distances. SetDistance's results should be less than google's.
        double arr[] = new double[2];
        arr[0] = 5.9;     // user address and combe way
        arr[1] = 6.2;     // user address and red deer street


        // Set coords for list
        SetDistance.setCoords(list);
        SetDistance.setDistances(list, userAddress);


        // Check list to make sure its distances kind of match up. (Google maps returns the fastest
        // route by driving; our function returns direct distance)
        boolean testResult = true;
        for(int i = 0; i < list.size(); i++){

            // Print the calculated value and goolge's value, if you want to manually check
            System.out.println("Google: " + arr[i] + "     Ours: " + list.get(i).getDistance());

            // Check .distance < google's value
            testResult = testResult && (list.get(i).getDistance() < arr[i]);
        }




        // return result
        assert(testResult == true);
    }


    @Test
    /**
     * Test the speed of setDistances()
     *
     * Conclusion: setDistances() works pretty quickly.
     *     About 0.5 seconds for 1000 listings. Probably most of it was getting the coordinates for
     *         the user-entered address.
     */
    public void testSetDistancesSpeed(){

        // Test variables

        // Variable for how many times to run
        int loop = 1000;

        // Create an ArrayList of one listings to test
        ArrayList<Listing> list = new ArrayList<>();
        list.add( new Listing("4198 Combe Way, San Diego, CA 92122"));


        // Create a user-entered address to test against
        String userAddress = "10001 Pacific Heights Blvd, San Diego, CA 92121";

        // Set coords for list
        SetDistance.setCoords(list);



        // Duplicate the first element "loop" many times
        for(int i = 0; i < loop; i++){
            Listing duplicate = new Listing("4198 Combe Way, San Diego, CA 92122");
            duplicate.setDistance(list.get(0).getDistance());

            list.add(duplicate);
        }



        // Test the speed of setDistances





        System.out.println("\nStart of "+ loop + " Haversine calculations");
        long startTime = System.nanoTime();

        SetDistance.setDistances(list, userAddress);

        long duration = System.nanoTime() - startTime;
        System.out.println("End of "+ loop  + " Haversine calculations: " + duration / 1000000 + " ms");




        // return result
        assert(true == true);
    }







} // end of public class SetDistanceTests
