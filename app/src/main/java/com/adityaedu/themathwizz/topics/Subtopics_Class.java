package com.adityaedu.themathwizz.topics;

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
import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.adityaedu.themathwizz.quiz.ActivityQuiz;
import com.adityaedu.themathwizz.quiz.QuizHelper;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by preetham on 2/8/2018.
 */

public class Subtopics_Class extends AppCompatActivity {

    TextView textView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtopics);
        textView = findViewById(R.id.subTopic_textView);
        listView = findViewById(R.id.subtopic_ListView);

        Bundle bundle = getIntent().getExtras();
        final String className = bundle.getString("className");
        String classTitle = bundle.getString("classTitle");
        String TopicName = bundle.getString("TopicName");
        this.setTitle(classTitle);



        //Load Question and Store them in local Database
        QuizHelper activityQuizScore = new QuizHelper();
        activityQuizScore.LoadQuestions(className);

        final ProgressDialog progressDialog = ProgressDialogSpinner.showProgressDialog(this, "Please Wait");

        //Load TopicName
        textView.setText(TopicName);

        //Load SubTopics
        TopicHelpers topicHelpers =  new TopicHelpers();
        topicHelpers.LoadQuery(this,this, R.id.subtopic_ListView,"SubTopics","Class",className,"TopicName",TopicName,"SubTopicName");

        progressDialog.dismiss();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubTopicName = (listView.getItemAtPosition(position)).toString();
                Log.d("selectedSubTopicName",""+selectedSubTopicName);
                Intent intent = new Intent(getApplicationContext(), ActivityQuiz.class);
                intent.putExtra("subTopic",selectedSubTopicName);
                intent.putExtra("className",className);
                startActivity(intent);
            }
        });

    }
}
