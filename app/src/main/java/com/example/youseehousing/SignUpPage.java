package com.example.youseehousing;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public class SignUpPage extends AppCompatActivity {

    String email, pw, confirmPw, fName, lName, dof, city;
    EditText emailInput, pwInput, pwInput2, fNameInput, lNameInput, dofInput, cityInput;

    //declare auth for email and password setup
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        //grab inputs from user
        emailInput = (EditText) findViewById(R.id.createEmail);
        pwInput = (EditText) findViewById(R.id.createPw);
        pwInput2 = (EditText) findViewById(R.id.createPw2);
        fNameInput = (EditText) findViewById(R.id.firstname);
        lNameInput = (EditText) findViewById(R.id.lastname);
        dofInput = (EditText) findViewById(R.id.dateofbirth);
        cityInput = (EditText) findViewById(R.id.city);

        //initialize auth
        mAuth = FirebaseAuth.getInstance();

    }


    public void createAccountonClick(View view) {
        email = emailInput.getText().toString();
        pw = pwInput.getText().toString();
        confirmPw = pwInput2.getText().toString();
        fName = fNameInput.getText().toString();
        lName = lNameInput.getText().toString();
        dof = dofInput.getText().toString();
        city = cityInput.getText().toString();


        // Email address is not entered
        if(email.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your email address.",
                    Toast.LENGTH_LONG).show();
        }
        // Email address is not in a valid format, either missing '@' or not 3 chars after '.'
        else if(!validFormat(email)) {
            Toast.makeText(getApplicationContext(), "Email address is not in a valid format." +
                    "\n\tPlease try again.", Toast.LENGTH_LONG).show();
        }

        // Search if an account already exists with the email entered by the user
        // (currently not functional)
        /*else if(searchUser(email)) {
            Toast.makeText(getApplicationContext(), "User already exists." +
                    "\n\tPlease try again.", Toast.LENGTH_LONG).show();
        }
        */

        // Check if both the password and confirm password are entered
        else if(pw.length() == 0 || confirmPw.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your password and confirm it.",
                     Toast.LENGTH_LONG).show();
        }
        // Check if the password matches with the confirm password.
        else if(pw.length() != confirmPw.length() || !pw.equals(confirmPw.substring(0, pw.length()))) {
            Toast.makeText(getApplicationContext(),"Password does not match." +
                    "\n\tPlease try again.", Toast.LENGTH_LONG).show();
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
        // Check if city is entered
        else if(city.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your city you're living.",
                    Toast.LENGTH_LONG).show();
        }
        // If everything is good, move onto the next screen
        else {
            //Intent myIntent = new Intent(SignUpPage.this, MainHousingListing.class);

            //record down the information into account.txt
            createAccount(email,pw);
            Intent myIntent = new Intent(SignUpPage.this, AccountCreation.class);
            startActivity(myIntent);
        }
    }

    private void createAccount(String email, String password){
        Log.d(TAG,"creatAccount:" + email);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this
                , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG,"createUserWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(),
                                    "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // This function checks if the input username is a valid email address format
    public boolean validFormat(String email) {

        // Find '@' from the input email. indexOf() returns -1 if the char is found in the string.
        int indexOfAt = email.indexOf('@');

        // Check if '@' is included && in their domain, we should expect exactly
        // 3 chars after '.' (ie. .com, .edu, .gov and so on).
        if((indexOfAt != -1) && (email.length() - 1 - email.indexOf('.', indexOfAt) == 3)) {
            return true;     // Email is in a valid format
        }
        return false;   // Email is not in a valid format
    }

/*
    public void recordData(String email, String pw){
        //Open the txt file that store all account information
        final String account = email + " " + encrypt(pw) + "\n" ;
        FileOutputStream outputStream;

        try {
            //data should be stored in to data/data/accounts.txt
            outputStream = openFileOutput("accounts.txt", Context.MODE_APPEND);
            outputStream.write(account.getBytes());
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    // This function checks if the input username is a valid email address format
    public boolean validFormat(String email) {

        // Find '@' from the input email. indexOf() returns -1 if the char is found in the string.
        int indexOfAt = email.indexOf('@');

        // Check if '@' is included && in their domain, we should expect exactly
        // 3 chars after '.' (ie. .com, .edu, .gov and so on).
        if((indexOfAt != -1) && (email.length() - 1 - email.indexOf('.', indexOfAt) == 3)) {
            return true;     // Email is in a valid format
        }
        return false;   // Email is not in a valid format
    }

    public boolean searchUser(String email) {
        //AssetManager assetManager = getAssets();
        InputStream is = null;
        String accountInfo;

        try{
            //is = assetManager.open("accounts.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        try {
            while((accountInfo = reader.readLine()) != null) {

                Log.d("STATE", "line I read is:  " + accountInfo);

                // ID check (This is not the most precise/safe check,
                // but let's move on just for now)
                if(email.equals(accountInfo.substring(0, email.length()))) {
                    return true;
                } // End of checking email
            } // End of while-loop
            // Catch a possible error thrown by Scanner
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("STATE", "*** A new user ***");
        return false;
    }

    private String encrypt(String input){
        //put the String into a char array so we could encrypt each character
        char[] password = input.toCharArray();
        int size = input.length();
        int[] encrypted = new int[input.length()];
        StringBuilder result = new StringBuilder();

        //flip the the order of the characters
        for(int i = 0; i < size; i++){
            //add 1 to the ASCII value of each charactar
            encrypted[i] = password[size -1 - i] + 1;
        }

        for(int i = 0; i < size; i++){
            result.append((char)encrypted[i]);
        }
        return result.toString();
    }
*/
}
