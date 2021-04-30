package edu.brynmawr.cmsc353.elevateproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import edu.brynmawr.cmsc353.elevateproject.models.User;

public class NotificationActivity extends AppCompatActivity {
    private User currentUser;
   // static List<JSONObject> requests_obj = new ArrayList<JSONObject>();

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    List<String> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
//        RecyclerView notificationList = findViewById(R.id.notificationList);
//        notifications = new ArrayList<String>();
//
//        NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationActivity.this, notifications);
//        notificationList.setAdapter(notificationAdapter);
//        notificationList.setLayoutManager((new LinearLayoutManager(this)));
//
//        //get list of requests from server


    }
}
