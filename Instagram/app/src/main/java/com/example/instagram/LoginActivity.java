package com.example.instagram;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private final static String TAG = "LoginActivity";
    private final int REQUEST_CODE = 22;

    private Button btnLogin;
    private Button btnSignUp;
    private EditText etUsername;
    private EditText etPassword;

    private ActivityResultLauncher<Intent> singUpLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSingUp);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignUpActivity();
            }
        });

        singUpLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            Log.i(TAG,data.getExtras().getString("username"));
                            //Toast.makeText(LoginActivity.this, data.getExtras().getString("username"),Toast.LENGTH_LONG).show();
                            signUpUser(data.getExtras().getString("username"),data.getExtras().getString("password"));
                        }
                    }
                });
    }

    private void loginUser(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.e(TAG,"Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Fail login!",Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success login!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUpUser(String username, String password){
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG,"Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Fail SignUp!",Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success SignUp!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goSignUpActivity(){
        Intent intent = new Intent(this, SignUpActivity.class);
        singUpLauncher.launch(intent);
    }

    private void goMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}