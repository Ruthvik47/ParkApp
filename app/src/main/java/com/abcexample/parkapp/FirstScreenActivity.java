package com.abcexample.parkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstScreenActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private ImageView Firstscreenimage;
    private int progress = 0;
    private final int pBarMax = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);


        Firstscreenimage=(ImageView) findViewById(R.id.firstscreenimage);
        Animation anim= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
        Firstscreenimage.setAnimation(anim);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                auth=FirebaseAuth.getInstance();
                user=auth.getCurrentUser();


                if(user!=null){
                    finish();
                    Intent i=new Intent(getApplicationContext(), ParkAppHomeActivity.class);
                    startActivity(i);
                }
                if(user==null)
                {
                    finish();
                    Intent i=new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });




    }
   }