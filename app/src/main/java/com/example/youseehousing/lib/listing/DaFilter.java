package com.example.youseehousing.lib.listing;

import android.util.Log;

/**
 * This static method is called to sort the ArrayList of Listing objects
 *           In general, there are two types of filters.
 *                  1. Filters that check a boolean value. The value should be either the string
 *                         "true" representing the boolean true, or the string "false"
 *                         representing the boolean false.
 *                  2. Filters that check the instance variables to be inside a range. They are
 *                         formatted as such. lowBound + "-" + highBound. If there is no
 *                         lowBound, pass in 0. If there is no highBound, pass in a really large
 *                         number, say MAX_VALUE or something.
 *
 *           Examples:
 *           Key: "priceMin",         Value: "500-1000
 *           Key: "distance",      Value: "0-3"      Note: Must call SetDistance.setDistances() first to use this filter
 *           Key: "numRooms",      Value: "2-4"
 *           Key: "size",          Value: "0-1000"
 *           Key: "numBaths",      Value: "1-3"
 *           Key: "numVacancies",  Value: "1-4"
 *           Key: "leaseDuration", Value: "9-12"
 *           Key: "hasWD",         Value: "true"
 *           Key: "hasPets",       Value: "false"
 *           Key: "hasFurniture",  Value: "true"
 *           Key: "hasUtils",      Value: "true"
 * @return the filtered ArrayList of Listings
 */
public class DaFilter {

    // Important variables:
    // If, for whatever reason, a Listing's address could not be converted to coordinates, use -1.
    public static final double NO_LATITUDE = -1000;  // Latitude goes from -90 to 90
    public static final double NO_LONGITUDE = -1000; // Longitude goes from -180 to 180
    public static final double NO_DISTANCE = -1; // Probably would want to print out "N/A"

    // Constants used for calculations (Haversine formula)
    public static final double R = 6372.8;             // In kilometers
    public static final double KM_CONSTANT = 0.621371; // How many miles in a kilometer

    public static final double[] UCSDCoords = {32.8752631656, -117.236132389};

    public static final int MAXIMUM_MIN_VALUE = 1000;

    private static final String STRING_PARSE = "Parsing string: ";
    private final String TAG = "DaFilter";
    private String priceMin;
    private String priceMax;
    private String distance;
    private String pets;
    private String washerDryer;
    private String parking;
    private String leaseLength;
    private String beds;
    private String baths;
    private String sqftMin;
    private String sqftMax;
    private String furnished;

    private static DaFilter theFilter;

    private DaFilter(){
        Log.i(TAG, "Instancing new DaFilter object!");
    }

    public static DaFilter getInstance(){
        if(theFilter == null){
            theFilter = new DaFilter();
        }
        return theFilter;
    }

    private static String parser(String myInput){
        return myInput.replaceAll("[^\\d]", "");
    }

