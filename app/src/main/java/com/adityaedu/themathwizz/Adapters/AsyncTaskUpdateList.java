package com.adityaedu.themathwizz.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.ref.WeakReference;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class AsyncTaskUpdateList extends AsyncTask<String, Void, List<ItemOfList>> {

    private ProgressDialog progressDialog;
    private WeakReference<Context> context;
    private String className, column, value, key1;

    public interface AsyncResponse {
        void processFinish(List<ItemOfList> output);
    }

    private AsyncResponse delegate = null;
    public AsyncTaskUpdateList( String className, String column, String value, String key1, Context context,AsyncResponse delegate) {
        this.className = className;
        this.column = column;
        this.value = value;
        this.key1 = key1;
        this.context = new WeakReference<>(context);
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialogSpinner.showProgressDialog(context.get(), "Loading");
    }

    @Override
    public List<ItemOfList> doInBackground(String... params) {
    /*
    String className = params[0];
    String column= params[1];
    String value = params[2];
    String key1 = params[3];
    */
        final List<ItemOfList> itemOfLists = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
        query.whereEqualTo(column, value);
        query.orderByAscending("createdAt");
        try {
            List<ParseObject> parseObjectResult = query.find();
            for (ParseObject parseObject : parseObjectResult) {
                String text = parseObject.getString(key1);
                Log.d("text_item", "" + text);
                ItemOfList item = new ItemOfList(text);
                itemOfLists.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemOfLists;
    }

    @Override
    protected void onPostExecute(List<ItemOfList> result) {
        progressDialog.dismiss();
        delegate.processFinish(result);
    }
}