package com.example.youseehousing.forlisting;


import java.util.ArrayList;         // Want to use ArrayList
import java.lang.String;            // To use Strings








/**
 * Summary: The Listing object represents a single listing. Some of the instance variables can be
 *          imported straight from the database while others may need to be calculated on-the-fly.
 *          The Listings will be sorted or filtered as the user desired and then displayed.
  */
public class Listing implements Comparable<Listing>{

    // A list of animals allowed
    public static final String DOG = "dog";
    public static final String CAT = "cat";


    // Instance variables (General info)
    public int id;                  // Maybe we would like a way to refer to a specific object
    public String address;          // TODO: REVIEW: String? Maybe another formatting for address?
    public String phoneNum;         // A phone-number to contact landlord. Format: (XXX)-XXX-XXXX
    public String email;            // An email address to contact the landlord
    public int numOfFilterMatching; // meant for organization of results, “fuzzy search”

    // Instance variables (Moderation)
    public int spam = 0;            // Number of times listing has been flagged as spam
    public int badListing = 0;      // Number of times listing has been flagged as a bad Listing
    public boolean hide = false;            // Whether or not to hide (should be unique to user)
    public boolean pets;            // whether or not they allow pets
    public ArrayList <String> animals = new ArrayList();    // What pets are allowed.

    // Instance variables (filtering and sorting)
    public double distance;          // Distance from user (should be unique to user)
    public double price;             // Rent per month
    public double size;              // Size of the listing, in sq.ft.
    public boolean hasUtils;         // Are the utilities factored into the rent?
    public boolean hasWD;            // has a washer/dryer
    public boolean hasFurniture;     // Whether or not it’s furnished
    public int numBaths;             // number of bathrooms
    public int numRooms;             // number of rooms
    public int numVacancies;         // number of vacancies
    public boolean hasPets;          // Whether or not pets are allowed
    public int leaseDuration;        // The duration of the lease, in months

    // Instance variables (needed for calculating this.distance)
    public double lat = 0;               // Latitude of the house's address
    public double lng = 0;               // Longitude of the house's address


    /**
     * Constructor.
     * Notes: I think we should create the object first and then fill in whatever parameters are
     *        necessary afterwards. I highly doubt that all the instance variables can be filled in.
     */
    public Listing(String newAddress){
        this.address = newAddress;  // the id should be based off of the address
    }

    public Listing() {}

    public int getId() {
        return id;
    }

    public boolean setId(int newId){
        this.id = newId;
        return true;    // this is not finished, this should check for id collisions(we may not need this)
    }


    public String getAddress() {
        return address;
    }

    public boolean setAddress(String newAddress){
        this.address = newAddress;
        return true;    // this should be checking if the address exists or not
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumOfFilterMatching() {
        return numOfFilterMatching;
    }

    public void setNumOfFilterMatching(int numOfFilterMatching) {
        this.numOfFilterMatching = numOfFilterMatching;
    }

    public int getSpam() {
        return spam;
    }

    public void setSpam(int spam) {
        this.spam = spam;
    }

    public int getBadListing() {
        return badListing;
    }

    public void setBadListing(int badListing) {
        this.badListing = badListing;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public boolean isPets() {
        return pets;
    }

    public void setPets(boolean pets) {
        this.pets = pets;
    }

    public ArrayList<String> getAnimals() {
        return animals;
    }

    public boolean removeAnimal(String animal) {
        if(!animals.contains(animal))   // check if it exists first
            return false;
        animals.remove(animal);
        return true;
    }

    public boolean addAnimal(String animal){
        if(animals.contains(animal))
            return false;
        animals.add(animal);
        return true;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isHasUtils() {
        return hasUtils;
    }

    public void setHasUtils(boolean hasUtils) {
        this.hasUtils = hasUtils;
    }

    public boolean isHasWD() {
        return hasWD;
    }

    public void setHasWD(boolean hasWD) {
        this.hasWD = hasWD;
    }

    public boolean isHasFurniture() {
        return hasFurniture;
    }

    public void setHasFurniture(boolean hasFurniture) {
        this.hasFurniture = hasFurniture;
    }

    public int getNumBaths() {
        return numBaths;
    }

    public void setNumBaths(int numBaths) {
        this.numBaths = numBaths;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public int getNumVacancies() {
        return numVacancies;
    }

    public void setNumVacancies(int numVacancies) {
        this.numVacancies = numVacancies;
    }

    public boolean isHasPets() {
        return hasPets;
    }

    public void setHasPets(boolean hasPets) {
        this.hasPets = hasPets;
    }

    public int getLeaseDuration() {
        return leaseDuration;
    }

    public void setLeaseDuration(int leaseDuration) {
        this.leaseDuration = leaseDuration;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public int compareTo(Listing anotherListing){
        return this.getNumOfFilterMatching() - anotherListing.getNumOfFilterMatching();
    }




    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }



} // end of public class Listing


























