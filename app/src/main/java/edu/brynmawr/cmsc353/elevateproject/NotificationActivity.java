package edu.brynmawr.cmsc353.elevateproject;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.parceler.Parcels;

import edu.brynmawr.cmsc353.elevateproject.models.User;

public class NotificationActivity extends AppCompatActivity {
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().hasExtra("currentuser")){
            currentUser = (User) Parcels.unwrap(getIntent().getParcelableExtra("currentuser"));
        }
    }
}
