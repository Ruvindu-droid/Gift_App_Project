package com.example.gift_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button joinNowButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        joinNowButton =(Button)findViewById(R.id.welcome_main_join_now_btn);
        loginButton =(Button)findViewById(R.id.welcome_main_login_btn);





        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginTest2.class);
                startActivity(intent);
            }
        });


        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TestRegister.class);
                startActivity(intent);
            }
        });


    }
}