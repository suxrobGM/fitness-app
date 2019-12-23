package com.example.fitnessapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessapp.context.AppDatabase;
import com.example.fitnessapp.context.User;

public class LoginActivity extends AppCompatActivity {
    EditText mTextUsernameEmail;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    AppDatabase db;
    User signedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = AppDatabase.getInstance(this);
        mTextUsernameEmail = findViewById(R.id.edittext_username_email);
        mTextPassword = findViewById(R.id.edittext_password);
        mButtonLogin = findViewById(R.id.button_login);
        mTextViewRegister = findViewById(R.id.textview_register);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isDataCorrect = checkDataEntered();
                String password = mTextPassword.getText().toString();

                if (!isDataCorrect) {
                    return;
                }

                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                User userByName = db.getUserDao().findByUsername(mTextUsernameEmail.getText().toString());
                User userByEmail = db.getUserDao().findByEmail(mTextUsernameEmail.getText().toString());

                if (userByEmail != null) {
                    signedUser = userByEmail;
                }
                else if (userByName != null) {
                    signedUser = userByName;
                }
                else {
                    alertDialog.setMessage("User did not found");
                    alertDialog.show();
                    return;
                }

                if (!password.equals(signedUser.password)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Password is incorrect", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.putExtra("USERNAME", signedUser.username);
        startActivity(mainIntent);
        this.finish();
    }

    private boolean isEmpty(EditText editText) {
        CharSequence str = editText.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private boolean checkDataEntered() {
        if (isEmpty(mTextUsernameEmail)) {
            mTextUsernameEmail.setError("Please enter username or email");
            return false;
        }

        if (isEmpty(mTextPassword)) {
            mTextPassword.setError("Please enter password");
            return false;
        }

        return true;
    }
}
