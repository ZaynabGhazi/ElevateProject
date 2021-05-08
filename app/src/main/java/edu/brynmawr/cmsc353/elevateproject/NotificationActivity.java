package edu.brynmawr.cmsc353.elevateproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
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
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.brynmawr.cmsc353.elevateproject.models.User;


public class NotificationActivity extends AppCompatActivity {

    static List<JSONObject> results = new ArrayList<JSONObject>();
    List<String> notifications = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        String requestURL = parseRequestUrl();
        List<String> usersInfo = getUserInfo(requestURL);
        setUpActivity(usersInfo);
    }

//    private static class RequestTask extends AsyncTask<URL, String, String>{
//        @Override
//        protected String doInBackground(URL...urls) {
//            try {
//                URL url = urls[0];
//
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                conn.connect();
//
//                Scanner in = new Scanner(url.openStream());
//                String response = in.nextLine();
//
//                JSONObject jo = new JSONObject(response);
//
//                return jo.getString("message");
//
//            }
//            catch (Exception e) {
//                //return e.toString();
//                return null;
//            }
//        }
//    }

    private static class MyTask extends AsyncTask<URL, Void, Void>{
        @Override
        protected Void doInBackground(URL...urls){
            try {
                URL url = urls[0];

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                Scanner in = new Scanner(url.openStream());
                String response = in.nextLine();

                //JSONObject jo = new JSONObject(response);
                JSONArray array = new JSONArray(response);
                Log.d("Notification Activity", "here!");
                for(int i = 0; i < array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    results.add(obj);
                    Log.d("Notification Activity", "Adding " + i + "th object!");
                }
                return null;

            }
            catch (Exception e) {
                //return e.toString();
                return null;
            }
        }
    }

    private String parseRequestUrl(){
        //get list of requests from intent
        String requestURL = "";
        ArrayList<String> requests = getIntent().getStringArrayListExtra("requests");
        for (int i = 0; i < requests.size(); i++){
            Log.d("Notification Activity", requests.get(i));
            if (i == requests.size()-1){
                requestURL += "request=" + requests.get(i);
            }
            else {
                requestURL += "request=" + requests.get(i) + "&";
            }
        }
        Log.d("Notification Activity", requestURL);
        return requestURL;
    }

    private List<String> getUserInfo(String requestURL){
        List<String> usersInfo = new ArrayList<String>();
        try {
            URL url;
            url = new URL("http://10.0.2.2:3000/api/user/notify?" + requestURL);
            Log.d("Notification Activity", "user url: " + url.toString());
            NotificationActivity.MyTask task = new NotificationActivity.MyTask();
            task.execute(url); // fill results with all requests

            Log.d("Notification Activity", results.size() + " requests");
            for (int i = 0; i < results.size(); i++){
                Log.d("Notification Activity", "searching through results!");
                String userInfo = "";
                JSONObject user = results.get(i);
                userInfo += user.getString("firstname") + " " + user.getString("lastname") + ",";
                userInfo += user.getString("_id");
                Log.d("Notification Activity", userInfo);
                usersInfo.add(userInfo);
            }
        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
            Log.d("Notification Activity", "invalid user!");
        }
        return usersInfo;
    }

    private void setUpActivity(List<String> usersInfo){
        setContentView(R.layout.activity_notification);
        RecyclerView notificationList = findViewById(R.id.notificationList);
        Log.d("Notification Activity", "creating view");
        NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationActivity.this, notifications, getIntent().getStringExtra("id"));
        notificationList.setAdapter(notificationAdapter);
        notificationList.setLayoutManager((new LinearLayoutManager(this)));
        notifications.addAll(usersInfo);
        notificationAdapter.notifyDataSetChanged();
    }
}

//Yutong's code
//public class NotificationActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notification);
//        String requests = getIntent().getStringExtra("currentRequests");
//        try {
//
//            URL url = new URL("http://10.0.2.2:3000/api/user/notify?"+requests);
//            NotifyTask task = new NotifyTask();
//            task.execute(url);
//            JSONArray array = task.get();
//
//            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearNotify);
//            linearLayout.setOrientation(LinearLayout.VERTICAL);
//            for(int i = 0; i<array.length(); i++) {
//                TextView textView = new TextView(this);
//                JSONObject requester = (JSONObject)array.get(i);
//                textView.setText(requester.getString("firstname") + " " + requester.getString("lastname"));
//                Button requestButton = new Button(this) ;
//                requestButton.setText("Accept");
//                requestButton.setTag(requester.getString("_id") ); //set tag to keep track of each requester's id
//                //TODO: set onclick listener for buttons. May refer to FindActivity. You may also need the current user's id, for which you can use putExtra in main activity(see line 108).
//
//                linearLayout.addView(textView);
//                linearLayout.addView(requestButton);
//            }
//        } catch (InterruptedException interruptedException) {
//            interruptedException.printStackTrace();
//        } catch (ExecutionException executionException) {
//            executionException.printStackTrace();
//        } catch (JSONException jsonException) {
//            jsonException.printStackTrace();
//        } catch (MalformedURLException malformedURLException) {
//            malformedURLException.printStackTrace();
//        }
//    }
//
//    private static class NotifyTask extends AsyncTask<URL, JSONArray, JSONArray>{
//        @Override
//        protected JSONArray doInBackground(URL...urls) {
//            try {
//                URL url = urls[0];
//
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                conn.connect();
//
//                Scanner in = new Scanner(url.openStream());
//                String response = in.nextLine();
//
//                JSONArray array = new JSONArray(response);
//
//                return array;
//
//            }
//            catch (Exception e) {
//                //return e.toString();
//                return null;
//            }
//        }
//    }
