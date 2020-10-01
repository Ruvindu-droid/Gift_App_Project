package com.example.gift_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Santa extends AppCompatActivity {

    private Button btn2;
    private String totalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_santa);

        Intent intent = new Intent();
        totalPrice = intent.getStringExtra("Total Price");


        btn2 = findViewById(R.id.button2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Santa.this,Cake.class);
                intent.putExtra("Total Price",totalPrice);
                startActivity(intent);
            }
        });

    }
}