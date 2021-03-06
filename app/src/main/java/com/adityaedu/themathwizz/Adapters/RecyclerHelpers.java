package com.adityaedu.themathwizz.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class RecyclerHelpers {

    public void setFlipperLayout(Activity activity, Context context, int resources) {
        int images[] = new int[]{
                R.drawable.m1, R.drawable.m2, R.drawable.m3, R.drawable.m4
        };
        for (int i = 0; i < 4; i++) {
            FlipperView flipperView = new FlipperView(context);
            flipperView.setImageDrawable(images[i]);
            FlipperLayout flipperLayout = activity.findViewById(resources);
            flipperLayout.addFlipperView(flipperView);
        }
    }

        public  void addRecyclerView(Activity activity, Context context, int resources, List<ItemOfList> itemOfLists,int orientation){

            RecyclerView recyclerView = activity.findViewById(resources);
            RecyclerListAdapter recyclerListAdapter = new RecyclerListAdapter(itemOfLists);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(context, orientation));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(recyclerListAdapter);
            recyclerListAdapter.notifyDataSetChanged();
    }
}