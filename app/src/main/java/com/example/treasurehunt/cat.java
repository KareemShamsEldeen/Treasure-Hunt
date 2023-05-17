package com.example.treasurehunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treasurehunt.Prevalent.Prevalent;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class cat extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView1;
    ArrayList<productModel> listp;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseDatabase firebaseDatabasep;


    TextView sname,semail;
    ImageView noGender , male , female;
    Dialog pop;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        pop = new Dialog(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        TextView textView = findViewById(R.id.textView2);
        String name1 = " Browse by " +name;
        textView.setText(name1);

        Toast.makeText(this,name,Toast.LENGTH_SHORT).show();

        if (isConnected())
        {
            // product/////////////////////////////////////////////////
            recyclerView1 = findViewById(R.id.recyclerView1);
            listp = new ArrayList<>();

            firebaseDatabasep = FirebaseDatabase.getInstance();

            for (productModel m : listp){
                if ( m.getCategory() != name)
                {
                    listp.remove(m);
                }
            }

            productAdepter recyclerAdapter = new productAdepter(listp, getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView1.setLayoutManager(linearLayoutManager);
            recyclerView1.addItemDecoration(new DividerItemDecoration(recyclerView1.getContext(),DividerItemDecoration.VERTICAL));
            recyclerView1.setNestedScrollingEnabled(false);
            recyclerView1.setAdapter(recyclerAdapter);

            firebaseDatabasep.getReference().child("product").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        productModel model = dataSnapshot.getValue(productModel.class);
                        String c = dataSnapshot.child("category").getValue().toString();
                        if(c.equals(name))
                        {
                            listp.add(model);
                        }

                    }
                    recyclerAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else
        {
            CreatepopUpwindow();
        }


        setNavigationViewListener();


        // product/////////////////////////////////////////////////

        // Side_NAV/////////////////////////////////////////////////
        Button hamMenu = findViewById(R.id.hamBtn);
        hamMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout navDrawer = findViewById(R.id.my_drawer_layout);
                if (!navDrawer.isDrawerOpen(GravityCompat.START))
                    navDrawer.openDrawer(GravityCompat.START);
                else navDrawer.closeDrawer(GravityCompat.END);
            }

        });
        drawerLayout = findViewById(R.id.my_drawer_layout);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        // Side_NAV/////////////////////////////////////////////////

    }
    // Ham Button/////////////////////////////////////////////
    private Boolean isDarkModeOn(){
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_theme_mode){
            if(isDarkModeOn()){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
                Toast.makeText(this, "Dark Mode OFF", Toast.LENGTH_SHORT).show();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
                Toast.makeText(this, "Dark Mode ON", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(cat.this , Home1.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if (id == R.id.nav_Languages) {
            Toast.makeText(this, "You are on English Mode", Toast.LENGTH_SHORT).show();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_Previous_bills) {
            Toast.makeText(this, "Previous Bills", Toast.LENGTH_SHORT).show();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_LogOut) {
            Toast.makeText(this, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_Help_and_support) {
            Intent intent = new Intent(cat.this , help_and_support.class);
            startActivity(intent);
            Toast.makeText(this, "Help and Support", Toast.LENGTH_SHORT).show();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_About_Us) {
            Intent intent = new Intent(cat.this , about_us.class);
            startActivity(intent);
            Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }
    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View head = navigationView.getHeaderView(0);
        noGender = head.findViewById(R.id.noGender);
        male = head.findViewById(R.id.male);
        female = head.findViewById(R.id.female);
        if(!(Prevalent.currentOnlineUser.getGender().equals("")))
        {
            if(Prevalent.currentOnlineUser.getGender().equals("male")){
                male.setVisibility(View.VISIBLE);
                noGender.setVisibility(View.GONE);
            }
            if(Prevalent.currentOnlineUser.getGender().equals("female")){
                female.setVisibility(View.VISIBLE);
                noGender.setVisibility(View.GONE);
            }
        }
        sname = head.findViewById(R.id.sname);
        semail = head.findViewById(R.id.semail);
        sname.setText(Prevalent.currentOnlineUser.getUsername());
        semail.setText(Prevalent.currentOnlineUser.getEmail());
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void CreatepopUpwindow()
    {
        pop.setContentView(R.layout.popup_chk_connection);
        Button Retry = pop.findViewById(R.id.Retry);
        Button Cancel = pop.findViewById(R.id.Cancel);
        Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected())
                {
                    recreate();
                    //Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    pop.dismiss();
                    recreate();
                }
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
                finish();
            }
        });
        pop.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.show();
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
