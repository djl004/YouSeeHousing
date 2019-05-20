/**
 * NOTE:
 *       Call setDistances() to set the distance variable in each Listing. Do it when scraping and
 *       storing into the database.
 */


package com.example.youseehousing.forlisting;

/** SECOND NOTE: Various classes needed for geocoding
 * If these imports cause build issues, go to java\lib\json-simple-1.1.1.jar.
 * Right-click the .jar file, and select add as library
 */
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;




/**
 * This class will contain various static methods designed to:
 *     1. Set the latitude and longitude of our ArrayList of Listings (if they aren't already set)
 *     2. Set the instance variable "distance" of the Listings in our ArrayList based on a
 *        user-entered address.
 *
 *     Important methods:
 *         1. setDistances(ArrayList<Listing>, address) to set the "distance" instance var
 *         2. setCoords(ArrayList<Listing>) to set the coordinates. Use when storing in database.
 */
public class SetDistance {


    // Important variables:
    // If, for whatever reason, a Listing's address could not be converted to coordinates, use -1.
    public static final double NO_LATITUDE = -1000;  // Latitude goes from -90 to 90
    public static final double NO_LONGITUDE = -1000; // Longitude goes from -180 to 180
    public static final double NO_DISTANCE = -1; // Probably would want to print out "N/A"


    // Index reference for the array returned by getCoords()
    public static final int LAT_INDEX = 0;
    public static final int LNG_INDEX = 1;


    // Constants used for calculations (Haversine formula)
    public static final double R = 6372.8;             // In kilometers
    public static final double KM_CONSTANT = 0.621371; // How many miles in a kilometer

    // UCSD info
    public static final String UCSD_ADDRESS = "9500 Gilman Dr, La Jolla, CA 92093";
    public static final double UCSD_LAT = 32.877651;
    public static final double UCSD_LNG = -117.240044;



    /**
     * Summary: Call this function to set the distances in the ArrayList. The distance will be the
     *          distance from the Listing to UCSD (9500 Gilman Dr, La Jolla, CA 92093)
     *
     * @param list: The ArrayList of Listings whose distance var will be filled
     *
     * return: True if the distances have been set relative to "address". False if the coordinates
     *               for "address" could not be found.
     */
    public static boolean setDistances(ArrayList<Listing> list){


        // Important variables
        double lat = 0;         // Latitude for address
        double lng = 0;         // Longitude for address
        double arr[] = new double[2]; // Array for storing result of getCoords()


        // Call setCoords()
        setCoords(list);



        // Now, calculate and set distances for Listings in list
        for(int i = 0; i < list.size(); i++){
            // Create reference to current Listing
            Listing curr = list.get(i);

            // Set curr's distance
            curr.setDistance(dBetweenCoords(curr.getLat(), curr.getLng(), UCSD_LAT, UCSD_LNG) );
        }


        // All finished, return success
        return true;

    } // end of setDistances()













    /**
     * Helper Function to set the coordinates of all the Listings in the ArrayList.
     *
     * Note: If a Listing in the ArrayList's coordinates cannot be found, then they will be set to
     *       -1.
     */
    public static void setCoords(ArrayList<Listing> list){


        // Go through list, and fill in Listings
        for(int i = 0; i < list.size(); i++){


            // Get the current Listing for convenience
            Listing curr = list.get(i);

            // Create a double array for storing coordinate information
            double arr[];

            // If Listing has not been filled (lat and lng == 0), then fill them in.
            if(curr.getLat() == 0 && curr.getLng() == 0){
                arr = getCoords(curr.getAddress());
                curr.setLat(arr[LAT_INDEX]);         // Latitude located in the 1st index
                curr.setLng(arr[LNG_INDEX]);         // Longitude located in the 2nd index
            }


        }


        return;
    } // end of setCoords()










    /**
     * Helper function for getting coords from an address. If the coordinates cannot be found, then
     * the returned double array will be filled with -1000, indicating null latitude and longitude
     *
     * Resource: http://julien.gunnm.org/geek/programming/2015/09/13/how-to-get-geocoding-information-in-java-without-google-maps-api/
     *
     * @return a double array of size 2. First index contains lat, second contains lng
     */
    public static double[] getCoords(String address){

        double arr[];

        arr = OpenStreetMapUtils.getInstance().getCoordinates(address);


        return arr;
    } // end of getCoords()













