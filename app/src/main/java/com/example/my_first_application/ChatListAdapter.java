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
import Task.Task;
import Task.TaskAPIService;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private static final String LOG_TAG = ChatListAdapter.class.getSimpleName();

    private ArrayList<Message> chatList;
    private Listener listener;

    // 使用介面解耦
    interface Listener {
        void onClick(int position);
    }


    public ChatListAdapter(ArrayList<Message> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_show_chat_item, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        CardView chatCardView = holder.chatCardView;

        final Message message = chatList.get(position);

        //用message裡的TaskID去抓Task的taskName
        TaskAPIService taskAPIService = new TaskAPIService();

        taskAPIService.getATask(message.getTaskID(), new TaskAPIService.GetAPIListener<Task>() {
            @Override
            public void onResponseOK(Task task) {
//                CardView chatCardView = holder.chatCardView;

                Task messageTask = task;

//                TextView taskTitle = chatCardView.findViewById(R.id.textView_Task_title);
//                taskTitle.setText(messageTask.getTaskName());
//                taskTitle.setText("sssss");
//
//                TextView messageField = chatCardView.findViewById(R.id.textView_message);
//                messageField.setText(message.getContent());
//                messageField.setText("aaa");
//
//                TextView messageTimeField = chatCardView.findViewById(R.id.textView_message_time);
//                LocalDateTime postTime = message.getPostTime();
//
//
//                if (postTime != null) {
//                    int hour = postTime.getHour();
//                    int minute = postTime.getMinute();
//                    messageTimeField.setText("" + hour + ":" + minute);
//                }
//                messageTimeField.setText("00:00");

//                chatCardView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (listener != null) {
//                            listener.onClick(position);
//                        }
//                    }
//                });
            }

            @Override
            public void onFailure() {

            }
        });
        //嘗試讓CardView顯示訊息
        TextView taskTitle = chatCardView.findViewById(R.id.textView_Task_title);
        taskTitle.setText("aaa");

    }



    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void setShowChatList(ArrayList<Message> chatList) {
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
