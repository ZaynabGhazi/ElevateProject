package edu.brynmawr.cmsc353.elevateproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import edu.brynmawr.cmsc353.elevateproject.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity {

    private User currentUser;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mAbDrawerToggle;
    private NavigationView mNavigationView;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigationDrawer();
        if (getIntent().hasExtra("currentuser")){
            currentUser = (User) Parcels.unwrap(getIntent().getParcelableExtra("currentuser"));
            Toast.makeText(MainActivity.this,"Welcome "+currentUser.getUserId(),Toast.LENGTH_SHORT).show();
        }
        setupFragments();
    }

    private void setupFragments() {
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
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