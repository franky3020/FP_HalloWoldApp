package com.example.my_first_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import Task.CreateTaskStateButtonFactory;
import Task.TaskAPIService;
import TaskState.ReceiveUser.ReceiveUserTaskStateContext;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "taskID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail2);

        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.task_detail_frag);
        final int taskID = getIntent().getExtras().getInt(EXTRA_TASK_ID);
        assert taskDetailFragment != null;
        taskDetailFragment.setTaskID(taskID);


        LinearLayout stateButtonsLayout = (LinearLayout)findViewById(R.id.task_state_buttons_container);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this, R.style.AppTheme);

        ReceiveUserTaskStateContext receiveUserTaskStateContext = new ReceiveUserTaskStateContext(taskID, contextThemeWrapper, stateButtonsLayout);
        receiveUserTaskStateContext.setTaskState(ReceiveUserTaskStateContext.BOOS_RELEASE_STATE);
        receiveUserTaskStateContext.updateUI();

    }

    private void onClickDeleteTask(int taskID) {
        TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.deleteTask(taskID, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("franky-test ok");
            }
        });
    }

}