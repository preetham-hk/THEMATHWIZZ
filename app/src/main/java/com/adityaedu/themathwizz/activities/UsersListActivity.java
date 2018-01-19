package com.adityaedu.themathwizz.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.adityaedu.themathwizz.R;

import com.adityaedu.themathwizz.adapters.UsersRecyclerAdapter;
import com.adityaedu.themathwizz.model.User;
import com.adityaedu.themathwizz.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by preetham on 1/18/2018.
 */

public class UsersListActivity extends AppCompatActivity {


    private AppCompatActivity activity = UsersListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();
    }

    // this method initialize views

    private void initViews() {

        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);
    }

    private void initObjects(){
        listUsers = new ArrayList<>();
        usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("Email");
        textViewName.setText(emailFromIntent);
        getDataFromSQLite();
    }

    private void getDataFromSQLite() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                listUsers.clear();
                listUsers.addAll(databaseHelper.getAllUser());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                super.onPostExecute(aVoid);
                usersRecyclerAdapter.notifyDataSetChanged();
            }

        }.execute();
    }
}


