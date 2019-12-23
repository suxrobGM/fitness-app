package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fitnessapp.context.AppDatabase;
import com.example.fitnessapp.context.User;

public class UserDataActivity extends AppCompatActivity {
    EditText mUsername;
    EditText mEmail;
    EditText mPassword;
    EditText mAge;
    EditText mWeight;
    EditText mHeight;
    Button mSaveBtn;
    Button mBackBtn;
    //Button mChangePhotoBtn;
    ImageView mUserPhoto;
    AppDatabase db;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        mUsername = findViewById(R.id.edittext_username);
        mEmail = findViewById(R.id.edittext_email);
        mPassword = findViewById(R.id.edittext_password);
        mAge = findViewById(R.id.edittext_user_age);
        mWeight = findViewById(R.id.edittext_weight);
        mHeight = findViewById(R.id.edittext_height);
        mSaveBtn = findViewById(R.id.save_btn);
        mBackBtn = findViewById(R.id.back_home_btn);
        mUserPhoto = findViewById(R.id.user_image_view);
        //mChangePhotoBtn = findViewById(R.id.change_photo_btn);
        db = AppDatabase.getInstance(this);

        Bundle extras = getIntent().getExtras();
        String username = extras.getString("USERNAME");
        user = db.getUserDao().findByUsername(username);
        mUsername.setText(user.username);
        mEmail.setText(user.email);
        mPassword.setText(user.password);
        mAge.setText(String.format("%s", user.age));
        mWeight.setText(String.format("%s", user.weight));
        mHeight.setText(String.format("%s", user.height));
    }

    public void saveButtonOnClick(View view) {
        boolean isDataCorrect = checkDataEntered();
        if (!isDataCorrect) {
            return;
        }

        user.username = mUsername.getText().toString();
        user.email = mEmail.getText().toString();
        user.password = mPassword.getText().toString();
        user.age = Integer.parseInt(mAge.getText().toString());
        user.weight = Float.parseFloat(mWeight.getText().toString());
        user.height = Float.parseFloat(mHeight.getText().toString());
        db.getUserDao().update(user);
        Toast toast = Toast.makeText(getApplicationContext(), "All changes successfully saved", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void backButtonOnClick(View view) {
        Intent mainIntent = new Intent(UserDataActivity.this, MainActivity.class);
        mainIntent.putExtra("USERNAME", user.username);
        startActivity(mainIntent);
        this.finish();
    }

    private boolean isEmpty(EditText editText) {
        CharSequence str = editText.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private boolean checkDataEntered() {
        CharSequence email = mEmail.getText().toString();
        CharSequence password = mPassword.getText().toString();

        if (password.length() < 7) {
            mPassword.setError("The length of password must be more than 8 characters");
            return false;
        }

        if (isEmpty(mEmail) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Is not valid email address");
            return false;
        }

        if (isEmpty(mUsername)) {
            mUsername.setError("Username is not valid");
            return false;
        }

        if (isEmpty(mPassword)) {
            mPassword.setError("Password is not valid");
            return false;
        }

        if (isEmpty(mHeight)) {
            mHeight.setError("Height is not valid");
            return false;
        }

        if (isEmpty(mWeight)) {
            mWeight.setError("Weight is not valid");
            return false;
        }

        if (isEmpty(mAge)) {
            mAge.setError("Age is not valid");
            return false;
        }

        return true;
    }
}
