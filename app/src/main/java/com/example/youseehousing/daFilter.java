package com.example.youseehousing;

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
public class daFilter {

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

    private static daFilter theFilter;

    private daFilter(){

    }

    public static daFilter getInstance(){
        if(theFilter == null){
            theFilter = new daFilter();
        }
        return theFilter;
    }

    public boolean passes(ListingDetails pending){
        String delims = "[-]+";
        String[] bounds;
        String[] boundsOfPrice;

        if(priceMin != null && priceMax != null){
            int min = Integer.parseInt(priceMin);
            int max = Integer.parseInt(priceMax);

            String unPainify = pending.getPrice();
            unPainify = unPainify.replace(",", "");
            unPainify = unPainify.replace("$", "");
            unPainify = unPainify.replace(" ", "");
            boundsOfPrice = unPainify.split(delims);
            int minPrice = Integer.parseInt(boundsOfPrice[0]);
            if(boundsOfPrice.length == 1){
                if(minPrice < min || minPrice > max) return false;
            }
            else {
                int maxPrice = Integer.parseInt(boundsOfPrice[1]);
                if(min < maxPrice || max > minPrice) return false;
            }
        }

        if(distance != null){

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

        if(leaseLength != null){

        }

        if(beds != null){

        }

        if(sqftMin != null && sqftMax != null){
            int min = Integer.parseInt(sqftMin);
            int max = Integer.parseInt(sqftMax);
            String unPainify = pending.getDim();
            unPainify = unPainify.replace(" Sq Ft", "");
            bounds = unPainify.split(delims);
            int minSize = Integer.parseInt(bounds[0]);
            if(bounds.length == 1){
                if(minSize < min || minSize > max) return false;
            }
            else {
                int maxSize = Integer.parseInt(bounds[1]);
                if(min < maxSize || max > minSize) return false;
            }
        }

        if(furnished != null){
            if(furnished.equals("true")) {
                if(pending.getFurnished().contains("No parking")) return false;
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

    public static daFilter getTheFilter() {
        return theFilter;
    }

    public static void setTheFilter(daFilter theFilter) {
        daFilter.theFilter = theFilter;
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
}
