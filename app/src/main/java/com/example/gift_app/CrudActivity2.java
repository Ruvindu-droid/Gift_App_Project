package com.example.gift_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CrudActivity2 extends AppCompatActivity {

    EditText giftname_et,kindgift_et,gender_et;
    Button button_save;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Upload upload;
    String giftname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud2);

        giftname_et = findViewById(R.id.editText_giftname);
        kindgift_et = findViewById(R.id.editText_kindGift);
        gender_et = findViewById(R.id.editText_gender);
        button_save = findViewById(R.id.button_save);
        upload = new Upload();
        recyclerView = findViewById(R.id.recyclerview_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //firebase database
        databaseReference = firebaseDatabase.getInstance().getReference().child("Data");

        //when user click save button after enter their details ,those data will auto matically saved
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload.setGiftname(giftname_et.getText().toString());
                upload.setKindgift(kindgift_et.getText().toString());
                upload.setGender(gender_et.getText().toString());


                String id = databaseReference.push().getKey();
                databaseReference.child(id).setValue(upload);
                //data successfully saved message
                Toast.makeText(CrudActivity2.this,"Data Saved Successfully!",Toast.LENGTH_SHORT).show();
            }
        });


    }
    //to retrieve that inserted data

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Upload> options =
                new FirebaseRecyclerOptions.Builder<Upload>()
                        .setQuery(databaseReference,Upload.class)
                        .build();

        FirebaseRecyclerAdapter<Upload,ViewHolder1> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Upload, ViewHolder1>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder1 holder, int position, @NonNull Upload model) {
                        holder.setData(getApplicationContext(),model.getGiftname(),model.getKindgift(),model.getGender());
                        //retrieve data to recyclerview
                        holder.setOnClickListener(new ViewHolder1.Clicklistener() {
                            @Override
                            public void onItemlongClick(View view, int position) {
                                giftname = getItem(position).getGiftname();

                                showDeleteDataDialog(giftname);
                            }
                        });
                    }


                    @NonNull
                    @Override
                    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.row,parent,false);

                        return new ViewHolder1(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    //implementing show delete dialog method
    private void showDeleteDataDialog(final String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(CrudActivity2.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to Delete this data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Query query = databaseReference.orderByChild("giftname").equalTo(giftname);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            //delete the data
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(CrudActivity2.this,"Data Deleted Successfully!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        //showing alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();




    }
}