package com.adityaedu.themathwizz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.sql.DatabaseHelper;

/**
 * Created by preetham on 1/10/2018.
 */

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private TextInputLayout username_inputlayout;
    private  TextInputLayout password_inputlayout;

    private TextInputEditText username;
    private  TextInputEditText password;

    private AppCompatButton main_button_login;

    private DatabaseHelper databaseHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListners();
        initObjects();
    }

    private  void initViews(){

        username_inputlayout =(TextInputLayout) findViewById(R.id.reg_email_inputLayout);
        password_inputlayout = (TextInputLayout) findViewById(R.id.reg_password_inputLayout);

        username = (TextInputEditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);

        main_button_login = (AppCompatButton) findViewById(R.id.main_button_login);
    }

    private  void initListners(){

        main_button_login.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
    }


    @Override
    public  void onClick(View v){

        switch (v.getId()){
            case R.id.main_button_login:
                verifyFromSQLite();
                break;
        }
    }


    private void  verifyFromSQLite(){
        if (databaseHelper.checkUser(username.getText().toString().trim()
        ,password.getText().toString().trim())){

            Intent accountIntent = new Intent(activity, UsersListActivity.class );
            accountIntent.putExtra("Email", username.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountIntent);
        }
    }

    private void emptyInputEditText(){

        username.setText(null);
        password.setText(null);
    }








}
