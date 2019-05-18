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


        // TEST PRICE
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


        // TEST DISTANCE
        testList = new ArrayList<Listing>();                 // list to use in testing
        filter = new HashMap<>();    // What to filter

        // Add a testSize objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }

        // Fill in the price instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setDistance(Math.random()* 1000);
        }

        // Filter out prices outside 500-800 inclusive
        filter.put("distance", "500-800");
        testList = Filtering.filter(filter, testList);

        // Go through testList, check that no prices outside 500-800
        for(Listing l: testList){
            testPassed = testPassed && l.getDistance() >= 500 && l.getDistance() <= 800;
        }





        // TEST SIZE
        testList = new ArrayList<Listing>();                 // list to use in testing
        filter = new HashMap<>();    // What to filter

        // Add a testSize objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }

        // Fill in the price instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setSize(Math.random()* 1000);
        }

        // Filter out prices outside 500-800 inclusive
        filter.put("size", "500-800");
        testList = Filtering.filter(filter, testList);

        // Go through testList, check that no prices outside 500-800
        for(Listing l: testList){
            testPassed = testPassed && l.getSize() >= 500 && l.getSize() <= 800;
        }




        // TEST SIZE
        testList = new ArrayList<Listing>();                 // list to use in testing
        filter = new HashMap<>();    // What to filter

        // Add a testSize objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }

        // Fill in the price instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setSize(Math.random()* 1000);
        }

        // Filter out prices outside 500-800 inclusive
        filter.put("size", "500-800");
        testList = Filtering.filter(filter, testList);

        // Go through testList, check that no prices outside 500-800
        for(Listing l: testList){
            testPassed = testPassed && l.getSize() >= 500 && l.getSize() <= 800;
        }




        // TEST NUM_ROOMS
        testList = new ArrayList<Listing>();                 // list to use in testing
        filter = new HashMap<>();    // What to filter

        // Add a testSize objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }

        // Fill in the price instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setNumRooms((int)(Math.random()* 1000));
        }

        // Filter out prices outside 500-800 inclusive
        filter.put("numRooms", "500-800");
        testList = Filtering.filter(filter, testList);

        // Go through testList, check that no prices outside 500-800
        for(Listing l: testList){
            testPassed = testPassed && l.getNumRooms() >= 500 && l.getNumRooms() <= 800;
        }



        // TEST NUM_BATHROOMS
        testList = new ArrayList<Listing>();                 // list to use in testing
        filter = new HashMap<>();    // What to filter

        // Add a testSize objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }

        // Fill in the price instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setNumBaths((int)(Math.random()* 1000));
        }

        // Filter out prices outside 500-800 inclusive
        filter.put("numBaths", "500-800");
        testList = Filtering.filter(filter, testList);

        // Go through testList, check that no prices outside 500-800
        for(Listing l: testList){
            testPassed = testPassed && l.getNumBaths() >= 500 && l.getNumBaths() <= 800;
        }





        // TEST NUM_VACANCIES
        testList = new ArrayList<Listing>();                 // list to use in testing
        filter = new HashMap<>();    // What to filter

        // Add a testSize objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }

        // Fill in the price instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setNumVacancies((int)(Math.random()* 1000));
        }

        // Filter out prices outside 500-800 inclusive
        filter.put("numVacancies", "500-800");
        testList = Filtering.filter(filter, testList);

        // Go through testList, check that no prices outside 500-800
        for(Listing l: testList){
            testPassed = testPassed && l.getNumVacancies() >= 500 && l.getNumVacancies() <= 800;
        }




        // TEST LEASE_DURATION
        testList = new ArrayList<Listing>();                 // list to use in testing
        filter = new HashMap<>();    // What to filter

        // Add a testSize objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }

        // Fill in the price instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setLeaseDuration((int)(Math.random()* 1000));
        }

        // Filter out prices outside 500-800 inclusive
        filter.put("leaseDuration", "500-800");
        testList = Filtering.filter(filter, testList);

        // Go through testList, check that no prices outside 500-800
        for(Listing l: testList){
            testPassed = testPassed && l.getLeaseDuration() >= 500 && l.getLeaseDuration() <= 800;
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






        // TEST HAS PETS
        testList = new ArrayList<Listing>();    // list to use in testing
        filter = new HashMap<>();    // What to filter

        // Add a testSize objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }

        // Fill in the price instance variable with random numbers
        for(int i = 0; i < testSize; i++){

            testList.get(i).setHasPets(Math.random() < 0.5);
        }

        // Filter out Listings without furniture
        filter.put("hasPets", "true");
        testList = Filtering.filter(filter, testList);

        // Go through testList, check Listing has furniture
        for(Listing l: testList){
            testPassed = testPassed && l.isHasPets();
        }




        // TEST HAS UTILS
        testList = new ArrayList<Listing>();    // list to use in testing
        filter = new HashMap<>();    // What to filter

        // Add a testSize objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }

        // Fill in the price instance variable with random numbers
        for(int i = 0; i < testSize; i++){

            testList.get(i).setHasUtils(Math.random() < 0.5);
        }

        // Filter out Listings without furniture
        filter.put("hasUtils", "true");
        testList = Filtering.filter(filter, testList);

        // Go through testList, check Listing has furniture
        for(Listing l: testList){
            testPassed = testPassed && l.isHasUtils();
        }




        // TEST HAS W/D
        testList = new ArrayList<Listing>();    // list to use in testing
        filter = new HashMap<>();    // What to filter

        // Add a testSize objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }

        // Fill in the price instance variable with random numbers
        for(int i = 0; i < testSize; i++){

            testList.get(i).setHasWD(Math.random() < 0.5);
        }

        // Filter out Listings without furniture
        filter.put("hasWD", "true");
        testList = Filtering.filter(filter, testList);

        // Go through testList, check Listing has furniture
        for(Listing l: testList){
            testPassed = testPassed && l.isHasWD();
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