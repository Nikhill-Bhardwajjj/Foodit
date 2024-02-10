package com.lihkin16.util;

import android.app.Application;

public class AdminApi extends Application {



    private  String username ;
    private String userId ;

    private static AdminApi instance ;

    public static AdminApi getInstance()
    {
        if(instance== null )

            instance = new AdminApi();
        return instance ;


    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }
}
