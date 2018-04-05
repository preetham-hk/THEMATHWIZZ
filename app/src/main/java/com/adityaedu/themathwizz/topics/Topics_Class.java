package com.adityaedu.themathwizz.topics;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.helpers.AsyncFile;


/**
 * Created by preetham on 2/14/2018.
 */

public class Topics_Class extends AppCompatActivity {

    ListView listView;
    String classTitle;
    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            className = bundle.getString("className");
            classTitle = bundle.getString("classTitle");
        }
        this.setTitle(classTitle);

        listView = findViewById(R.id.listView_topics_class1);

        //Retrieve Topics
        TopicHelpers topicHelpers = new TopicHelpers();
        topicHelpers.LoadQuery(this, this, R.id.listView_topics_class1, "Topics", "Class", className, "Class", className, "TopicName");

        //Retrieve Image
        topicHelpers.LoadImageFile( this, R.id.class1_imageView, "ImageFiles", "Class", className, "Class", className, "classImage");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                    showSubTopics(position);

                } else if (position == 1) {
                    showSubTopics(position);
                } else if (position == 2) {
                    showSubTopics(position);
                } else if (position == 3) {
                    showSubTopics(position);
                } else if (position == 4) {
                    showSubTopics(position);
                } else if (position == 5) {
                    showSubTopics(position);
                } else if (position == 6) {
                    showSubTopics(position);
                } else if (position == 7) {
                    showSubTopics(position);
                } else if (position == 8) {
                    showSubTopics(position);
                }

            }
        });

    }

    private void showSubTopics(int position) {
        String TopicSelected = (listView.getItemAtPosition(position)).toString();
        Intent intent = new Intent(getApplicationContext(), Subtopics_Class.class);
        intent.putExtra("classTitle", classTitle);
        intent.putExtra("className", className);
        intent.putExtra("TopicName", TopicSelected);
        startActivity(intent);
    }
}