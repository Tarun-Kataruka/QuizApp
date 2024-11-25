package com.example.quizapp;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.util.Log; // Import for logging
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.quizapp.R;

import com.example.quizapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity"; // Tag for logging
    private BottomNavigationView bottomNavigationView;
    private FrameLayout main_frame;
    private TextView drawerProfileName;
    private TextView drawerProfileText;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.nav_home) {
                        Log.d(TAG, "onNavigationItemSelected: Navigating to CategoryFragment");
                        setFragment(new CategoryFragment());
                        return true;
                    } else if (menuItem.getItemId() == R.id.nav_leaderboard) {
                        setFragment(new LeaderBoardFragment());
                        return true;
                    } else if (menuItem.getItemId() == R.id.nav_account) {
                        setFragment(new AccountFragment());
                        return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Started");
        super.onCreate(savedInstanceState);

        // Inflate the binding
        @NonNull ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d(TAG, "onCreate: Set content view");

        // Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Log.d(TAG, "onCreate: Toolbar set up successfully");
        } else {
            Log.e(TAG, "onCreate: Toolbar is null");
        }

        // Initialize BottomNavigationView and FrameLayout
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        main_frame = findViewById(R.id.main_frame);

        if (bottomNavigationView != null && main_frame != null) {
            Log.d(TAG, "onCreate: BottomNavigationView and FrameLayout initialized");
        } else {
            Log.e(TAG, "onCreate: BottomNavigationView or FrameLayout is null");
        }

        // Set the listener for BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        Log.d(TAG, "onCreate: BottomNavigationView listener set");

        // Set up DrawerLayout and NavigationView
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            Log.d(TAG, "onCreate: DrawerLayout and toggle set up");
        } else {
            Log.e(TAG, "onCreate: DrawerLayout is null");
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerProfileName=navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_name);
        drawerProfileText=navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_text_img);

        String name = DbQuery.myProfile.getName();
        drawerProfileName.setText(name);

        drawerProfileText.setText(name.toUpperCase().substring(0,1));

        setFragment(new CategoryFragment());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            Log.d(TAG, "onBackPressed: Drawer is open, closing it");
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.d(TAG, "onBackPressed: Drawer is not open, calling super");
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected: Item selected in NavigationView - " + item.getTitle());
        int id = item.getItemId();

        if(id == R.id.nav_home){
            setFragment(new CategoryFragment());
        }
        else if(id == R.id.nav_account)
        {
            setFragment(new AccountFragment());
            return true;
        }
        else if(id == R.id.nav_leaderboard)
        {
            setFragment(new LeaderBoardFragment());
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return  true;
    }

    private void setFragment(Fragment fragment) {
        if (fragment != null) {
            Log.d(TAG, "setFragment: Replacing fragment with " + fragment.getClass().getSimpleName());
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame, fragment); // Directly use the ID here
            transaction.addToBackStack(null); // Optional: Allow back navigation
            transaction.commit();
        } else {
            Log.e(TAG, "setFragment: Fragment is null");
        }
    }

}
