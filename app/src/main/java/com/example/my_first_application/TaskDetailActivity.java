package com.example.my_first_application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import Task.Task;
import Task.TaskAPIService;
import Task.TaskState;
import User.GetLoginUser;

import TaskState.ITaskStateContext;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import TaskState.ITaskStateAction;
import TaskState.BoosReleaseState;
import Task.TaskStateEnum;

public class TaskDetailActivity extends AppCompatActivity implements ITaskStateContext {

    private Activity activity = this;
    private ITaskStateContext thisContext = this;
    private static final String LOG_TAG = TaskDetailActivity.class.getSimpleName();

    public static final String EXTRA_TASK_ID = "taskID";
    public static final String EXTRA_TASK_RELEASE_USER_ID = "taskReleaseUserID";
    int taskID;
    int taskReleaseUserID;
    int loginUserId;

    public static final String IS_RELEASE_USER = "releaseUser";
    public static final String IS_RECEIVE_USER = "receiveUser";
    private String userMode = IS_RELEASE_USER;

    LinearLayout stateButtonsLayout;
    ITaskStateAction state = BoosReleaseState.getInstance();
//    private TaskState taskState; // 我想這樣加 但還不行

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Todo 要在初始化時就要知道此任務狀態 與 taskID
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail2);

        this.loginUserId = GetLoginUser.getLoginUser().getId();

        // 初始化此頁面必要資訊
        taskID = getIntent().getExtras().getInt(EXTRA_TASK_ID);

        taskReleaseUserID = getIntent().getExtras().getInt(EXTRA_TASK_RELEASE_USER_ID);
        updateUserMode();

        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.task_detail_frag);
        assert taskDetailFragment != null;
        taskDetailFragment.setTaskID(taskID);

        stateButtonsLayout = findViewById(R.id.task_state_buttons_container);
        getTaskStateAndUpdate();
    }

    private void getTaskStateAndUpdate() {
        TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.getTaskState(taskID, new TaskAPIService.GetAPIListener<TaskState>() {
            @Override
            public void onResponseOK(TaskState taskState) {
                if (taskState.getTaskStateEnum() == TaskStateEnum.BOOS_RELEASE_AND_SELECTING_WORKER) {
                    changeTaskState(BoosReleaseState.getInstance());
                }
                state.showUI(thisContext);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void changeTaskState(ITaskStateAction stateAction) {
        state = stateAction;
    }

    @Override
    public void addBoosDeleteButton() {
        final MaterialButton materialButton = getBaseButton();

        materialButton.setBackgroundColor(Color.parseColor("#C40C27"));
        materialButton.setText("Delete");
        materialButton.setTextColor(Color.parseColor("#FFFFFF"));

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.deleteTask(taskID, new Callback() {

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) {
                        finish();
                    }

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }
                });
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stateButtonsLayout.addView(materialButton);
            }
        });
    }

    @Override
    public void addBoosSelectedWorkerButton() {
        final MaterialButton materialButton = getBaseButton();

        materialButton.setBackgroundColor(Color.parseColor("#32A852"));
        materialButton.setText("SelectRequestUser"); // Todo 之後要改為用多國語言字串
        materialButton.setTextColor(Color.parseColor("#FFFFFF"));

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(activity, ShowRequestUsersActivity.class);
                intent.putExtra(ShowRequestUsersActivity.EXTRA_TASK_ID, taskID);

                activity.startActivity(intent);
            }
        });

        runOnUiThread(new Runnable() { // 一定要記得跑在UI thread上才會更新UI
            @Override
            public void run() {
                stateButtonsLayout.addView(materialButton);
            }
        });
    }

    @Override
    public void addWorkerRequestTaskButton() {

    }

    @Override
    public void addWorkerCancelRequestButton() {

    }

    @Override
    public void addWorkerConfirmExecutionButton() {

    }

    @Override
    public void addWorkerRequestCheckTheTaskDoneButton() {

    }

    @Override
    public void addSendMessageButton() {

    }

    @Override
    public boolean isReleaseUser() {
        if (userMode.equals(IS_RELEASE_USER)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isReceiveUser() {
        if (userMode.equals(IS_RECEIVE_USER)) {
            return true;
        } else {
            return false;
        }
    }

    private MaterialButton getBaseButton() {

        //參考以下 獲得如何修改button 風格的
        //https://stackoverflow.com/questions/52120168/programmatically-create-a-materialbutton-with-outline-style
        MaterialButton materialButton = new MaterialButton(activity, null, R.attr.materialButtonOutlinedStyle);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(12, 12, 12, 12);
        materialButton.setLayoutParams(params);
        materialButton.setCornerRadius(5);
        materialButton.setTextSize(24);

        // 以下可以設定斜體字之類的 還需要查一下
//        materialButton.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);

        return materialButton;
    }

    private void updateUserMode() {
        if (taskReleaseUserID == loginUserId) {
            userMode = IS_RELEASE_USER;
            Log.d(LOG_TAG, "is releaseUser");
        } else {
            userMode = IS_RECEIVE_USER;
            Log.d(LOG_TAG, "is receiveUser");
        }
    }
}