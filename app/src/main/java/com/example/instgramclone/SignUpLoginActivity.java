package com.example.instgramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity {

    private EditText edtUserName, edtUserNameLogin, edtUserPassword, edtUserPasswordLogin;
    private Button btnLogin, btnSignUp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);
        edtUserName = findViewById(R.id.edtUserName);
        edtUserNameLogin = findViewById(R.id.edtUserNameLogin);
        edtUserPassword = findViewById(R.id.edtPassword);
        edtUserPasswordLogin = findViewById(R.id.edtUserPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser appUser = new ParseUser();
                appUser.setUsername(edtUserName.getText().toString());
                appUser.setPassword(edtUserPassword.getText().toString());
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            FancyToast.makeText(SignUpLoginActivity.this, "Success",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            Intent intent = new Intent(SignUpLoginActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                        } else {
                            FancyToast.makeText(SignUpLoginActivity.this, "Error",
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(edtUserNameLogin.getText().toString(), edtUserPasswordLogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null){
                            FancyToast.makeText(SignUpLoginActivity.this, "Success",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            Intent intent = new Intent(SignUpLoginActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                        } else {
                            FancyToast.makeText(SignUpLoginActivity.this, "Error",
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });
    }
}
