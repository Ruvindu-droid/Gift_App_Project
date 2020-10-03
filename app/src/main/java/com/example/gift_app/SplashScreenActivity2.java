package com.example.gift_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import static java.lang.String.valueOf;

public class SplashScreenActivity2 extends AppCompatActivity {

    private static  int SPLASH_SCREEN = 5000;

    Animation topAnim, bottomAnim;
    //ImageView img;
    TextView logo;
    String totalPrice = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);

        //Intent price

        Intent intent = new Intent();
        totalPrice = getIntent().getStringExtra("Total Price");

        //animation call to the project
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        //bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        //img = findViewById(R.id.img);
        logo = findViewById(R.id.textView);

        //img.setAnimation(topAnim);
        logo.setAnimation(topAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity2.this,SelectActivity2.class);
                intent.putExtra("Total Price", totalPrice);
                startActivity(intent);
            }
        },SPLASH_SCREEN);


    }
}