    /**
     * If a ListingDetails object 'passes' the filter, then return true.
     * @param pending
     * @return
     */
    public boolean passes(ListingDetails pending){
        String delims = "[-]+";
        String[] bounds;
        String[] boundsOfPrice;

        if(priceMin != null || priceMax != null){
            try {
                int min = Integer.parseInt(parser(priceMin));
                int max = Integer.parseInt(parser(priceMax));

                String unPainify = pending.getPrice();
                unPainify = unPainify.replace(",", "");
                unPainify = unPainify.replace("$", "");
                unPainify = unPainify.replace(" ", "");
                boundsOfPrice = unPainify.split(delims);
                int minPrice = Integer.parseInt(parser(boundsOfPrice[0]));
                if (boundsOfPrice.length == 1) {
                    if (minPrice < min || minPrice > max) return false;
                } else {
                    int maxPrice = Integer.parseInt(parser(boundsOfPrice[1]));
                    if (maxPrice < min || minPrice > max) return false;
                }
            }
            catch (NumberFormatException e) {
                Log.e(TAG, "Error parsing int in price");
                e.printStackTrace();
                return false;
            }
        }

        if(distance != null && !distance.equals("Any")){
            double preferredDistanceToUCSD = Double.parseDouble(parser(distance));
            double distanceToUCSD = dBetweenCoords(Double.parseDouble(pending.getCoordinates().get(0)), Double.parseDouble(pending.getCoordinates().get(1)), UCSDCoords[0], UCSDCoords[1]);
            if( distanceToUCSD > preferredDistanceToUCSD)
                return false;
            // null for complication so far
        }

        if(pets != null){
            if(pets.equals("true")) {
                if (pending.getPet().equals("No information on pet policy")) return false;
                if (pending.getPet().toLowerCase().contains("no pet")) return false;
            }
        }

        if(washerDryer != null){
            if(washerDryer.equals("true")){
                if(pending.getWasherDryer().contains("No info")) return false;
            }
        }

        if(parking != null){
            if(parking.equals("true")) {
                if(pending.getParking().contains("No parking")) return false;
            }
        }

        if(leaseLength != null && !leaseLength.equals("Any")){
            String searchTerm = leaseLength.replace(" Month", "");
            searchTerm = searchTerm.replace(" Months", "");
            searchTerm = parser(searchTerm);

            if(!pending.getBuildingLease().contains(searchTerm) && !pending.getUnitLease().contains(searchTerm)) return false;
        }

        if(beds != null && !beds.equals("Any")){
            String searchTerm = beds.replace(" Bed", "");
            Log.i(TAG, STRING_PARSE + searchTerm);
            try {

                boolean studio = false;
                if (searchTerm.contains("Studio")) {
                    searchTerm = searchTerm.replace("1 Studio", "Studio");
                    studio = true;
                }
                if (searchTerm.equals("4+")) {
                    searchTerm = searchTerm.replace("+", "");
                    String lotsOfBeds = Character.toString(pending.getBed().charAt(0));
                    int searchInt = Integer.parseInt(searchTerm);
                    int focusInt = Integer.parseInt(lotsOfBeds);
                    if (focusInt < searchInt) return false;
                }
                else {
                    if (studio) {
                        if (!pending.getBed().contains("Studio")) return false;
                    }
                    if (searchTerm.contains(".5")) {
                        searchTerm = searchTerm.replace(".5", "½");
                    }
                    if (!pending.getBed().contains(searchTerm)) return false;
                }
            }
            catch (NumberFormatException e) {
                Log.e(TAG, "Error parsing int in Beds!");
                e.printStackTrace();
                return false;
            }
        }

        if(baths != null && !baths.equals("Any")){
            String searchTerm = baths.replace(" Bathroom","");
            Log.i(TAG, STRING_PARSE + searchTerm);
            try {
                if (searchTerm.equals("3+")) {
                    searchTerm = searchTerm.replace("+", "");
                    String lotsOfBaths = Character.toString(pending.getBed().charAt(0));
                    int searchInt = Integer.parseInt(searchTerm);
                    int focusInt = Integer.parseInt(lotsOfBaths);
                    if (focusInt < searchInt) return false;
                }
                else {
                    if (searchTerm.contains(".5")) {
                        searchTerm = searchTerm.replace(".5", "½");
                    }
                    if (!pending.getBath().contains(searchTerm)) return false;
                }
            }
            catch (NumberFormatException e) {
                Log.e(TAG, "Error parsing int in Baths!");
                e.printStackTrace();
                return false;
            }
        }

        if(sqftMin != null || sqftMax != null){

            try {
                int min;
                int max;
                if(sqftMin.equals("Any")) min = 0;
                else min = Integer.parseInt(parser(sqftMin));

                if(sqftMax.equals("Any")) max = Integer.MAX_VALUE;
                else max = Integer.parseInt(parser(sqftMax));

                if(sqftMax.contains("+")) max = Integer.MAX_VALUE;
                String unPainify = pending.getDim();
                unPainify = unPainify.replace(" Sq Ft", "");
                bounds = unPainify.split(delims);
                int minSize = Integer.parseInt(parser(bounds[0]));
                if (bounds.length == 1) {
                    if (minSize < min || minSize > max) return false;
                } else {
                    int maxSize = Integer.parseInt(parser(bounds[1]));
                    if (maxSize < min || minSize > max) return false;
                }
            }
            catch (NumberFormatException e) {
                Log.e(TAG, "Error parsing int in sqFt!");
                e.printStackTrace();
                return false;
            }
        }

        if(furnished != null){
            if(furnished.equals("true")) {
                if(pending.getFurnished().equals("false")) return false;
            }
        }

        return true;
    }

    public String getSqftMax() {
        return sqftMax;
    }

    public void setSqftMax(String sqftMax) {
        this.sqftMax = sqftMax;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }

    public String getLeaseLength() {
        return leaseLength;
    }

    public void setLeaseLength(String leaseLength) {
        this.leaseLength = leaseLength;
    }

    public String getBeds() {
        return beds;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public String getBaths() {
        return baths;
    }

    public void setBaths(String baths) {
        this.baths = baths;
    }

    public String getSqftMin() {
        return sqftMin;
    }

    public void setSqftMin(String sqftMin) {
        this.sqftMin = sqftMin;
    }

    public String getFurnished() {
        return furnished;
    }

    public void setFurnished(String furnished) {
        this.furnished = furnished;
    }

    public static DaFilter getTheFilter() {
        return theFilter;
    }

    public static void setTheFilter(DaFilter theFilter) {
        DaFilter.theFilter = theFilter;
    }

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        Log.i(TAG, "Setting distance filter to : " + distance);
        this.distance = distance;
    }

    public String getPets() {
        return pets;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }

    public String getWasherDryer() {
        return washerDryer;
    }

    public void setWasherDryer(String washerDryer) {
        this.washerDryer = washerDryer;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    /**
     * Clear filter by making instance null
     */
    public void resetFilters() {
        Log.i(TAG, "Resetting filters!");
        theFilter = null;
    }

    /**
     * Helper function used to calculate the distance (in miles) between two sets of coordinates
     * Resource: https://rosettacode.org/wiki/Haversine_formula#Java
     *
     * Error case: If any of the coordinates are invalid (-1000), then return -1
     *
     * @return the distance, in miles, between the two sets of coordinates
     */
    public static double dBetweenCoords(double lat1, double lng1, double lat2, double lng2){

        // If any of the coords are invalid, return -1
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


}
