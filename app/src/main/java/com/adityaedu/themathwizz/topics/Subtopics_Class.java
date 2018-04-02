package com.adityaedu.themathwizz.topics.subTopics;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by preetham on 2/8/2018.
 */

public class subtopics_class1 extends AppCompatActivity {


    String subTopicName, subTopicName1;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtopics);
        textView = findViewById(R.id.subTopic_textView);

        Bundle bundle = getIntent().getExtras();
        final String subObj = bundle.getString("subObj");
        Log.e("hi",""+subObj);
        final ProgressDialog progressDialog = ProgressDialogSpinner.showProgressDialog(this, "Please Wait");

        ParseQuery<ParseObject> query=ParseQuery.getQuery("Topics");
        query.getInBackground(subObj, new GetCallback<ParseObject>(){
            public void done(ParseObject arg0, ParseException arg1) {

                if (arg1==null)
                {
                        subTopicName = arg0.getString("class_1");
                        textView.setText("" + subTopicName);

                }

                else
                {
                    Log.d("topic", "Error: " + arg1.getMessage());
                }

    }});

        final ArrayList<String> topics1 = new ArrayList<>();
        final ListView listView = findViewById(R.id.subtopic_ListView);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, topics1);

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("SubTopics");
        query1.orderByAscending("createdAt");
        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject topic : objects) {
                        String text = topic.getString("class1_topic1");
                        if (text != null && !text.isEmpty()) {
                            topics1.add(topic.getString("class1_topic1"));
                        }
                        listView.setAdapter(arrayAdapter);
                        progressDialog.dismiss();
                    }
                }
                else {
                    e.printStackTrace();
                }
            }

        });

    }
}
