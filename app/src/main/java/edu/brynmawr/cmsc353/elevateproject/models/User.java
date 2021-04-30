package edu.brynmawr.cmsc353.elevateproject.models;

import android.app.DownloadManager;
import android.os.Parcelable;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class User {
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private String userId;
    private ArrayList<String> requests;
    private ArrayList<String> connections;


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

    public ArrayList<String> getRequests() { return requests; }

    public ArrayList<String> getConnections() { return connections; }

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

    public void setRequests(ArrayList<String> requests) { this.requests = requests; }

    public void setConnections(ArrayList<String> connections) { this.connections = connections; }

    public void addRequest(String userId) {
        ArrayList<String> connectionList = getConnections();
        if (!(connectionList.contains(userId))) {
            ArrayList<String> requestList = getRequests();
            requestList.add(userId);
            setRequests(requestList);
        }
    }

    public void acceptConnection(String userId) {
        ArrayList<String> requestList = getRequests();
        ArrayList<String> connectionList = getConnections();
        if (requestList.contains(userId)){
            if (!(connectionList.contains(userId))) {
                connectionList.add(userId);
            }
            requestList.remove(userId);
        }
        setRequests(requestList);
        setConnections(connectionList);
    }


}
