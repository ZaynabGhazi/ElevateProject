package edu.brynmawr.cmsc353.elevateproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginTask extends AsyncTask<String, String, String> {

    Context mContext;
    String TAG = "LOGIN TASK";
    public LoginTask(Context context){
        mContext = context;
    }


    @Override
    protected String doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String email = params[1];
            String password = params[2];
            JSONObject data = new JSONObject();
            data.put("email",email);
            data.put("password",password);
            Log.i(TAG,data.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(data.toString());

            os.flush();
            os.close();

            String result="";

            int code = conn.getResponseCode();
            if (code > 200){
                result+="ERROR:"+code;
            }//error-handling
            else{
              //return jwb token
                Scanner sc = new Scanner(conn.getInputStream());
                String token="";
                while(sc.hasNext())
                {
                    token+=sc.nextLine();
                }
                Log.i(TAG,"token is "+token);
                sc.close();
                JSONParser parser = new JSONParser();
                JSONObject resultObj = (JSONObject) parser.parse(token);
                result += resultObj.get("token");
            }
            conn.disconnect();
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,e.toString());
            return "ERROR:500";
        }

    }

    @Override
    protected void onPostExecute(String result) {
        if (!result.contains("ERROR")){
            Toast.makeText(mContext,"Login successful!",Toast.LENGTH_SHORT).show();
        }
        else{
            int code = Integer.parseInt(result.split(":")[1]);
            switch(code){
                case 400: {
                    Toast.makeText(mContext, "Email and password cannot be empty!", Toast.LENGTH_SHORT).show();
                    break;
                }
                case 401: {
                    Toast.makeText(mContext, "Email or password invalid.", Toast.LENGTH_SHORT).show();
                    break;
                }
                case 402:{
                    Toast.makeText(mContext, "Email is not linked to an account!", Toast.LENGTH_SHORT).show();
                    break;
                }
                case 403:{
                    Toast.makeText(mContext, "Email and password do not match!", Toast.LENGTH_SHORT).show();
                    break;
                }
                case 500:{
                    Toast.makeText(mContext, "Server Error! Please try again!", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }

}