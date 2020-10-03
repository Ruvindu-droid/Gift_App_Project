package com.example.gift_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gift_app.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FinalPageRK extends AppCompatActivity {


    private Button cancelthedeal,editdetails,finaldeal;
    private TextView Receivername,Receiveraddress, Giftpayment, RappingPayment, TransportPayment, TotalPayment ;
    private String totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_page_r_k);

        Intent intent = getIntent();
        totalPrice = intent.getStringExtra("Total_Price");






        //Extra remove at the end
       // Toast.makeText(FinalPageRK.this,"  total check >>>>"+totalPrice+"<< for testing process",Toast.LENGTH_SHORT).show();







        cancelthedeal =(Button) findViewById(R.id.Cancel_Whole_Details_btn);
        editdetails =(Button) findViewById(R.id.Edit_Final_Details_btn);
        finaldeal =(Button) findViewById(R.id.Fial_Ok_btn);


        Receivername =(TextView) findViewById(R.id.Final_Message_variable);
        Receiveraddress =(TextView) findViewById(R.id.Final_address_variable);
        Giftpayment =(TextView) findViewById(R.id.Goods_pay_variable);
        RappingPayment =(TextView) findViewById(R.id.Wrapping_pay_variable);
        TransportPayment =(TextView) findViewById(R.id.Transport_pay_variable);
        TotalPayment =(TextView) findViewById(R.id.Total_pay_variable);
        TotalPayment =(TextView) findViewById(R.id.Total_pay_variable);


        // User Information display methord
        userInfroDisplay( Receivername, Receiveraddress, Giftpayment, RappingPayment, TransportPayment,TotalPayment);


        cancelthedeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelDeal();
            }
        });

        editdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinalPageRK.this,FinalDetailEditRK.class);
            //    intent.putExtra("Total Price", totalPrice);
                startActivity(intent);
            }
        });

        finaldeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizedeal();
            }
        });

    }

    private void userInfroDisplay(final TextView receivername,final TextView receiveraddress,final TextView giftpayment,final TextView rappingPayment,final TextView transportPayment,final TextView totalPayment) {


        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("ORDER").child(Prevalent.currentonlineuser.getPhone());

        UserRef.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {

                    // change firebase account according to the point they add the values

                    String name =dataSnapshot.child("recName").getValue().toString();
                    String phone =dataSnapshot.child("recPhone").getValue().toString();
                    String pass =dataSnapshot.child("recAddress").getValue().toString();




                    receivername.setText(name);
                    receiveraddress.setText(phone);
                    giftpayment.setText(pass);

                    rappingPayment.setText("Rs.200.00");
                    transportPayment.setText("Rs.320.00");

                    //Double d=Double.parseDouble("300.00");
                    Double d=Double.parseDouble(totalPrice);
                    Double r =calculate_Final_Amount(200.00,320.00,d);
                    String result = r.toString();

                    totalPayment.setText("Rs."+result);

                    //Extra remove at the end
                   // Toast.makeText(FinalPageRK.this," Akka total >>>>"+totalPrice+"<<Balanna",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    } //User infro display methord over

    public Double calculate_Final_Amount(Double i, Double i1, Double totalPrice) {
        Double total = i + i1 + totalPrice;
        return total;
    }


    private void finalizedeal() {
        // finalizing the deal crud

        Toast.makeText(FinalPageRK.this,"Your Deal added successfully ! We will receive that for you !",Toast.LENGTH_SHORT).show();
        Toast.makeText(FinalPageRK.this," Thank You! be happy! Be with Gift App ! GIFTAPP!",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(FinalPageRK.this,HomeActivity.class); //change according to the name of final update
        startActivity(intent);
    }




    private void CancelDeal() {
        //firebase crud for delete all the columns regarding this deal

        Intent intent = new Intent(FinalPageRK.this,HomeActivity.class); //change according to the name of final update
        startActivity(intent);


        final DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("ORDER").child(Prevalent.currentonlineuser.getPhone());

        UserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserRef.removeValue();


                //Feedback to the user via a toast..

                Toast.makeText(FinalPageRK.this,"Your Deal cancelled ! Please try again with new plan !",Toast.LENGTH_SHORT).show();
                Toast.makeText(FinalPageRK.this," Thank You! be happy! Be with GIFTAPP!",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}