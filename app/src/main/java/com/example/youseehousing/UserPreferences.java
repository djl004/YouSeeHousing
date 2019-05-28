package com.example.youseehousing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserPreferences extends AppCompatActivity {

    private static final String TAG = "UserProfile";
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String Uid,smoking,noise,wake,sleep,guest,other;

    //Spinner input
    private AppCompatSpinner smokingInput,noiseInput,wakeInput,sleepInput,guestInput;
    private EditText otherInput;
    private Button update;

    Account uInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        //declare all the variables
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        Uid = user.getUid();
        update = findViewById(R.id.doneUserPreferences);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFxn();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG,"user signed in" + user.getUid());
                } else{
                    Log.d(TAG,"unsuccessful sign in");
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                Log.d(TAG, "showData called");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.");
            }
        });
    }


    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            uInfo = new Account();
           // Log.d(TAG,"Before set name: " + ds.child(Uid).getValue(Account.class).getName());
            uInfo.setName( ds.child(Uid).getValue(Account.class).getName()); //set the name
            uInfo.setEmail(ds.child(Uid).getValue(Account.class).getEmail()); //set the email
            uInfo.setFavorites(ds.child(Uid).getValue(Account.class).getFavorites()); // set the favorites
            Log.d(TAG, "name:" + ds.child(Uid).getValue());
            //display all the information
            Log.d(TAG, "showData: name: " + uInfo.getName());
            Log.d(TAG, "showData: email: " + uInfo.getEmail());
            Log.d(TAG, "showData: favorites: " + uInfo.getFavorites());

            final TextView name = (TextView) findViewById(R.id.name);
            name.setText(uInfo.getName());
            final TextView userEmail = (TextView) findViewById(R.id.email);
            userEmail.setText(uInfo.getEmail());

        }
    }
/*
    public void doneUserPreferencesOnClick(View view) {
        Intent myIntent = new Intent(UserPreferences.this, MainActivity.class);
        startActivity(myIntent);
        Toast.makeText(getApplicationContext(), "Please log in",
                Toast.LENGTH_LONG).show();
    }
    */

    private void updateFxn(){
        otherInput = findViewById(R.id.other);
        noiseInput = findViewById(R.id.noise);
        smokingInput = findViewById(R.id.smoking);
        wakeInput = findViewById(R.id.wake);
        sleepInput = findViewById(R.id.sleep);
        guestInput = findViewById(R.id.guest);

        other = otherInput.getText().toString();
        noise = noiseInput.getSelectedItem().toString();
        smoking = smokingInput.getSelectedItem().toString();
        wake = wakeInput.getSelectedItem().toString();
        sleep = sleepInput.getSelectedItem().toString();
        guest = guestInput.getSelectedItem().toString();


        myRef.child("users").child(Uid).child("other").setValue(other);
        myRef.child("users").child(Uid).child("noise").setValue(noise);
        myRef.child("users").child(Uid).child("smoking").setValue(smoking);
        myRef.child("users").child(Uid).child("wake").setValue(wake);
        myRef.child("users").child(Uid).child("sleep").setValue(sleep);
        myRef.child("users").child(Uid).child("guest").setValue(guest);

        Intent myIntent = new Intent(UserPreferences.this, MainActivity.class);
        startActivity(myIntent);
        Toast.makeText(getApplicationContext(), "Please log in",
                Toast.LENGTH_LONG).show();

    }
}
