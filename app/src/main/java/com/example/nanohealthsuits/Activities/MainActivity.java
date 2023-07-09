package com.example.nanohealthsuits.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.nanohealthsuits.R;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "user_session";
    private static final String KEY_USERNAME = "username";
    private static String KEY_TOKEN = "token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE); // Initialize sharedPreferences

        if (isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, AllProducts.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, UserLogin.class);
            startActivity(intent);
        }
    }

    // Method to check if user is already logged in
    private boolean isLoggedIn() {
        String username = sharedPreferences.getString(KEY_USERNAME, null);
        String token = sharedPreferences.getString(KEY_TOKEN, null);
        return (username != null && token != null);
    }
}