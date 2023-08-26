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
import java.util.List;

import Message.Message;
import User.User;
import User.GetLoginUser;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    private List<Message> messageList;
    private int loginUserID;

    public ChatAdapter(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT){
            CardView view = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_chat_right, parent, false);
            return new ViewHolder(view);
        }
        else {
            CardView view = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_chat_left, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        CardView chatCardView = holder.chatCardView;

        TextView messageTV = chatCardView.findViewById(R.id.messageTV);

        String message = messageList.get(position).getContent();
        messageTV.setText(message);


        TextView timeTV = chatCardView.findViewById(R.id.timeTV);
        LocalDateTime messageSendTime = messageList.get(position).getPostTime();

        if (messageSendTime != null) {
            String AMPM = "上午";
            int hour = messageSendTime.getHour();
            if (hour > 12){
                AMPM = "下午";
                hour = hour - 12;
            }
            int minute = messageSendTime.getMinute();
            if(minute < 10){
                timeTV.setText(AMPM + hour + ":0" + minute);
            }
            else {
                timeTV.setText(AMPM + hour + ":" + minute);
            }
        }
        //TODO 已讀功能
//        TextView isSeenTV = chatCardView.findViewById(R.id.isSeenTV);
//        if (position ==  messageList.size() - 1){
//            if (messageList.get(position).isSeen()) {
//                isSeenTV.setText("已讀");
//            }
//            else {
//                isSeenTV.setText("已送出");
//            }
//        }
//        else {
//            isSeenTV.setVisibility(View.GONE);
//        }

    }


    @Override
    public int getItemViewType(int position) {
        loginUserID = GetLoginUser.getLoginUser().getId();

        if (messageList.get(position).getUserID() == loginUserID){
            return MSG_TYPE_RIGHT;
        }
        else return MSG_TYPE_LEFT;
    }

    public void setShowChatList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView chatCardView;

        public ViewHolder(CardView view){
            super(view);
            this.chatCardView = view;
        }
    }
}
