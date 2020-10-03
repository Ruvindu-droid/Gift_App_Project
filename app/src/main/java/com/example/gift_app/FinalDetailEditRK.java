package com.example.gift_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gift_app.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class FinalDetailEditRK extends AppCompatActivity {

    private EditText Receivername, Receiveraddress, Receiverphone;
    private TextView closeBtn, saveTextButton;
   // private String totalPrice="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_detail_edit_r_k);


   //     Intent intent = new Intent();
   //     totalPrice = getIntent().getStringExtra("Total_Price");

        Receivername = (EditText) findViewById(R.id.Edit_name_of_receiver);
        Receiveraddress = (EditText) findViewById(R.id.Receivers_address_edit);
        Receiverphone = (EditText) findViewById(R.id.Edit_Receivers_Phone_Number);

        closeBtn =(TextView) findViewById(R.id.Close_final_settings_btn);
        saveTextButton =(TextView) findViewById(R.id.update_final_settings);



        FinaluserInfroDisplay( Receivername, Receiveraddress, Receiverphone);

        //close button
        closeBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });


        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                updateOnlyUserInfoLast();
            }
        });
    }





    //Update Moderate Methord

    private void updateOnlyUserInfoLast()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("ORDER");

        HashMap<String, Object> userMap = new HashMap<>();

        userMap. put("recName", Receivername.getText().toString());
        userMap. put("recAddress", Receiveraddress.getText().toString());
        userMap. put("recPhone", Receiverphone.getText().toString());

        ref.child(Prevalent.currentonlineuser.getPhone()).updateChildren(userMap);


        Toast.makeText(this, "Updated Successfully.", Toast.LENGTH_SHORT).show();
     //   startActivity(new Intent(FinalDetailEditRK.this, FinalPageRK.class));
    //    Intent intent = new Intent(FinalDetailEditRK.this,FinalPageRK.class);
        //intent.putExtra("Total Price", totalPrice);
        //startActivity(intent);


        Toast.makeText(FinalDetailEditRK.this, "Receiver Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();

    }


    //Moderate user infro disply

    private void FinaluserInfroDisplay( final EditText receivername, final EditText receiveraddress,final EditText receiverphone){

        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("ORDER").child(Prevalent.currentonlineuser.getPhone());

        UserRef.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    String name =dataSnapshot.child("recName").getValue().toString();
                    String phone =dataSnapshot.child("recPhone").getValue().toString();
                    String Add =dataSnapshot.child("recAddress").getValue().toString();


                    Receivername.setText(name);
                    Receiveraddress.setText(phone);
                    Receiverphone.setText(Add);



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    } //User infro display methord over




}