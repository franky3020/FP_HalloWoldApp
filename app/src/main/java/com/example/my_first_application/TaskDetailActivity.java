package com.example.my_first_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_TITLE = "taskTitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail2);

        TaskDetailFragment frag = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.task_detail_frag);
        String taskTitle = getIntent().getExtras().getString(EXTRA_TASK_TITLE);
        frag.setTaskTitle(taskTitle);
    }
}