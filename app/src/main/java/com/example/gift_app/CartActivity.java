package com.example.gift_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gift_app.Model.Cart;
import com.example.gift_app.Prevalent.Prevalent;
import com.example.gift_app.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.String.valueOf;

//IT19052298

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private Button CalculateTotalBtn;
    private TextView txtTotalAmount;

    private float overTotalPrice = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessBtn = (Button) findViewById(R.id.next_process_button);
        CalculateTotalBtn = (Button) findViewById(R.id.calculate_total);

        txtTotalAmount = (TextView) findViewById(R.id.total_price);

        CalculateTotalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTotalAmount.setText("Total Price = Rs "+ valueOf(overTotalPrice));
            }
        });

        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //txtTotalAmount.setText("Total Price = Rs "+ valueOf(overTotalPrice));

                Intent intent = new Intent(CartActivity.this,SplashScreenActivity2.class);
                intent.putExtra("Total Price", valueOf(overTotalPrice));
                startActivity(intent);
                finish();
            }
        });

       // txtTotalAmount.setText("Total Price = Rs "+ valueOf(overTotalPrice));

    }

    @Override
    protected void onStart() {


        super.onStart();

       // txtTotalAmount.setText("Total Price = Rs "+ valueOf(overTotalPrice));

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("User View")
                        .child(Prevalent.currentonlineuser.getPhone()).child("Products"), Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final Cart cart) {

                cartViewHolder.txtProductQuantity.setText("Quantity = "+ cart.getQuantity());
                cartViewHolder.txtProductPrice.setText("Price = Rs "+ cart.getPrice());
                cartViewHolder.txtProductName.setText(cart.getPname());

               // int oneTypeProductTPrice = ((Integer.valueOf(cart.getPrice()))) * Integer.valueOf(cart.getQuantity());
               float oneTypeProductTPrice = calculateProductPrice((Float.valueOf(cart.getPrice())),Float.valueOf(cart.getQuantity())); // today
                //overTotalPrice = overTotalPrice + oneTypeProductTPrice;
                overTotalPrice = calculateTotalProductPrice(overTotalPrice,oneTypeProductTPrice);

                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"

                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(i==0){
                                    Intent intent = new Intent(CartActivity.this,ProductDetailsActivity.class);
                                    intent.putExtra("pid",cart.getPid());
                                    startActivity(intent);
                                }
                                if(i == 1){
                                    cartListRef.child("User View").child(Prevalent.currentonlineuser.getPhone()).child("Products").child(cart.getPid())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(CartActivity.this,"Item removed successfully",Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(CartActivity.this,HomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        });

                        builder.show();
                    }
                });



            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    public float calculateProductPrice(float price, float quantity){

        float total = price * quantity;
        return  total;

    };

    public float calculateTotalProductPrice(float overTotalPrice, float oneTypeProductPrice){

        float totalPrice = overTotalPrice + oneTypeProductPrice;
        return  totalPrice;
    };



}