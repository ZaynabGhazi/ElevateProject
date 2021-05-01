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
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.brynmawr.cmsc353.elevateproject.models.User;

public class NotificationActivity extends AppCompatActivity {

    private User currentUser;

    static List<JSONObject> results = new ArrayList<JSONObject>();

    List<String> notifications = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        RecyclerView notificationList = findViewById(R.id.notificationList);

        NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationActivity.this, notifications);
        notificationList.setAdapter(notificationAdapter);
        notificationList.setLayoutManager((new LinearLayoutManager(this)));

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
                notifications.add(userInfo);
                notificationAdapter.notifyDataSetChanged();
            }
            notificationAdapter.notifyDataSetChanged();
        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
            Log.d("Notification Activity", "invalid user!");
        }
        notificationAdapter.notifyDataSetChanged();
    }

    private static class RequestTask extends AsyncTask<URL, String, String>{
        @Override
        protected String doInBackground(URL...urls) {
            try {
                URL url = urls[0];

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                Scanner in = new Scanner(url.openStream());
                String response = in.nextLine();

                JSONObject jo = new JSONObject(response);

                return jo.getString("message");

            }
            catch (Exception e) {
                //return e.toString();
                return null;
            }
        }
    }

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
}
