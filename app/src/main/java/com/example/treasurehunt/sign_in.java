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

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treasurehunt.Prevalent.Prevalent;
import com.example.treasurehunt.UModel.Users;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;


public class sign_in extends AppCompatActivity {
    private TextView myTextView;
    private TextView forgotPassView;
    private Button signIn;
    private ProgressDialog loadingBar;
    private TextView username, password;

    private String Db = "users";
    private CheckBox chkbox;

    ProgressDialog dialog;

    Dialog pop;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        pop = new Dialog(this);

        if (isConnected())
        {
            ScrollView layout = findViewById(R.id.mainLayout);
            sign_in.OnSwipeTouchListener onSwipeTouchListener = new sign_in.OnSwipeTouchListener(this, findViewById(R.id.mainLayout));

            myTextView = findViewById(R.id.signUp);
            myTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(sign_in.this, sign_up.class);
                    startActivity(intent);
                }
            });

            forgotPassView = findViewById(R.id.forgotPass);
            forgotPassView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(sign_in.this, forgot_password.class);
                    startActivity(intent);
                }
            });

            signIn = findViewById(R.id.signIn);
            username = findViewById(R.id.username);
            password = findViewById(R.id.password);
            loadingBar = new ProgressDialog(this,R.style.AlertDialogCustom);
            chkbox = (CheckBox) findViewById(R.id.checkBox);
            Paper.init(this);

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    LoginUser();
                }

            });

        }
        else
        {
            CreatepopUpwindow();
        }


    }

    private void LoginUser()
    {
        String iname = username.getText().toString().trim();
        String ipassword = password.getText().toString().trim();

        if (TextUtils.isEmpty(iname))
        {
            username.setError("Username is empty");
            return;
        }
        else if (TextUtils.isEmpty(ipassword))
        {
            username.setError("Password is empty");
            return;
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(iname,ipassword);
        }
    }

    private void AllowAccessToAccount(String iname, String ipassword)
    {

        if(chkbox.isChecked())
        {
            Paper.book().write(Prevalent.Username,iname);
            Paper.book().write(Prevalent.Userpass,ipassword);
        }


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.child("users").child(iname).exists())
                {
                    Users usersData =  snapshot.child("users").child(iname).getValue(Users.class);
                    if(usersData.getUsername().equals(iname))
                    {
                        if(usersData.getPassword().equals(ipassword))
                        {
                            Toast.makeText(sign_in.this,"Logged in Successfully...",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(sign_in.this , Home1.class);
                            Prevalent.currentOnlineUser = usersData;
                            finish();
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(sign_in.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(sign_in.this,"This " +iname+ " not exists.",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    public class OnSwipeTouchListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector;
        Context context;
        OnSwipeTouchListener(Context ctx, View mainView) {
            gestureDetector = new GestureDetector(ctx, new sign_in.OnSwipeTouchListener.GestureListener());
            mainView.setOnTouchListener(this);
            context = ctx;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
        public class GestureListener extends
                GestureDetector.SimpleOnGestureListener {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }
        void onSwipeRight() {
            //Toast.makeText(context, "Swiped Right", Toast.LENGTH_SHORT).show();
            //this.onSwipe.swipeRight();
        }
        void onSwipeLeft() {
            //Toast.makeText(context, "Swiped Left", Toast.LENGTH_SHORT).show();
            //this.onSwipe.swipeLeft();
        }
        void onSwipeTop() {
            //Toast.makeText(context, "Swiped Up", Toast.LENGTH_SHORT).show();
            //this.onSwipe.swipeTop();
        }
        void onSwipeBottom() {
            //Toast.makeText(context, "Swiped Down", Toast.LENGTH_SHORT).show();
            //this.onSwipe.swipeBottom();
        }
        interface onSwipeListener {
            void swipeRight();
            void swipeTop();
            void swipeBottom();
            void swipeLeft();
        }
        sign_in.OnSwipeTouchListener.onSwipeListener onSwipe;
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
    private void CreatepopUpwindow()
    {
        pop.setContentView(R.layout.popup_chk_connection);
        Button Retry = pop.findViewById(R.id.Retry);
        Button Cancel = pop.findViewById(R.id.Cancel);
        Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected()) {
                    recreate();
                    //Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    pop.dismiss();
                    Intent i=new Intent(sign_in.this,MainActivity.class);
                    startActivity(i);
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