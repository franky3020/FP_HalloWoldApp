package com.example.my_first_application;

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

public class ShowChatAdapter extends RecyclerView.Adapter<ShowChatAdapter.ViewHolder> {

    private static final String LOG_TAG = ShowChatAdapter.class.getSimpleName();

    private ArrayList<User> userList;
    private Listener listener;

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

                TextView userNameField = chatCardView.findViewById(R.id.textView_user_name);
                userNameField.setText(user.getName());

                TextView messageField = chatCardView.findViewById(R.id.textView_message);
                messageField.setText("context_ 未加上");

                TextView messageTimeField = chatCardView.findViewById(R.id.textView_message_time);
                LocalDateTime messageSendTime = LocalDateTime.now(); // 這先用目前時間代替

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
                // TODO 抓最新的訊息的內容以及時間呈現在外部聊天室
//                MessageAPIService messageAPIService = new MessageAPIService();
//                messageAPIService.getAllChatMessageFromTwoUsers(user.getId(), mReceiverId, new MessageAPIService.GetAPIListener<ArrayList<Message>>() {
//
//                @Override
//                public void onResponseOK(ArrayList<Message> messages) {
//                    mMessagesList = messages;
//                    messageListUIUpdate();
//                    }
//                @Override
//                public void onFailure() {
//
//                }
//                });
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
