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

import TaskState.ITaskStateAction;
import TaskState.BoosReleaseState;
import TaskState.BoosSelectedWorkerState;
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
    public static final String IS_RECEIVE_USER = "receiveUser";
    private String userMode = IS_RELEASE_USER;

    LinearLayout stateButtonsLayout;
    ITaskStateAction state = BoosReleaseState.getInstance();
//    private TaskState taskState; // 我想這樣加 但還不行 之後要顯示修改狀態的時間

    private final int allUpdateFunctionCount  = 3;
    private int currentUpdateFunctionDone = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Todo 要在初始化時就要知道此任務狀態 與 taskID
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail2);

        this.loginUserID = GetLoginUser.getLoginUser().getId();

        // 初始化此頁面必要資訊
        taskID = getIntent().getExtras().getInt(EXTRA_TASK_ID);



        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.task_detail_frag);
        assert taskDetailFragment != null;
        taskDetailFragment.setTaskID(taskID);

        stateButtonsLayout = findViewById(R.id.task_state_buttons_container);
        initAllUI();
        // 以上順序為 updateMTask() >  getTaskStateAndUpdate() > updateTheUserIsAlreadyRequest() > upDateAllUI()
        // 還未想到好方法簡化


    }

    private void initAllUI() {
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
        addATaskStateForTest();
        state.showUI(thisContext);
    }

    private void addATaskStateForTest() {
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

            case BOOS_CANCEL_RELEASE:

            case WORKER_CONFIRM_EXECUTION:

            case WORKER_CANCEL_REQUEST:

            default:
                return EmptyState.getInstance();
                // no thing
        }
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

        final MaterialButton materialButton = getBaseButton();

        materialButton.setBackgroundColor(Color.parseColor("#32A852"));
        materialButton.setText("RequestTask");
        materialButton.setTextColor(Color.parseColor("#FFFFFF"));

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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "已申請", Toast.LENGTH_SHORT).show(); // 這之後要用string
                            }
                        });

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

        final MaterialButton materialButton = getBaseButton();

        materialButton.setBackgroundColor(Color.parseColor("#C40C27"));
        materialButton.setText("Cancel_Request");
        materialButton.setTextColor(Color.parseColor("#FFFFFF"));


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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "已取消申請", Toast.LENGTH_SHORT).show(); // 這之後要用string
                            }
                        });

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
    public void addWorkerConfirmExecutionButton() {

        final MaterialButton materialButton = getBaseButton();

        materialButton.setBackgroundColor(Color.parseColor("#32A852"));
        materialButton.setText("Confirm_Execution");
        materialButton.setTextColor(Color.parseColor("#FFFFFF"));

        runOnUiThread(new Runnable() { // 一定要記得跑在UI thread上才會更新UI
            @Override
            public void run() {
                stateButtonsLayout.addView(materialButton);
            }
        });

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

    @Override
    public boolean hasRequestTask() {
        return isUserAlreadyRequest;
    }

    @Override
    public boolean isBoosSelectThatUserToDoTask() {
        if ( mTask.getReceiveUserID() == loginUserID) {
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
        if (mTask.getReleaseUserID() == loginUserID) {
            userMode = IS_RELEASE_USER;
        } else {
            userMode = IS_RECEIVE_USER;
        }
    }
}