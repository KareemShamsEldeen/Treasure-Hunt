package com.example.treasurehunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasurehunt.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class search extends AppCompatActivity {
    RecyclerView recyclerView;
    searchAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.search);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        recyclerView = findViewById(R.id.recyclerviewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<productModel> options =
                new FirebaseRecyclerOptions.Builder<productModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("product"),productModel.class)
                        .build();

        mainAdapter = new searchAdapter(options,getApplicationContext());
        recyclerView.setAdapter(mainAdapter);




        // Initialize and assign variable
        //BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        // Set search selected
        bottomNavigationView.setSelectedItemId(R.id.search);
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
    public void onBackPressed(){
        Intent intent = new Intent(search.this, Home1.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.searchId);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                else
                {
                    BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }

            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String newText)
            {
                mysearch(newText.toLowerCase(Locale.ROOT));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                mysearch(newText.toLowerCase(Locale.ROOT));
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void mysearch(String str)
    {

        FirebaseRecyclerOptions<productModel> options =
                new FirebaseRecyclerOptions.Builder<productModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("product").orderByChild("sername").startAt(str).endAt(str+"\uf8ff"),productModel.class)
                        .build();
        
        mainAdapter = new searchAdapter(options,getApplicationContext());
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);

    }



    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}