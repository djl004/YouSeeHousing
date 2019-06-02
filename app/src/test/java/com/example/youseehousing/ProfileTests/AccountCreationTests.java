package com.example.youseehousing.ProfileTests;





// Import for testing
import com.example.youseehousing.SignUpPage;

import junit.framework.TestCase;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;
import java.lang.Math;              // To generate random numbers



public class AccountCreationTests {
/**
 * CURRENT ISSUES:
 *     1. When signing up for a new account, clicking "next" brings me back to homepage even though
 *        I haven't properly filled in the user account info.
 *
 *
 *
 * TESTS
 * NOTE: Writing unit tests seems kind of difficult. So, some of it will be manual.
 *
 * SIGN_UP_PAGE.JAVA:
 *
 * Valid email address:
 *
 *
 *
 */



    @Test
    /**
    * Test that SignUpPage.validDate() works correctly
    */
    public void testValidDate() {

        boolean testResult = true;       // falsify if any of the tests fail


        /////////////////// Check some valid dates
        testResult = testResult && SignUpPage.validDate("01/03/1999");
        testResult = testResult && SignUpPage.validDate("02/24/2018");
        testResult = testResult && SignUpPage.validDate("04/23/1989");



        ///////////////////Test that incorrect string lengths fail

        // Test length smaller than seven
        testResult = testResult && !SignUpPage.validDate("123456");
        testResult = testResult && !SignUpPage.validDate("1234");
        testResult = testResult && !SignUpPage.validDate("");


        // Test lengths larger than seven
        testResult = testResult && !SignUpPage.validDate("12345678");
        testResult = testResult && !SignUpPage.validDate("1234512638756912735");


        //////////////////Test that strings MUST be in the format XX/XX/XXXX, where X is a digit

        // Test extra digits in wrong place (but still seven characters total)
        testResult = testResult && !SignUpPage.validDate("123/12/123");
        testResult = testResult && !SignUpPage.validDate("1234/12/12");
        testResult = testResult && !SignUpPage.validDate("//12345678");


        // Test that non-digits fail the test
        testResult = testResult && !SignUpPage.validDate("AB/12/1234");
        testResult = testResult && !SignUpPage.validDate("12/AB/5124");


        // Check if all the tests passed
    } // end of testValidDate()



    @Test
    /**
     * Test that SignUpPage.validFormat() works correctly
     */
    public void testValidEmailFormat() {

        // Local variables
        boolean testResult = true;       // falsify if any of the tests fail
        SignUpPage obj = new SignUpPage();

        /////////////////// Check some valid email addresses
        testResult = testResult && obj.validFormat("aam060@ucsd.edu");
        testResult = testResult && obj.validFormat("etesting46@gmail.com");






        /////////////////// Check that email addresses MUST have the "@" symbol
        testResult = testResult && !obj.validFormat("randomrandomrandom");
        testResult = testResult && !obj.validFormat("");







        // Check if all the tests passed
    } // end of testValidDate()







}
