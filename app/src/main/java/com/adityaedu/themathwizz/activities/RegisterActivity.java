package com.adityaedu.themathwizz.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;

import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.model.User;
import com.adityaedu.themathwizz.sql.DatabaseHelper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

/**
 * Created by preetham on 1/10/2018.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener  {

    String[] SPINNERClass = {"Class 1", "Class 2", "Class 3", "Class 4", "Class 5", "Class 6",
            "Class 7", "Class 8", "Class 9",};

    private  final AppCompatActivity activity = RegisterActivity.this;

    private ScrollView scrollView;

    /* private TextInputLayout reg_email_inputlayout;
    private  TextInputLayout reg_password_inputLayout;
    private TextInputLayout reg_rePassword_inputLayout;
    private TextInputLayout reg_dob_inputLayout;
    */
    private TextInputEditText username_textfield;
    private TextInputEditText password_textfield;
    private TextInputEditText repassword_textfield;
    private TextInputEditText dob_textfield;

    private AppCompatButton btn_register;
    private DatabaseHelper databaseHelper;
    private User user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERClass);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.class_Spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);

        initViews();
        initListners();
        initObjects();
    }
        private void  initViews(){

            scrollView = (ScrollView) findViewById(R.id.scrollview);
            /*reg_email_inputlayout = (TextInputLayout) findViewById(R.id.reg_email_inputlayout);
            reg_password_inputLayout = (TextInputLayout) findViewById(R.id.reg_password_inputLayout);
            reg_rePassword_inputLayout = (TextInputLayout) findViewById(R.id.reg_rePassword_inputLayout);
            reg_dob_inputLayout = (TextInputLayout) findViewById(R.id.reg_dob_inputLayout);
            */

            username_textfield = (TextInputEditText) findViewById(R.id.reg_email_EditText);
            password_textfield = (TextInputEditText) findViewById(R.id.reg_password_EditText);
            repassword_textfield =(TextInputEditText) findViewById(R.id.reg_rePassword_EditText);
            dob_textfield =(TextInputEditText) findViewById(R.id.reg_dob_EditText);

            btn_register =(AppCompatButton) findViewById(R.id.reg_register_button);


        }

        // initialize Listners

        private void initListners (){

            btn_register.setOnClickListener(this);
        }

        private void initObjects(){

            databaseHelper = new DatabaseHelper(activity);
            user = new User();
        }

        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.reg_register_button:
                    postDataToSQLite();
                    break;

            }
        }

        private  void postDataToSQLite(){

            if (!databaseHelper.checkUser(username_textfield.getText().toString().trim())) {


                user.setEmail(username_textfield.getText().toString().trim());
                user.setPassword(password_textfield.getText().toString().trim());

                databaseHelper.addUser(user);

                // Snack Bar to show success message that record saved successfully
                Snackbar.make(scrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
                emptyInputEditText();


            } else {
                // Snack Bar to show error message that record already exists
                Snackbar.make(scrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
            }


        }
              private void emptyInputEditText() {
                  username_textfield.setText(null);
                  dob_textfield.setText(null);
                  password_textfield.setText(null);
                  repassword_textfield.setText(null);
    }
}



