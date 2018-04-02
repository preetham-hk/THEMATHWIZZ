package com.adityaedu.themathwizz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by preetham on 1/18/2018.
 *
 */

public class UsersProfileActivity extends AppCompatActivity {

    protected TextView profile_name;
    protected TextView profile_email;
    protected TextView profile_AddChild;
    protected ListView childListView;
    protected String selectedChildName;
    protected String childClass;
    protected String childClassQuery;
    protected String childClassSubQuery;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_about);

        profile_name = findViewById(R.id.profile_name);
        profile_email = findViewById(R.id.profile_email);
        profile_AddChild = findViewById(R.id.profile_AddChild);
        childListView = findViewById(R.id.profile_childAccount_ListView);

         progressDialog = ProgressDialogSpinner.showProgressDialog(this, "Loading");

        ParseUser.getCurrentUser().fetchInBackground();

        ParseUser currentUser = ParseUser.getCurrentUser();
        final String username = currentUser.getUsername();
        profile_name.setText(currentUser.getString("Name"));
        profile_email.setText(currentUser.getEmail());

        final ArrayList<String> childAccounts = new ArrayList<>();
        final ListView listView = findViewById(R.id.profile_childAccount_ListView);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, childAccounts);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("ChildUser");
        query.whereEqualTo("Username", username);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0){
                    if (e == null) {
                        for (ParseObject childAcc : objects) {
                            String text = childAcc.getString("ChildName");
                            if (text != null && !text.isEmpty()) {
                                childAccounts.add(childAcc.getString("ChildName"));
                            }
                            listView.setAdapter(arrayAdapter);
                            progressDialog.dismiss();
                        }

                    } else {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Please Add Student", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }
            }
            else {
                    progressDialog.dismiss();
                    Toast.makeText(UsersProfileActivity.this,"No Child Account Exist, Please Add", Toast.LENGTH_LONG).show();
               }


            }

        });

        profile_AddChild.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),AddChildActivity.class);
                startActivity(intent);
            }
        });

        childListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                progressDialog.show();
                selectedChildName = (childListView.getItemAtPosition(position)).toString();
                //getting Child Class
                ParseQuery<ParseObject> query1=ParseQuery.getQuery("ChildUser");
                query1.whereEqualTo("Username", username);
                query1.whereEqualTo("ChildName",selectedChildName);
                query1.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            for (ParseObject childClassName : objects) {
                                 childClass = childClassName.getString("Class");
                                Log.d("Class",childClass);
                                childClassQuery = "class_"+childClass.charAt(childClass.length() - 1);
                                Log.d("String Cut", childClassQuery);
                                childClassSubQuery = "class"+childClass.charAt(childClass.length() - 1);
                                Log.d("String Cut 2", childClassSubQuery);
                                progressDialog.dismiss();
                                //Toast.makeText(getApplicationContext(),""+ selectedChildName,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),ChildHomeActivity.class);
                                intent.putExtra("ChildClassName", childClass);
                                intent.putExtra("childClassQuery",childClassQuery);
                                intent.putExtra("childClassSubQuery",childClassSubQuery);
                                startActivity(intent);
                            }
                        }
                    }
                });


            }
        });

    }

}
