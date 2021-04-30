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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        String requests = getIntent().getStringExtra("currentRequests");
            try {

                URL url = new URL("http://10.0.2.2:3000/api/user/notify?"+requests);
                NotifyTask task = new NotifyTask();
                task.execute(url);
                JSONArray array = task.get();

                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearNotify);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                for(int i = 0; i<array.length(); i++) {
                    TextView textView = new TextView(this);
                    JSONObject requester = (JSONObject)array.get(i);
                    textView.setText(requester.getString("firstname") + " " + requester.getString("lastname"));
                    Button requestButton = new Button(this) ;
                    requestButton.setText("Accept");
                    requestButton.setTag(requester.getString("_id") ); //set tag to keep track of each requester's id
                    //TODO: set onclick listener for buttons. May refer to FindActivity. You may also need the current user's id, for which you can use putExtra in main activity(see line 108).

                    linearLayout.addView(textView);
                    linearLayout.addView(requestButton);
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
}
