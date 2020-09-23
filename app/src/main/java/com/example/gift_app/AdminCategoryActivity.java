package com.example.gift_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

//IT19052298

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView sweets, jewelery, toys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        sweets = (ImageView) findViewById(R.id.sweets);
        jewelery = (ImageView) findViewById(R.id.jewelery);
        toys = (ImageView) findViewById(R.id.toys);


        sweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "sweets");
                startActivity(intent);
            }
        });


        jewelery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "jewelery");
                startActivity(intent);
            }
        });


        toys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "toys");
                startActivity(intent);
            }
        });
    }
}