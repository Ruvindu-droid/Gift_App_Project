package com.example.gift_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gift_app.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginTest2 extends AppCompatActivity {

    private EditText InputPhoneNumber ,InputPassword ;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView AdminLink,Notadminlink;

    private String parentDBName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test2);

        LoginButton =(Button) findViewById(R.id.login_btn);
        InputPassword =(EditText) findViewById(R.id.login_password_input);
        InputPhoneNumber =(EditText) findViewById(R.id.login_phone_number_input);

        AdminLink = (TextView) findViewById(R.id.admin_panal_link);
        Notadminlink = (TextView) findViewById(R.id.not_panal_link);

        loadingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                Notadminlink.setVisibility(View.VISIBLE);
                parentDBName ="Admins";
            }
        });

        Notadminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                Notadminlink.setVisibility(View.INVISIBLE);
                parentDBName ="Users";
            }
        });


    }

    private void LoginUser(){
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please Enter The Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Enter The password",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please Wait while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToTheAccount(phone,password);
        }
    }

    private void AllowAccessToTheAccount(final String phone, final String password){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDBName).child(phone).exists()){
                    Users usersData = dataSnapshot.child(parentDBName).child(phone).getValue(Users.class);
                    if(usersData.getPhone().equals(phone))
                    {
                        if(usersData.getPassword().equals(password))
                        {

                            if(parentDBName.equals("Admins")){

                                Toast.makeText(LoginTest2.this,"Welcome Admin ! You are Successfully logged in",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginTest2.this,AdminAddNewProductActivity.class);
                                startActivity(intent);
                            }


                            else if(parentDBName.equals("Users")){
                                Toast.makeText(LoginTest2.this,"Successfully logged in",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginTest2.this,AkkaHome.class);
                                startActivity(intent);
                            }



                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(LoginTest2.this,"Sorry !, Password Is incorrect" ,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginTest2.this,"Sorry! ,Account with this"+ phone + "Do not Exists" ,Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(LoginTest2.this,"You Need to create a new Account" ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}