package edu.brynmawr.cmsc353.elevateproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    List<String> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        RecyclerView notificationList = findViewById(R.id.notificationList);
//        notifications = new ArrayList<String>();
//
//        NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationActivity.this, notifications);
//        notificationList.setAdapter(notificationAdapter);
//        notificationList.setLayoutManager((new LinearLayoutManager(this)));
//
//        //get list of requests from server


    }
}
