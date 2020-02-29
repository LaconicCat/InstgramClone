package com.example.instgramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmailLogin, edtPasswordLogin;
    private Button btnSignUpLogin, btnLoginInLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmailLogin = findViewById(R.id.edtEnterEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtEnterPasswordLogin);
        btnLoginInLogin = findViewById(R.id.btnLoginInLogin);
        btnSignUpLogin = findViewById(R.id.btnSignUpLogin);

        btnSignUpLogin.setOnClickListener(LoginActivity.this);
        btnLoginInLogin.setOnClickListener(LoginActivity.this);

        if (ParseUser.getCurrentUser() != null) {
            //ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginInLogin:
                if (edtEmailLogin.getText().toString().equals("") ||
                        edtPasswordLogin.getText().toString().equals("")) {
                    FancyToast.makeText(LoginActivity.this, "Email, Password is required!",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
                    break;
                }
                ParseUser.logInInBackground(edtEmailLogin.getText().toString(), edtPasswordLogin.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (e == null && user != null) {
                                    FancyToast.makeText(LoginActivity.this, user.getUsername() + " is Logged in.",
                                            FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                    transitionToSocialMediaActivity();
                                } else {
                                    FancyToast.makeText(LoginActivity.this, "Error" + e.getMessage(),
                                            FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                                }
                            }
                        });
                break;
            case R.id.btnSignUpLogin:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void rootLayoutTapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace(); //to the log
        }

    }

    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}
