package com.example.gift_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectActivity2 extends AppCompatActivity {
    Button custombtn,choosebtn,nextbtn;
    String totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select2);

        //Intent for total price
        Intent intent = new Intent();
        totalPrice = intent.getStringExtra("Total Price");

        custombtn = findViewById(R.id.custombtn);
        choosebtn = findViewById(R.id.choosebtn);
        nextbtn = findViewById(R.id.nextbtn);

        custombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity2.this,CrudActivity2.class);
                startActivity(intent);
            }
        });

        choosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity2.this,ChooseActivity2.class);
                startActivity(intent);
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity2.this,Tharushika.class);
                intent.putExtra("Total Price",totalPrice);
                startActivity(intent);
            }
        });

    }
}