package com.adityaedu.themathwizz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.adityaedu.themathwizz.helpers.SessionManager;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by preetham on 1/31/2018.
 *
 */

public class LoginActivity extends AppCompatActivity {

    TextInputEditText login_email_editText;
    TextInputEditText login_password_editText;
    AppCompatButton email_login_button;
    ProgressDialog progressDialog;
    SessionManager sessionManager;

    public void showHomeActivity(){
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());

        email_login_button = findViewById(R.id.email_login_button);

        email_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login_email_editText = findViewById(R.id.login_email_editText);
                login_password_editText = findViewById(R.id.login_password_editText);

                if (login_email_editText.getText().toString().matches("") || login_password_editText.getText().toString().matches("")) {

                    Toast.makeText(getApplicationContext(), "Email and Password are required", Toast.LENGTH_SHORT).show();

                } else {

                   progressDialog = ProgressDialogSpinner.showProgressDialog(LoginActivity.this,"Please Wait");

                    final String username = login_email_editText.getText().toString();
                    final String password = login_password_editText.getText().toString();

                    ParseUser.logInInBackground(username,password, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null) {

                                sessionManager.createLoginSession(username,password);

                                progressDialog.dismiss();
                                showHomeActivity();
                            } else {
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }


            }
        });

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }
}