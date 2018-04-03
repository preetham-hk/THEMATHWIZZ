package com.adityaedu.themathwizz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adityaedu.themathwizz.Adapters.ItemOfList;
import com.adityaedu.themathwizz.Adapters.RecyclerListAdapter;
import com.adityaedu.themathwizz.Adapters.RecyclerTouchListener;
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
 */

public class UsersProfileActivity extends AppCompatActivity {

    protected TextView profile_name;
    protected TextView profile_email;
    protected TextView profile_AddChild;

    protected String childClass;
    protected String childClassQuery;
    protected String childClassSubQuery;


    ProgressDialog progressDialog;
    private String username;
    private List<ItemOfList> itemOfLists = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerListAdapter recyclerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_about);

        profile_name = findViewById(R.id.profile_name);
        profile_email = findViewById(R.id.profile_email);
        profile_AddChild = findViewById(R.id.profile_AddChild);
        recyclerView = findViewById(R.id.Profile_childAccount_Recycler);
        recyclerListAdapter = new RecyclerListAdapter(itemOfLists);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerListAdapter);

        progressDialog = ProgressDialogSpinner.showProgressDialog(this, "Loading");

        ParseUser.getCurrentUser().fetchInBackground();
        ParseUser currentUser = ParseUser.getCurrentUser();
        username = currentUser.getUsername();
        profile_name.setText(currentUser.getString("Name"));
        profile_email.setText(currentUser.getEmail());

        profile_AddChild.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddChildActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                ItemOfList selectedItem = itemOfLists.get(position);
                Log.d("Selected Child", "" + selectedItem);
                String selectedChildName = selectedItem.getTitle();
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("ChildUser");
                query1.whereEqualTo("Username", username);
                query1.whereEqualTo("ChildName", selectedChildName);
                query1.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            for (ParseObject childClassName : objects) {
                                childClass = childClassName.getString("Class");
                                Log.d("Class", childClass);
                                childClassQuery = "class_" + childClass.charAt(childClass.length() - 1);
                                Log.d("String Cut", childClassQuery);
                                childClassSubQuery = "class" + childClass.charAt(childClass.length() - 1);
                                Log.d("String Cut 2", childClassSubQuery);
                                progressDialog.dismiss();
                                //Toast.makeText(getApplicationContext(),""+ selectedChildName,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), ChildHomeActivity.class);
                                intent.putExtra("ChildClassName", childClass);
                                intent.putExtra("childClassQuery", childClassQuery);
                                intent.putExtra("childClassSubQuery", childClassSubQuery);
                                startActivity(intent);
                            }
                        }
                    }
                });

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        childUserList();

    }

    private void childUserList() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ChildUser");
        query.whereEqualTo("Username", username);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0) {
                    if (e == null) {
                        for (ParseObject childAcc : objects) {
                            String text = childAcc.getString("ChildName");
                            if (text != null && !text.isEmpty()) {
                                String childAccount = childAcc.getString("ChildName");
                                ItemOfList item = new ItemOfList(childAccount);
                                itemOfLists.add(item);
                            }
                            recyclerView.setAdapter(recyclerListAdapter);
                            progressDialog.dismiss();
                        }

                    } else {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Please Add Student", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(UsersProfileActivity.this, "No Child Account Exist, Please Add", Toast.LENGTH_LONG).show();
                }
            }
        });
        recyclerListAdapter.notifyDataSetChanged();
    }
}