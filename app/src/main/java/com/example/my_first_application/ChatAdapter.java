package com.example.my_first_application;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Message.ModelChat;
import Task.Task;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    Context context;
    private List<ModelChat> chatList;
    String imageUrl;
    private Listener listener;


    interface Listener {
        void onClick(int position);
    }
    public ChatAdapter(ArrayList<ModelChat> chatList) {
        this.chatList = chatList;
    }

    public ChatAdapter(Context context, List<ModelChat> chatList, String imageUrl) {
        this.context = context;
        this.chatList = chatList;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        if (viewType==MSG_TYPE_RIGHT){
            CardView view = (CardView) LayoutInflater.from(context).inflate(R.layout.row_chat_right, parent, false);
            return new ViewHolder(view);
        }
        else {
            CardView view = (CardView) LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        CardView chatCardView = holder.chatCardView;

        ImageView profileIV = chatCardView.findViewById(R.id.profileIV);

        TextView messageTV = chatCardView.findViewById(R.id.messageTV);
        TextView timeTV = chatCardView.findViewById(R.id.timeTV);
        TextView isSeenTV = chatCardView.findViewById(R.id.isSeenTV);

        String message = chatList.get(position).getMessage();
        String timeStamp = chatList.get(position).getTimestamp();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(timeStamp));
        String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa", cal).toString();

        messageTV.setText(message);
        timeTV.setText(dateTime);

        if (position ==  chatList.size() - 1){
            if (chatList.get(position).isSeen()) {
                isSeenTV.setText("已讀");
            }
            else {
                isSeenTV.setText("已送出");
            }
        }
        else {
            isSeenTV.setVisibility(View.GONE);
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
    public int getItemViewType(int position) {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatList.get(position).getSender().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else return  MSG_TYPE_LEFT;
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
    public void setShowChatList(ArrayList<ModelChat> chatList) {
        this.chatList = chatList;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView chatCardView;

        public ViewHolder(CardView view){
            super(view);
            this.chatCardView = view;
        }
    }
}
