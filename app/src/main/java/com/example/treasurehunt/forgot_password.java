package com.example.treasurehunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treasurehunt.UModel.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class forgot_password extends AppCompatActivity {
    private Button confirm;
    private TextView username, password, email, phone, confirmPass;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        confirm = findViewById(R.id.confirm);


        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmPass = findViewById(R.id.confirmPass);

        loadingBar = new ProgressDialog(this, R.style.AlertDialogCustom);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iname = username.getText().toString().trim();
                String iemail = email.getText().toString().trim();
                String iphone = phone.getText().toString().trim();
                String ipassword = password.getText().toString().trim();
                String iconfirmPass = confirmPass.getText().toString().trim();

                if (TextUtils.isEmpty(iname)) {
                    username.setError("Username is empty");
                    return;
                } else if (TextUtils.isEmpty(iemail)) {
                    email.setError("Email is empty");
                    return;
                } else if (TextUtils.isEmpty(iphone)) {
                    phone.setError("Phone is empty");
                    return;
                } else if (TextUtils.isEmpty(ipassword)) {
                    password.setError("Password is empty");
                    return;
                } else if (TextUtils.isEmpty(iconfirmPass)) {
                    confirmPass.setError("Confirm Password is empty");
                    return;
                } else if (!ipassword.equals(iconfirmPass)) {
                    confirmPass.setError("Enter Your Password Correctly");
                    return;
                } else {
                    loadingBar.setTitle("Setting New Password");
                    loadingBar.setMessage("Please wait");
                    loadingBar.setCanceledOnTouchOutside(false);
                    final DatabaseReference RootRef;
                    RootRef = FirebaseDatabase.getInstance().getReference();

                    RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if ((snapshot.child("users").child(iname).exists())) {
                                Users usersData =  snapshot.child("users").child(iname).getValue(Users.class);
                                if(usersData.getPassword().equals(iconfirmPass)) {
                                    Toast.makeText(forgot_password.this, "Old Password", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    loadingBar.show();

                                    RootRef.child("users").child(iname).child("password").setValue(iconfirmPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(forgot_password.this, "Congratulations,Your Password has been updated", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();

                                                Intent intent = new Intent(forgot_password.this, sign_in.class);
                                                finish();
                                                startActivity(intent);

                                            } else {
                                                loadingBar.dismiss();
                                                Toast.makeText(forgot_password.this, "Error...", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                            else {
                                Toast.makeText(forgot_password.this, "User " + iname + " does not exist.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Toast.makeText(forgot_password.this, "Please try again using your Username.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                }

            }
        });
    }
}
