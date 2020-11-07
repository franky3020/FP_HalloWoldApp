package com.example.my_first_application;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Message.Message;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    private List<Message> messageList;

    public ChatAdapter(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        CardView view = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat_right, parent, false);
        return new ViewHolder(view);


        // Todo 之後再判斷左右邊
//        if (viewType == MSG_TYPE_RIGHT){
//            CardView view = (CardView) LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.row_chat_right, parent, false);
//            return new ViewHolder(view);
//        }
//        else {
//            CardView view = (CardView) LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
//            return new ViewHolder(view);
//        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        CardView chatCardView = holder.chatCardView;

        TextView messageTV = chatCardView.findViewById(R.id.messageTV);

        String message = messageList.get(position).getContent();
        messageTV.setText(message);


//        TextView timeTV = chatCardView.findViewById(R.id.timeTV);
//        TextView isSeenTV = chatCardView.findViewById(R.id.isSeenTV);
//        String timeStamp = "XX:XX"; // Todo 之後加上

//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(Long.parseLong(timeStamp));
//        String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa", cal).toString();
//

//        timeTV.setText(dateTime);


        // 先不要判斷已讀 (這是舊的程式碼 先沒刪掉)
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

    // Todo 之後再判斷有沒有已讀 (這是舊的程式碼 先沒刪掉)
//    @Override
//    public int getItemViewType(int position) {
//        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (chatList.get(position).getSender().equals(fUser.getUid())){
//            return MSG_TYPE_RIGHT;
//        }
//        else return  MSG_TYPE_LEFT;
//    }

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
