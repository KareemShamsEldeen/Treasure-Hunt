package com.example.treasurehunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class bill_confrmation3 extends AppCompatActivity {

    Button continue_shopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_confrmation3);

        continue_shopping = findViewById(R.id.continue_shopping);
        continue_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bill_confrmation3.this, Home1.class);
                startActivity(intent);
            }
        });
    }
}