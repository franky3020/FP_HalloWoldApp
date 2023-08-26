package com.example.my_first_application;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import User.User;

public class ShowRequestUsersAdapter extends RecyclerView.Adapter<ShowRequestUsersAdapter.ViewHolder> {

    private ArrayList<User> users;
    private Listener listener;

    public ShowRequestUsersAdapter(ArrayList<User> users) {
        this.users = users;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_show_request_users_item, parent, false);

        return new ShowRequestUsersAdapter.ViewHolder(cardView);
    }

    // 使用介面解耦
    interface Listener {
        void onClick(int position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final User user = users.get(position);
        final CardView requestUserCardView = holder.userCardView;

        TextView userName = requestUserCardView.findViewById(R.id.user_name);
        userName.setText(user.getName());

        final ImageView sendMessage = requestUserCardView.findViewById(R.id.chat_image_view);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requestUserCardView.getContext(), ChatActivity.class);
                intent.putExtra(ChatActivity.EXTRA_RECEIVER_ID, user.getId());
                requestUserCardView.getContext().startActivity(intent);
            }
        });

        Button selectButton = requestUserCardView.findViewById(R.id.select_user_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

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
