package com.example.youseehousing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class MainActivity extends AppCompatActivity {

    private Button signUp, logIn;
    private FirebaseAuth mAuth;
    private static final String TAG = "login";


    String email, pw;
    EditText emailInput, pwInput;
    TextView pwRecovery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = findViewById(R.id.signup);
        logIn = findViewById(R.id.login);
        emailInput = findViewById(R.id.loginUsername);
        pwInput = findViewById(R.id.password);
        pwRecovery = (TextView) findViewById(R.id.forgotPwButton);
        mAuth = FirebaseAuth.getInstance();

        logIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                email = emailInput.getText().toString().toLowerCase();
                pw = pwInput.getText().toString();
                //when no inputs
                if(email.length() == 0 || pw.length() == 0){
                    Toast.makeText(getApplicationContext(), "Please enter email and password",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    signIn(email, pw);
                }
            }
        });

        pwRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                Intent recover = new Intent(MainActivity.this, SignUpPage.class);
                startActivity(recover);
            }
        });

        // When "SIGN UP" button is clicked.
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, SignUpPage.class);
                startActivity(myIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


   private void signIn(String email, String password){
        Log.d(TAG,"signIn:" + email);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this
                , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            Intent myIntent = new Intent(MainActivity.this, ActivityFragmentOrigin.class);
                            startActivity(myIntent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() { // If authentication fails
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "signInWithEmail:failure");

                String errorCode;
                // Check for invalid email or password
                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    errorCode =  ((FirebaseAuthInvalidCredentialsException) e).getErrorCode();

                    if (errorCode.equals("ERROR_INVALID_EMAIL")) {
                        Toast.makeText(getApplicationContext(), "Email address is not in a valid format.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid password",
                                Toast.LENGTH_LONG).show();
                    }
                }
                // Check for invalid user
                else if (e instanceof FirebaseAuthInvalidUserException) {

                    errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();

                    if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                        Toast.makeText(getApplicationContext(), "No matching account found",
                                Toast.LENGTH_LONG).show();
                    } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                        Toast.makeText(getApplicationContext(), "User account has been disabled",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "An error occurred",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
   }
}
