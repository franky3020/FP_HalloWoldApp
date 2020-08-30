package com.example.my_first_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ShowTask.ShowTask;

public class ShowTaskAdapter extends RecyclerView.Adapter<ShowTaskAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ShowTask> mTaskShowList;

    public ShowTaskAdapter(Context context, ArrayList<ShowTask> taskList) {
        this.mContext = context;
        this.mTaskShowList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.activity_show_task_item, parent, false);
        final ViewHolder holder = new ViewHolder((CardView) view);
//        holder.taskView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = holder.getAdapterPosition();
//                ShowTask task = mTaskShowList.get(position);
//                Toast.makeText(view.getContext(), "View"+ task.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowTask task = mTaskShowList.get(position);
        holder.userImage.setImageResource(task.getImageId());
        holder.userName.setText(task.getName());
        holder.taskContent.setText(task.getContent());
    }

    @Override
    public int getItemCount() {
        return mTaskShowList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView taskView;
        private ImageView userImage;
        private TextView userName;
        private TextView taskContent;

        public ViewHolder(CardView view) {
            super(view);
            this.taskView = view;
            this.userImage = view.findViewById(R.id.imageView_pic);
            this.userName = view.findViewById(R.id.textView_showTask_name);
            this.taskContent = view.findViewById(R.id.textView_showTask_content);
        }
    }
}
