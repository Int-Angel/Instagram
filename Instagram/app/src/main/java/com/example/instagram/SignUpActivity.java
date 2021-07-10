package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";

    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.etUsernameSU);
        etPassword = findViewById(R.id.etPasswordSU);
        btnSignup = findViewById(R.id.btnSingUpSU);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });

    }

    private void signUpUser(String username, String password) {
        Intent data = new Intent();
        data.putExtra("username", username);
        data.putExtra("password", password);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

}