package com.example.treasurehunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class confirm_upload extends AppCompatActivity {
    Button continuee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_upload);
        continuee = findViewById(R.id.continuee);
        continuee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(confirm_upload.this, Home1.class);
                startActivity(intent);
            }
        });
    }
}