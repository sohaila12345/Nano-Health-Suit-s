package com.example.nanohealthsuits.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nanohealthsuits.NetworkTransaction.CallToApi;
import com.example.nanohealthsuits.NetworkTransaction.Instantiation;
import com.example.nanohealthsuits.NetworkTransaction.RequestToLogin;
import com.example.nanohealthsuits.NetworkTransaction.ResponseOfLogin;
import com.example.nanohealthsuits.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLogin extends AppCompatActivity {
    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "user_session";
    private static final String KEY_USERNAME = "username";
    private static String KEY_TOKEN = "token";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // Initialize views
        userNameEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.btn_continue);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);


        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if (validateInputs(userName, password)) {
                    authentication(userName, password);
                }
            }
        });
    }

    private boolean validateInputs(String userName, String password) {
        if (userName.isEmpty()) {
            userNameEditText.setError("Username is required");
            userNameEditText.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return false;
        }
        return true;
    }

    private void authentication(String userName, String password) {
        RequestToLogin requestToLogin = new RequestToLogin();
        requestToLogin.setUserName(userName);
        requestToLogin.setPassword(password);

        Call<ResponseOfLogin> loginResponseCall = Instantiation.generateCallToAPI().userLogin(userName, password);
        loginResponseCall.enqueue(new Callback<ResponseOfLogin>() {
            @Override
            public void onResponse(Call<ResponseOfLogin> call, Response<ResponseOfLogin> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserLogin.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    KEY_TOKEN = response.body().getToken();
                    saveUserSession(KEY_TOKEN);
                    Intent intent = new Intent(UserLogin.this, AllProducts.class);
                    startActivity(intent);
                } else {
                    if (response.code() == 401) {
                        Toast.makeText(UserLogin.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(UserLogin.this, "Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseOfLogin> call, Throwable t) {
                Toast.makeText(UserLogin.this, "Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                Log.e("ErrorMessage", t.getLocalizedMessage());
            }
        });
    }

    private void saveUserSession( String token) {
        UserSessionManager.getInstance(this).loginUser(token);
    }
}
