package com.example.treasurehunt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class wallpaper3 extends AppCompatActivity {
    private Button getStarted;
    private View firstPage;
    private View secondPage;
    private View thirdPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper3);

        LinearLayout layout = findViewById(R.id.mainLayout);
        OnSwipeTouchListener  onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.mainLayout));



        getStarted = (Button) findViewById(R.id.getStarted);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wallpaper3.this, sign_in.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });

        firstPage = findViewById(R.id.firstPage);
        firstPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wallpaper3.this, wallpaper1.class);
                startActivity(intent);
                Toast.makeText(wallpaper3.this, "1/3", Toast.LENGTH_SHORT).show();
            }
        });
        secondPage = findViewById(R.id.secondPage);
        secondPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wallpaper3.this, wallpaper2.class);
                startActivity(intent);
                Toast.makeText(wallpaper3.this, "2/3", Toast.LENGTH_SHORT).show();
            }
        });
        thirdPage = findViewById(R.id.thisPage);
        thirdPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(wallpaper3.this, "3/3", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public class OnSwipeTouchListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector;
        Context context;
        OnSwipeTouchListener(Context ctx, View mainView) {
            gestureDetector = new GestureDetector(ctx, new wallpaper3.OnSwipeTouchListener.GestureListener());
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
            Intent intent = new Intent(wallpaper3.this, wallpaper2.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            //Toast.makeText(context, "Swiped Right", Toast.LENGTH_SHORT).show();
            this.onSwipe.swipeRight();
        }
        void onSwipeLeft() {
            //Intent intent = new Intent(wallpaper3.this, sign_in.class);
            //startActivity(intent);
            //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            //Toast.makeText(context, "Swiped Left", Toast.LENGTH_SHORT).show();
            this.onSwipe.swipeLeft();
        }
        void onSwipeTop() {
            //Toast.makeText(context, "Swiped Up", Toast.LENGTH_SHORT).show();
            this.onSwipe.swipeTop();
        }
        void onSwipeBottom() {
            //Toast.makeText(context, "Swiped Down", Toast.LENGTH_SHORT).show();
            this.onSwipe.swipeBottom();
        }
        interface onSwipeListener {
            void swipeRight();
            void swipeTop();
            void swipeBottom();
            void swipeLeft();
        }
        wallpaper3.OnSwipeTouchListener.onSwipeListener onSwipe;
    }
}