package com.adityaedu.themathwizz.activities;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.adityaedu.themathwizz.R;


public class MainActivity extends AppCompatActivity {


    Button btn1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //setContentView(R.layout.profile_recycler);
        btn1 = (Button) findViewById(R.id.button_login);
        btn2 = (Button) findViewById(R.id.reg_register_button);
    }

        //btn1.setOnClickListener(new View.OnClickListener(){

        public void onClick(View view) {

        if (view.getId() == R.id.button_login) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.button_register) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }

}


//);


//setContentView(R.layout.activity_signup);

//setContentView(R.layout.activity_login);
        /*setContentView(R.layout.activity_register);


        */


