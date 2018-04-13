package com.paradise.malariastressfighter.Authentication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paradise.malariastressfighter.R;

public class SplashWelcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_welcome);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent intent = new Intent( SplashWelcome.this,UserTypeActivity.class);
                    startActivity(intent);
                    finish();
                }
            },2000);
    } }
