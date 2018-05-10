package com.adityaedu.themathwizz.quiz;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.adityaedu.themathwizz.Adapters.RecentScoreList;
import com.adityaedu.themathwizz.Adapters.RecentScoreListAdapter;
import com.adityaedu.themathwizz.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class RecentQuiz extends AppCompatActivity {

    ParseUser user = ParseUser.getCurrentUser();
    String currentUser = user.getUsername();

    private RecyclerView recyclerView;
    private List<RecentScoreList> recentScoreList = new ArrayList<>();
    private RecentScoreListAdapter recentScoreListAdapter;

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_recent_activities);

        TextView textView = findViewById(R.id.recent_activity_textView);
        String activity = "Your Recent Quizzes";
        textView.setText(activity);

        recyclerView = (RecyclerView) findViewById(R.id.RecentActivity_RecyclerView);
        recentScoreListAdapter = new RecentScoreListAdapter(recentScoreList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecentActivityList();
    }

    private void RecentActivityList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please Wait");


        ParseQuery<ParseObject> query = ParseQuery.getQuery("RecentActivity");
        query.whereEqualTo("Username", currentUser);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject topic : objects) {

                        String Activity = topic.getString("Activity1");
                        Log.d("Activity1", "" + Activity);

                        Integer Score1 = topic.getInt("Scores");
                        String Score = Score1.toString();
                        Log.d("Score", "" + Score);

                        String Mastery = topic.getString("Mastery");
                        Log.d("Mastery", "" + Mastery);
                        RecentScoreList scoreList = new RecentScoreList(Activity, Score, Mastery);
                        recentScoreList.add(scoreList);
                        recyclerView.setAdapter(recentScoreListAdapter);
                    }
                    progressDialog.dismiss();
                    Log.d("List", "" + recentScoreList);
                } else {
                    e.printStackTrace();
                }
            }

        });
        recentScoreListAdapter.notifyDataSetChanged();
    }

}
