package edu.brynmawr.cmsc353.elevateproject.models;

import android.os.Parcelable;

import org.parceler.Parcel;

@Parcel
public class User {
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private String userId;


    public User(){}
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUserId() {
        return userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
