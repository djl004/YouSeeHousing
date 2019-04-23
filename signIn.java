import java.io.*;
import java.util.*;



public class signIn {

/* Should we implement how we access user emails and passwords?
 * I feel like there should be easier way (ie. using a library).
 * And since we are not actually letting bunch of people use our app.
 * Storing user account info in a vector won't be that slow.
 * For this one, I'll just iterate them linearly.
 *
 * Nevertheless, here are my ideas in terms of storing/accessing user info
 * 1. use the email domains as a hash key. Ex) gmail.com
 * 2. hashmap inside the domain hashmap
 * 3. If the domain and the ASCII sum of email username is the same,
 *    we won't have that many accounts to search through.
 *
 * What we need to resolve:
 * 1. We need a check for an invalid domain
 * 2. We need to hide user password when they type. 
 *    (Might just work easily in Android studio)
 *
 * */




  public static boolean searchUser(String email, String pw) {

    String accountInfo;
    
    try { 
      File info = new File("./accounts.txt");
      Scanner sc = new Scanner(info);

      while(sc.hasNextLine()) {
        // Entire line containing both email and pw
        accountInfo = sc.nextLine(); 

        // ID check (This is not the most precise/safe check, 
        // but let's move on just for now)
        if(email.equals(accountInfo.substring(0, email.length()))) {

          // Password check
          if(pw.equals(accountInfo.substring(email.length() + 1))) {
            System.out.println("Welcome back! You are logged in!");
            return true;
          }
          System.err.println("\n*** Password does not match ***");
          return false;
        } // End of checking email
      } // End of while-loop 
    // Catch a possible error thrown by Scanner 
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.err.println("\n*** Username does not exist ***");
    return false;
  }

  // This function checks if the input username is a valid email address format
  public static boolean validFormat(String email) {

    // Locate '@' in the input username. It returns -1 if not found.
    int indexOfAt = email.indexOf('@');

    // Check if '@' is included && in their domain, we should expect exactly
    // 3 chars after '.' (ie. .com, .edu, .gov and so on).
    if(indexOfAt != -1 && 
       email.length() - 1 - email.indexOf('.', indexOfAt) == 3) {
      // Valid format
      return true;
    }
      
    System.err.println("*** Invalid format for username ***");
    System.err.println("Example: example@email.com");

    return false;
  }

  public static void main (String [] args) {

    int numTrial = 0;
    String email, password;

    do {
      if(numTrial > 0)
        System.out.println("Please try again..\n");

      System.out.println("Enter your email:");
      Scanner scanner = new Scanner(System.in);
      email = scanner.nextLine();

      System.out.println("Enter your password:");
      scanner = new Scanner(System.in);
      password = scanner.nextLine();

      numTrial++;
    } while (! (validFormat(email) && searchUser(email, password)));
 
    System.err.println("Exiting program..");

  } // End of main

}
