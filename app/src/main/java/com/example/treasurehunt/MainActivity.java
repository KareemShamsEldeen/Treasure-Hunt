package com.example.treasurehunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.treasurehunt.Prevalent.Prevalent;
import com.example.treasurehunt.UModel.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private ProgressDialog loadingBar;
    Dialog pop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pop = new Dialog(this);
        loadingBar = new ProgressDialog(this,R.style.AlertDialogCustom);
        Paper.init(this);

        String Username = Paper.book().read(Prevalent.Username);
        String pass = Paper.book().read(Prevalent.Userpass);

        if (Username != "" && pass != "")
        {

            if (!TextUtils.isEmpty(Username) && !TextUtils.isEmpty(pass))
            {
                if (isConnected())
                {
                    AllowAccess(Username,pass);

                    loadingBar.setTitle("Already Logged in");
                    loadingBar.setMessage("Please wait.....");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                }
                else
                {
                    CreatepopUpwindow();
                }
            }
            else
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        Intent i=new Intent(MainActivity.this,wallpaper1.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        finish();
                    }
                }, 1000);
            }
        }


            button1 = (Button) findViewById(R.id.touch);
            button1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    //Intent intent = new Intent(MainActivity.this, wallpaper1.class);
                    //startActivity(intent);
                    //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }

            });

    }
    private void AllowAccess(String username, String pass)
    {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.child("users").child(username).exists())
                {
                    Users usersData =  snapshot.child("users").child(username).getValue(Users.class);
                    if(usersData.getUsername().equals(username))
                    {
                        if(usersData.getPassword().equals(pass))
                        {
                            Toast.makeText(MainActivity.this,"Please wait,You already Logged in....",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run()
                                {
                                    Intent intent = new Intent(MainActivity.this , Home1.class);
                                    Prevalent.currentOnlineUser = usersData;
                                    finish();
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                }
                            }, 1000);
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"This " +username+ " not exists.",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
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
                    String Username = Paper.book().read(Prevalent.Username);
                    String pass = Paper.book().read(Prevalent.Userpass);
                    AllowAccess(Username,pass);

                    loadingBar.setTitle("Already Logged in");
                    loadingBar.setMessage("Please wait.....");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
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

}