package com.example.gift_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.String.valueOf;

public class DetailsM extends AppCompatActivity {


    EditText txtID,txtName,txtAdd,txtNo;
    Button btnSave,btnShow,btnUpdate,btnDelete,btnFreeNxt;
    DatabaseReference dbref;
    Student std;
    private String totalPrice="";

    private void clearControls(){
        txtID.setText("");
        txtName.setText("");
        txtAdd.setText("");
        txtNo.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_m);



        txtID = findViewById(R.id.txtID);
        txtName = findViewById(R.id.txtName);
        txtAdd = findViewById(R.id.txtAdd);
        txtNo = findViewById(R.id.txtNo);

        btnSave = findViewById(R.id.btnSave);
        btnShow = findViewById(R.id.btnShow);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnFreeNxt=findViewById(R.id.freenext);

        std = new Student();

        Intent intent = new Intent();
        totalPrice = getIntent().getStringExtra("Total Price");


        //save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref= FirebaseDatabase.getInstance().getReference().child("Student");
                try{
                    if (TextUtils.isEmpty(txtID.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the gift name or id", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtName.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtAdd.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the message", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtNo.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter the card id", Toast.LENGTH_SHORT).show();
                    else {
                        std.setId(txtID.getText().toString().trim());
                        std.setName(txtName.getText().toString().trim());
                        std.setMessage(txtAdd.getText().toString().trim());
                        std.setCardID(Integer.parseInt(txtNo.getText().toString().trim()));
                        //((DatabaseReference) dbRef).push().setValue(std);
                        dbref.child("Std1").setValue(std);
                        Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }

                }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Invalid card ID", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //show button
        btnShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            txtID.setText(dataSnapshot.child("id").getValue().toString());
                            txtName.setText(dataSnapshot.child("name").getValue().toString());
                            txtAdd.setText(dataSnapshot.child("message").getValue().toString());
                            txtNo.setText(dataSnapshot.child("cardID").getValue().toString());
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No source to display", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });




        //update button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Student");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Std1")){
                            try {
                                std.setId(txtID.getText().toString().trim());
                                std.setName(txtName.getText().toString().trim());
                                std.setMessage(txtAdd.getText().toString().trim());
                                std.setCardID(Integer.parseInt(txtNo.getText().toString().trim()));

                                dbref = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                                dbref.setValue(std);
                                clearControls();

                                Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
                            }
                            catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext(),"invalid cardID", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No source to Update", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        //delete button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Student");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Std1")){
                            dbref = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                            dbref.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(), "Data deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No source to delete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnFreeNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DetailsM.this,Ruvindu.class);
               intent.putExtra("Total Price", totalPrice);
                startActivity(intent);


            }
        });

    }
}