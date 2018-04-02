package com.adityaedu.themathwizz.quiz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adityaedu.themathwizz.R;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by preetham on 3/8/2018.
 *
 */

public class QuizSaveRecent {

    private String username = ParseUser.getCurrentUser().getUsername();

    public void onCreate(final String subTopic, final Integer Score) {

        final ParseObject RecentActivity = new ParseObject("RecentActivity");
        RecentActivity.put("Username", username);
        RecentActivity.put("Activity1", subTopic);
        RecentActivity.put("Scores", Score);
        RecentActivity.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
            }
        });

        final String Score1 = Score.toString();
        Log.d("Executing", "yes");
        Log.d("key", "" + subTopic);

        final ArrayList<String> recentSubTopic = new ArrayList<>();
        final ArrayList<String> activityScores = new ArrayList<>();
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("RecentActivity");
        query.whereEqualTo("Username", username);
        query.whereEqualTo("Activity1", subTopic);
        query.orderByAscending("Scores");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> sub, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject : sub) {
                        recentSubTopic.add(parseObject.getString("Activity1"));
                        activityScores.add(parseObject.getString("Scores"));
                        Log.d("list", "" + recentSubTopic);
                        Log.d("key", "" + subTopic);
                        Log.d("Scores", "" + activityScores);

                        if (sub.size() > 1) {
                            //sub.get(0).deleteInBackground();
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


}
