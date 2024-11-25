package com.example.quizapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log; // Import for logging
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class SplashMainActivity2 extends AppCompatActivity {

    private static final String TAG = "SplashMainActivity2"; // Tag for logging
    private TextView appName;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main2);
        Log.d(TAG, "onCreate: Set content view");

        // Initialize the TextView
        appName = findViewById(R.id.app_name);
        if (appName != null) {
            Log.d(TAG, "onCreate: TextView appName initialized");
        } else {
            Log.e(TAG, "onCreate: TextView appName is null");
        }

        // Set custom font for app name
        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        if (typeface != null) {
            Log.d(TAG, "onCreate: Typeface loaded successfully");
            appName.setTypeface(typeface);
        } else {
            Log.e(TAG, "onCreate: Typeface could not be loaded");
        }

        // Load and set the animation
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        if (anim != null) {
            Log.d(TAG, "onCreate: Animation loaded successfully");
            appName.setAnimation(anim);
        } else {
            Log.e(TAG, "onCreate: Animation could not be loaded");
        }

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        if (mAuth != null) {
            Log.d(TAG, "onCreate: FirebaseAuth instance initialized");
        } else {
            Log.e(TAG, "onCreate: FirebaseAuth instance is null");
        }

        DbQuery.g_firestore = FirebaseFirestore.getInstance();

        // Use Handler to introduce a delay before transitioning to the LoginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: Handler executed, checking user authentication state");
                if (mAuth.getCurrentUser() != null) {
                    Log.d(TAG, "run: User is authenticated, starting MainActivity");

                    DbQuery.loadData(new MyCompleteListener() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent(SplashMainActivity2.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(SplashMainActivity2.this,"Something went wrong! Try again",Toast.LENGTH_SHORT).show();
                        }
                    });



                } else {
                    Log.d(TAG, "run: No authenticated user, starting LoginActivity");
                    Intent intent = new Intent(SplashMainActivity2.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Finish SplashActivity to remove it from back stack
                }
            }
        }, 3000); // 3000 ms (3 seconds) delay

        Log.d(TAG, "onCreate: Ended");
    }
}


