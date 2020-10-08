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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import Task.Task;

public class ShowTaskAdapter extends RecyclerView.Adapter<ShowTaskAdapter.ViewHolder> {

//    private ArrayList<ShowTask> taskShowList;
    private ArrayList<Task> taskList;
    private Listener listener;

    // 使用介面解耦
    interface Listener {
        void onClick(int position);
    }


    public ShowTaskAdapter(ArrayList<Task> taskList) {
        this.taskList = taskList;
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
        final Task task = taskList.get(position);

        CardView taskCardView = holder.taskCardView;

        ImageView userImage = taskCardView.findViewById(R.id.imageView_user_pic);
        userImage.setImageResource(R.drawable.ic_user_show_task);

        TextView taskTitle = taskCardView.findViewById(R.id.textView_showTask_title);
        taskTitle.setText(task.getTaskName());

        TextView taskType = taskCardView.findViewById(R.id.textView_showTask_type);
        taskType.setText(task.getTypeName());

        TextView taskAddress = taskCardView.findViewById(R.id.textView_showTask_address);
        taskAddress.setText(task.getTaskAddress());

        TextView taskDate = taskCardView.findViewById(R.id.textView_showTask_date);
        TextView taskTime = taskCardView.findViewById(R.id.textView_showTask_time);

        Timestamp startPostTime = task.getStartPostTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startPostTime);
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH) + 1 ;
        int day = calendar.get(calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        taskDate.setText( "" + year + "/" + month + "/" + day);
        taskTime.setText("" + hour + ":" + minute);

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
        return taskList.size();
    }

    public void setShowTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
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