    /**
     * Helper function used to calculate the distance (in miles) between two sets of coordinates
     * Resource: https://rosettacode.org/wiki/Haversine_formula#Java
     *
     * Error case: If any of the coordinates are invalid (-1000), then return -1
     *
     * @return the distance, in miles, between the two sets of coordinates
     */
    public static double dBetweenCoords(double lat1, double lng1, double lat2, double lng2){

        // If any of the coords are invalid, return -1, NO_DISTANCE
        if(lat1 == NO_LATITUDE || lat2 == NO_LATITUDE || lng1 == NO_LONGITUDE
                || lng2 == NO_LONGITUDE){
            return NO_DISTANCE;
        }


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














//////////////////// GEOCODING METHODS (WILL BE CALLED AUTOMATICALLY BY ABOVE METHODS) /////////////


/**
 * Contains methods for us to use to geocode.
 *
 * Source: http://julien.gunnm.org/geek/programming/2015/09/13/how-to-get-geocoding-information-in-java-without-google-maps-api/
 *
 */

class OpenStreetMapUtils {


    // Important variables:
    // If, for whatever reason, a Listing's address could not be converted to coordinates, use -1.
    public static final double NO_LATITUDE = -1000;  // Latitude goes from -90 to 90
    public static final double NO_LONGITUDE = -1000; // Longitude goes from -180 to 180
    public static final double NO_DISTANCE = -1; // Probably would want to print out "N/A"


    // Index reference for the array returned by getCoords()
    public static final int LAT_INDEX = 0;
    public static final int LNG_INDEX = 1;



    public final static Logger log = Logger.getLogger("OpenStreeMapUtils");

    private static OpenStreetMapUtils instance = null;
    private JSONParser jsonParser;

    public OpenStreetMapUtils() {
        jsonParser = new JSONParser();
    }

    public static OpenStreetMapUtils getInstance() {
        if (instance == null) {
            instance = new OpenStreetMapUtils();
        }
        return instance;
    }

    /**
     * Method for getting request from website
     */
    private String getRequest(String url) throws Exception {

        final URL obj = new URL(url);
        final HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }


    /**
     * Get coordinates given an address
     */
    public double[] getCoordinates(String address) {

        //Map<String, Double> res;
        double arr[] = new double[2];
        StringBuffer query;
        String[] split = address.split(" ");
        String queryResult = null;

        query = new StringBuffer();


        query.append("https://nominatim.openstreetmap.org/search?q=");

        if (split.length == 0) {
            return null;
        }

        for (int i = 0; i < split.length; i++) {
            query.append(split[i]);
            if (i < (split.length - 1)) {
                query.append("+");
            }
        }
        query.append("&format=json&addressdetails=1");

        //log.debug("Query:" + query);

        try {
            queryResult = getRequest(query.toString());
        // If failure to get a query result, return error values
        } catch (Exception e) {
            //log.error("Error when trying to get data with the following query " + query);
            arr[LAT_INDEX] = NO_LATITUDE;
            arr[LNG_INDEX] = NO_LONGITUDE;
            return arr;
        }


        // If nothing was got from the query. "[]" means no return.
        if (queryResult == null || queryResult.equals("[]")) {
            arr[LAT_INDEX] = NO_LATITUDE;
            arr[LNG_INDEX] = NO_LONGITUDE;
            return arr;
        }

        Object obj = JSONValue.parse(queryResult);
        //log.debug("obj=" + obj);

        if (obj instanceof JSONArray) {
            JSONArray array = (JSONArray) obj;
            if (array.size() > 0) {
                JSONObject jsonObject = (JSONObject) array.get(0);

                String lon = (String) jsonObject.get("lon");
                String lat = (String) jsonObject.get("lat");
                //log.debug("lon=" + lon);
                //log.debug("lat=" + lat);
                //res.put("lon", Double.parseDouble(lon));
                //res.put("lat", Double.parseDouble(lat));
                arr[LAT_INDEX] = Double.parseDouble(lat);
                arr[LNG_INDEX] = Double.parseDouble(lon);

            }
        }

        return arr;
        //return res;
    } // end of getCoordinates()
} // end of class OpenStreetMapUtils
























