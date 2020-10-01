package com.example.gift_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class GridItemActivity2 extends AppCompatActivity {


    TextView name;
    //TextView price;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_item2);


        name = findViewById(R.id.pinky);
        //price = findViewById(R.id.pricepinky);
        image = findViewById(R.id.imgpinky);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        //price.setText(intent.getStringExtra("pricepinky"));
        image.setImageResource(intent.getIntExtra("image",0));


    }
}