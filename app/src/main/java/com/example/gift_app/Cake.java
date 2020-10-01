package com.example.gift_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Cake extends AppCompatActivity {
    private Button btn3;
    private String totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake);

        Intent intent = new Intent();
        totalPrice = intent.getStringExtra("Total Price");

        btn3 = findViewById(R.id.button4);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cake.this,Card.class);
                intent.putExtra("Total Price",totalPrice);
                startActivity(intent);
            }
        });
    }
}