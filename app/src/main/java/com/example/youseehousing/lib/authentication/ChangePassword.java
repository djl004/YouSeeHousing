package com.example.youseehousing.lib.authentication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.youseehousing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    private final String TAG = "changePw";
    private EditText currPwInput, newPwInput, newPwConfirmInput;
    private Button changePwButton;
    String currPw, newPw, newPwConfirm;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        currPwInput = findViewById(R.id.currentPw);
        newPwInput = findViewById(R.id.newPw);
        newPwConfirmInput = findViewById(R.id.newPwConfirm);
        changePwButton = findViewById(R.id.changePwButtonFinal);

        changePwButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                currPw = currPwInput.getText().toString();
                newPw = newPwInput.getText().toString();
                newPwConfirm = newPwConfirmInput.getText().toString();

                // check for input validity
                if(currPw.length() == 0){
                    Toast.makeText(getApplicationContext(), "Please enter your current password",
                            Toast.LENGTH_LONG).show();
                }
                else if(newPw.length() == 0){
                    Toast.makeText(getApplicationContext(), "Please enter your new password",
                            Toast.LENGTH_LONG).show();
                }
                else if(newPwConfirm.length() == 0){
                    Toast.makeText(getApplicationContext(), "Please enter your confirm new password",
                            Toast.LENGTH_LONG).show();
                }
                else if(!newPw.equals(newPwConfirm)){
                    Toast.makeText(getApplicationContext(), "New password does not match",
                            Toast.LENGTH_LONG).show();
                }
                else if(newPw.equals(currPw)){
                    Toast.makeText(getApplicationContext(), "New password must be different\n\tfrom the current password",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    updatePw(); // Try updating their password
                }
            }
        });
    }


    private void updatePw() {
        // Get user's credential using their current password they input
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currPw);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    user.updatePassword(newPw).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Password has been updated",
                                        Toast.LENGTH_LONG).show();
                                finish(); // Redirect user back to where they came from

                            } else {
                                Toast.makeText(getApplicationContext(), "Something went wrong",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener(){
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "changePassword:failure");

                            String errorCode;
                            // Check for invalid email or password
                            if (e instanceof FirebaseAuthInvalidCredentialsException) {

                                errorCode =  ((FirebaseAuthInvalidCredentialsException) e).getErrorCode();

                                if (errorCode.equals("ERROR_WEAK_PASSWORD")) {
                                    Toast.makeText(getApplicationContext(), "Password must be more than 6 characters",
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
        }).addOnFailureListener(new OnFailureListener() {   // In case user types in wrong password
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "changePassword:failure");

                String errorCode;
                // Check for invalid email or password
                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    errorCode =  ((FirebaseAuthInvalidCredentialsException) e).getErrorCode();

                    if (errorCode.equals("ERROR_WRONG_PASSWORD")) {
                        Toast.makeText(getApplicationContext(), "Invalid current password",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "An error occurred: " + errorCode,
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    // To make 'ActionBar back button' work the same as the 'hardware back button' in the bottom of screen.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}