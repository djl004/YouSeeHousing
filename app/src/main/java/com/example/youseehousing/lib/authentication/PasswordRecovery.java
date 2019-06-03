package com.example.youseehousing.lib.authentication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.example.youseehousing.R;

public class PasswordRecovery extends AppCompatActivity {
    EditText emailInput;
    String email = "Helloooooooooooooooooooooooooooooooooooooooo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);


        Log.d("recovery", "user Email: " + email);
    }
}
