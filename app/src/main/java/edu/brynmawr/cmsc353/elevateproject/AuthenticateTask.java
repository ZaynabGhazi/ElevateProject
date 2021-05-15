package edu.brynmawr.cmsc353.elevateproject;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.*;

import edu.brynmawr.cmsc353.elevateproject.models.User;

public class AuthenticateTask extends AsyncTask<String, String, User> {

    Context mContext;
    String TAG = "AUTHENTICATE TASK";
    public AuthenticateTask(Context context){
        mContext = context;
    }


    @Override
    protected User doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            String token = params[1];
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setRequestProperty("token",token);

            User user = new User();
            int code = conn.getResponseCode();
            Log.i(TAG,"code is +"+code);
            if (code > 200){
                return null;
            }//error-handling
            else{
                String result="";
                Scanner sc = new Scanner(conn.getInputStream());
                while(sc.hasNext())
                {
                    result+=sc.nextLine();
                }
                sc.close();
                JSONParser parser = new JSONParser();
                JSONObject usr = (JSONObject) parser.parse(result);
                user.setUserId((String) usr.get("_id"));

                user.setConnections((List)usr.get("connections"));
                user.setRequests((List)usr.get("requests"));
                user.setNewConnections((List)usr.get("newConnections"));

                user.setFirstname((String) usr.get("firstname"));
                user.setLastname((String) usr.get("lastname"));

            }
            conn.disconnect();
            return user;
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,e.toString());
            return null;
        }

    }

    @Override
    protected void onPostExecute(User user) {
        if (user == null){
            Toast.makeText(mContext,"Authentication error!",Toast.LENGTH_SHORT).show();
        }
        }
    }