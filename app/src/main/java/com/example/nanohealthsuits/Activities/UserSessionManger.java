package com.example.nanohealthsuits.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

class UserSessionManager {
    private static UserSessionManager instance;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "user_session";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_TOKEN = "token";

    private UserSessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized UserSessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserSessionManager(context);
        }
        return instance;
    }

    public void loginUser( String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public void logoutUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();
    }

    public boolean isLoggedIn() {
        String token = sharedPreferences.getString(KEY_TOKEN, null);
        return (token != null);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }
}

