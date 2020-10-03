package com.example.gift_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gift_app.Model.Cart;
import com.example.gift_app.Model.ORDER;
import com.example.gift_app.Model.Users;
import com.example.gift_app.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Ruvindu extends AppCompatActivity {


    private Button CreateDealButton;
    private EditText InputReceiverName ,InputReciverAddress ,InputReceiverPhoneNumber ;
    private ProgressDialog loadingBar;
    private String totalPrice="";
    private String CHANNEL_ID="";
    private static final String TAG = "Ruvindu";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruvindu);

        Intent intent = new Intent();
        totalPrice = getIntent().getStringExtra("Total Price");






        CreateDealButton =(Button) findViewById(R.id.Enter_Final_Details_btn);

        InputReceiverName =(EditText) findViewById(R.id.name_of_receiver);
        InputReciverAddress =(EditText) findViewById(R.id.Receivers_address);
        InputReceiverPhoneNumber =(EditText) findViewById(R.id.Receivers_Phone_Number);

        loadingBar = new ProgressDialog(this);


        //Notification Try IT19062884

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }




        CreateDealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDeal();
            }
        });

    }



    //correct methord
    private void createDeal() {

        String ReceiverName = InputReceiverName.getText().toString();
        String ReceiverPhone = InputReceiverPhoneNumber.getText().toString();
        String ReceiverAddress = InputReciverAddress.getText().toString();

        if(TextUtils.isEmpty(ReceiverName)){
            Toast.makeText(this,"Please Enter The Name of the Receiver for this deal ",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(ReceiverPhone)){
            Toast.makeText(this,"Please Enter any valid contact Number of the receiver for the deal",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(ReceiverAddress)){
            Toast.makeText(this,"Please Enter Valid address for receive the gift for the deal",Toast.LENGTH_SHORT).show();
        }
        else{

            loadingBar.setTitle("Create Recever detail plot");
            loadingBar.setMessage("Please Wait while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            validateteReceiver( ReceiverName,  ReceiverPhone, ReceiverAddress);
        }

    }








// exteaMethord

    private void validateteReceiver(final String ReceiverName, final String ReceiverPhone, final String ReceiverAddress){


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("USERS").child(Prevalent.currentonlineuser.getPhone()).exists())){

                    //Firebase giving
                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("recName",ReceiverName);
                    userdataMap.put("recPhone",ReceiverPhone);
                    userdataMap.put("recAddress",ReceiverAddress);


                    RootRef.child("ORDER").child(Prevalent.currentonlineuser.getPhone()).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Ruvindu.this,"Receiver datails added successfully!",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();


                                 //     Intent intent = new Intent(Ruvindu.this,FinalPageRK.class);
                                 //     intent.putExtra("Total Price", totalPrice);
                                 //     startActivity(intent);




                                        // Create an explicit intent for an Activity in your app
                                        Intent intent = new Intent(Ruvindu.this, FinalPageRK.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        PendingIntent pendingIntent = PendingIntent.getActivity(Ruvindu.this, 0, intent, 0);

                                        NotificationCompat.Builder builder = new NotificationCompat.Builder(Ruvindu.this, CHANNEL_ID)
                                                .setSmallIcon(R.drawable.background)
                                                .setContentTitle("Receiver Detail Conformation")
                                                .setContentText("Selected gifts will receive to the receiver successfully!")
                                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            // Set the intent that will fire when the user taps the notification
                                                .setContentIntent(pendingIntent)
                                                .setAutoCancel(true);
                                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Ruvindu.this);
                            // notificationId is a unique int for each notification that you must define
                                        notificationManager.notify(0, builder.build());
                                        Log.i(TAG, "onComplete: total= "+totalPrice);

                                     //   Toast.makeText(Ruvindu.this, "Not notification" + totalPrice, Toast.LENGTH_SHORT).show(); //Testing process

                                        intent.putExtra("Total_Price", totalPrice);
                                        startActivity(intent);



                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(Ruvindu.this,"Sorry !Network Error! ,Please Try again",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(Ruvindu.this,"This Order already Exists" ,Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Ruvindu.this,"Plese try again !" ,Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Ruvindu.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }






//remove loding bar if it not work
    //Thank You ! , R.K






}