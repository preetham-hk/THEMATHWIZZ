package com.adityaedu.themathwizz.quiz;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by preetham on 3/8/2018.
 *
 */

public class QuizHelper {
    private String username = ParseUser.getCurrentUser().getUsername();

    void saveToRecentActivity(final String subTopic, final Integer Score) {

        final ParseObject RecentActivity = new ParseObject("RecentActivity");

        Log.d("Username", "" + username);
        Log.d("Activity1", "" + subTopic);
        Log.d("Score", "" + Score);

        RecentActivity.put("Username", username);
        RecentActivity.put("Activity1", subTopic);
        try {
            RecentActivity.put("Scores", Score);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecentActivity.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
            }
        });

        Log.d("Executing", "yes");
        Log.d("key", "" + subTopic);

        final ArrayList<String> recentSubTopic = new ArrayList<>();
        final ArrayList<Integer> activityScores = new ArrayList<>();
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("RecentActivity");
        query.whereEqualTo("Username", username);
        Log.d("User", "" + username);
        query.whereEqualTo("Activity1", subTopic);
        query.orderByAscending("Scores");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> sub, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject : sub) {
                        recentSubTopic.add(parseObject.getString("Activity1"));
                        activityScores.add(parseObject.getInt("Scores"));
                        Log.d("list", "" + recentSubTopic);
                        Log.d("key", "" + subTopic);
                        Log.d("Scores", "" + activityScores);

                        if (sub.size() > 0) {
                            sub.get(0).deleteInBackground();
                            Log.d("Removing", "yes");
                            RecentActivity.saveInBackground();
                        }

                    }
                } else {
                    Log.e("Message", "" + e.getMessage());
                }
            }

        });
    }

    public void LoadQuestions(String className) {

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Quiz");
        query2.whereEqualTo("Class", className);
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> questions, ParseException e) {
                if (e == null) {
                    ParseObject.unpinAllInBackground("Quiz");
                    ParseObject.pinAllInBackground("Quiz", questions);
                } else {
                    Log.d("Error", "error");
                }
            }
        });

    }

}
