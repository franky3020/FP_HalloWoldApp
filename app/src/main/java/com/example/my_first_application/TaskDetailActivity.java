package com.example.my_first_application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import TaskState.*;

import TaskState.EmptyState;
import Task.TaskStateEnum;
import Task.TaskBuilder;

public class TaskDetailActivity extends AppCompatActivity implements ITaskStateContext {

    private Activity activity = this;
    private ITaskStateContext thisContext = this;
    private static final String LOG_TAG = TaskDetailActivity.class.getSimpleName();

    public static final String EXTRA_TASK_ID = "taskID";
    int taskID;
    int loginUserID;
    boolean isUserAlreadyRequest = false;
    Task mTask = TaskBuilder.aTask(0,0,0).build(); // 初始化假的任務

    public static final String IS_RELEASE_USER = "releaseUser";
    public static final String IS_CAN_REQUEST_TASK_USER = "canRequestTaskUser";
    public static final String IS_RECEIVE_USER = "receiveUser";
    public static final String IS_UNKNOWN_USER = "unknownUser";
    private String userMode = IS_UNKNOWN_USER;

    LinearLayout stateButtonsLayout;
    ITaskStateAction state = BoosReleaseState.getInstance();
//    private TaskState taskState; // 我想這樣加 但還不行 之後要顯示修改狀態的時間

