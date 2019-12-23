package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fitnessapp.context.AppDatabase;
import com.example.fitnessapp.context.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView mTextUsername;
    TextView mTextHeight;
    TextView mTextWeight;
    ImageView mUserPhoto;
    ImageSlider mImageSlider;
    User signedUser;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextUsername = findViewById(R.id.textview_email);
        mTextHeight = findViewById(R.id.textview_height);
        mTextWeight = findViewById(R.id.textview_weight);
        mImageSlider = findViewById(R.id.image_slider);
        mUserPhoto = findViewById(R.id.user_image_view);
        db = AppDatabase.getInstance(this);

        ArrayList<SlideModel> imageList = new ArrayList();
        imageList.add(new SlideModel(R.drawable.img_slider_1));
        imageList.add(new SlideModel(R.drawable.img_slider_2));
        imageList.add(new SlideModel(R.drawable.img_slider_3));
        mImageSlider.setImageList(imageList, false);
        mImageSlider.startSliding(3000);

        Bundle extras = getIntent().getExtras();
        String username = extras.getString("USERNAME");
        signedUser = db.getUserDao().findByUsername(username);
        mTextUsername.setText(signedUser.username);
        mTextWeight.setText("Weight: " + signedUser.weight + " kg");
        mTextHeight.setText("Height: " + signedUser.height + " cm");

        mUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userDataIntent = new Intent(MainActivity.this, UserDataActivity.class);
                userDataIntent.putExtra("USERNAME", signedUser.username);
                startActivity(userDataIntent);
            }
        });
    }

}
