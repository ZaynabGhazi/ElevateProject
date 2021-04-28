package edu.brynmawr.cmsc353.elevateproject;

import androidx.appcompat.app.AppCompatActivity;
import edu.brynmawr.cmsc353.elevateproject.models.User;

import android.os.Bundle;
import android.widget.Toast;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity {

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().hasExtra("currentuser")){
            currentUser = (User) Parcels.unwrap(getIntent().getParcelableExtra("currentuser"));
            Toast.makeText(MainActivity.this,"Welcome "+currentUser.getUserId(),Toast.LENGTH_SHORT).show();
        }
    }
}