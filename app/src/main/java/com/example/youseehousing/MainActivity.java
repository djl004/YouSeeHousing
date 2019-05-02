package com.example.youseehousing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.youseehousing.forlisting.Listing;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

                signIn(email,pw);
            }
        });
    }

    private void signupFxn() {
        Intent myIntent = new Intent(MainActivity.this, SignUpPage.class);
        startActivity(myIntent);
    }

    private void logInFxn() {
        Intent myIntent = new Intent(MainActivity.this, MainHousingListing.class);
        startActivity(myIntent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   private void signIn(String email,String password){
        Log.d(TAG,"signIn:" + email);
        if(password.length() == 0) {
           Toast.makeText(getApplicationContext(), "Input needs to be checked",
                   Toast.LENGTH_LONG).show();
       }else if(email.length() == 0){
            Toast.makeText(getApplicationContext(), "Input needs to be checked",
                    Toast.LENGTH_LONG).show();
       }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this
                , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            logInFxn();
                        }else {
                            Log.w(TAG,"signInWithEmail:failure",task.getException());
                            Toast.makeText(getApplicationContext(), "Input needs to be checked",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
   }
}
