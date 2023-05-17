package com.example.treasurehunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.load.resource.gif.GifOptions;
import com.example.treasurehunt.Prevalent.Prevalent;
import com.example.treasurehunt.UModel.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class bill_confrmation2 extends AppCompatActivity {

    TextView first_name,password,confirm_password,cardname,cardnumber,cardcvv;
    private RadioGroup radiogGroup;
    private RadioButton radioCButton,radioVButton;

    LinearLayout linearLayout;

    String total;

    Button confirm,back;

    FirebaseDatabase firebaseDatabasep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_confrmation2);
        total = getIntent().getStringExtra("total");

        first_name = findViewById(R.id.first_name);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        cardname = findViewById(R.id.cardname);
        cardnumber = findViewById(R.id.cardnumber);
        cardcvv = findViewById(R.id.cardcvv);

        radiogGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioCButton = (RadioButton) findViewById(R.id.Cash);
        radioVButton = (RadioButton) findViewById(R.id.visa);
        linearLayout = findViewById(R.id.hidden);

        first_name.setText(Prevalent.currentOnlineUser.getUsername());

        radioVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
            }
        });

        radioCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });


        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Check();
            }

        });

    }

    private void Check()
    {
        if (TextUtils.isEmpty(first_name.getText().toString())) {
            first_name.setError("Username is empty");
            return;
        } else if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Password is empty");
            return;
        }
        else if (TextUtils.isEmpty(confirm_password.getText().toString()))
        {
            confirm_password.setError("Confirm Password is empty");
            return;
        }
        else if (TextUtils.isEmpty(cardname.getText().toString()) && radioVButton.isChecked())
        {
            cardname.setError("Card Owner Name is empty");
            return;
        }
        else if (TextUtils.isEmpty(cardnumber.getText().toString()) && radioVButton.isChecked())
        {
            cardnumber.setError("Card Number is empty");
            return;
        }
        else if (TextUtils.isEmpty(cardcvv.getText().toString()) && radioVButton.isChecked())
        {
            cardcvv.setError("CVV is empty");
            return;
        }
        else if (confirm_password.getText().toString().equals(password.getText().toString()) && !confirm_password.getText().toString().equals(Prevalent.currentOnlineUser.getPassword()))
        {
            password.setError("Incorrect Password");
            return;
        }
        else if (!confirm_password.getText().toString().equals(password.getText().toString()))
        {
            confirm_password.setError("Enter correct Password");
            return;
        }
        else
        {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder()
    {
        String savecurtime, savecurdate;

        Calendar calfordate = Calendar.getInstance();
        SimpleDateFormat currdate = new SimpleDateFormat("MMM dd, yyyy");
        savecurdate = currdate.format(calfordate.getTime());

        SimpleDateFormat currtime = new SimpleDateFormat("HH:mm:ss a");
        savecurtime = currtime.format(calfordate.getTime());

        final DatabaseReference orderref = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getUsername()).child("order");
        final HashMap<String , Object> ordermap = new HashMap<>();
        ordermap.put("TotalAMount",total);
        ordermap.put("name",Prevalent.currentOnlineUser.getUsername());
        ordermap.put("phone",Prevalent.currentOnlineUser.getPhone());
        ordermap.put("email",Prevalent.currentOnlineUser.getEmail());
        ordermap.put("address",Prevalent.currentOnlineUser.getAddress());
        ordermap.put("date",savecurdate);
        ordermap.put("time",savecurtime);
        if(radioVButton.isChecked())
        {
            ordermap.put("card name",cardname.getText().toString());
            ordermap.put("card number",cardnumber.getText().toString());
            ordermap.put("CVV",cardcvv.getText().toString());
        }

        orderref.child(savecurdate+savecurtime).updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                ArrayList<Cart> listcart;
                listcart = new ArrayList<>();
                DatabaseReference cartref =FirebaseDatabase.getInstance().getReference().child("Cart List").child(Prevalent.currentOnlineUser.getUsername()).child("products");
                Log.e("before00000", "1");
                cartref.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        Log.e("before0000", "1");
                        for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            Cart model = dataSnapshot.getValue(Cart.class);
                            listcart.add(model);
                            Log.e("before listcart.add(model);", "listcart.add(model);");
                        }


                        Log.e("before000", "1");
                        for (Cart cartItem : listcart)
                        {
                            Log.e("before for (Cart cartItem : listcart)", "for (Cart cartItem : listcart)");
                            FirebaseDatabase.getInstance().getReference().child("product").addListenerForSingleValueEvent(new ValueEventListener()
                            {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot)
                                {
                                    Log.e("before00", "1");
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                    {
                                        productModel product = snapshot.getValue(productModel.class);
                                        String key = snapshot.getKey();
                                        Log.e("before0", key);
                                        if(product.getTitle().equals(cartItem.getName()) && Integer.parseInt(product.getQuantity()) >= Integer.parseInt(cartItem.getQun()) )
                                        {
                                            Log.e("after", key);
                                            int new1 = Integer.parseInt(product.getQuantity()) - Integer.parseInt(cartItem.getQun());
                                            Log.e("after1", String.valueOf(new1));
                                            if(new1 > 0)
                                            {
                                                Log.e("after11", String.valueOf(new1));
                                                final DatabaseReference RootRef;
                                                RootRef = FirebaseDatabase.getInstance().getReference();
                                                RootRef.child("product").child(key).child("quantity").setValue(String.valueOf(new1));
                                            }
                                            else if (new1 == 0)
                                            {
                                                final DatabaseReference RootRef;
                                                RootRef = FirebaseDatabase.getInstance().getReference();
                                                RootRef.child("product").child(key).removeValue();
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });

                FirebaseDatabase.getInstance().getReference().child("Cart List")
                        .child(Prevalent.currentOnlineUser.getUsername())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
            }
        });
        reset();
        Intent intent = new Intent(bill_confrmation2.this, bill_confrmation3.class);
        startActivity(intent);
    }

    private void reset() {
        navigateUpTo(new Intent(bill_confrmation2.this, Home1.class));
        startActivity(getIntent());
    }


}