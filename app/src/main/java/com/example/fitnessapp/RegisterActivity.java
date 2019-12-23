package com.example.fitnessapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fitnessapp.context.AppDatabase;
import com.example.fitnessapp.context.User;

public class RegisterActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextEmail;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    EditText mTextWeight;
    EditText mTextHeight;
    EditText mTextAge;
    Spinner mGender;
    Button mButtonRegister;
    TextView mTextViewLogin;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextUsername = findViewById(R.id.edittext_email);
        mTextEmail = findViewById(R.id.edittext_username_email);
        mTextPassword = findViewById(R.id.edittext_password);
        mTextCnfPassword = findViewById(R.id.edittext_cnf_password);
        mButtonRegister = findViewById(R.id.button_login);
        mTextViewLogin = findViewById(R.id.textview_register);
        mTextWeight = findViewById(R.id.edittext_weight);
        mTextHeight = findViewById(R.id.edittext_height);
        mTextAge = findViewById(R.id.edittext_age);
        mGender = findViewById(R.id.spinner_gender);
        db = AppDatabase.getInstance(this);
    }

    public void loginButtonOnClick(View view) {
        startLoginActivity();
    }

    public void registerButtonOnClick(View view) {
        boolean isDataCorrect = checkDataEntered();
        if (!isDataCorrect) {
            return;
        }

        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startLoginActivity();
            }
        });

        User user = new User();
        user.username = mTextUsername.getText().toString();
        user.email = mTextEmail.getText().toString();
        user.password = mTextPassword.getText().toString();
        user.weight = Float.parseFloat(mTextWeight.getText().toString());
        user.height = Float.parseFloat(mTextHeight.getText().toString());
        user.age = Integer.parseInt(mTextAge.getText().toString());
        user.gender = mGender.getSelectedItem().toString();
        db.getUserDao().insert(user);
        alertDialog.setMessage("User successfully registered " + user.username);
        alertDialog.show();
    }

    private void startLoginActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        this.finish();
    }

    private boolean isEmpty(EditText editText) {
        CharSequence str = editText.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private boolean checkDataEntered() {
        CharSequence email = mTextEmail.getText().toString();
        CharSequence password = mTextPassword.getText().toString();
        CharSequence confirmPassword = mTextCnfPassword.getText().toString();

        if (password.length() < 7) {
            mTextPassword.setError("The length of password must be more than 8 characters");
            return false;
        }

        if (isEmpty(mTextCnfPassword) || !TextUtils.equals(password, confirmPassword)) {
            mTextCnfPassword.setError("Password is not match");
            return false;
        }

        if (isEmpty(mTextEmail) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mTextEmail.setError("Is not valid email address");
            return false;
        }

        if (isEmpty(mTextUsername)) {
            mTextUsername.setError("Username is required");
            return false;
        }

        if (isEmpty(mTextPassword)) {
            mTextPassword.setError("Password is required");
            return false;
        }

        if (isEmpty(mTextHeight)) {
            mTextHeight.setError("Height is required");
            return false;
        }

        if (isEmpty(mTextWeight)) {
            mTextWeight.setError("Weight is required");
            return false;
        }

        if (isEmpty(mTextAge)) {
            mTextAge.setError("Age is required");
            return false;
        }

        return true;
    }
}




