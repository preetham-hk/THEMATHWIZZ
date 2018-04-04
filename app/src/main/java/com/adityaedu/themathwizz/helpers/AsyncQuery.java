package com.adityaedu.themathwizz.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AsyncQuery extends AsyncTask<String, Void, List<String>> {

    @Override
    public List<String> doInBackground(String... params) {

        String className = params[0];
        String column1 = params[1];
        String value1 = params[2];
        String column2 = params[3];
        String value2 = params[4];
        String key = params[5];

        final List<String> itemOfLists = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
        query.whereEqualTo(column1, value1);
        query.whereEqualTo(column2,value2);
        query.orderByAscending("createdAt");
        try {
            List<ParseObject> parseObjectResult = query.find();
            for (ParseObject parseObject : parseObjectResult) {
                String text = parseObject.getString(key);
                Log.d("text_item", "" + text);
                itemOfLists.add(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemOfLists;
    }

}