package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.RatingCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import Task.Task;
import Task.TaskAPIService;
import Task.TaskState;
import User.GetLoginUser;

import TaskState.ITaskStateContext;
import User.UserAPIService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import TaskState.*;

import TaskState.EmptyState;
import Task.TaskStateEnum;
import Task.TaskBuilder;


//Todo 並未考慮任務已被刪除 但使用者操作任務的任何情況 需補上
public class TaskDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "taskID";
    int taskID;
    int loginUserID;
    Task mTask = TaskBuilder.aTask(0,0,0).build(); // 初始化假的任務

    private static final String IS_UNKNOWN_USER = "unknownUser";
    private String userMode = IS_UNKNOWN_USER;

    LinearLayout stateButtonsLayout;
    ITaskStateAction state = BoosReleaseState.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.loginUserID = GetLoginUser.getLoginUser().getId();

        // 初始化此頁面必要資訊
        taskID = getIntent().getExtras().getInt(EXTRA_TASK_ID);

        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.task_detail_frag);
        assert taskDetailFragment != null;
        taskDetailFragment.setTaskID(taskID);

        stateButtonsLayout = findViewById(R.id.task_state_buttons_container);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}