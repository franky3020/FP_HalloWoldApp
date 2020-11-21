package com.example.my_first_application;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.ArrayList;

import Message.Message;
import Message.MessageAPIService;
import User.User;
import User.GetLoginUser;

public class ShowChatAdapter extends RecyclerView.Adapter<ShowChatAdapter.ViewHolder> {

    private static final String LOG_TAG = ShowChatAdapter.class.getSimpleName();

    private ArrayList<User> userList;
    private Listener listener;
    private int loginUserID = GetLoginUser.getLoginUser().getId();;

    ArrayList<Message> mMessagesList = new ArrayList<>();

    // 使用介面解耦
    interface Listener {
        void onClick(int position);
    }


    public ShowChatAdapter(ArrayList<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ShowChatAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_show_chat_item, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        CardView chatCardView = holder.chatCardView;

        final User user = userList.get(position);
        final Message message;

        TextView userNameField = chatCardView.findViewById(R.id.textView_user_name);
        userNameField.setText(user.getName());

        final TextView messageField = chatCardView.findViewById(R.id.textView_message);
        final TextView messageTimeField = chatCardView.findViewById(R.id.textView_message_time);

        MessageAPIService messageAPIService = new MessageAPIService();
        messageAPIService.getLatestMessageFromTwoUsers(loginUserID, user.getId(), new MessageAPIService.GetAPIListener<ArrayList<Message>>() {

            @Override
            public void onResponseOK(ArrayList<Message> messages) {
                messageField.setText(messages.get(0).getContent());

                LocalDateTime messageSendTime = messages.get(0).getPostTime();
                if (messageSendTime != null) {
                    String AMPM = "上午";
                    int hour = messageSendTime.getHour();
                    if (hour > 12){
                        AMPM = "下午";
                        hour = hour - 12;
                    }
                    int minute = messageSendTime.getMinute();
                    if(minute < 10){
                        messageTimeField.setText(AMPM + hour + ":0" + minute);
                    }
                    else {
                        messageTimeField.setText(AMPM + hour + ":" + minute);
                    }
                }
            }
            @Override
            public void onFailure() {

            }
        });

        chatCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.onClick(position);
                        }
                    }
        });
    }



    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setShowChatList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView chatCardView;

        public ViewHolder(CardView view) { //每一個 ViewHolder都會顯示一個CardView
            super(view);
            this.chatCardView = view;
        }
    }
}
