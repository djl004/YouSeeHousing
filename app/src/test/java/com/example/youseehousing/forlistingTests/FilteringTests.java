package com.example.youseehousing.forlistingTests;

import com.example.youseehousing.forlisting.Listing;
import com.example.youseehousing.forlisting.Filtering;

import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.*;
import java.lang.Math;              // To generate random numbers





/**
 * Contains unit tests for testing Filtering.java
 */
public class FilteringTests {


    // Relevant test variables
    int testSize = 1000;              // How many items in the list you want to test





    @Test
    /**
     * Test that the filter works on a single variable (that uses a double)
     */
    public void testSingleVariableDouble() {


        // Important variables
        ArrayList<Listing> testList = new ArrayList<Listing>();    // list to use in testing
        boolean testPassed = true;                           // boolean to keep track of success
        HashMap<String, String> filter = new HashMap<>();    // What to filter

        // Add a testSize objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }


        // Fill in the price instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setPrice(Math.random()* 1000);
        }




        // Filter out prices outside 500-800 inclusive
        filter.put("price", "500-800");
        testList = Filtering.filter(filter, testList);



        // Go through testList, check that no prices outside 500-800
        for(Listing l: testList){
            testPassed = testPassed && l.getPrice() >= 500 && l.getPrice() <= 800;
        }



        // Check that the test passed
        assert(testPassed);

    } // end of unit test



    @Test
    /**
     * Test that the filter works on a single variable (that uses boolean)
     */
    public void testSingleVariableBoolean() {


        // Important variables
        ArrayList<Listing> testList = new ArrayList<Listing>();    // list to use in testing
        boolean testPassed = true;                           // boolean to keep track of success
        HashMap<String, String> filter = new HashMap<>();    // What to filter

        // Add a testSize objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }


        // Fill in the price instance variable with random numbers
        for(int i = 0; i < testSize; i++){

            testList.get(i).setHasFurniture(Math.random() < 0.5);
        }




        // Filter out Listings without furniture
        filter.put("hasFurniture", "true");
        testList = Filtering.filter(filter, testList);



        // Go through testList, check Listing has furniture
        for(Listing l: testList){
            testPassed = testPassed && l.isHasFurniture();
        }



        // Check that the test passed
        assert(testPassed);

    } // end of unit test




    @Test
    /**
     * Test that the filter works with multiple filters
     */
    public void testMultipleVars(){
        // Important variables
        ArrayList<Listing> testList = new ArrayList<Listing>();    // list to use in testing
        boolean testPassed = true;                           // boolean to keep track of success
        HashMap<String, String> filter = new HashMap<>();    // What to filter

        // Add a testSize objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }


        // Fill in the "distance" instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setDistance(Math.random() * 1000);
        }

        // Fill in the "hasUtils" instance var with random booleans
        for(int i = 0; i < testSize; i++){
            testList.get(i).setHasUtils(Math.random() < 0.5);
        }



        // Filter OUT listings with distances outside 500-800 and listings with "hasUtils == false"
        filter.put("distance", "500-800");
        filter.put("hasUtils", "true");
        testList = Filtering.filter(filter, testList);


        // Go through testList, check that distance between 500-800 and "hasUtils" == true
        for(Listing l: testList){
            testPassed = testPassed && l.getDistance() >= 500 && l.getDistance() <= 800;
            testPassed = testPassed && (l.isHasPets() == true);
        }



        // Check that the test passed
        assert(testPassed);

    }




} // end of public class SortingUnitTests