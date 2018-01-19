package com.adityaedu.themathwizz.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.model.User;

import java.util.List;

/**
 * Created by preetham on 1/17/2018.
 */

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<User> listUsers;

    public UsersRecyclerAdapter(List<User> listUsers) {

        this.listUsers = listUsers;
    }

    @Override

    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_recycler, parent, false);

        return new UserViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewDob.setText(listUsers.get(position).getDob());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
        holder.textViewPassword.setText(listUsers.get(position).getPassword());
    }

    @Override

    public int getItemCount() {

        Log.v(UsersRecyclerAdapter.class.getSimpleName(), "" + listUsers.size());
        return listUsers.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView textViewDob;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewPassword;

        public UserViewHolder(View view) {
            super(view);
            textViewDob = (AppCompatTextView) view.findViewById(R.id.textViewDob);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewPassword = (AppCompatTextView) view.findViewById(R.id.textViewPassword);
        }
    }
}