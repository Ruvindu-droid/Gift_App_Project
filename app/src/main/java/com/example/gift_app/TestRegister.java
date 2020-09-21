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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TestRegister extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName ,InputPhoneNumber ,InputPassword ,InputBankName , InputBankAccountNo;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_register);


        CreateAccountButton =(Button) findViewById(R.id.Register_btn);
        InputName =(EditText) findViewById(R.id.Register_username_input_input);
        InputPhoneNumber =(EditText) findViewById(R.id.Register_phone_number_input);
        InputPassword =(EditText) findViewById(R.id.Register_password_input);
        //Special Fields
        InputBankName =(EditText) findViewById(R.id.Register_Bank_Name_input);
        InputBankAccountNo =(EditText) findViewById(R.id.Register_Bank_Account_input);

        loadingBar = new ProgressDialog(this);






        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount(){
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        String BankName = InputBankName.getText().toString();
        String Account = InputBankAccountNo.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please Enter The Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please Enter The Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Enter The password",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(BankName)){
            Toast.makeText(this,"Please Enter The Bank Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Account)){
            Toast.makeText(this,"Please Enter Your Bank Account Number",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please Wait while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validatetelephoneNumber(name,phone,password,BankName,Account);
        }

    }

    private void validatetelephoneNumber(final String name, final String phone, final String password, final String BankName, final String Account){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("USERS").child(phone).exists())){

                    //Firebase giving
                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("password",password);
                    userdataMap.put("name",name);
                    userdataMap.put("BankName",BankName);
                    userdataMap.put("Account",Account);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(TestRegister.this,"Congratulations! Your Account Has Been Successfully Created on GIFTAPP!",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(TestRegister.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(TestRegister.this,"Sorry !Network Error! ,Please Try again",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(TestRegister.this,"This"+ phone + "already Exists" ,Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(TestRegister.this,"Plese try again with another phone number" ,Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(TestRegister.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    //Thank You ! , R.K
}