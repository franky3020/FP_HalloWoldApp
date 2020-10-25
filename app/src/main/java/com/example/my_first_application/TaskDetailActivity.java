package com.example.my_first_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.os.Bundle;
import android.widget.LinearLayout;

import Task.TaskAPIService;
import Task.TaskState;
import TaskState.ReceiveUser.ReceiveUserTaskStateContext;
import User.GetLoginUser;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "taskID";
    int taskID;
    int loginUserId;

    public static final String IS_RELEASE_USER = "releaseUser";
    public static final String IS_RECEIVE_USER = "receiveUser";

    private String userMode = IS_RELEASE_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail2);

        this.loginUserId = GetLoginUser.getLoginUser().getId();

        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.task_detail_frag);
        taskID = getIntent().getExtras().getInt(EXTRA_TASK_ID);

        assert taskDetailFragment != null;
        taskDetailFragment.setTaskID(taskID);

        LinearLayout stateButtonsLayout = findViewById(R.id.task_state_buttons_container);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this, R.style.AppTheme);

        if( loginUserId == 14 ) {
            ReceiveUserTaskStateContext receiveUserTaskStateContext = new ReceiveUserTaskStateContext(taskID, contextThemeWrapper, stateButtonsLayout, this);
            getTaskStateAndUpdate(receiveUserTaskStateContext);
        }
    }


    private void getTaskStateAndUpdate(final ReceiveUserTaskStateContext context) {
        TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.getTaskState(taskID, new TaskAPIService.GetAPIListener<TaskState>() {
            @Override
            public void onResponseOK(TaskState taskState) {
                context.setTaskState(taskState.getTaskStateEnum());
                context.updateUI();
            }

            @Override
            public void onFailure() {

            }
        });
    }

}