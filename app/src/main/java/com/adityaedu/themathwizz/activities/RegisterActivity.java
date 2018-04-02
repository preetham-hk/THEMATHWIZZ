package com.adityaedu.themathwizz.activities;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;
import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import java.util.Calendar;

/**
 * Created by preetham on 1/10/2018.
 *
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText reg_dob_EditText;
    DatePickerDialog datePickerDialog;
    ParseUser user;
    protected TextInputEditText reg_email_EditText;
    protected TextInputEditText reg_password_EditText;
    protected TextInputEditText reg_name_EditText;
    protected AppCompatButton reg_register_button;

    @Override
    public  void onClick(View view){

        if (view.getId() == R.id.reg_linearLayout || view.getId() == R.id.reg_scrollview || view.getId()==R.id.reg_imageView){

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }

    }
            protected AwesomeValidation awesomeValidation;
            public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            //Keyboard Manager

            awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
            awesomeValidation.setContext(this);

            awesomeValidation.addValidation(this, R.id.reg_email_EditText, Patterns.EMAIL_ADDRESS, R.string.error_message_email);
            awesomeValidation.addValidation(this, R.id.reg_name_EditText, "^[A-Za-z\\s]{2,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.error_message_name);
            String regexPassword = ".{6,}";
            awesomeValidation.addValidation(this, R.id.reg_password_EditText, regexPassword, R.string.error_message_password);
            //awesomeValidation.addValidation(this, R.id.reg_rePassword_EditText, R.id.reg_password_EditText, R.string.error_password_match);

            // initiate the date picker and a button
            reg_dob_EditText =  findViewById(R.id.reg_dob_EditText);
            // perform click event on edit text
            reg_dob_EditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    reg_dob_EditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);


                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                    Log.d("Date",""+reg_dob_EditText);
                }
            });

            //Register Activity

            reg_register_button =  findViewById(R.id.reg_register_button);
            reg_email_EditText =  findViewById(R.id.reg_email_EditText);
            reg_password_EditText =  findViewById(R.id.reg_password_EditText);
            reg_name_EditText =  findViewById(R.id.reg_name_EditText);
            reg_dob_EditText =  findViewById(R.id.reg_dob_EditText);

            reg_register_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("Date",""+reg_dob_EditText);
                    if(awesomeValidation.validate()){
                        final ProgressDialog progressDialog = ProgressDialogSpinner.showProgressDialog(RegisterActivity.this,"Registering User");
                        user = new ParseUser();
                        user.setUsername(reg_email_EditText.getText().toString());
                        user.setPassword(reg_password_EditText.getText().toString());
                        user.setEmail(reg_email_EditText.getText().toString());
                        user.put("Name", reg_name_EditText.getText().toString());

                        //user.put("dateOfBirth", reg_dob_EditText.getText().toString());
                        user.signUpInBackground(new SignUpCallback() {
                             @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(getApplicationContext(), AddChildActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                } else {

                                    Toast.makeText(RegisterActivity.this, "Registration Failed "+ e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Fields Required ", Toast.LENGTH_LONG).show();
                    }
                }


            });
            ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
