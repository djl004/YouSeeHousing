package com.example.youseehousing.forlisting;



import java.util.ArrayList;         // Want to use ArrayList
import java.util.Collections;       // Use Collections to sort a list
import java.util.Comparator;        // To create Comparator classes for sorting


/**
 * Contains the static method for sorting.
 */
public class Sorting{


    /**
     * This static method is called to sort a list of listings
     * @param l: The ArrayList to sort
     * @param param: The listing parameter to sort by. So far, can be:
     *               - "distance" to sort by distance
     *               - "price" to sort by price
     *               - "numVacancies" to sort by number of vacancies
     * @param reverse: false to sort from low->high. true to sort from high->low
     * @return: The sorted list
     */
    public static ArrayList <Listing> sort(ArrayList<Listing> l, String param, boolean reverse){

        // Sort according to the parameter passed in
        if(param.equals("distance")){
            Collections.sort(l, new SortDistance());
        } else if (param.equals("price")) {
            Collections.sort(l, new SortPrice());
        } else if (param.equals("numVacancies")){
            Collections.sort(l, new SortVacancies());
        }


        // If reverse is True, then reverse the list
        if(reverse){
            Collections.reverse(l);
        }


        // List is sorted, return it
        return l;
    }

} // end of public class Sorting




/**
 * Comparator class used by Collections.sort() to sort by distance
 */
class SortDistance implements Comparator<Listing> {

    public int compare(Listing a, Listing b){


        // Return 1 if a is bigger, -1 if b is bigger. If they are equal, it doesn't matter
        if(a.getDistance() > b.getDistance()){
            return 1;
        }
        else{
            return -1;
        }
    }

} // end of public class SortDistance


/**
 * Comparator class used by Collections.sort() to sort by rent
 */
class SortPrice implements Comparator<Listing>{

    public int compare(Listing a, Listing b){

        // Return 1 if a is bigger, -1 if b is bigger. If they are equal, it doesn't matter
        if(a.getPrice() > b.getPrice()){
            return 1;
        }
        else{
            return -1;
        }
    }

} // end of public class SortRent




/**
 * Comparator class used by Collections.sort() to sort by number of vacancies
 */
class SortVacancies implements Comparator<Listing>{

    public int compare(Listing a, Listing b){

        return a.getNumVacancies() - b.getNumVacancies();
    }

} // end of public class SortVacancies



