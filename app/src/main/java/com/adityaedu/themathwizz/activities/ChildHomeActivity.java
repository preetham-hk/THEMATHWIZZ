package com.adityaedu.themathwizz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
 *
 * Created by Preetham on 2/23/2018.
 */

public class ChildHomeActivity extends AppCompatActivity {
    TextView childClassView;
    ListView listView;

    String[] classObjId = new String[]{"fRGt3oz8af","4lwOVfRBs8","D9gbpqwMJz","wYh8XBOMyn","dxiqNhT4Vn","n0lPYRWw61","ZZAqadyNnm","oTqiGL7CiW","aJPgspDUte"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_home);
        Bundle bundle = getIntent().getExtras();
        final String className = bundle.getString("ChildClassName");
        final String childClassQuery = bundle.getString("childClassQuery");
        final String childClassSubQuery = bundle.getString("childClassSubQuery");
        Log.d("class Title", "" + className);
        Log.d("class Query", "" + childClassQuery);
        Log.d("childClassSubQuery",""+childClassSubQuery);

        final ProgressDialog progressDialog = ProgressDialogSpinner.showProgressDialog(this, "Loading");

        childClassView = findViewById(R.id.child_class_name);
        childClassView.setText(className);
        listView = findViewById(R.id.child_listView_topics);


        final ArrayList<String> topics1 = new ArrayList<>();
        final ListView listView = findViewById(R.id.child_listView_topics);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, topics1);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Topics");
        query.whereEqualTo("Class",childClassQuery);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject topic : objects) {
                        String text = topic.getString("TopicName");
                        if (text != null && !text.isEmpty()) {
                            topics1.add(topic.getString("TopicName"));
                        }
                        listView.setAdapter(arrayAdapter);
                        progressDialog.dismiss();
                    }
                } else {
                    e.printStackTrace();
                }
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    Intent intent = new Intent(getApplicationContext(), Subtopics_Class.class);
                    intent.putExtra("classTitle",className);
                    intent.putExtra("className",childClassQuery);
                    intent.putExtra("subObj",classObjId[0]);
                    String childClassSubQuery1 = childClassSubQuery+"_topic1";
                    intent.putExtra("subTopicObj",childClassSubQuery1);
                    startActivity(intent);
                }

                else if (position==1)
                {
                    Intent intent = new Intent(getApplicationContext(), Subtopics_Class.class);
                    intent.putExtra("classTitle",className);
                    intent.putExtra("className",childClassQuery);
                    intent.putExtra("subObj",classObjId[1]);
                    String childClassSubQuery1 = childClassSubQuery+"_topic2";
                    intent.putExtra("subTopicObj",childClassSubQuery1);
                    startActivity(intent);
                }
                else if (position==2)
                {
                    Intent intent = new Intent(getApplicationContext(), Subtopics_Class.class);
                    intent.putExtra("classTitle",className);
                    intent.putExtra("className",childClassQuery);
                    intent.putExtra("subObj",classObjId[2]);
                    String childClassSubQuery1 = childClassSubQuery+"_topic3";
                    intent.putExtra("subTopicObj",childClassSubQuery1);
                    startActivity(intent);
                }
                else if (position==3)
                {
                    Intent intent = new Intent(getApplicationContext(), Subtopics_Class.class);
                    intent.putExtra("classTitle",className);
                    intent.putExtra("className",childClassQuery);
                    intent.putExtra("className",childClassQuery);
                    intent.putExtra("subObj",classObjId[3]);
                    String childClassSubQuery1 = childClassSubQuery+"_topic4";
                    intent.putExtra("subTopicObj",childClassSubQuery1);
                    startActivity(intent);
                }
                else if (position==4)
                {
                    Intent intent = new Intent(getApplicationContext(), Subtopics_Class.class);
                    intent.putExtra("classTitle",className);
                    intent.putExtra("className",childClassQuery);
                    intent.putExtra("subObj",classObjId[4]);
                    String childClassSubQuery1 = childClassSubQuery+"_topic5";
                    intent.putExtra("subTopicObj",childClassSubQuery1);
                    startActivity(intent);
                }
                else if (position==5)
                {
                    Intent intent = new Intent(getApplicationContext(), Subtopics_Class.class);
                    intent.putExtra("classTitle",className);
                    intent.putExtra("className",childClassQuery);
                    intent.putExtra("subObj",classObjId[5]);
                    String childClassSubQuery1 = childClassSubQuery+"_topic6";
                    intent.putExtra("subTopicObj",childClassSubQuery1);
                    startActivity(intent);
                }
                else if (position==6)
                {
                    Intent intent = new Intent(getApplicationContext(), Subtopics_Class.class);
                    intent.putExtra("classTitle",className);
                    intent.putExtra("className",childClassQuery);
                    intent.putExtra("subObj",classObjId[6]);
                    String childClassSubQuery1 = childClassSubQuery+"_topic7";
                    intent.putExtra("subTopicObj",childClassSubQuery1);
                    startActivity(intent);
                }
                else if (position==7)
                {
                    Intent intent = new Intent(getApplicationContext(), Subtopics_Class.class);
                    intent.putExtra("classTitle",className);
                    intent.putExtra("className",childClassQuery);
                    intent.putExtra("subObj",classObjId[7]);
                    String childClassSubQuery1 = childClassSubQuery+"_topic8";
                    intent.putExtra("subTopicObj",childClassSubQuery1);
                    startActivity(intent);
                }
                else if (position==8)
                {
                    Intent intent = new Intent(getApplicationContext(), Subtopics_Class.class);
                    intent.putExtra("classTitle",className);
                    intent.putExtra("className",childClassQuery);
                    intent.putExtra("subObj",classObjId[8]);
                    String childClassSubQuery1 = childClassSubQuery+"_topic9";
                    intent.putExtra("subTopicObj",childClassSubQuery1);
                    startActivity(intent);
                }
            }
        });


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
                            Log.i("tag","Logout failed");
                        }
                    }
                });
                return true;

            case R.id.menu_allContent:
                Intent intent1 = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}