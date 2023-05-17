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

public class wallpaper2 extends AppCompatActivity {
    private Button next;
    private View firstPage;
    private View secondPage;
    private View thirdPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper2);

        LinearLayout layout = findViewById(R.id.mainLayout);
        OnSwipeTouchListener  onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.mainLayout));

        firstPage = findViewById(R.id.firstPage);
        firstPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wallpaper2.this, wallpaper1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                Toast.makeText(wallpaper2.this, "1/3", Toast.LENGTH_SHORT).show();
            }
        });
        secondPage = findViewById(R.id.thisPage);
        secondPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(wallpaper2.this, "2/3", Toast.LENGTH_SHORT).show();
            }
        });
        thirdPage = findViewById(R.id.thirdPage);
        thirdPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wallpaper2.this, wallpaper3.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                Toast.makeText(wallpaper2.this, "3/3", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public class OnSwipeTouchListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector;
        Context context;
        OnSwipeTouchListener(Context ctx, View mainView) {
            gestureDetector = new GestureDetector(ctx, new wallpaper2.OnSwipeTouchListener.GestureListener());
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
            Intent intent = new Intent(wallpaper2.this, wallpaper1.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            //Toast.makeText(context, "Swiped Right", Toast.LENGTH_SHORT).show();
            this.onSwipe.swipeRight();
        }
        void onSwipeLeft() {
            Intent intent = new Intent(wallpaper2.this, wallpaper3.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            finish();
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
        wallpaper2.OnSwipeTouchListener.onSwipeListener onSwipe;
    }

}