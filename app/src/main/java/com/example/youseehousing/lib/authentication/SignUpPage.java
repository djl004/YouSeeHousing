package com.example.youseehousing.lib.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.youseehousing.lib.fragment.ActivityFragmentOrigin;
import com.example.youseehousing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpPage extends AppCompatActivity {
    EditText emailInput, pwInput, pwInput2, fNameInput, lNameInput, genderInput,dofInput;
    DatePicker dofInput2;
    String email, pw, confirmPw, fName, lName, dof, gender, uid;

    //used to store all user info for firebase database
    //Account newUser = new Account();

    //declare auth for email and password setup
    private FirebaseAuth mAuth;
    //setup the database to store user info
    private DatabaseReference mDatabase;

    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //grab inputs from user
        emailInput = findViewById(R.id.createEmail);
        pwInput = findViewById(R.id.createPw);
        pwInput2 = findViewById(R.id.createPw2);
        fNameInput = findViewById(R.id.firstname);
        lNameInput = findViewById(R.id.lastname);
        dofInput = findViewById(R.id.dateofbirth);
        genderInput = findViewById(R.id.gender);

        //initialize auth
        mAuth = FirebaseAuth.getInstance();
        //initialize mDatabase
        mDatabase = FirebaseDatabase.getInstance().getReference("users/");

    }


    public void createAccountOnClick(View view) {

        email = emailInput.getText().toString().toLowerCase();
        pw = pwInput.getText().toString();
        confirmPw = pwInput2.getText().toString();
        fName = capFirstChar(fNameInput.getText().toString());
        lName = capFirstChar(lNameInput.getText().toString());
        dof = dofInput.getText().toString();
        gender = capFirstChar(genderInput.getText().toString());


        // Email address is not entered
        if(email.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your email address.",
                    Toast.LENGTH_LONG).show();
        }
        // Email address is not in a valid format, either missing '@' or not 3 chars after '.'
        else if(!validFormat(email)) {
            Toast.makeText(getApplicationContext(), "Email address is not in a valid format." ,
                    Toast.LENGTH_LONG).show();
        }
        // Check if the password matches with the confirm password.
        else if(pw.length() != confirmPw.length() || !pw.equals(confirmPw.substring(0, pw.length()))) {
            Toast.makeText(getApplicationContext(),"Password does not match.",
                    Toast.LENGTH_LONG).show();
        }
        // Check if both the first and last name are entered
        else if(fName.length() == 0 || lName.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your first and last name.",
                    Toast.LENGTH_LONG).show();
        }
        // Check if date of birth is entered - Still need to work on the format for this!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        else if(dof.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your date of birth.",
                    Toast.LENGTH_LONG).show();
        }
        /** Suggested replacement for above else if case
         else if(!validDate(dof)){
         Toast.makeText(getApplicationContext(), "Please enter your date of birth in a format similar to this: 01/30/1999",
         Toast.LENGTH_LONG).show();
         }
         */

        // Check if gender is entered
        else if(gender.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your city you're living.",
                    Toast.LENGTH_LONG).show();
        }
        // If everything is good, move onto the next screen
        else {
            //Intent myIntent = new Intent(SignUpPage.this, MainHousingListing.class);
            //record down the information into account.txt
            createAccount();
        }
    }

    private void  createAccount(){
        Log.d(TAG,"Creating an account: " + email);

        mAuth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(this
                , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG,"createUserWithEmail:success");
                            uid = mAuth.getInstance().getCurrentUser().getUid();
                            Log.d(TAG,uid);
                            profileSetUp();
                            Intent myIntent = new Intent(SignUpPage.this, ActivityFragmentOrigin.class);
                            startActivity(myIntent);

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() { // If authentication fails
            @Override
            public void onFailure(@NonNull Exception e) {

                // Check for email already in use
                if(e instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText(getApplicationContext(), "\t\t\tUser email already in use" +
                            "\nPlease use back button to log in.", Toast.LENGTH_LONG).show();
                }
                // Check if user typed in more than 6 characters for their password
                else if (e instanceof FirebaseAuthWeakPasswordException) {
                    Toast.makeText(getApplicationContext(), ((FirebaseAuthWeakPasswordException) e).getReason()
                            , Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void profileSetUp(){
        Log.d(TAG,"Entering profilesetup#1");
        Account user = new Account();
        user.setName(fName + " " + lName);
        user.setGender(gender);
        user.setEmail(email);
        user.setBirth(dof);
        user.setOther("");
        user.setWake("");
        user.setSleep("");
        user.setGuest("");
        user.setSmoking("");
        //user.setFavorites(new ArrayList<String>());
        //Log.d(TAG,  user.getBirth());
        Log.d(TAG, uid);

        Log.d(TAG,"Entering profilesetup#2");
        //write in to firebase
        Log.d(TAG,uid);
        mDatabase.child(uid).setValue(user);

        Log.d(TAG,"Entering profilesetup#3");
    }

    private String getUid(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            for(UserInfo profile : user.getProviderData()) {
                return profile.getUid();
            }
        }
        return "";
    }


    // This function checks if the input username is a valid email address format
    public boolean validFormat(String email) {

        // Find '@' from the input email. indexOf() returns -1 if the char is found in the string.
        int indexOfAt = email.indexOf('@');
        int indexOfDot = email.indexOf('.', indexOfAt);
        // Check if '@' is included && in their domain, we should expect more than
        // 1 character after '.' (ie. .com, .edu, .gov and so on).
        return (indexOfAt != -1) && (indexOfDot != -1) && ((email.length() - 1 - indexOfDot) > 1);
    }

    public String capFirstChar(String name) {
        int indexOfSpace = name.indexOf(' ');
        if(indexOfSpace != -1) {
            return name.substring(0,1).toUpperCase() +
                    name.substring(1, indexOfSpace).toLowerCase() + " " +
                    capFirstChar(name.substring(indexOfSpace+1));
        }
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }


    /**
     * NOTE: How much date-checking do you guys want? So far, I have it so that the format is
     *       checked. Do you actually want to check that the date is reasonable (ie. not born in
     *       future or on January 41 or something)? It seems like we would have better things to do.
     *
     * Check for valid Date.
     *
     * Should be in this format: Month/Day/Year, like "01/30/1999"
     *
     * @param date: String to check for valid format.
     * @return
     */
    public static boolean validDate(String date){


        // Date-checking constants
        final int DATE_LENGTH = 10;               //num of chars in date
        final String SLASH_CHAR = "/";            // The slash char


        // local variables to parse the input
        int month = 0;
        int day = 0;
        int year = 0;


        // If the date has incorrect number of characters, return false
        if(date.length() != DATE_LENGTH){
            return false;
        }

        // Date number have "(two digits)/(two digits)/four digits". If not, return false
        // First, check that the slash chars are in the right place (one at index 2, another at 5)
        if(!(date.substring(2, 3).equals(SLASH_CHAR) && date.substring(5, 6).equals(SLASH_CHAR)) ){
            return false;
        }

        // Check that the other digits are actually digits
        try{
            month = Integer.parseInt(date.substring(0, 2));     // month begins on index 0
            day = Integer.parseInt(date.substring(3, 5));       // day begins on index 3
            year = Integer.parseInt(date.substring(6, 10));     // year begins on index 6
        } catch(Exception e){
            return false;
        }

        //return true, because the date is valid
        return true;
    } // end of validDate()

}
