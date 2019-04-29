package com.example.youseehousing.forlisting;



import java.util.ArrayList;         // Want to use ArrayList
import java.util.Collection;        // Use Collection to sort, to reverse a list



/**
 * Contains the static method for sorting.
 */
public class Sorting{


    /**
     * This static method is called to sort a list of listings
     * @param l: The arraylist to sort
     * @param param: The listing parameter to sort by. So far, can be:
     *               - distance, pass in "distance"
     *               - rent, pass in "rent"
     *               - number of vacancies, pass in "numVacancies"
     * @param reverse: False to sort from low->high. True to sort from high->low
     * @return: The sorted list
     */
    public static ArrayList <Listing> sort(ArrayList<Listing> l, String param, boolean reverse){

        // Sort according to the parameter passed in
        if(param.equals("distance")){
            Collections.sort(l, new SortDistance());
        } else if (param.equals("rent")) {
            Collections.sort(l, new SortRent());
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
 * Comparator class to sort by distance
 */
public class SortDistance implements Comparator<Listing>{

    public int compare(Listing a, Listing b){
        return a.getDistance() - b.getDistance();
    }

} // end of public class SortDistance


/**
 * Comparator class to sort by rent
 */
public class SortRent implements Comparator<Listing>{

    public int compare(Listing a, Listing b){
        return a.getRent() - b.getRent();
    }

} // end of public class SortRent


/**
 * Comparator class to sort by number of vacancies
 */
public class SortVacancies implements Comparator<Listing>{

    public int compare(Listing a, Listing b){
        return a.getNumVacancies() - b.getNumVacancies();
    }

} // end of public class SortVacancies



