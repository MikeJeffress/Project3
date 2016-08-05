package com.example.michaeljeffress.project3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.michaeljeffress.project3.activities.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {
                // TODO Auto-generated method stub
                finish();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        }, 1000);
//
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//        finish();
    }
}
