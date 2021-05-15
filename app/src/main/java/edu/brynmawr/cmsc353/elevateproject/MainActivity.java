package edu.brynmawr.cmsc353.elevateproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import edu.brynmawr.cmsc353.elevateproject.fragments.QuestionAndAnswerFragment;
import edu.brynmawr.cmsc353.elevateproject.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private User currentUser;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mAbDrawerToggle;
    private NavigationView mNavigationView;
    private BottomNavigationView mBottomNavigationView;
    private FragmentManager mfragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigationDrawer();
        if (getIntent().hasExtra("currentuser")){
            currentUser = (User) Parcels.unwrap(getIntent().getParcelableExtra("currentuser"));
            Toast.makeText(MainActivity.this,"Welcome "+currentUser.getFirstname()+" "+currentUser.getLastname(),Toast.LENGTH_SHORT).show();
        }
        mfragmentManager = getSupportFragmentManager();
        setupFragments();
    }

    private void setupFragments() {
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch(item.getItemId()){
                    case R.id.opporunitiesView:
                        //code fragment to go to opportunities
                        break;

                    case R.id.threadView:
                        //code fragment to go to q&a
                        Bundle bundle = new Bundle();
                        bundle.putString("username", currentUser.getFirstname() + " " + currentUser.getLastname());
                        bundle.putString("userId", currentUser.getUserId());
                        fragment = new QuestionAndAnswerFragment();
                        fragment.setArguments(bundle);
                        mfragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.flContainer,fragment)
                                .commit();
                        mfragmentManager.beginTransaction().replace(R.id.flContainer,fragment).commit();
                        break;
                    case R.id.connectView:
                        Intent connectionsIntent = new Intent(getBaseContext(), FindActivity.class);
                        connectionsIntent.putExtra("userId", currentUser.getUserId());
                        startActivity(connectionsIntent);
                        break;
                    case R.id.profileView:
                        Intent profileIntent = new Intent(getBaseContext(), ProfileActivity.class);
                        profileIntent.putExtra("userName", currentUser.getFirstname() + " " + currentUser.getLastname());
                        profileIntent.putExtra("userId", currentUser.getUserId());
                        profileIntent.putExtra("userProfile", true);
                        startActivity(profileIntent);
                        break;
                }
                //UNCOMMENT THIS WHEN YOU IMPLEMENT FRAGMENTS
//                mfragmentManager.beginTransaction().replace(R.id.flContainer,fragment).commit();
                return true;
            }
        });
        mBottomNavigationView.setSelectedItemId(R.id.opporunitiesView);
    }

    private void setupNavigationDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mAbDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Open, R.string.Close);
        mDrawerLayout.addDrawerListener(mAbDrawerToggle);
        mAbDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationView = (NavigationView) findViewById(R.id.nv);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.logOut){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                if (id == R.id.notifications){
                    Log.d("Main Activity", "Notifications!");
                    Intent notificationIntent = new Intent(getBaseContext(), NotificationActivity.class);
                    List<String> requests = currentUser.getRequests();
                    List<String> newConnections = currentUser.getNewConnections();
                    String requests_str = "";
                    String newConnections_str = "";
                    for(int i = 0; i< requests.size(); i++){
                        requests_str+="request=";
                        requests_str += requests.get(i);
                        if(i!=requests.size()-1) requests_str+="&";
                    }
                    for(int i = 0; i< newConnections.size(); i++){
                        Log.d("Main Activity", newConnections_str);
                        newConnections_str+="request=";
                        newConnections_str += newConnections.get(i);
                        if(i!=newConnections.size()-1) newConnections_str+="&";
                    }
                    Log.d("Main Activity", "request string: " + requests_str);
                    notificationIntent.putExtra("currentRequests", requests_str);
                    notificationIntent.putExtra("currentId", currentUser.getUserId());
                    notificationIntent.putExtra("newConnections", newConnections_str);
                    startActivity(notificationIntent);
                }
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mAbDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}