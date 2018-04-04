package com.adityaedu.themathwizz.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adityaedu.themathwizz.Adapters.AsyncTaskUpdateList;
import com.adityaedu.themathwizz.Adapters.RecyclerHelpers;
import com.adityaedu.themathwizz.Adapters.ItemOfList;
import com.adityaedu.themathwizz.Adapters.RecyclerTouchListener;
import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.adityaedu.themathwizz.quiz.RecentQuiz;
import com.adityaedu.themathwizz.topics.Subtopics_Class;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Preetham on 2/23/2018.
 */

public class ChildHomeActivity extends AppCompatActivity {
    TextView childClassView;
    private List<ItemOfList> itemOfLists = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerHelpers recyclerHelpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_home);
        Bundle bundle = getIntent().getExtras();
        final String className = bundle.getString("ChildClassName");
        final String childClassQuery = bundle.getString("childClassQuery");

        childClassView = findViewById(R.id.child_class_name);
        childClassView.setText(className);


         recyclerView = findViewById(R.id.Child_Home_RecyclerView);
         Button activities_Button = findViewById(R.id.ChildHome_activities_Button);
         activities_Button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(), RecentQuiz.class);
                 startActivity(intent);
             }
         });

        //Loading ImageSlidShow
        recyclerHelpers = new RecyclerHelpers();
        recyclerHelpers.setFlipperLayout(this, this, R.id.child_Home_flipper_layout);

        //Getting List of Topics
        AsyncTaskUpdateList asyncTaskUpdateList = new AsyncTaskUpdateList("Topics", "Class", childClassQuery, "TopicName", this, new AsyncTaskUpdateList.AsyncResponse() {
            @Override
            public void processFinish(List<ItemOfList> output) {
                itemOfLists = output;
                //Adding RecyclerView
                recyclerHelpers.addRecyclerView(ChildHomeActivity.this, ChildHomeActivity.this, R.id.Child_Home_RecyclerView, itemOfLists, LinearLayoutManager.VERTICAL);

                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        final String TopicSelected;
                        ItemOfList selectedItem = itemOfLists.get(position);
                        TopicSelected = selectedItem.getTitle();
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
            }
        });

        asyncTaskUpdateList.execute();

        /*
        try {
        //    itemOfLists=asyncTaskUpdateList.get();
            Log.d("List",""+itemOfLists);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        */

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