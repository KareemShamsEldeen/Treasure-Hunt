package com.example.treasurehunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treasurehunt.Prevalent.Prevalent;
import com.example.treasurehunt.UModel.Cart;
import com.example.treasurehunt.UModel.CartViewHolder;
import com.example.treasurehunt.UModel.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button next;
    private TextView txttotal;

    int total =0;
    String total_price;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Cart List");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.cart);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        next = (Button)  findViewById(R.id.next);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(total == 0)
                {
                    Toast.makeText(CartActivity.this, "Cart is Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(CartActivity.this, bill_confrmation1.class);
                    startActivity(intent);
                }
            }
        });


        // Initialize and assign variable
        //BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        // Set cart selected
        bottomNavigationView.setSelectedItemId(R.id.cart);

        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.cart);
        badge.setBackgroundColor(0xFFFFFBD3);
        badge.setBadgeTextColor(0xFF000000);

        DatabaseReference cart= FirebaseDatabase.getInstance().getReference("Cart List");
        cart.child(Prevalent.currentOnlineUser.getUsername()).child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    badge.setVisible(true);
                    int count = (int) snapshot.getChildrenCount();
                    badge.setNumber(count);
                }
                else
                {
                    badge.setVisible(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Home1.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.sell:
                        startActivity(new Intent(getApplicationContext(),UploadProductActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cart:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(CartActivity.this, Home1.class);
        startActivity(intent);
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
                holder.txtprice.setText(model.getPrice());
                Picasso.get().load(model.getImg()).into(holder.imgp);

                int onetype = Integer.parseInt(model.getPrice()) * Integer.parseInt(model.getQun());
                total = total + onetype;
                total_price = "Your Total is "+ String.valueOf(total) + " EGP";


                holder.btnplus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        int i = Integer.parseInt(model.getQun());
                        if (i < Integer.parseInt(model.getRqun()))
                        {
                            //Log.e("Your Total before is ", String.valueOf(total));
                            //int onetype = Integer.parseInt(model.getPrice()) * Integer.parseInt(model.getQun());
                            //total = total - onetype ;
                            //Log.e("i is ", String.valueOf(i));
                            //Log.e("Your onetype before is ", String.valueOf(onetype));
                            //Log.e("Your Total is ", String.valueOf(total));
                            i++;
                            //Log.e("i after is ", String.valueOf(i));
                            model.setQun(String.valueOf(i));
                            holder.txtquan.setText(model.getQun());
                            final HashMap<String , Object> cartmap = new HashMap<>();
                            cartmap.put("name",model.getName());
                            cartmap.put("img",model.getImg());
                            cartmap.put("price",model.getPrice());
                            cartmap.put("qun",model.getQun());
                            cartmap.put("rqun",model.getRqun());
                            cartmap.put("date",model.getDate());
                            cartmap.put("time",model.getTime().toString());
                            cartmap.put("owner",Prevalent.currentOnlineUser.getUsername());
                            mDatabase.child(model.getOwner()).child("products").child(model.getDate().toString()+model.getTime().toString()).updateChildren(cartmap);
                            //onetype = Integer.parseInt(model.getPrice()) * i;
                            //total = total + onetype;
                            //Log.e("i is ", String.valueOf(i));
                            //Log.e("Your onetype after is ", String.valueOf(onetype));
                            //Log.e("Your Total after is ", String.valueOf(total));
                           //total_price = "Your Total is "+ String.valueOf(total) + " EGP";
                            //recreate();
                        }
                        else
                        {
                            Toast.makeText(CartActivity.this, "There isn't enough quantity", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                holder.btnmins.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = Integer.parseInt(model.getQun());
                        if (i > 0)
                        {
                            i--;
                            if (i != 0)
                            {
                                //int onetype = Integer.parseInt(model.getPrice()) * Integer.parseInt(model.getQun());
                                //total = total - onetype;
                                model.setQun(String.valueOf(i));
                                holder.txtquan.setText(model.getQun());
                                final HashMap<String , Object> cartmap = new HashMap<>();
                                cartmap.put("name",model.getName());
                                cartmap.put("img",model.getImg());
                                cartmap.put("price",model.getPrice());
                                cartmap.put("qun",model.getQun());
                                cartmap.put("rqun",model.getRqun());
                                cartmap.put("date",model.getDate());
                                cartmap.put("time",model.getTime().toString());
                                cartmap.put("owner",Prevalent.currentOnlineUser.getUsername());
                                mDatabase.child(model.getOwner()).child("products").child(model.getDate().toString()+model.getTime().toString()).updateChildren(cartmap);
                                //onetype = Integer.parseInt(model.getPrice()) * Integer.parseInt(model.getQun());
                                //total = total +onetype;
                                //total_price = "Your Total is "+ String.valueOf(total) + " EGP";
                                //recreate();
                            }
                            else
                            {
                                mDatabase.child(model.getOwner()).child("products").child(model.getDate().toString()+model.getTime().toString()).removeValue();
                                recreate();
                                Toast.makeText(CartActivity.this, "Item removed successfully", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                });
                holder.btndelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDatabase.child(model.getOwner()).child("products").child(model.getDate().toString()+model.getTime().toString()).removeValue();
                        recreate();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}