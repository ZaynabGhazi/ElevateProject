package edu.brynmawr.cmsc353.elevateproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity  extends AppCompatActivity {


    private TextView profileName;
    private Button btnConnect;
    private Button btnReject;

    String receiverId;
    String receiverName;
    String userId;
    String userName;
    Boolean userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        retrieveExtras(intent);
        viewSetUp();
        Log.d("ProfileActivity", "userId: " + userId);
        Log.d("ProfileActivity", "user profile? " + userProfile);
        if (userProfile){
            profileName.setText(userName);
            btnConnect.setVisibility(View.GONE);
            btnReject.setVisibility(View.GONE);
        }
        else {
            Log.d("ProfileActivity", "receiverId: " + receiverId);
            Log.d("ProfileActivity", "receiverName: " + receiverName);
            profileName.setText(receiverName);
            btnConnect.setTag(receiverId);
            btnReject.setTag(receiverId);
        }


    }

    private void viewSetUp(){
        profileName = findViewById(R.id.userName);
        btnConnect = findViewById(R.id.btnConnect);
        btnReject = findViewById(R.id.btnReject);
    }

    private void retrieveExtras(Intent intent){
        userProfile = intent.getExtras().getBoolean("userProfile");
        if (userProfile){
            userName = intent.getStringExtra("userName");
        }
        else {
            receiverId = intent.getStringExtra("receiverId");
            userName = intent.getStringExtra("receiverName");
            userId = intent.getStringExtra("userId");
        }
    }
}
