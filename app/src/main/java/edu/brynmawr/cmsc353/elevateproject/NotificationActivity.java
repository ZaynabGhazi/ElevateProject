package edu.brynmawr.cmsc353.elevateproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

// Dana's code

//public class NotificationActivity extends AppCompatActivity {
//
//    static List<JSONObject> results = new ArrayList<JSONObject>();
//    List<String> notifications = new ArrayList<String>();
//    Button btnRefresh;
//    String currentUser;
//    ArrayList<String> requests;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        results.clear();
//        Intent intent = getIntent();
//        super.onCreate(savedInstanceState);
//        String requestURL = parseRequestUrl();
//        getExtras(intent);
//        List<String> usersInfo = getUserInfo(requestURL);
//        setUpActivity(usersInfo);
//        btnRefresh = findViewById(R.id.btnRefresh);
//        if(results.size() < 1 || results == null) {
//            refreshPage();
//        }
//
//    }
//
//    private void getExtras(Intent intent){
//        currentUser = intent.getStringExtra("id");
//        requests = intent.getStringArrayListExtra("requests");
//    }
//
//    private void refreshPage(){
//        btnRefresh.setVisibility(View.VISIBLE);
//        btnRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("NotificationActivity", "Refresh button clicked");
//                Intent notificationsIntent = new Intent(NotificationActivity.this, NotificationActivity.class);
//                notificationsIntent.putExtra("id", currentUser);
//                notificationsIntent.putStringArrayListExtra("requests", (ArrayList<String>)requests);
//                finish();
//                startActivity(notificationsIntent);
//            }
//        });
//    }
//
//    private static class MyTask extends AsyncTask<URL, Void, Void>{
//        @Override
//        protected Void doInBackground(URL...urls){
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
//                //JSONObject jo = new JSONObject(response);
//                JSONArray array = new JSONArray(response);
//                Log.d("Notification Activity", "here!");
//                for(int i = 0; i < array.length(); i++){
//                    JSONObject obj = array.getJSONObject(i);
//                    if (results == null) {
//                        results.add(obj);
//                    }
//                    else {
//                        if (results.contains(obj)) {
//                            Log.d("Notification Activity", "found duplicate profile!");
//                        }
//                        else {
//                            Log.d("Notification Activity", "found new profile!");
//                            results.add(obj);
//                        }
//                    }
//                }
//                return null;
//
//            }
//            catch (Exception e) {
//                //return e.toString();
//                return null;
//            }
//        }
//    }
//
//    private String parseRequestUrl(){
//        //get list of requests from intent
//        String requestURL = "";
//        ArrayList<String> requests = getIntent().getStringArrayListExtra("requests");
//        for (int i = 0; i < requests.size(); i++){
//            Log.d("Notification Activity", requests.get(i));
//            if (i == requests.size()-1){
//                requestURL += "request=" + requests.get(i);
//            }
//            else {
//                requestURL += "request=" + requests.get(i) + "&";
//            }
//        }
//        Log.d("Notification Activity", requestURL);
//        return requestURL;
//    }
//
//    private List<String> getUserInfo(String requestURL){
//        List<String> usersInfo = new ArrayList<String>();
//        try {
//            URL url;
//            url = new URL("http://10.0.2.2:3000/api/user/notify?" + requestURL);
//            Log.d("Notification Activity", "user url: " + url.toString());
//            NotificationActivity.MyTask task = new NotificationActivity.MyTask();
//            task.execute(url); // fill results with all requests
//
//            Log.d("Notification Activity", results.size() + " requests");
//            for (int i = 0; i < results.size(); i++){
//                Log.d("Notification Activity", "searching through results!");
//                String userInfo = "";
//                JSONObject user = results.get(i);
//                userInfo += user.getString("firstname") + " " + user.getString("lastname") + ",";
//                userInfo += user.getString("_id");
//                Log.d("Notification Activity", userInfo);
//                usersInfo.add(userInfo);
//            }
//        } catch (MalformedURLException | JSONException e) {
//            e.printStackTrace();
//            Log.d("Notification Activity", "invalid user!");
//        }
//        return usersInfo;
//    }
//
//    private void setUpActivity(List<String> usersInfo){
//        setContentView(R.layout.activity_notification);
//        RecyclerView notificationList = findViewById(R.id.notificationList);
//        Log.d("Notification Activity", "creating view");
//        NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationActivity.this, notifications, getIntent().getStringExtra("id"));
//        notificationList.setAdapter(notificationAdapter);
//        notificationList.setLayoutManager((new LinearLayoutManager(this)));
//        for (int i = 0; i < usersInfo.size(); i++){
//            if (notifications.size() == 0){
//                notifications.addAll(usersInfo);
//            }
//            else {
//                if (notifications.contains(usersInfo.get(i))) {
//                    Log.d("Notification Activity", "request already in array!");
//                } else {
//                    notifications.add(usersInfo.get(i));
//                }
//            }
//        }
//
//        Log.d("Notification Activity", "There are " + requests.size() + " notifications");
//        notificationAdapter.notifyDataSetChanged();
//        Log.d("Notification Activity", "Data set changed");
//    }
//}

public class NotificationActivity extends AppCompatActivity {

