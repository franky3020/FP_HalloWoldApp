package com.example.my_first_application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Task.ShowTask;

public class ShowTaskAdapter extends RecyclerView.Adapter<ShowTaskAdapter.ViewHolder> {

    private ArrayList<ShowTask> taskShowList;
    private Listener listener;

    // 使用介面解耦
    interface Listener {
        void onClick(int position);
    }


    public ShowTaskAdapter(ArrayList<ShowTask> taskList) {
        this.taskShowList = taskList;
    }

    @NonNull
    @Override
    public ShowTaskAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_show_task_item, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ShowTask task = taskShowList.get(position);

        CardView taskCardView = holder.taskCardView;

        ImageView userImage = taskCardView.findViewById(R.id.imageView_user_pic);
        userImage.setImageResource(task.getImageId());

        TextView taskTitle = taskCardView.findViewById(R.id.textView_showTask_title);
        taskTitle.setText(task.getTitle());

        TextView taskType = taskCardView.findViewById(R.id.textView_showTask_type);
        taskType.setText(task.getType());

        TextView taskAddress = taskCardView.findViewById(R.id.textView_showTask_address);
        taskAddress.setText(task.getAddress());

        TextView taskDate = taskCardView.findViewById(R.id.textView_showTask_date);
        taskDate.setText(task.getDate());

        TextView taskTime = taskCardView.findViewById(R.id.textView_showTask_time);
        taskTime.setText(task.getTime());


        taskCardView.setOnClickListener(new View.OnClickListener() {
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
        return taskShowList.size();
    }

    public void setShowTaskList(ArrayList<ShowTask> taskShowList) {
        this.taskShowList = taskShowList;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView taskCardView;

        public ViewHolder(CardView view) { //每一個 ViewHolder都會顯示一個CardView
            super(view);
            this.taskCardView = view;
        }
    }
}
