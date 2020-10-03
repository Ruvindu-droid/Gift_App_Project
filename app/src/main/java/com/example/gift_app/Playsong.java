package com.example.gift_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Playsong extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    private Button btn8;
    private String totalPrice="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playsong);

        btn8 = findViewById(R.id.button7);

        Intent intent = new Intent();
        totalPrice = getIntent().getStringExtra("Total Price");


        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Playsong.this,DetailsM.class);
                intent.putExtra("Total Price",totalPrice);
                startActivity(intent);
            }
        });
    }
    public void play(View view){
        if(mediaPlayer==null){
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.song);
            mediaPlayer.start();
        }else {
            mediaPlayer.start();
        }
    }

    public void pause(View view){
        if(mediaPlayer != null){
            mediaPlayer.pause();
        }
    }

    public void stop(View view){
        mediaPlayer.release();
        mediaPlayer = null;
    }
}