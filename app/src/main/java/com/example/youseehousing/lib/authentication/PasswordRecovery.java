package com.example.youseehousing.lib.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.youseehousing.MainActivity;
import com.example.youseehousing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class PasswordRecovery extends AppCompatActivity {
    private final String TAG = "recoverPw";
    private EditText emailInput;
    private Button recoverPw;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
        emailInput = findViewById(R.id.recoverPwEmail);
        recoverPw = findViewById(R.id.recoverPwButton);

        recoverPw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                email = emailInput.getText().toString().toLowerCase();
                //when no inputs
                if(email.length() == 0){
                    Toast.makeText(getApplicationContext(), "Please enter your email",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Log.d(TAG, "User email: " + email);

                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void>task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Password reset email has been sent\n" +
                                                        "\t\t\tto " + email, Toast.LENGTH_LONG).show();
                                        finish();   // Redirect user back to the log-in page
                                    } else {
                                        Log.d(TAG, "Something went wrong (task not successful)" + email);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Recover Password: failure");

                            String errorCode;
                            // Check for invalid email or password
                            if (e instanceof FirebaseAuthInvalidCredentialsException) {

                                errorCode =  ((FirebaseAuthInvalidCredentialsException) e).getErrorCode();

                                if (errorCode.equals("ERROR_INVALID_EMAIL")) {
                                    Toast.makeText(getApplicationContext(), "Email address is not in a valid format.",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "An error occurred: " + errorCode,
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
                                    Toast.makeText(getApplicationContext(), "An error occurred: " + errorCode,
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}
