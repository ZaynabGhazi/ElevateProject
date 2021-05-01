package edu.brynmawr.cmsc353.elevateproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity  extends AppCompatActivity {


    private TextView userName;
    private Button btnConnect;
    private Button btnReject;

    String receiverId;
    String name;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        retrieveExtras(intent);
        viewSetUp();
        Log.d("ProfileActivity", "receiverId: " + receiverId);
        Log.d("ProfileActivity", "name: " + name);
        Log.d("ProfileActivity", "userId: " + userId);
        userName.setText(name);
        btnConnect.setTag(receiverId);
        btnReject.setTag(receiverId);
    }

    private void viewSetUp(){
        userName = findViewById(R.id.userName);
        btnConnect = findViewById(R.id.btnConnect);
        btnReject = findViewById(R.id.btnReject);
    }

    private void retrieveExtras(Intent intent){
        receiverId = intent.getStringExtra("receiverId");
        name = intent.getStringExtra("name");
        userId = intent.getStringExtra("userId");
    }
}
