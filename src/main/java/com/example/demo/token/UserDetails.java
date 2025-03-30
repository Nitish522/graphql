package com.example.demo.token;

public class UserDetails {


    public UserDetails(String userName) {
        this.userName = userName;
    }

    public String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
