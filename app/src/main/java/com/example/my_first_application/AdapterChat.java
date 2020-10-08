package com.example.my_first_application;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyHolder> {

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    Context context;
    List<ModelChat> chatList;
    String imageUrl;


    public AdapterChat(Context context, List<ModelChat> chatList, String imageUrl) {
        this.context = context;
        this.chatList = chatList;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, parent, false);
            return new MyHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
            return new MyHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String message = chatList.get(position).getMessage();
        String timeStamp = chatList.get(position).getTimestamp();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(timeStamp));
        String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa", cal).toString();

        holder.messageTV.setText(message);
        holder.timeTV.setText(dateTime);

        if (position ==  chatList.size() - 1){
            if (chatList.get(position).isSeen()) {
                holder.isSeenTV.setText("已讀");
            }
            else {
                holder.isSeenTV.setText("已送出");
            }
        }
        else {
            holder.isSeenTV.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView profileIV;
        TextView messageTV, timeTV, isSeenTV;


        public MyHolder(@NonNull View itemView){
            super(itemView);

            profileIV = itemView.findViewById(R.id.profileIV);
            messageTV = itemView.findViewById(R.id.messageTV);
            timeTV = itemView.findViewById(R.id.timeTV);
            isSeenTV = itemView.findViewById(R.id.isSeenTV);
        }
    }
}
