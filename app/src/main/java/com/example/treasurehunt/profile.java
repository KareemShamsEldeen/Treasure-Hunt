package com.example.treasurehunt;

import static android.view.View.VISIBLE;
import static java.sql.Types.NULL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treasurehunt.Prevalent.Prevalent;
import com.example.treasurehunt.UModel.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

import io.paperdb.Paper;

public class profile extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Button dateButton;
    private Button back;

    private RadioButton radiomButton,radiowButton;
    private RadioGroup radiogGroup;
    ImageView p1,p2;
    private Button update;
    private int year, month, day;
    TextView first_name,address,email,phone;
    private ProgressDialog loadingBar;

    public boolean isKeyBoardVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);


        p1 = findViewById(R.id.profilem);
        p2 = findViewById(R.id.profilew);

        radiogGroup = (RadioGroup) findViewById(R.id.radioGroup);
        first_name = findViewById(R.id.first_name);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);


        if(!(Prevalent.currentOnlineUser.getGender().equals("")))
        {
            if(Prevalent.currentOnlineUser.getGender().equals("male")){
                p1.setVisibility(View.VISIBLE);
                p2.setVisibility(View.GONE);
            }
            if(Prevalent.currentOnlineUser.getGender().equals("female")){
                p2.setVisibility(View.VISIBLE);
                p1.setVisibility(View.GONE);
            }
        }
        else
        {
            radiomButton = (RadioButton) findViewById(R.id.male);
            radiowButton = (RadioButton) findViewById(R.id.female);
            radiomButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    p1.setVisibility(View.VISIBLE);
                    p2.setVisibility(View.GONE);
                }
            });

            radiowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    p2.setVisibility(View.VISIBLE);
                    p1.setVisibility(View.GONE);
                }
            });
        }





        first_name.setText(Prevalent.currentOnlineUser.getUsername());
        address.setText(Prevalent.currentOnlineUser.getAddress());
        email.setText(Prevalent.currentOnlineUser.getEmail());
        phone.setText(Prevalent.currentOnlineUser.getPhone());


        first_name.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    isKeyBoardVisible = true;
                    BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                else
                {
                    isKeyBoardVisible = false;
                    BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }

            }
        });

        address.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    isKeyBoardVisible = true;
                    BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                else
                {
                    isKeyBoardVisible = false;
                    BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }

            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    isKeyBoardVisible = true;
                    BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                else
                {
                    isKeyBoardVisible = false;
                    BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }

            }
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    isKeyBoardVisible = true;
                    BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setVisibility(View.GONE);
                }
                else
                {
                    isKeyBoardVisible = false;
                    BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }

            }
        });


        if(Prevalent.currentOnlineUser.getYear() != "0" && Prevalent.currentOnlineUser.getMonth() != "0" && Prevalent.currentOnlineUser.getDay() != "0")
        {
            year = Integer.parseInt(Prevalent.currentOnlineUser.getYear());
            month = Integer.parseInt(Prevalent.currentOnlineUser.getMonth());
            day = Integer.parseInt(Prevalent.currentOnlineUser.getDay());
        }
        loadingBar = new ProgressDialog(this, R.style.AlertDialogCustom);


        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });




        dateButton = findViewById(R.id.date_btn);
        if(year != 0 && month != 0 && day != 0)
        {
            String dateString = String.format(Locale.getDefault(), "%02d/%02d/%d", day, month , year);
            dateButton.setText(dateString);
        }
        dateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if(year == 0 && month == 0 && day == 0)
                {
                    showDatePickerDialog();
                }
                else
                {
                    showDatePickerDialog(year, month, day);
                }
            }


        });



        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ifirst_name = first_name.getText().toString().trim();
                String iemail = email.getText().toString().trim();
                String iphone = phone.getText().toString().trim();
                String iaddress = address.getText().toString().trim();
                // get selected radio button from radioGroup
                int selectedId = radiogGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radiomButton = (RadioButton) findViewById(selectedId);

                loadingBar.setTitle("Updating Your Profile");
                loadingBar.setMessage("Please wait");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();

                if(ifirst_name.equals(Prevalent.currentOnlineUser.getUsername().trim()))
                {
                    RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if ((snapshot.child("users").child(ifirst_name).exists())) {
                                Users usersData =  snapshot.child("users").child(ifirst_name).getValue(Users.class);
                                if(!usersData.getEmail().equals(iemail) && !TextUtils.isEmpty(iemail)) {
                                    Prevalent.currentOnlineUser.setEmail(iemail);
                                    RootRef.child("users").child(ifirst_name).child("email").setValue(iemail);
                                }
                                if(!usersData.getAddress().equals(iaddress) && !TextUtils.isEmpty(iaddress)){
                                    Prevalent.currentOnlineUser.setAddress(iaddress);
                                    RootRef.child("users").child(ifirst_name).child("address").setValue(iaddress);
                                }
                                if(!usersData.getPhone().equals(iphone) && !TextUtils.isEmpty(iphone)){
                                    Prevalent.currentOnlineUser.setPhone(iphone);
                                    RootRef.child("users").child(ifirst_name).child("phone").setValue(iphone);
                                }
                                if(!usersData.getDay().equals(Integer.toString(day)) && !TextUtils.isEmpty(Integer.toString(day))){
                                    RootRef.child("users").child(ifirst_name).child("day").setValue(Integer.toString(day));
                                }
                                if(!usersData.getMonth().equals(Integer.toString(month)) && !TextUtils.isEmpty(Integer.toString(month))){
                                    RootRef.child("users").child(ifirst_name).child("month").setValue(Integer.toString(month));
                                }
                                if(!usersData.getYear().equals(Integer.toString(year)) && !TextUtils.isEmpty(Integer.toString(year))){
                                    RootRef.child("users").child(ifirst_name).child("year").setValue(Integer.toString(year));
                                }
                                if(selectedId == R.id.male){
                                    p1.setVisibility(View.VISIBLE);
                                    p2.setVisibility(View.GONE);
                                    RootRef.child("users").child(ifirst_name).child("gender").setValue("male");
                                }
                                if(selectedId == R.id.female){
                                    p2.setVisibility(View.VISIBLE);
                                    p1.setVisibility(View.GONE);
                                    RootRef.child("users").child(ifirst_name).child("gender").setValue("female");
                                }
                                loadingBar.dismiss();

                                Paper.book().destroy();

                            }
                            else {
                                Toast.makeText(profile.this, "User " + ifirst_name + " does not exist.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Toast.makeText(profile.this, "Please try again using your Username.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                    Toast.makeText(profile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    reset();
                    Intent intent = new Intent(profile.this, MainActivity.class);
                    startActivity(intent);


                }
                else
                {
                    loadingBar.dismiss();
                    Toast.makeText(profile.this, "You can't change your Username", Toast.LENGTH_SHORT).show();
                }
            }

        });


        // Initialize and assign variable
        //BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        // Set profile selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

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
                        startActivity(new Intent(getApplicationContext(),search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });


    }
    @Override
    public void onBackPressed(){
        finish();
        Intent intent = new Intent(profile.this, Home1.class);
        startActivity(intent);
    }
    private void reset() {
        navigateUpTo(new Intent(profile.this, Home1.class));
        startActivity(getIntent());
    }
    private void showDatePickerDialog()
    {
        // Get the current date

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        // Create a new DatePickerDialog instance
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.ThemeOverlay_App_DatePicker,this, year, month, day);

        // Show the dialog
        datePickerDialog.show();

    }
    private void showDatePickerDialog(int year, int month, int day)
    {
        // Create a new DatePickerDialog instance
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.ThemeOverlay_App_DatePicker,this, year, month-1, day);

        // Show the dialog
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day)
    {
        // Save the selected date
        this.year = year;
        this.month = month+1;
        this.day = day;

        // Update the text of the button with the selected date
        String dateString = String.format(Locale.getDefault(), "%02d/%02d/%d", day, month+1 , year);
        dateButton.setText(dateString);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
        {
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText)
            {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int)ev.getRawX();
                int rawY = (int)ev.getRawY();
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                    hideKeyboard(view);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void hideKeyboard(View view)
    {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}