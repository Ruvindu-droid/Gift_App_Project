package com.example.gift_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gift_app.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsRK extends AppCompatActivity {


    private CircleImageView profileImageView;
    private EditText fullNameEditText, userPhoneEditText, passwordEdittext , bankaccountedittext, banknameedittext;
    private TextView profilechangetextbutton,closeBtn, saveTextButton;

    private Uri imageUri;
    private String myUri ="";
    private StorageTask uploadTask;
    private StorageReference storageprofilePictureRef;
    private String checker = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_r_k);


        storageprofilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        profileImageView = (CircleImageView) findViewById(R.id.settings_profile_Image);
        fullNameEditText = (EditText) findViewById(R.id.settings_full_name);
        userPhoneEditText = (EditText) findViewById(R.id.settings_Phone_Number);
        passwordEdittext = (EditText) findViewById(R.id.settings_password);
        bankaccountedittext = (EditText) findViewById(R.id.Setting_Bank_Account_Number);
        banknameedittext = (EditText) findViewById(R.id.Setting_Bank_Name);

        profilechangetextbutton =(TextView) findViewById(R.id.Profile_image_change_btn);
        closeBtn =(TextView) findViewById(R.id.Close_settings_btn);
        saveTextButton =(TextView) findViewById(R.id.update_Account_settings);

        // User Information display methord
        userInfroDisplay( fullNameEditText, userPhoneEditText, passwordEdittext, bankaccountedittext, banknameedittext);

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
                if(checker.equals("clicked"))
                {
                    userInfroSaved(); // IMPLIMET WITH IMAGE
                }
                else
                {
                    updateOnlyUserInfo();
                }
            }
        });





        profilechangetextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker ="clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(SettingsRK.this);

            }
        });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode ==RESULT_OK && data!=null){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        }
        else {
            Toast.makeText(this, "Error try again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsRK.this,SettingsRK.class));
            finish();
        }
    }

    //Created Below activity successfully

    private void updateOnlyUserInfo()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();

        userMap. put("name", fullNameEditText.getText().toString());
        userMap. put("password", passwordEdittext.getText().toString());
        userMap. put("phone", userPhoneEditText.getText().toString());
        userMap. put("Account", bankaccountedittext.getText().toString());
        userMap. put("BankName", banknameedittext.getText().toString());

        ref.child(Prevalent.currentonlineuser.getPhone()).updateChildren(userMap);


        startActivity(new Intent(SettingsRK.this, HomeActivity.class));
        Toast.makeText(SettingsRK.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();

    }


    //Impliment Below with img
    private void userInfroSaved() {

        if (TextUtils.isEmpty(fullNameEditText.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(passwordEdittext.getText().toString()))
        {
            Toast.makeText(this, "Name is address.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userPhoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(bankaccountedittext.getText().toString()))
        {
            Toast.makeText(this, "Account is address.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(banknameedittext.getText().toString()))
        {
            Toast.makeText(this, "Bank name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked"))
        {
            uploadImage();
        }

    }






    private void uploadImage()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri != null)
        {
            final StorageReference fileRef = storageprofilePictureRef
                    .child(Prevalent.currentonlineuser.getPhone() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        myUri = downloadUri.toString();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                        HashMap<String, Object> userMap = new HashMap<>();

                        userMap. put("name", fullNameEditText.getText().toString());
                        userMap. put("password", passwordEdittext.getText().toString());
                        userMap. put("phone", userPhoneEditText.getText().toString());
                        userMap. put("Account", bankaccountedittext.getText().toString());
                        userMap. put("BankName", banknameedittext.getText().toString());
                        userMap. put("image", myUri);
                        ref.child(Prevalent.currentonlineuser.getPhone()).updateChildren(userMap);

                        progressDialog.dismiss();

                        startActivity(new Intent(SettingsRK.this, MainActivity.class));
                        Toast.makeText(SettingsRK.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(SettingsRK.this, "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }









    //final CircleImageView profileImageView,

    private void userInfroDisplay( final EditText fullNameEditText, final EditText userPhoneEditText, final EditText passwordEdittext, final EditText bankaccountedittext, final EditText banknameedittext){

        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentonlineuser.getPhone());

        UserRef.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    if(dataSnapshot.child("image").exists())
                    {

                        String image =dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profileImageView);

                    }

                    String name =dataSnapshot.child("name").getValue().toString();
                    String phone =dataSnapshot.child("phone").getValue().toString();
                    String pass =dataSnapshot.child("password").getValue().toString();
                    String bankname =dataSnapshot.child("BankName").getValue().toString();
                    String account =dataSnapshot.child("Account").getValue().toString();



                    fullNameEditText.setText(name);
                    userPhoneEditText.setText(phone);
                    passwordEdittext.setText(pass);
                    bankaccountedittext.setText(account);
                    banknameedittext.setText(bankname);



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    } //User infro display methord over



}