package com.adityaedu.themathwizz.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

public class AsyncFile extends AsyncTask <String,Void,Bitmap> {

    @Override
    public Bitmap doInBackground(String... params) {
        Bitmap bitmapImage = null;
        String className = params[0];
        String column1 = params[1];
        String value1 = params[2];
        String column2 = params[3];
        String value2 = params[4];
        final String key = params[5];
        ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
        query.fromLocalDatastore();
        query.whereEqualTo(column1, value1);
        query.whereEqualTo(column2,value2);
        query.orderByAscending("createdAt");
        try {
            List<ParseObject> parseObjectResult = query.find();
            for (ParseObject parseObject : parseObjectResult) {
                ParseFile file = (ParseFile) parseObject.get(key);
                Log.d("ImageFile",""+file);
                if (file != null){
                try {
                    byte[] data = file.getData();
                    bitmapImage = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Log.d("ImageFile",""+bitmapImage);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                }
                else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return bitmapImage;
    }
}