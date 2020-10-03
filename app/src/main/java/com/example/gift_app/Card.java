package com.example.gift_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Card extends AppCompatActivity {
    private Button btn6;
    private String totalPrice ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        btn6 = findViewById(R.id.btncard);

        Intent intent = new Intent();
        totalPrice = getIntent().getStringExtra("Total Price");


        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Card.this,Playsong.class);
                intent.putExtra("Total Price",totalPrice);
                startActivity(intent);
            }
        });
    }
}