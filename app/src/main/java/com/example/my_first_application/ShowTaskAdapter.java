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

public class ShowTaskAdapter extends RecyclerView.Adapter<ShowTaskAdapter.ViewHolder> {

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


        TextView taskTitle = taskCardView.findViewById(R.id.textView_showTask_title);
        taskTitle.setText(task.getTaskName());

        TextView taskType = taskCardView.findViewById(R.id.textView_showTask_salary);
        taskType.setText(String.valueOf(task.getSalary()));

        TextView taskAddress = taskCardView.findViewById(R.id.textView_showTask_address);
        taskAddress.setText(task.getTaskAddress());

        TextView taskDate = taskCardView.findViewById(R.id.textView_showTask_date);
        TextView taskTime = taskCardView.findViewById(R.id.textView_showTask_time);

        LocalDateTime startPostTime = task.getStartPostTime();
        if (startPostTime != null) {
            int year = startPostTime.getYear();
            int month = startPostTime.getMonthValue() ;
            int day = startPostTime.getDayOfMonth();
            int hour = startPostTime.getHour();
            int minute = startPostTime.getMinute();

            taskDate.setText( "" + year + "/" + month + "/" + day);

            if(minute < 10) {
                taskTime.setText("" + hour + ":0" + minute);
            } else {
                taskTime.setText("" + hour + ":" + minute);
            }


        }


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
