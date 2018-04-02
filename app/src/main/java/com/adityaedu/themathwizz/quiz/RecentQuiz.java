package com.adityaedu.themathwizz.quiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.topics.TopicHelpers;
import com.parse.ParseUser;

public class RecentQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_recent_activities);

        TextView textView = findViewById(R.id.recent_activity_textView);

        String activity = "Your Recent Quizzes";
        textView.setText(activity);
        ParseUser user = ParseUser.getCurrentUser();
        String currentUser = user.getUsername();

        TopicHelpers topicHelpers = new TopicHelpers();
        topicHelpers.LoadQuery(this,this,R.id.recent_activity_ListView,"RecentActivity","Username",currentUser,  "Username",currentUser,"Activity1");
    }
}
