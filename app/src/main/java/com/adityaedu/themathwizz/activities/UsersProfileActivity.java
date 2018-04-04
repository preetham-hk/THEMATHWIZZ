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

import com.adityaedu.themathwizz.Adapters.AsyncTaskUpdateList;
import com.adityaedu.themathwizz.Adapters.ItemOfList;
import com.adityaedu.themathwizz.Adapters.RecyclerHelpers;
import com.adityaedu.themathwizz.Adapters.RecyclerListAdapter;
import com.adityaedu.themathwizz.Adapters.RecyclerTouchListener;
import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.adityaedu.themathwizz.helpers.AsyncQuery;
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

    private String username;
    private List<ItemOfList> itemOfLists = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_about);

        profile_name = findViewById(R.id.profile_name);
        profile_email = findViewById(R.id.profile_email);
        profile_AddChild = findViewById(R.id.profile_AddChild);
        recyclerView = findViewById(R.id.Profile_childAccount_Recycler);

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

        AsyncTaskUpdateList asyncTaskUpdateList = new AsyncTaskUpdateList("ChildUser", "Username", username, "ChildName", this, new AsyncTaskUpdateList.AsyncResponse() {
            @Override
            public void processFinish(List<ItemOfList> output) {
                itemOfLists = output;

                if (itemOfLists.size() <= 0) {
                    Toast.makeText(getApplicationContext(), "Please Add Student", Toast.LENGTH_LONG).show();
                }
                RecyclerHelpers recyclerHelpers = new RecyclerHelpers();
                recyclerHelpers.addRecyclerView(UsersProfileActivity.this, UsersProfileActivity.this, R.id.Profile_childAccount_Recycler, itemOfLists, LinearLayoutManager.VERTICAL);

                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        ItemOfList selectedItem = itemOfLists.get(position);
                        String selectedChildName = selectedItem.getTitle();

                        AsyncQuery asyncQuery = new AsyncQuery();
                        List<String> childList = asyncQuery.doInBackground("ChildUser","Username",username,"ChildName",selectedChildName,"Class");
                        childClass = childList.get(0);
                        childClassQuery = "class_" + childClass.charAt(childClass.length() - 1);
                        childClassSubQuery = "class" + childClass.charAt(childClass.length() - 1);

                        launchAcivity();
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                    }
                }));
            }
        });
        asyncTaskUpdateList.execute();

        //itemOfLists = asyncTaskUpdateList.doInBackground("ChildUser", "Username", username, "ChildName");
    }

    public void  launchAcivity(){
        Intent intent = new Intent(getApplicationContext(), ChildHomeActivity.class);
        intent.putExtra("ChildClassName", childClass);
        intent.putExtra("childClassQuery", childClassQuery);
        intent.putExtra("childClassSubQuery", childClassSubQuery);
        startActivity(intent);
    }
}