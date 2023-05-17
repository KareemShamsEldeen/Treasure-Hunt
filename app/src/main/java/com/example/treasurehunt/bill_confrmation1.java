package com.example.treasurehunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treasurehunt.Prevalent.Prevalent;
import com.example.treasurehunt.UModel.Cart;
import com.example.treasurehunt.UModel.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class bill_confrmation1 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button next,cancel;
    private TextView txttotal;

    int total =0;
    String total_price;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Cart List");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_confrmation1);


        recyclerView = findViewById(R.id.cart_list1);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        txttotal = (TextView)  findViewById(R.id.total);
        next = (Button)  findViewById(R.id.next);
        cancel = (Button)  findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(bill_confrmation1.this, bill_confrmation2.class);
                intent.putExtra("total",String.valueOf(total));
                startActivity(intent);
            }
        });



    }


    @Override
    protected void onStart()
    {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child(Prevalent.currentOnlineUser.getUsername()).child("products"),Cart.class).build();

        FirebaseRecyclerAdapter<Cart , CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model)
            {
                holder.txtname.setText(model.getName());
                holder.txtquan.setText(model.getQun());
                int onetype = Integer.parseInt(model.getPrice()) * Integer.parseInt(model.getQun());
                holder.txtprice.setText(String.valueOf(onetype));
                total = total + onetype;
                total_price = "Your Total is "+ String.valueOf(total) + " EGP";
                txttotal.setText(total_price);

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill1,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}