    private final static int allUpdateFunctionCount  = 3; // 因為總共有三個非同步函式需要被計算
    private int currentUpdateFunctionDone = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail2);

        this.loginUserID = GetLoginUser.getLoginUser().getId();

        // 初始化此頁面必要資訊
        taskID = getIntent().getExtras().getInt(EXTRA_TASK_ID);



        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.task_detail_frag);
        assert taskDetailFragment != null;
        taskDetailFragment.setTaskID(taskID);

        stateButtonsLayout = findViewById(R.id.task_state_buttons_container);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initAllUI();
    }

    private void initAllUI() { // 使用同步鎖 確認這三件事都有完成時 才會觸發更新UI
        currentUpdateFunctionDone = 0; // 一定要先初始化為0, 這樣重複執行此函式才不會出錯
        updateMTask();
        getTaskStateAndUpdate();
        updateTheUserIsAlreadyRequest();
    }

    private void checkTheInitIsOkThenUpdateAllUI() {
        synchronized((Object) currentUpdateFunctionDone) {
            currentUpdateFunctionDone++;
            if(currentUpdateFunctionDone == allUpdateFunctionCount) {
                upDateAllUI();
            }
        }
    }

    private void updateMTask() {
        TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.getATask(taskID, new TaskAPIService.GetAPIListener<Task>() {

            @Override
            public void onResponseOK(Task task) {
                mTask = task;
                updateUserMode();
                Log.d(LOG_TAG, "updateMTask");
                checkTheInitIsOkThenUpdateAllUI();
            }

            @Override
            public void onFailure() {
                // 先假設網路都正常
            }
        });
    }

    private void getTaskStateAndUpdate() {
        TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.getTaskState(taskID, new TaskAPIService.GetAPIListener<TaskState>() {
            @Override
            public void onResponseOK(TaskState taskStateDate) {
                ITaskStateAction newState = getTaskStateAction(taskStateDate);
                changeTaskState(newState);
                Log.d(LOG_TAG, "getTaskStateAndUpdate");
                checkTheInitIsOkThenUpdateAllUI();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void updateTheUserIsAlreadyRequest() {
        TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.checkUserAlreadyRequest(taskID, loginUserID, new TaskAPIService.GetAPIListener<Boolean>() {
            @Override
            public void onResponseOK(Boolean aBoolean) {
                isUserAlreadyRequest = aBoolean;
                Log.d(LOG_TAG, "updateTheUserIsAlreadyRequest");
                checkTheInitIsOkThenUpdateAllUI();
            }

            @Override
            public void onFailure() {
                isUserAlreadyRequest = false;
            }
        });
    }

    private void upDateAllUI() {
        state.showUI(thisContext);
    }

    @Override
    public void addATaskStateShow() {
        final TextView taskStateTextView = new TextView(this);
        taskStateTextView.setText(state.toString());

        runOnUiThread(new Runnable() { // 一定要記得跑在UI thread上才會更新UI
            @Override
            public void run() {
                stateButtonsLayout.addView(taskStateTextView);
            }
        });
    }

    private ITaskStateAction getTaskStateAction(TaskState taskState) {

        TaskStateEnum taskStateEnum = taskState.getTaskStateEnum();

        switch (taskStateEnum) {
            case BOOS_RELEASE_AND_SELECTING_WORKER:
                return BoosReleaseState.getInstance();

            case BOOS_SELECTED_WORKER:
                return BoosSelectedWorkerState.getInstance();

            case TASK_ON_GOING:
                return TaskOnGoingState.getInstance();

            case WAIT_BOOS_CHECK_THE_USER_IS_DONE_TASK:
                return WaitBoosCheckTheUserIsDoneTaskState.getInstance();

            case WAIT_WORK_AGREE_STOP_THE_TASK:
                return WaitWorkAgreeStopTheTaskState.getInstance();

            case PERFECT_END:
                return PerfectEndState.getInstance();

            case FAILURE_END:
                return FailureEndState.getInstance();

            default:
                return EmptyState.getInstance();
        }
    }


    @Override
    public void changeTaskState(ITaskStateAction stateAction) {
        state = stateAction;
    }

    @Override
    public void addBoosDeleteButton() {
        final MaterialButton materialButton = getNegativeButton("Delete");

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
    public void addBoosSelectedWorkerButton() { // Todo 這裡有bug 因為當發布者選擇接收者時 跳回到此頁時 會不是更新為最新的任務狀態
                                               // 應該要回到此任務介面即時更新,  之後改成 把更新放在onStart()
        
        final MaterialButton materialButton = getPositiveButton("Select Request User");

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

        final MaterialButton materialButton = getPositiveButton("RequestTask");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.addTaskRequestUser(taskID, loginUserID, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) {
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                });

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
    public void addWorkerCancelRequestButton() {

        final MaterialButton materialButton = getNegativeButton("Cancel Request");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.deleteTaskRequestUser(taskID, loginUserID, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) {
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                });

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
    public void addWorkerConfirmExecutionButton() { // 此按鈕應該修改任務狀態為 WORKER_CONFIRM_EXECUTION

        final MaterialButton materialButton = getPositiveButton("Confirm Execution");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.updateTaskState(taskID, TaskStateEnum.TASK_ON_GOING, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                });
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
    public void addWorkerRequestCheckTheTaskDoneButton() {

        final MaterialButton materialButton = getPositiveButton("Request check Done");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.updateTaskState(taskID, TaskStateEnum.WAIT_BOOS_CHECK_THE_USER_IS_DONE_TASK, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                });
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
    public void addBoosAgreeTaskIsDone() {

        final MaterialButton materialButton = getPositiveButton("Agree The Task is Done");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.updateTaskState(taskID, TaskStateEnum.PERFECT_END, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                });
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
    public void removeAllViewForTaskStateContext() {
        runOnUiThread(new Runnable() { // 一定要記得跑在UI thread上才會更新UI
            @Override
            public void run() {
                stateButtonsLayout.removeAllViews();
            }
        });
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

    @Override
    public boolean hasRequestTask() {
        return isUserAlreadyRequest;
    }

    @Override
    public boolean isCanRequestTaskUser() {
        if (userMode.equals(IS_CAN_REQUEST_TASK_USER)) {
            return true;
        } else {
            return false;
        }
    }

    private MaterialButton getPositiveButton(String buttonText) {

        MaterialButton materialButton = getBaseButton();
        materialButton.setBackgroundColor(Color.parseColor("#32A852"));
        materialButton.setText(buttonText);
        materialButton.setTextColor(Color.parseColor("#FFFFFF"));

        return materialButton;
    }

    private MaterialButton getNegativeButton(String buttonText) {

        MaterialButton materialButton = getBaseButton();

        materialButton.setBackgroundColor(Color.parseColor("#C40C27"));
        materialButton.setText(buttonText);
        materialButton.setTextColor(Color.parseColor("#FFFFFF"));

        return materialButton;
    }

    private MaterialButton getBaseButton() {

        //參考以下 獲得如何修改button 風格的
        //https://stackoverflow.com/questions/52120168/programmatically-create-a-materialbutton-with-outline-style
        MaterialButton materialButton = new MaterialButton(activity, null, R.attr.materialButtonOutlinedStyle);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(70, 20, 70, 20);
        materialButton.setLayoutParams(params);
        materialButton.setCornerRadius(30);
        materialButton.setTextSize(24);

        // 以下可以設定斜體字之類的 還需要查一下
//        materialButton.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);

        return materialButton;
    }

    private void updateUserMode() {
        if (mTask.getReleaseUserID() == loginUserID) {
            userMode = IS_RELEASE_USER;
        } else if (mTask.getReceiveUserID() == loginUserID){
            userMode = IS_RECEIVE_USER;
        } else { // 如果任務不屬於該使用者 且 該使用者未被指定, 則是可以申請任務狀態, 先不考慮沒登入的情況
            userMode = IS_CAN_REQUEST_TASK_USER;
        }
    }
}