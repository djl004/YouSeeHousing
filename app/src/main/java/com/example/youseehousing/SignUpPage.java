package com.example.youseehousing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpPage extends AppCompatActivity {

    String email, pw, confirmPw, fName, lName, dof, city,Uid;
    EditText emailInput, pwInput, pwInput2, fNameInput, lNameInput, dofInput, cityInput;
    int pass;
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
        //initialize mDatabase
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
            if(createAccount(email,pw) == true) {
                String name = fName + lName;
                //profileSetUp(name, email, dof, Uid);
                Intent myIntent = new Intent(SignUpPage.this, AccountCreation.class);
                startActivity(myIntent);
            }
            else{
                //do nothing
            }
        }
    }

    private boolean createAccount(String email, String password){
        Log.d(TAG,"creatAccount:" + email);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this
                , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG,"createUserWithEmail:success");
                            //record down user Id
                         /*
                            Uid = getUid();
                            */
                            pass = 1;


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(),
                                    "Authentication failed.", Toast.LENGTH_SHORT).show();
                            pass = 0;
                        }
                    }
                });
        if(pass!=1) {
            return false;
        }
        else{
            return true;
        }
    }
/*

   private void profileSetUp(String name,String email, String birth,String Uid){
        Account user = new Account();
        user.setName(name);
        user.setEmail(email);
        user.setBirth(birth);

        if(user.getBirth() == null || Uid == null){
            Toast.makeText(getApplicationContext(),"data not written to account or missing uid",Toast.LENGTH_SHORT);
        }
        else {
            //write in to firebase
            mDatabase.setValue(user);
        }
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
*/

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

}
