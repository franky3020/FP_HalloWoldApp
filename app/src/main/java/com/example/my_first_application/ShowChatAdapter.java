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

import Task.Task;

public class ShowChatAdapter extends RecyclerView.Adapter<ShowChatAdapter.ViewHolder> {

    private static final String LOG_TAG = ShowChatAdapter.class.getSimpleName();

    private ArrayList<Task> chatList;
    private Listener listener;

    // 使用介面解耦
    interface Listener {
        void onClick(int position);
    }


    public ShowChatAdapter(ArrayList<Task> chatList) {
        this.chatList = chatList;
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

                final Task task = chatList.get(position);

                TextView taskTitle = chatCardView.findViewById(R.id.textView_Task_title);
                taskTitle.setText(task.getTaskName());

                TextView messageField = chatCardView.findViewById(R.id.textView_message);
                messageField.setText("context_ 未加上");

                TextView messageTimeField = chatCardView.findViewById(R.id.textView_message_time);
                LocalDateTime messageSendTime = LocalDateTime.now(); // 這先用目前時間代替

                if (messageSendTime != null) {
                    int hour = messageSendTime.getHour();
                    int minute = messageSendTime.getMinute();
                    messageTimeField.setText("" + hour + ":" + minute);
                }
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
        return chatList.size();
    }

    public void setShowChatList(ArrayList<Task> chatList) {
        this.chatList = chatList;
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
