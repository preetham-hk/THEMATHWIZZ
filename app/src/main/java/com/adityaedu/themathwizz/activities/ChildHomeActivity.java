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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.adityaedu.themathwizz.Adapters.RecyclerHelpers;
import com.adityaedu.themathwizz.Adapters.ItemOfList;
import com.adityaedu.themathwizz.Adapters.RecyclerListAdapter;
import com.adityaedu.themathwizz.Adapters.RecyclerTouchListener;
import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.adityaedu.themathwizz.topics.Subtopics_Class;
import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Preetham on 2/23/2018.
 */

public class ChildHomeActivity extends AppCompatActivity {
    TextView childClassView;
    private List<ItemOfList> itemOfLists = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerListAdapter recyclerListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_home);
        Bundle bundle = getIntent().getExtras();
        final String className = bundle.getString("ChildClassName");
        final String childClassQuery = bundle.getString("childClassQuery");

        final ProgressDialog progressDialog = ProgressDialogSpinner.showProgressDialog(this, "Loading");

        RecyclerHelpers recyclerHelpers = new RecyclerHelpers();
        recyclerHelpers.setFlipperLayout(this, this, R.id.child_Home_flipper_layout);

        childClassView = findViewById(R.id.child_class_name);
        childClassView.setText(className);

        recyclerView = findViewById(R.id.Child_Home_RecyclerView);

        recyclerListAdapter = new RecyclerListAdapter(itemOfLists);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerListAdapter);

        getClassTopics(childClassQuery);
        //recyclerHelpers.populateRecyclerList(this, this, R.id.Child_Home_RecyclerView, "Topics", "Class", childClassQuery, "TopicName");

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("position", "" + position);
                final String TopicSelected;
                    ItemOfList selectedItem = itemOfLists.get(position);
                    TopicSelected = selectedItem.getTitle();
                    Log.d("TopicSelected",""+TopicSelected);
                switch (position) {

                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        launchSubtopics(className, childClassQuery, TopicSelected);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        progressDialog.dismiss();
    }


    private void getClassTopics(String childClassQuery) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Topics");
        query.whereEqualTo("Class", childClassQuery);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject topic : objects) {
                        String text = topic.getString("TopicName");
                        if (text != null && !text.isEmpty()) {
                            String classTopic = topic.getString("TopicName");
                            ItemOfList item = new ItemOfList(classTopic);
                            itemOfLists.add(item);
                            recyclerView.setAdapter(recyclerListAdapter);
                        }
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
        recyclerListAdapter.notifyDataSetChanged();
    }


    private void launchSubtopics(String className, String childClassQuery, String TopicSelected) {
        Intent intent = new Intent(getApplicationContext(), Subtopics_Class.class);
        intent.putExtra("classTitle", className);
        intent.putExtra("className", childClassQuery);
        intent.putExtra("TopicName", TopicSelected);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, R.id.menu_profile, Menu.NONE, "Profile");
        menu.add(Menu.NONE, R.id.menu_allContent, Menu.NONE, "All Content");
        menu.add(Menu.NONE, R.id.menu_logout, Menu.NONE, "Logout");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile:
                Intent intent = new Intent(getApplicationContext(), UsersProfileActivity.class);
                startActivity(intent);

                return true;

            case R.id.menu_logout:

                final ProgressDialog progressDialog = ProgressDialogSpinner.showProgressDialog(this, "Logging Out");
                ParseUser.logOutInBackground(new LogOutCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            progressDialog.dismiss();
                        } else {
                            Log.i("tag", "Logout failed");
                        }
                    }
                });
                return true;

            case R.id.menu_allContent:
                Intent intent1 = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}