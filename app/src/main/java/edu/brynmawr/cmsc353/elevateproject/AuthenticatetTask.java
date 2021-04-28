package edu.brynmawr.cmsc353.elevateproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthenticatetTask extends AsyncTask<String, String, Integer> {

    Context mContext;
    String TAG = "AUTHENTICATE TASK";
    public AuthenticatetTask(Context context){
        mContext = context;
    }


    @Override
    protected Integer doInBackground(String... params) {

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

            int code = conn.getResponseCode();
            conn.disconnect();
           return code;
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,e.toString());
            return 500;
        }

    }

    @Override
    protected void onPostExecute(Integer code) {
        switch(code){
            case 400: {
                Toast.makeText(mContext, "Email and password cannot be empty!", Toast.LENGTH_SHORT).show();
                break;
            }
            case 401: {
                Toast.makeText(mContext, "Email or password invalid. Password should have a minimum of 6 characters!", Toast.LENGTH_SHORT).show();
                break;
            }
            case 402:{
                Toast.makeText(mContext, "Email is already linked to an account!", Toast.LENGTH_SHORT).show();
                break;
            }
            case 500:{
                Toast.makeText(mContext, "Server Error! Please try again!", Toast.LENGTH_SHORT).show();
                break;
            }
            default:{
                Toast.makeText(mContext, "Sign up successful! Try to login now.", Toast.LENGTH_SHORT).show();
                break;            }
        }
    }

}