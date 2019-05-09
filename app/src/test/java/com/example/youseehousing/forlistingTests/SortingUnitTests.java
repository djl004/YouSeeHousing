
package com.example.youseehousing.forlistingTests;

import com.example.youseehousing.forlisting.Listing;
import com.example.youseehousing.forlisting.Sorting;

import junit.framework.TestCase;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;
import java.lang.Math;              // To generate random numbers




/**
 * Contains unit tests for testing Sorting.java
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SortingUnitTests {


    // Relevant test variables
    int testSize = 1000;              // How many items in the list you want to test





    @Test
    /**
     * Create an ArrayList of Listing objects. Populate them with random distances. Sort, and then
     * check that the list is sorted from low->high
     */
    public void testSortDistance() {


        // Create a list for testing
        ArrayList<Listing> testList = new ArrayList<Listing>();

        // Add a hundred objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }


        // Fill in the distance instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setDistance(Math.random()* 1000);
        }


        // Sort by distance, low->high
        Sorting.sort(testList, "distance", false);


        // boolean value to keep track of if filter works or not
        boolean testPassed = true;

        // Check that the sort has worked correctly. Current item should be smaller than the next
        for(int i = 0; i < testList.size() - 1; i++){

            // If one pair of items is out of order, test has failed
            if( testList.get(i).getDistance() > testList.get(i+1).getDistance()){
                testPassed = false;
            }
        }

        // Check that the test passed
        assert(testPassed);

    } // end of unit test

    @Test
    /**
     * Same as testSortDistance(), except sorted from high->low
     */
    public void testSortDistanceReverse() {


        // Create a list for testing
        ArrayList<Listing> testList = new ArrayList<Listing>();

        // Add a hundred objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }


        // Fill in the distance instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setDistance(Math.random()* 1000);
        }


        // Sort by distance, low->high
        Sorting.sort(testList, "distance", true);


        // boolean value to keep track of if filter works or not
        boolean testPassed = true;

        // Check that the sort has worked correctly. Current item should be larger than the next
        for(int i = 0; i < testList.size() - 1; i++){

            // If one pair of items is out of order, test has failed
            if( testList.get(i).getDistance() < testList.get(i+1).getDistance()){
                testPassed = false;
            }
        }

        // Check that the test passed
        assert(testPassed);

    } // end of unit test



    @Test
    /**
     * Create an ArrayList of Listing objects. Populate them with random prices. Sort, and then
     * check that the list is sorted from low->high
     */
    public void testSortPrice() {


        // Create a list for testing
        ArrayList<Listing> testList = new ArrayList<Listing>();

        // Add a hundred objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }


        // Fill in the distance instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setPrice(Math.random()* 1000);
        }


        // Sort by distance, low->high
        Sorting.sort(testList, "price", false);


        // boolean value to keep track of if filter works or not
        boolean testPassed = true;

        // Check that the sort has worked correctly. Current item should be smaller than the next
        for(int i = 0; i < testList.size() - 1; i++){

            // If one pair of items is out of order, test has failed
            if( testList.get(i).getPrice() > testList.get(i+1).getPrice()){
                testPassed = false;
            }
        }

        // Check that the test passed
        assert(testPassed);

    } // end of unit test

    @Test
    /**
     * Same as testSortPrice(), but sorted from high->low
     */
    public void testSortPriceReverse() {


        // Create a list for testing
        ArrayList<Listing> testList = new ArrayList<Listing>();

        // Add a hundred objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }


        // Fill in the distance instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setPrice(Math.random()* 1000);
        }


        // Sort by distance, low->high
        Sorting.sort(testList, "price", true);


        // boolean value to keep track of if filter works or not
        boolean testPassed = true;

        // Check that the sort has worked correctly. Current item should be larger than the next
        for(int i = 0; i < testList.size() - 1; i++){

            // If one pair of items is out of order, test has failed
            if( testList.get(i).getPrice() < testList.get(i+1).getPrice()){
                testPassed = false;
            }
        }

        // Check that the test passed
        assert(testPassed);

    } // end of unit test







    @Test
    /**
     * Create an ArrayList of Listing objects. Populate them with random prices. Sort, and then
     * check that the list is sorted from low->high
     */
    public void testSortNumVacancies() {


        // Create a list for testing
        ArrayList<Listing> testList = new ArrayList<Listing>();

        // Add a hundred objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }


        // Fill in the distance instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setNumVacancies((int) (Math.random()* 1000));
        }


        // Sort by distance, low->high
        Sorting.sort(testList, "numVacancies", false);


        // boolean value to keep track of if filter works or not
        boolean testPassed = true;

        // Check that the sort has worked correctly. Current item should be smaller than the next
        for(int i = 0; i < testList.size() - 1; i++){

            // If one pair of items is out of order, test has failed
            if( testList.get(i).getNumVacancies() > testList.get(i+1).getNumVacancies()){
                testPassed = false;
            }
        }

        // Check that the test passed
        assert(testPassed);

    } // end of unit test


    @Test
    /**
     * Same as above, but sorted from high->low
     */
    public void testSortNumVacanciesReverse() {


        // Create a list for testing
        ArrayList<Listing> testList = new ArrayList<Listing>();

        // Add a hundred objects to testList
        for(int i = 0; i < testSize; i++){
            testList.add(new Listing("meaninglessAddress"));
        }


        // Fill in the distance instance variable with random numbers
        for(int i = 0; i < testSize; i++){
            testList.get(i).setNumVacancies((int) (Math.random()* 1000));
        }


        // Sort by distance, low->high
        Sorting.sort(testList, "numVacancies", true);


        // boolean value to keep track of if filter works or not
        boolean testPassed = true;

        // Check that the sort has worked correctly. Each item is larger than the last
        for(int i = 0; i < testList.size() - 1; i++){

            // If one pair of items is out of order, test has failed
            if( testList.get(i).getNumVacancies() < testList.get(i+1).getNumVacancies()){
                testPassed = false;
            }
        }

        // Check that the test passed
        assert(testPassed);

    } // end of unit test






} // end of public class SortingUnitTests