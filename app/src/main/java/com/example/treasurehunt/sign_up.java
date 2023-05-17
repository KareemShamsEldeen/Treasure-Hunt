package com.example.treasurehunt;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Objects;


public class sign_up extends AppCompatActivity {
    private Button signUp;

    private TextView username, password, email, phone,address;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.sign_up);


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);

        loadingBar = new ProgressDialog(this,R.style.AlertDialogCustom);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CreateAccount();
            }

        });
    }

    private void CreateAccount()
    {
        String iname = username.getText().toString().trim();
        String iemail = email.getText().toString().trim();
        String ipassword = password.getText().toString().trim();
        String iphone = phone.getText().toString().trim();
        String iaddress = address.getText().toString().trim();

        if (TextUtils.isEmpty(iname))
        {
            username.setError("Username is empty");
            return;
        }
        else if (TextUtils.isEmpty(iemail))
        {
            email.setError("Email is empty");
            return;
        }
        else if (TextUtils.isEmpty(ipassword))
        {
            password.setError("Password is empty");
            return;
        }
        else if (TextUtils.isEmpty(iaddress))
        {
            address.setError("Address is empty");
            return;
        }
        else if (TextUtils.isEmpty(iphone))
        {
            phone.setError("Phone is empty");
            return;
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateUsername(iname,iphone,ipassword,iemail,iaddress);

        }


    }

    private void ValidateUsername(String iname, String iphone, String ipassword,String iemail,String iaddress)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (!(snapshot.child("users").child(iname).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("username",iname);
                    userdataMap.put("phone",iphone);
                    userdataMap.put("password",ipassword);
                    userdataMap.put("email",iemail);
                    userdataMap.put("address",iaddress);
                    userdataMap.put("gender","");
                    userdataMap.put("day","0");
                    userdataMap.put("month","0");
                    userdataMap.put("year","0");
                    RootRef.child("users").child(iname).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(sign_up.this,"Congratulations,Your Account has been created",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(sign_up.this , sign_in.class);
                                        finish();
                                        startActivity(intent);

                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(sign_up.this,"Error...",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(sign_up.this,"User " +iname+ " already exists.",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(sign_up.this,"Please try again using another Username.",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(sign_up.this , sign_in.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int)ev.getRawX();
                int rawY = (int)ev.getRawY();
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}