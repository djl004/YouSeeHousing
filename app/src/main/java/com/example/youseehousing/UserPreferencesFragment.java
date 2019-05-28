package com.example.youseehousing;


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
            smokingInput.setSelection(getIndex(smokingInput,uInfo.getSmoking()));
            guestInput.setSelection(getIndex(guestInput,uInfo.getGuest()));
            wakeInput.setSelection(getIndex(wakeInput,uInfo.getWake()));
            sleepInput.setSelection(getIndex(sleepInput,uInfo.getSleep()));
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

    private int getIndex(AppCompatSpinner spinner,String value){
        String list[] = new String[]{"alpha","beta","charlie","delta"};
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