    String requests;
    String userId;
    String newConnections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        requests = getIntent().getStringExtra("currentRequests");
        userId = getIntent().getStringExtra("currentId");
        newConnections = getIntent().getStringExtra("newConnections");
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearNotify);
        Button btn_refresh = findViewById(R.id.btn_refresh);
        try {
            //Getting new connections
            URL cUrl = new URL("http://10.0.2.2:3000/api/user/notify?"+ newConnections);
            Log.d("Notification Activity", "newConnections url: " + newConnections);
            NotifyTask cTask = new NotifyTask();
            cTask.execute(cUrl);
            JSONArray connections = cTask.get();

            //Getting requests
            URL rUrl = new URL("http://10.0.2.2:3000/api/user/notify?"+ requests);
            Log.d("Notification Activity", "requests url: " + requests);
            NotifyTask rTask = new NotifyTask();
            rTask.execute(rUrl);
            JSONArray array = rTask.get();

            if (array == null || connections == null || array.length() < 1 || connections.length() < 1){
                if (array != null) {
                    Log.d("Notification Activity", "requests: " + array.length());
                }
                if (connections != null) {
                    Log.d("Notification Activity", "new connections: " + connections.length());
                }
                refreshPage(btn_refresh);
            }

            linearLayout.setOrientation(LinearLayout.VERTICAL);

            // displaying new connections
            if (connections != null) {
                TextView connectionTextView = new TextView(this);
                String connectionText = "";
                for (int i = 0; i < connections.length(); i++) {
                    Log.d("Notification Activity", "here!");
                    JSONObject acceptConnect = (JSONObject) connections.get(i);
                    connectionText += (acceptConnect.getString("firstname") + " " + acceptConnect.getString("lastname") + " is a new connection. \n");
                }
                connectionTextView.setText(connectionText);
                Log.d("Notification Activity", "connectionText: " + connectionText);
                linearLayout.addView(connectionTextView);
                if (connectionText.length() >= 1) {
                    Button clearConnectButton = new Button(this);
                    clearConnectButton.setTag(userId);
                    clearConnectButton.setText("Clear Connections from View");
                    clearConnectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("on clock accept button", (String) clearConnectButton.getTag());
                            try {
                                URL url = new URL("http://10.0.2.2:3000/api/user/clearNewConnection");
                                Log.d("on click clear Connection", (String) view.getTag());
                                AcceptTask task = new AcceptTask();
                                task.execute(url);
                                Toast.makeText(NotificationActivity.this, "Cleared New Connections", Toast.LENGTH_SHORT).show();
                                connectionTextView.setVisibility(View.GONE);
                                clearConnectButton.setVisibility(View.GONE);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    linearLayout.addView(clearConnectButton);
                }

            }


            // displaying requests
            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    TextView textView = new TextView(this);
                    JSONObject requester = (JSONObject) array.get(i);
                    textView.setText(requester.getString("firstname") + " " + requester.getString("lastname"));
                    Button acceptButton = new Button(this);
                    Button rejectButton = new Button(this);
                    acceptButton.setText("Accept");
                    rejectButton.setText("Reject");

                    //TODO: set onclick listener for buttons. May refer to FindActivity. You may also need the current user's id, for which you can use putExtra in main activity(see line 108).
                    acceptButton.setTag(requester.getString("_id")); //set tag to keep track of each requester's id
                    acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("on clock accept button", (String) acceptButton.getTag());
                            try {
                                URL url = new URL("http://10.0.2.2:3000/api/user/accept?" + "id_sender" + "=" + (String) view.getTag() + "&" + "id_receiver" + "=" + userId);
                                Log.d("on click accept button", (String) view.getTag());
                                AcceptTask task = new AcceptTask();
                                task.execute(url);
                                Toast.makeText(NotificationActivity.this, task.get(), Toast.LENGTH_SHORT).show();
                                textView.setVisibility(View.GONE);
                                acceptButton.setVisibility(View.GONE);
                                rejectButton.setVisibility(View.GONE);


                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    //TODO: set onclick listener for reject, clear after click if possible.
                    rejectButton.setTag(requester.getString("_id"));
                    rejectButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("on click reject button", (String) rejectButton.getTag());
                            try {
                                URL url = new URL("http://10.0.2.2:3000/api/user/reject?" + "id_sender" + "=" + (String) view.getTag() + "&" + "id_receiver" + "=" + userId);
                                Log.d("on click reject button", (String) view.getTag());
                                RejectTask task = new RejectTask();
                                task.execute(url);
                                //Log.d("check reject task", task.get());
                                Toast.makeText(NotificationActivity.this, task.get(), Toast.LENGTH_SHORT).show();
                                textView.setVisibility(View.GONE);
                                acceptButton.setVisibility(View.GONE);
                                rejectButton.setVisibility(View.GONE);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                    linearLayout.addView(textView);
                    linearLayout.addView(acceptButton);
                    linearLayout.addView(rejectButton);
                }
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (ExecutionException executionException) {
            executionException.printStackTrace();
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        }
    }

        private void refreshPage(Button btnRefresh){
            btnRefresh.setVisibility(View.VISIBLE);
            btnRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("NotificationActivity", "Refresh button clicked");
                    Intent notificationsIntent = new Intent(NotificationActivity.this, NotificationActivity.class);
                    notificationsIntent.putExtra("currentId", userId);
                    notificationsIntent.putExtra("currentRequests", requests);
                    notificationsIntent.putExtra("newConnections", newConnections);
                    finish();
                    startActivity(notificationsIntent);
                }
            });
    }

    private static class NotifyTask extends AsyncTask<URL, JSONArray, JSONArray>{
        @Override
        protected JSONArray doInBackground(URL...urls) {
            try {
                URL url = urls[0];

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                Scanner in = new Scanner(url.openStream());
                String response = in.nextLine();

                JSONArray array = new JSONArray(response);

                return array;

            }
            catch (Exception e) {
                //return e.toString();
                return null;
            }
        }
    }


    private static class AcceptTask extends AsyncTask<URL, String, String>{
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

    private static class RejectTask extends AsyncTask<URL, String, String>{
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
}