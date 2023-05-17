package com.example.treasurehunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treasurehunt.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SingleProductActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    TextView pcat,pname,pprice,pcolor,desp,size,sname,sphone,plocation,sername,quantity;
    ImageView pImage;
    Button addcart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });

        ImageView cartButton;

        TextView count1;


        count1 = findViewById(R.id.count);

        cartButton = findViewById(R.id.cartButton);

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SingleProductActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        DatabaseReference cart= FirebaseDatabase.getInstance().getReference("Cart List");
        cart.child(Prevalent.currentOnlineUser.getUsername()).child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    int count = (int) snapshot.getChildrenCount();
                    count1.setText(String.valueOf(count));
                }
                else
                {
                    count1.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        pcat = findViewById(R.id.pcat);
        pname = findViewById(R.id.pname);
        pprice = findViewById(R.id.pprice);
        pcolor = findViewById(R.id.pcolor);
        desp = findViewById(R.id.desp);
        size = findViewById(R.id.size);
        sname = findViewById(R.id.sname);
        sphone = findViewById(R.id.sphone);
        plocation = findViewById(R.id.plocation);
        quantity = findViewById(R.id.quantity);

        pImage = findViewById(R.id.pImage);

        addcart = findViewById(R.id.addcart);

        Picasso.get().load(getIntent().getStringExtra("pImage")).into(pImage);

        pcat.setText(getIntent().getStringExtra("pcat"));
        pname.setText(getIntent().getStringExtra("pname"));
        pprice.setText("$"+getIntent().getStringExtra("pprice"));
        pcolor.setText(getIntent().getStringExtra("pcolor"));
        desp.setText(getIntent().getStringExtra("desp"));
        size.setText(getIntent().getStringExtra("size"));
        sname.setText(getIntent().getStringExtra("sname"));
        sphone.setText(getIntent().getStringExtra("sphone"));
        plocation.setText(getIntent().getStringExtra("plocation"));
        quantity.setText(getIntent().getStringExtra("quantity"));

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button addcart = findViewById(R.id.addcart);
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String name = pname.getText().toString();
                String img = getIntent().getStringExtra("pImage");
                String price = pprice.getText().toString();
                String rqun = quantity.getText().toString();
                addtocart(name, img ,price , "1",rqun);
            }

        });

    }

    private void addtocart(String name, String img , String price , String qun,String rqun)
    {
        String savecurtime, savecurdate;

        Calendar calfordate = Calendar.getInstance();
        SimpleDateFormat currdate = new SimpleDateFormat("MMM dd, yyyy");
        savecurdate = currdate.format(calfordate.getTime());

        SimpleDateFormat currtime = new SimpleDateFormat("HH:mm:ss a");
        savecurtime = currtime.format(calfordate.getTime());

        String strNew = price.replace("$", "");
        final HashMap<String , Object> cartmap = new HashMap<>();
        cartmap.put("name",name);
        cartmap.put("img",img);
        cartmap.put("price",strNew);
        cartmap.put("qun",qun);
        cartmap.put("rqun",rqun);
        cartmap.put("date",savecurdate);
        cartmap.put("time",savecurtime);
        cartmap.put("owner",Prevalent.currentOnlineUser.getUsername());


        mDatabase.child("Cart List").child(Prevalent.currentOnlineUser.getUsername()).child("products").child(savecurdate+savecurtime).updateChildren(cartmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(SingleProductActivity.this, "Item added to cart successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(SingleProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}