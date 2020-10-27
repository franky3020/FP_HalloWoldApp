package com.example.my_first_application;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import User.User;

public class ShowRequestUsersAdapter extends RecyclerView.Adapter<ShowRequestUsersAdapter.ViewHolder> {

    private ArrayList<User> users;

    public ShowRequestUsersAdapter(ArrayList<User> users) {
        this.users = users;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_show_request_users_item, parent, false);

        return new ShowRequestUsersAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = users.get(position);
        CardView requestUserCardView = holder.userCardView;

        TextView userName = requestUserCardView.findViewById(R.id.user_name);
        userName.setText("" + user.getId()); // Todo 先用ID 替代
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setRequestUsers(ArrayList<User> users) {
        this.users = users;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView userCardView;

        public ViewHolder(CardView view) { //每一個 ViewHolder都會顯示一個CardView
            super(view);
            this.userCardView = view;
        }
    }

}
