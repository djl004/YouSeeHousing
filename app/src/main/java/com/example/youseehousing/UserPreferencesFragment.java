package com.example.youseehousing;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserPreferencesFragment extends Fragment {

    private static final String TAG = "UserProfileFragment";
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
    View view;

    public UserPreferencesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_preferences, container, false);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        Uid = user.getUid();

        otherInput = view.findViewById(R.id.other);
        noiseInput = view.findViewById(R.id.noise);
        smokingInput = view.findViewById(R.id.smoking);
        wakeInput = view.findViewById(R.id.wake);
        sleepInput = view.findViewById(R.id.sleep);
        guestInput = view.findViewById(R.id.guest);

        update = view.findViewById(R.id.update);

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
        // Inflate the layout for this fragment
        return view;
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            uInfo = new Account();
            // TODO: Add error checks here in case something returns null.
            // TODO: For example try username b@b.com password bbbbbb
            // Log.d(TAG,"Before set name: " + ds.child(Uid).getValue(Account.class).getName());
            uInfo.setName( ds.child(Uid).getValue(Account.class).getName()); //set the name
            uInfo.setEmail(ds.child(Uid).getValue(Account.class).getEmail()); //set the email
            uInfo.setOther(ds.child(Uid).getValue(Account.class).getOther()); //set the other
            uInfo.setSmoking(ds.child(Uid).getValue(Account.class).getSmoking());
            uInfo.setWake(ds.child(Uid).getValue(Account.class).getWake());
            uInfo.setSleep(ds.child(Uid).getValue(Account.class).getSleep());
            uInfo.setGuest(ds.child(Uid).getValue(Account.class).getGuest());
            uInfo.setNoise(ds.child(Uid).getValue(Account.class).getNoise());

            //uInfo.setFavorites(ds.child(Uid).getValue(Account.class).getFavorites()); // set the favorites
            Log.d(TAG, "name:" + ds.child(Uid).getValue());
            //display all the information
            Log.d(TAG, "showData: name: " + uInfo.getName());
            Log.d(TAG, "showData: email: " + uInfo.getEmail());
            Log.d(TAG, "showData: favorites: " + uInfo.getFavorites());

            final TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(uInfo.getName());
            final TextView userEmail = (TextView) view.findViewById(R.id.email);
            userEmail.setText(uInfo.getEmail());

            //set the database selection

            smokingInput.setSelection(getIndex(smokingInput,uInfo.getSmoking(),"smokingList"));
            guestInput.setSelection(getIndex(guestInput,uInfo.getGuest(),"guestList"));
            wakeInput.setSelection(getIndex(wakeInput,uInfo.getWake(),"bedtimeList"));
            sleepInput.setSelection(getIndex(sleepInput,uInfo.getSleep(),"bedtimeList"));
            noiseInput.setSelection(getIndex(noiseInput,uInfo.getNoise(),"noiseList"));
            otherInput.setText(uInfo.getOther());


        }
    }

    private void updateFxn(){

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
    }

    private int getIndex(AppCompatSpinner spinner,String value,String categories){
        String smokingArray[] = new String[]{"Yes","No"};
        String bedTimeArray[] = new String[]{"1 AM", "2 AM", "3 AM", "4 AM","5 AM", "6 AM", "7 AM",
                "8 AM","9 AM", "10 AM", "11 AM", "12 AM", "1 PM", "2 PM","3 PM", "4 PM", "5 PM",
                "6 PM","7 PM", "8 PM","9 PM", "10 PM","11 PM", "12 PM"};
        String guestArray[] = new String[]{"No guests ever", "Guest are fine as long as im told before",
        "Guest are always allowed"};
        String noiseArray[] = new String[]{"Whisper Only", "Normal Indoor Voice", "Shout"};
        //detect the list being use

        String list[] = new String[0];
        if(categories.equals("smokingList")){
            list = smokingArray;
        }
        else if(categories.equals("bedtimeList")){
            list = bedTimeArray;
        }
        else if(categories.equals("guestList")){
            list = guestArray;
        }
        else if(categories.equals("noiseList")){
            list = noiseArray;
        }

        for(int i =0; i < list.length; i++){
            if(value != null) {
                if (list[i].equals(value)) {
                    return i;
                }
            }
            else{
                Log.d(TAG,"NO VALUE BEING GET");
            }
        }
        Log.d(TAG,"NO MATCH: " + value);
        return 0;
    }

}
