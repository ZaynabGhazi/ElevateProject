package edu.brynmawr.cmsc353.elevateproject;


import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.*;
import android.widget.*;
import android.util.*;
import android.view.View;
import android.view.ViewGroup;

public class FindActivity extends AppCompatActivity {
    Button mButton;
    EditText mEdit1;
    EditText mEdit2;
    TextView mText;
    //Set<JSONObject> results = new java.util.HashSet<JSONObject>();
    static List<JSONObject> results = new ArrayList<JSONObject>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        mButton = (Button)findViewById(R.id.button1);
        mEdit1 = (EditText)findViewById(R.id.editText1); // search object
        mEdit2 = (EditText)findViewById(R.id.editText2); // feature the user is searching for
        mText = (TextView)findViewById(R.id.textView1);

    }

    public void onSubmitButtonClick(View v){
        try {

            String feature = mEdit2.getText().toString();
            String request = mEdit1.getText().toString();
            URL url = new URL("http://10.0.2.2:3000/api/user/find?" + feature + "=" + request);

            MyTask task = new MyTask();
            task.execute(url); // fill the results with all searched users
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linear);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            for(int i = 0; i<results.size(); i++){
                TextView textView = new TextView(this);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setText(results.get(i).getString("firstname") + " " + results.get(i).getString("lastname"));
                Button addButton = new Button(this) ;
                addButton.setText("add");
                linearLayout.addView(textView);
                linearLayout.addView(addButton);
            }
            results.clear();


        }
        catch (Exception e) {
            // uh oh
            e.printStackTrace();
            mText.setText(e.toString());
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
                for(int i = 0; i < array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    results.add(obj);
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
