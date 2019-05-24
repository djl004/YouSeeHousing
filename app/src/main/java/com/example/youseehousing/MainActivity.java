package com.example.youseehousing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private boolean success;
    private Button signUp, logIn;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";


    String email, pw;
    EditText emailInput, pwInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = findViewById(R.id.signup);
        logIn = findViewById(R.id.login);
        emailInput = findViewById(R.id.loginUsername);
        pwInput = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        // When "SIGN UP" button is clicked.
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupFxn();
            }
        });

        logIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                email = emailInput.getText().toString();
                pw = pwInput.getText().toString();
                //when no inputs
                if(email.length() == 0 || pw.length() == 0){
                    Toast.makeText(getApplicationContext(), "Input needs to be checked",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    signIn(email, pw);
                }
            }
        });
    }

    private void signupFxn() {
        Intent myIntent = new Intent(MainActivity.this, SignUpPage.class);
        startActivity(myIntent);
    }

    private void logInFxn() {
        Intent myIntent = new Intent(MainActivity.this, ActivityFragmentOrigin.class);
        startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


   private void signIn(String email,String password){
//        Log.d(TAG,"signIn:" + email);
//        if(password.length() == 0) {
//           Toast.makeText(getApplicationContext(), "Input needs to be checked",
//                   Toast.LENGTH_LONG).show();
//       }else if(email.length() == 0){
//            Toast.makeText(getApplicationContext(), "Input needs to be checked",
//                    Toast.LENGTH_LONG).show();
//       }
//        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this
//                , new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            logInFxn();
//                        }else {
//                            Log.w(TAG,"signInWithEmail:failure",task.getException());
//                            Toast.makeText(getApplicationContext(), "Input needs to be checked",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
   }
}
