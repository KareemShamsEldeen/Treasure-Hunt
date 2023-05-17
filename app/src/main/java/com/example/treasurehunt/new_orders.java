package com.example.treasurehunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.treasurehunt.Prevalent.Prevalent;
import com.example.treasurehunt.UModel.Orders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class new_orders extends AppCompatActivity {

    private RecyclerView orderlist;
    private DatabaseReference orderref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_orders);

        orderref = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getUsername()).child("order");

        orderlist = findViewById(R.id.orders_list);
        orderlist.setLayoutManager(new LinearLayoutManager(this));

        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Orders> options =
                new FirebaseRecyclerOptions.Builder<Orders>()
                        .setQuery(orderref, Orders.class)
                        .build();

        FirebaseRecyclerAdapter<Orders,ordersViewHolder> adapter =
                new FirebaseRecyclerAdapter<Orders, ordersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ordersViewHolder holder, int position, @NonNull Orders model)
                    {
                        holder.name.setText("Name: " + model.getName());
                        holder.phone_number.setText("Phone: " + model.getPhone());
                        holder.total_price.setText("Total Price: " + model.getTotalAMount() + " EGP");
                        holder.address.setText("Address: " + model.getAddress());
                        holder.date.setText("Order at: " + model.getDate() + " "+ model.getTime());

                    }

                    @NonNull
                    @Override
                    public ordersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout,parent,false);
                        return new ordersViewHolder(view);
                    }
                };
        orderlist.setAdapter(adapter);
        adapter.startListening();
    }

    public static class ordersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name,phone_number,total_price,address,date;

        public ordersViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            phone_number = itemView.findViewById(R.id.phone_number);
            total_price = itemView.findViewById(R.id.total_price);
            address = itemView.findViewById(R.id.address);
            date = itemView.findViewById(R.id.date);

        }
    }
}