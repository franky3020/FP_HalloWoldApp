package com.example.my_first_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "taskID";
    public static final String EXTRA_TASK_TITLE = "taskTitle";
    public static final String EXTRA_TASK_MESSAGE = "taskTitle";
    public static final String EXTRA_TASK_START_POST_TIME = "taskTitle";
    public static final String EXTRA_TASK_END_POST_TIME = "taskTitle";
    public static final String EXTRA_TASK_SALARY = "taskTitle";
    public static final String EXTRA_TASK_TYPENAME = "taskTitle";
    public static final String EXTRA_TASK_RELEASE_USER_ID = "taskTitle";
    public static final String EXTRA_TASK_RELEASE_TIME = "taskTitle";
    public static final String EXTRA_TASK_RECEIVE_USER_ID = "taskTitle";
    public static final String EXTRA_TASK_RECEIVE_TIME = "taskTitle";
    public static final String EXTRA_TASK_TASK_ADDRESS = "taskTitle";
    public static final String EXTRA_TASK_CITY = "taskTitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail2);

        TaskDetailFragment frag = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.task_detail_frag);
        int taskID = getIntent().getExtras().getInt(EXTRA_TASK_ID);
        String taskTitle = getIntent().getExtras().getString(EXTRA_TASK_TITLE);
        frag.setTaskID(taskID);
        frag.setTaskTitle(taskTitle);
    }
}