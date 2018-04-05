package com.adityaedu.themathwizz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


/**
 * Created by Preetham on 2/20/2018.
 */

public class AddChildActivity extends AppCompatActivity implements View.OnClickListener {

    String[] SPINNERClass = {"Class 1", "Class 2", "Class 3", "Class 4", "Class 5", "Class 6", "Class 7", "Class 8", "Class 9"};

    String[] SchoolClass = {"CBSE", "ICSE", "NCERT"};

    protected TextInputEditText reg_childName_EditText;
    protected RadioGroup radioGroup;
    protected MaterialBetterSpinner materialBetterSpinner;
    protected TextInputEditText reg_school_EditText;
    protected String itemValue;
    protected String curriculumValue;
    protected String gender;
    protected AppCompatButton reg_child_button;
    protected ParseObject childUser;

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.reg_child_scrollview || view.getId() == R.id.reg_child_relativeLayout || view.getId() == R.id.AddChild_imageView) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_register);

        reg_childName_EditText = findViewById(R.id.reg_childName_EditText);
        reg_school_EditText = findViewById(R.id.reg_school_EditText);
        reg_child_button = findViewById(R.id.reg_child_button);

        radioGroup = findViewById(R.id.gender_radioGroup);
        gender = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();

        //class Select
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, SPINNERClass);

        materialBetterSpinner = findViewById(R.id.class_Spinner);
        materialBetterSpinner.setAdapter(arrayAdapter);
        materialBetterSpinner.setAdapter(arrayAdapter);
        materialBetterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemValue = parent.getItemAtPosition(position).toString();
            }
        });

        // select curriculum
        final ArrayAdapter<String> school = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, SchoolClass);
        final MaterialBetterSpinner materialDesignSpinner1 = findViewById(R.id.curriculum_Spinner);
        materialDesignSpinner1.setAdapter(school);

        materialDesignSpinner1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curriculumValue = parent.getItemAtPosition(position).toString();
            }
        });

        //Add child

        reg_child_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                boolean failFlag = false;
                if (reg_childName_EditText.getText().toString().trim().length() == 0) {
                    failFlag = true;
                    reg_childName_EditText.setError("Please Enter Child Name");
                }
                if (reg_school_EditText.getText().toString().trim().length() == 0) {
                    failFlag = true;
                    reg_school_EditText.setError("Please Enter School Name");
                }
                if (itemValue == null) {
                    failFlag = true;
                    materialBetterSpinner.setError("Class is required");
                }
                if (curriculumValue == null) {
                    failFlag = true;
                    materialDesignSpinner1.setError("Curriculum is required");
                }


                // if all are fine
                if (failFlag) {
                    Toast.makeText(AddChildActivity.this, "All Fields are required", Toast.LENGTH_LONG).show();
                } else {
                    final ProgressDialog progressDialog = ProgressDialogSpinner.showProgressDialog(AddChildActivity.this, "Adding Child");
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    String userName = currentUser.getUsername();
                    childUser = new ParseObject("ChildUser");
                    childUser.put("Username", userName);
                    childUser.put("ChildName", reg_childName_EditText.getText().toString());
                    childUser.put("Gender", gender);
                    childUser.put("Class", itemValue);
                    childUser.put("Curriculum", curriculumValue);
                    childUser.put("School", reg_school_EditText.getText().toString());
                    ParseACL postACL = new ParseACL(ParseUser.getCurrentUser());
                    postACL.setPublicReadAccess(true);
                    postACL.setPublicWriteAccess(true);
                    childUser.setACL(postACL);
                    childUser.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                progressDialog.dismiss();
                                Toast.makeText(AddChildActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), ChildHomeActivity.class);
                                try {
                                    intent.putExtra("ChildClassName", itemValue);
                                    String childClassQuery = "class_" + itemValue.charAt(itemValue.length() - 1);
                                    Log.d("String Cut", childClassQuery);
                                    String childClassSubQuery = "class" + itemValue.charAt(itemValue.length() - 1);
                                    Log.d("String Cut 2", childClassSubQuery);
                                    intent.putExtra("childClassQuery", childClassQuery);
                                    intent.putExtra("childClassSubQuery", childClassSubQuery);
                                    startActivity(intent);
                                    finish();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                    Log.d("ChildAddError", "" + e1.getMessage());
                                }
                            } else {
                                // fail
                                Log.e("Tag", "getting to fail " + e.getMessage());
                            }
                        }
                    });

                }
            }
        });
    }
}
