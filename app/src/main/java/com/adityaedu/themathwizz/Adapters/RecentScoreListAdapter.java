package com.adityaedu.themathwizz.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adityaedu.themathwizz.R;

import java.util.List;

public class RecentScoreListAdapter extends RecyclerView.Adapter<RecentScoreListAdapter.MyViewHolder> {

    private List<RecentScoreList> recentScoreLists;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, score, mastery;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.fragment_TopicTextView);
            score = view.findViewById(R.id.fragment_Topic_ScoreText);
            mastery = view.findViewById(R.id.fragment_TopicLevelText);
        }
    }


    public RecentScoreListAdapter(List<RecentScoreList> recentScoreLists) {
        this.recentScoreLists = recentScoreLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recentactivitylist, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecentScoreList recentScoreList = recentScoreLists.get(position);
        holder.title.setText(recentScoreList.getTitle());
        holder.score.setText(recentScoreList.getScore());
        holder.mastery.setText(recentScoreList.getMastery());
    }

    @Override
    public int getItemCount() {
        return recentScoreLists.size();
    }
}

