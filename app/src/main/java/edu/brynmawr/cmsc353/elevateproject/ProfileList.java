package edu.brynmawr.cmsc353.elevateproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileList extends AppCompatActivity implements View.OnClickListener {

    ProfileAdapter profileAdapter;
    RecyclerView profileView;
    Intent intent = getIntent();
    ArrayList<String> profiles = intent.getStringArrayListExtra("profiles");
    Button requestConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilelist);
        profileView = (RecyclerView)findViewById(R.id.listRecycler);
        requestConnection = (Button)findViewById(R.id.requestConnection);

        // create adapter
        profileAdapter = new ProfileAdapter(this, profiles);
        profileAdapter.notifyDataSetChanged();

        // set the adapter on recycler view
        profileView.setAdapter(profileAdapter);
        // set layout manager on adapter
        profileView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getBaseContext(), "Request sent!", Toast.LENGTH_LONG).show();
    }
}
