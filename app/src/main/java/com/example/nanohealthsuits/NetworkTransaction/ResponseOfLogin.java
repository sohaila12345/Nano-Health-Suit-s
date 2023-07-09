package com.example.nanohealthsuits.NetworkTransaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseOfLogin {

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    User user = new User();

    public String getUserName()
    {
        return user.getUsername();
    }
    public String getPassword()
    {
        return user.getPassword();
    }
}
