package com.example.my_first_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import Task.Task;
import Task.TaskAPIService;
import Task.TaskState;
import TaskState.ReceiveUser.ReceiveUserTaskStateContext;
import User.GetLoginUser;

public class TaskDetailActivity extends AppCompatActivity {

    private Activity activity = this;
    private static final String LOG_TAG = TaskDetailActivity.class.getSimpleName();

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

        final LinearLayout stateButtonsLayout = findViewById(R.id.task_state_buttons_container);
        final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this, R.style.AppTheme);



        // 拿整個Task  Todo: 應該用享元模式, 不然需要一直發API 才知道任務細節 現在是應急用
        TaskAPIService taskApiService = new TaskAPIService();

        taskApiService.getATask(taskID, new TaskAPIService.GetAPIListener<Task>() {
            @Override
            public void onResponseOK(Task task) {
                if (task.getReleaseUserID() == loginUserId) {
                    Log.d(LOG_TAG, "is loginUser");
                    ReceiveUserTaskStateContext receiveUserTaskStateContext = new ReceiveUserTaskStateContext(taskID, contextThemeWrapper, stateButtonsLayout, activity);
                    getTaskStateAndUpdate(receiveUserTaskStateContext);
                } else {
                    Log.d(LOG_TAG, "is not loginUser");
                    // do nothing
                }
            }

            @Override
            public void onFailure() {

            }
        });
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