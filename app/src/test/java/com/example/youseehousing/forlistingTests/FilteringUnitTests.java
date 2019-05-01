package com.example.youseehousing.forlistingTests;

import com.example.youseehousing.forlisting.Filtering;
import com.example.youseehousing.forlisting.Listing;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
class ExampleUnitTest extends TestCase {





    @Test
    public void testFiltersOutDistance() {


        // Create a list for testing
        ArrayList<Listing> testList = new ArrayList<>();

        // Add a hundred objects to testList
        for(int i = 0; i < 100; i++){
            testList.add(new Listing("meaninglessAddress"));
        }

        // Fill in the distance instance variable with random numbers
        for(int i = 0; i < 100; i++){
            testList.get(i).setDistance(i * 4);
        }


        // Filter out things outside of a and b
        int a = 100;
        int b = 200;
        Filtering.filter(testList, "distance", a, b);


        // boolean value to keep track of if filter works or not
        boolean testPassed = true;

        // Check that the filter has worked correctly (Falsify testPassed if it fails)
        for(int i = 0; i < testList.size(); i++){
            if( !(testList.get(i).distance >= a || testList.get(i).distance <= b)){
                testPassed = false;
            }
        }

        // Check that the test passed
        assert(testPassed);

    } // end of unit test







} // end of public class ExampleUnitTest