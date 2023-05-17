package com.example.treasurehunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treasurehunt.Prevalent.Prevalent;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;


public class Home1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // profile/////////////////////////////////////////////////
    Button search;
    // profile/////////////////////////////////////////////////
    // profile/////////////////////////////////////////////////
    Button profile;
    // profile/////////////////////////////////////////////////
    // product/////////////////////////////////////////////////
    Button upload;
    RecyclerView recyclerView1;
    ArrayList<productModel> listp;

    FirebaseDatabase firebaseDatabasep;

    // product/////////////////////////////////////////////////
    // category/////////////////////////////////////////////////
    RecyclerView recyclerView;
    DatabaseReference database;
    categoriesAdapter Adapter;
    ArrayList<category> list;
    // category/////////////////////////////////////////////////
    // Side_NAV/////////////////////////////////////////////////
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    TextView sname,semail;
    ImageView noGender , male , female;
    // Side_NAV/////////////////////////////////////////////////
    // Cart/////////////////////////////////////////////////
    ImageView cartButton;
    DatabaseReference cart= FirebaseDatabase.getInstance().getReference("Cart List");
    TextView count1;
    private ActionMode navigationView;

    // Cart/////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        setNavigationViewListener();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
        // category/////////////////////////////////////////////////
        recyclerView = findViewById(R.id.catlist);
        database = FirebaseDatabase.getInstance().getReference("categories");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        list = new ArrayList<>();
        Adapter = new categoriesAdapter(this,list);
        recyclerView.setAdapter(Adapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    category category1 = dataSnapshot.getValue(category.class);
                    list.add(category1);
                }
                Adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // category/////////////////////////////////////////////////
        // product/////////////////////////////////////////////////
        recyclerView1 = findViewById(R.id.recyclerView1);
        listp = new ArrayList<>();

        firebaseDatabasep = FirebaseDatabase.getInstance();

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
                    listp.add(model);
                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        // product/////////////////////////////////////////////////
        // Side_NAV/////////////////////////////////////////////////


        Button hamMenu = findViewById(R.id.Hambtn);
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


        // Initialize and assign variable
        //BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

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
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private Boolean isDarkModeOn(){
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_theme_mode){
            if (isDarkModeOn()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
                Toast.makeText(this, "Dark Mode OFF", Toast.LENGTH_SHORT).show();
            } else if (!isDarkModeOn()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
                item.setChecked(false);
                Toast.makeText(this, "Dark Mode ON", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.nav_Languages) {
            Toast.makeText(this, "You are on English Mode", Toast.LENGTH_SHORT).show();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (id == R.id.nav_Previous_bills)
        {
            Toast.makeText(this, "Previous Bills", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Home1.this , new_orders.class);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_LogOut)
        {
            Paper.book().destroy();
            Intent intent = new Intent(Home1.this , sign_in.class);
            finish();
            startActivity(intent);
            Toast.makeText(this, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_Help_and_support) {
            Intent intent = new Intent(Home1.this , help_and_support.class);
            startActivity(intent);
            Toast.makeText(this, "Help and Support", Toast.LENGTH_SHORT).show();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_About_Us) {
            Intent intent = new Intent(Home1.this , about_us.class);
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

    @Override
    public void onBackPressed(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.home != selectedItemId) {
            bottomNavigationView.setSelectedItemId(R.id.home);
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
            builder.setCancelable(false);
            builder.setMessage("Do you want to Exit?");
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    finishAffinity();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

}