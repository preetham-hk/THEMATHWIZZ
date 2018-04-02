package com.adityaedu.themathwizz.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adityaedu.themathwizz.R;

import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.MyViewHolder> {
    private List<ItemOfList> itemOfLists;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.recycler_row_TextView);
        }
    }


    public RecyclerListAdapter(List<ItemOfList> itemOfLists) {
        this.itemOfLists = itemOfLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recyclerlist_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ItemOfList itemOfList = itemOfLists.get(position);
        holder.title.setText(itemOfList.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemOfLists.size();
    }
}

