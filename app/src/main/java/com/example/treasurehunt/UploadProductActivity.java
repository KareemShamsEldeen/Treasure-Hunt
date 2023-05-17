package com.example.treasurehunt;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

// dexter imports
import com.example.treasurehunt.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Locale;

public class UploadProductActivity extends AppCompatActivity {
    TextView title,descriptions,price,location,descriptionp,category,name,phone,color,sername,quantity;
    ImageView uploadbtn,productImage;
    Button addproduct;
    Uri ImageURI;
    RelativeLayout relativeLayout;

    public boolean isKeyBoardVisible = false;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    ProgressDialog dialog;

    Dialog pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.sell);

        pop = new Dialog(this);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.setTitle("Product Uploading");
        dialog.setCanceledOnTouchOutside(false);

        title = findViewById(R.id.title);
        descriptions = findViewById(R.id.descriptions);
        price = findViewById(R.id.price);
        location = findViewById(R.id.location);
        descriptionp = findViewById(R.id.descriptionp);
        category = findViewById(R.id.category);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        color = findViewById(R.id.color);
        quantity = findViewById(R.id.quantity);

        name.setText(Prevalent.currentOnlineUser.getUsername());
        phone.setText(Prevalent.currentOnlineUser.getPhone());
        location.setText(Prevalent.currentOnlineUser.getAddress());

        title.setOnFocusChangeListener(new View.OnFocusChangeListener()
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
        descriptions.setOnFocusChangeListener(new View.OnFocusChangeListener()
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
        price.setOnFocusChangeListener(new View.OnFocusChangeListener()
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
        location.setOnFocusChangeListener(new View.OnFocusChangeListener()
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
        descriptionp.setOnFocusChangeListener(new View.OnFocusChangeListener()
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
        category.setOnFocusChangeListener(new View.OnFocusChangeListener()
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
        name.setOnFocusChangeListener(new View.OnFocusChangeListener()
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
        color.setOnFocusChangeListener(new View.OnFocusChangeListener()
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
        quantity.setOnFocusChangeListener(new View.OnFocusChangeListener()
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

        relativeLayout = findViewById(R.id.relimg);


        uploadbtn = findViewById(R.id.uploadbtn);
        productImage = findViewById(R.id.productImage);

        addproduct = findViewById(R.id.addproduct);


        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatepopUpwindow();
            }
        });


        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(title.getText().toString())){
                    title.setError("Title is empty");
                    return;
                }
                else if (TextUtils.isEmpty(descriptions.getText().toString())){
                    descriptions.setError("Size description is empty");
                    return;
                }
                else if (TextUtils.isEmpty(price.getText().toString())){
                    price.setError("Price is empty");
                    return;
                }
                else if (TextUtils.isEmpty(location.getText().toString())){
                    location.setError("Location is empty");
                    return;
                }
                else if (TextUtils.isEmpty(descriptionp.getText().toString())){
                    descriptionp.setError("Product description is empty");
                    return;
                }
                else if (TextUtils.isEmpty(category.getText().toString())){
                    category.setError("Category is empty");
                    return;
                }
                else if (TextUtils.isEmpty(name.getText().toString())){
                    name.setError("Name is empty");
                    return;
                }
                else if (TextUtils.isEmpty(phone.getText().toString())){
                    phone.setError("Phone is empty");
                    return;
                }
                else if (TextUtils.isEmpty(color.getText().toString())){
                    color.setError("Color is empty");
                    return;
                }
                else if (TextUtils.isEmpty(quantity.getText().toString())){
                    quantity.setError("Quantity is empty");
                    return;
                }
                else if (uploadbtn.getVisibility() == View.VISIBLE)
                {
                    pop.setContentView(R.layout.popup_pls_upload_img);
                    Button upload_img = pop.findViewById(R.id.upload_img);
                    Button Cancel = pop.findViewById(R.id.Cancel);
                    upload_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pop.dismiss();
                            CreatepopUpwindow();
                        }
                    });
                    Cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pop.dismiss();
                        }
                    });
                    pop.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    pop.show();
                }
                else
                {
                    dialog.show();

                    final StorageReference reference = firebaseStorage.getReference().child("product")
                            .child(System.currentTimeMillis()+"");

                    reference.putFile(ImageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    productModel model = new productModel();
                                    model.setProductImage(uri.toString());

                                    model.setTitle(title.getText().toString());
                                    model.setDescriptions(descriptions.getText().toString());
                                    model.setPrice(price.getText().toString());
                                    model.setLocation(location.getText().toString());
                                    model.setDescriptionp(descriptionp.getText().toString());
                                    model.setCategory(category.getText().toString());
                                    model.setName(name.getText().toString());
                                    model.setPhone(phone.getText().toString());
                                    model.setColor(color.getText().toString());
                                    model.setQuantity(quantity.getText().toString());
                                    model.setSername(title.getText().toString().toLowerCase(Locale.ROOT));

                                    database.getReference().child("product").push().setValue(model)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(UploadProductActivity.this, "Product Upload Successfully", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                    Intent intent = new Intent(UploadProductActivity.this, confirm_upload.class);
                                                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    dialog.dismiss();
                                                    Toast.makeText(UploadProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    });
                }
            }
        });




        // Initialize and assign variable
        //BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        // Set sell selected
        bottomNavigationView.setSelectedItemId(R.id.sell);

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
        Intent intent = new Intent(UploadProductActivity.this, Home1.class);
        startActivity(intent);
    }
    private void UploadImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK){
            ImageURI = data.getData();
            productImage.setImageURI(ImageURI);
        }
        else {
            relativeLayout.setVisibility(View.GONE);
            uploadbtn.setVisibility(View.VISIBLE);
        }
    }


    private void CreatepopUpwindow()
    {
        pop.setContentView(R.layout.popuppermission);
        Button allow = pop.findViewById(R.id.allow);
        Button dallow = pop.findViewById(R.id.dallow);
        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
                relativeLayout.setVisibility(View.VISIBLE);
                uploadbtn.setVisibility(View.GONE);
                pop.dismiss();
            }
        });
        dallow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
                relativeLayout.setVisibility(View.GONE);
                uploadbtn.setVisibility(View.VISIBLE);
            }
        });
        pop.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.show();
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
                    hideKeyboard(view);
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
