package com.example.my_first_application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowTaskAdapter extends RecyclerView.Adapter<ShowTaskAdapter.ViewHolder> {
    private List<Task> taskShowList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView taskView;
        private TextView taskName;

        public ViewHolder(RecyclerView view) {
            super(view);
            this.taskView = view;
            this.taskName = view.findViewById(R.id.taskShow);
        }
    }

    public static class Task {
        private String name;
        public Task(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_show_task_item, parent, false);
        final ViewHolder holder = new ViewHolder((RecyclerView) view);
        holder.taskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Task task = taskShowList.get(position);
                Toast.makeText(view.getContext(), "View"+ task.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskShowList.get(position);
        holder.taskName.setText(task.getName());
    }

    @Override
    public int getItemCount() {
        return taskShowList.size();
    }

    public ShowTaskAdapter(List<Task> taskList) {
        taskShowList = taskList;
    }

}
