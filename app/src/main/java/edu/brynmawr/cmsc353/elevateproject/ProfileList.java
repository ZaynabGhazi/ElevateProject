package edu.brynmawr.cmsc353.elevateproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProfileList extends Activity {

    Bundle extras = getIntent().getExtras();
    String results;
    String[] profiles;

    List<String> info;
    Button requestConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilelist);
        Log.d("ProfileList", "setContentView");
        RecyclerView profileView = findViewById(R.id.listRecycler);
        info = new ArrayList<>();

        // create adapter
        ProfileAdapter profileAdapter = new ProfileAdapter(this, info);
        // set the adapter on recycler view
        profileView.setAdapter(profileAdapter);
        // set layout manager on adapter
        profileView.setLayoutManager(new LinearLayoutManager(this));

        try{
            results = extras.getString("results");
            profiles = results.split(";");
            for (String s : profiles){
                info.add(s);
            }
        } catch (Exception e){
            results = "";
            profiles = new String[0];
        }
        profileAdapter.notifyDataSetChanged();
    }
}
