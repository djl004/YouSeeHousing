package com.example.youseehousing;

import android.accounts.Account;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.youseehousing.forlisting.Listing;

public class AccountCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
    }

    public void finishedonClick (View view) {
        Intent myIntent = new Intent(AccountCreation.this, Listing.class);
        startActivity(myIntent);
    }

}
