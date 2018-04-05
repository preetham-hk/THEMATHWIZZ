package com.adityaedu.themathwizz.topics;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Preetham on 24-03-2018.
 */

public class TopicHelpers {

   public void LoadQuery(Context context, Activity activity, int resources, String ClassName, String Column1, String Value1, String Column2, String Value2, final String Key){

        final ProgressDialog progressDialog = ProgressDialogSpinner.showProgressDialog(context, "Please Wait");

        final ArrayList<String> topics1 = new ArrayList<>();
        final ListView listView = activity.findViewById(resources);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, topics1);
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ClassName);
        query.whereEqualTo(Column1,Value1);
        query.whereEqualTo(Column2,Value2);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject topic : objects) {
                        topics1.add(topic.getString(Key));
                        Log.d("Topics",""+topics1);
                    }
                    listView.setAdapter(arrayAdapter);
                    progressDialog.dismiss();
                } else {
                    e.printStackTrace();
                }
            }

        });

    }

    public void LoadImageFile(final Activity activity, final int resources, String ClassName, String Column1, String Value1, String Column2, String Value2, final String Key){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ClassName);
        query.whereEqualTo(Column1, Value1);
        query.whereEqualTo(Column2, Value2);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    for(ParseObject object : objects){
                        ParseFile file = (ParseFile) object.get(Key);
                        Log.d("image1",""+file);
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(e==null){
                                    Bitmap bitmapImage = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ImageView class_imageView = activity.findViewById(resources);
                                    class_imageView.setImageBitmap(bitmapImage);
                                    Log.d("Image",""+bitmapImage);
                                }
                                else{
                                    Log.i("info", e.getMessage());
                                }
                            }
                        });
                    }
                }
                else{
                    Log.i("info", e.getMessage());
                }
            }
        });
    }
}
