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
public class TaskDetailActivity extends AppCompatActivity implements ITaskStateContext {

    private Activity activity = this;
    private ITaskStateContext thisContext = this;
    private static final String LOG_TAG = TaskDetailActivity.class.getSimpleName();

    public static final String EXTRA_TASK_ID = "taskID";
    int taskID;
    int loginUserID;
    boolean isUserAlreadyRequest = false;
    Task mTask = TaskBuilder.aTask(0,0,0).build(); // 初始化假的任務

    private static final String IS_RELEASE_USER = "releaseUser";
    private static final String IS_CAN_REQUEST_TASK_USER = "canRequestTaskUser";
    private static final String IS_CAN_CANCEL_REQUEST_TASK_USER = "canCancelRequestTaskUser";
    private static final String IS_RECEIVE_USER = "receiveUser";
    private static final String IS_UNKNOWN_USER = "unknownUser";
    private String userMode = IS_UNKNOWN_USER;

    LinearLayout stateButtonsLayout;
    ITaskStateAction state = BoosReleaseState.getInstance();
//    private TaskState taskState; // 我想這樣加 但還不行 之後要顯示修改狀態的時間

    private static final int ALL_UPDATE_FUNCTION_COUNT = 3; // 因為總共有三個非同步函式需要被計算
    private int currentUpdateFunctionDone = 0;

    Handler updateActivityHandler = new Handler();

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
        this.updateActivityHandler.post(updateActivity_Runnable); // Todo 這更新時會閃一下, 可以判斷狀態一樣就不要更新
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.updateActivityHandler.removeCallbacks(updateActivity_Runnable);
    }

    private final Runnable updateActivity_Runnable = new Runnable() {

        @Override
        public void run() {
            initAllUI();
            int delayMillis = 3000;
            updateActivityHandler.postDelayed(updateActivity_Runnable, delayMillis);
        }

        private void initAllUI() { // 使用同步鎖 確認這三件事都有完成時 才會觸發更新UI
            synchronized((Object) currentUpdateFunctionDone) {
                currentUpdateFunctionDone = 0; // 一定要先初始化為0, 這樣重複執行此函式才不會出錯
            }
            updateMTask();
            getTaskStateAndUpdate();
            updateTheUserIsAlreadyRequest();
        }

        private void updateMTask() {
            TaskAPIService taskApiService = new TaskAPIService();
            taskApiService.getATask(taskID, new TaskAPIService.GetAPIListener<Task>() {

                @Override
                public void onResponseOK(Task task) {
                    mTask = task;
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
                    // Todo
                }
            });
        }

        private void updateTheUserIsAlreadyRequest() {
            TaskAPIService taskApiService = new TaskAPIService();
            taskApiService.checkUserAlreadyRequest(taskID, loginUserID, new TaskAPIService.GetAPIListener<Boolean>() {
                @Override
                public void onResponseOK(Boolean aBoolean) {
                    isUserAlreadyRequest = aBoolean; // Todo 應該是releaseTask 狀態要自行去得知, 有壞味道

                    Log.d(LOG_TAG, "updateTheUserIsAlreadyRequest");
                    checkTheInitIsOkThenUpdateAllUI();
                }

                @Override
                public void onFailure() {
                    isUserAlreadyRequest = false;
                }
            });
        }

    };


    private void checkTheInitIsOkThenUpdateAllUI() {
        synchronized((Object) currentUpdateFunctionDone) {
            currentUpdateFunctionDone++;
            if(currentUpdateFunctionDone >= ALL_UPDATE_FUNCTION_COUNT) {
                updateUserMode();
                upDateAllUI();
            }
        }
    }







    private void upDateAllUI() {
        state.showUI(thisContext);
    }

    @Override
    public void addATaskStateShow() {
//        final TextView taskStateTextView = new TextView(this);
//
//        runOnUiThread(new Runnable() { // 一定要記得跑在UI thread上才會更新UI
//            @Override
//            public void run() {
//                taskStateTextView.setText(state.toString());
//                stateButtonsLayout.addView(taskStateTextView);
//            }
//        });
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
        final MaterialButton materialButton = getNegativeButton("刪除");

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
                        // Todo
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
    public void addBoosRevokeTaskButton() {
        final MaterialButton materialButton = getNegativeButton("收回任務");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserAPIService userAPIService = new UserAPIService();
                userAPIService.increaseUserPoint(mTask.getReleaseUserID(), mTask.getSalary(), new Callback() {

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        TaskAPIService taskApiService = new TaskAPIService();
                        taskApiService.deleteTask(taskID, new Callback() {

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) {
                                finish();
                            }

                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                // Todo
                            }
                        });
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
        
        final MaterialButton materialButton = getPositiveButton("選擇任務接收者");

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
    public void addBoosCancelRequestThatUserButton() {
        final MaterialButton materialButton = getNegativeButton("取消選擇該接收者");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TaskAPIService taskApiService = new TaskAPIService();
                // Todo 這邊需要更新任務的接收者為該使用者
                taskApiService.setTaskReceiveUser(taskID, 0, new Callback() { // Todo 設定 0 是為無接收者的初始值狀態, 有壞味道 需要重構
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        // Todo
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Log.d(LOG_TAG, "setTaskReceiveUser Ok");

                        TaskAPIService taskApiService = new TaskAPIService();
                        taskApiService.updateTaskState(taskID, TaskStateEnum.BOOS_RELEASE_AND_SELECTING_WORKER, new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                // Todo
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.d(LOG_TAG, "updateTaskState Ok");
                                reloadActivity();
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
    public void addWorkerRequestTaskButton() {

        final MaterialButton materialButton = getPositiveButton("申請接收任務");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.addTaskRequestUser(taskID, loginUserID, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        // Todo
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) {
                        reloadActivity();
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

        final MaterialButton materialButton = getNegativeButton("取消申請");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.deleteTaskRequestUser(taskID, loginUserID, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        // Todo
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) {
                        reloadActivity();
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

        final MaterialButton materialButton = getPositiveButton("確認接收並執行");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.updateTaskState(taskID, TaskStateEnum.TASK_ON_GOING, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        // Todo
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        reloadActivity();
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
    public void addWorkerNotConfirmExecutionButton() {

        final MaterialButton materialButton = getNegativeButton("收回接收申請");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 修改任務的接收者為0
                final TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.setTaskReceiveUser(taskID, 0, new Callback() { // Todo 設定 0 是為無接收者的初始值狀態, 有壞味道 需要重構
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        // Todo
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Log.d(LOG_TAG, "setTaskReceiveUser Ok");

                        // 改變任務狀態
                        TaskAPIService taskApiService = new TaskAPIService();
                        taskApiService.updateTaskState(taskID, TaskStateEnum.BOOS_RELEASE_AND_SELECTING_WORKER, new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                // Todo
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                Log.d(LOG_TAG, "updateTaskState Ok");

                                // 取消請求該任務
                                TaskAPIService taskApiService = new TaskAPIService();
                                taskApiService.deleteTaskRequestUser(taskID, loginUserID, new Callback() {
                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                        // Todo
                                    }

                                    @Override
                                    public void onResponse(@NotNull Call call, @NotNull Response response) {
                                        reloadActivity();
                                    }
                                });

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
    public void addWorkerRequestCheckTheTaskDoneButton() {

        final MaterialButton materialButton = getPositiveButton("請求確認完成任務");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.updateTaskState(taskID, TaskStateEnum.WAIT_BOOS_CHECK_THE_USER_IS_DONE_TASK, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        // Todo
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        reloadActivity();
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
    public void addBoosAgreeTaskIsDoneButton() {

        final MaterialButton materialButton = getPositiveButton("同意完成任務");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.updateTaskState(taskID, TaskStateEnum.PERFECT_END, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        // Todo
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        UserAPIService userAPIService = new UserAPIService();
                        userAPIService.increaseUserPoint(mTask.getReceiveUserID(), mTask.getSalary(), new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                reloadActivity();
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
    public void addBoosNotAgreeTaskIsDoneButton() {

        final MaterialButton materialButton = getNegativeButton("駁回該任務");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.updateTaskState(taskID, TaskStateEnum.TASK_ON_GOING, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        // Todo
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        reloadActivity();
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
    public void addBoosRequestStopTaskButton() {

        final MaterialButton materialButton = getNegativeButton("請求中止任務");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.updateTaskState(taskID, TaskStateEnum.WAIT_WORK_AGREE_STOP_THE_TASK, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        // Todo
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        reloadActivity();
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
    public void addBoosCancelTheStopTaskRequestButton() {

        final MaterialButton materialButton = getPositiveButton("取消請求中止任務");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.updateTaskState(taskID, TaskStateEnum.TASK_ON_GOING, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        // Todo
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        reloadActivity();
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
    public void addWorkerNotAgreeStopTaskButton() {

        final MaterialButton materialButton = getNegativeButton("不同意中止任務");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.updateTaskState(taskID, TaskStateEnum.TASK_ON_GOING, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        //Todo
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        reloadActivity();
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
    public void addWorkerStopTaskButton() {

        final MaterialButton materialButton = getNegativeButton("中止任務");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.updateTaskState(taskID, TaskStateEnum.FAILURE_END, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        // Todo
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        UserAPIService userAPIService = new UserAPIService();
                        userAPIService.increaseUserPoint(mTask.getReleaseUserID(), mTask.getSalary(), new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                reloadActivity();
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
    public void removeAllViewForTaskStateContext() {
        runOnUiThread(new Runnable() { // 一定要記得跑在UI thread上才會更新UI
            @Override
            public void run() {
                stateButtonsLayout.removeAllViews();
            }
        });
    }


    @Override
    public void addSendMessageToReleaseUserButton() {

        final ImageView sendMessage = findViewById(R.id.image_releaseTask_message);

        runOnUiThread(new Runnable() { // 一定要記得跑在UI thread上才會更新UI
            @Override
            public void run() {
                sendMessage.setVisibility(View.VISIBLE);
                sendMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, ChatActivity.class);
                        intent.putExtra(ChatActivity.EXTRA_RECEIVER_ID, mTask.getReleaseUserID());
                        activity.startActivity(intent);
                    }
                });
            }
        });

    }

    @Override
    public void addSendMessageToReceiveUserButton() {
        final ImageView sendMessage = findViewById(R.id.image_receiveTask_message);

        runOnUiThread(new Runnable() { // 一定要記得跑在UI thread上才會更新UI
            @Override
            public void run() {
                sendMessage.setVisibility(View.VISIBLE);
                sendMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, ChatActivity.class);
                        intent.putExtra(ChatActivity.EXTRA_RECEIVER_ID, mTask.getReceiveUserID());
                        activity.startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public boolean isReleaseUser() {
        return isUserModeEqualTo(IS_RELEASE_USER);
    }

    @Override
    public boolean isReceiveUser() {
        return isUserModeEqualTo(IS_RECEIVE_USER);
    }

    @Override
    public boolean isCanRequestTaskUser() {
        return isUserModeEqualTo(IS_CAN_REQUEST_TASK_USER);
    }

    @Override
    public boolean isCanCancelRequestTaskUser() {
        return isUserModeEqualTo(IS_CAN_CANCEL_REQUEST_TASK_USER);
    }

    @Override
    public int getReleaseUserId() {
        return mTask.getReleaseUserID();
    }

    @Override
    public int getReceiveUserId() {
        return mTask.getReceiveUserID();
    }

    private boolean isUserModeEqualTo(String userModeStr) {
        if (userMode.equals(userModeStr)) {
            return true;
        } else {
            return false;
        }
    }


    private MaterialButton getPositiveButton(String buttonText) {

        MaterialButton materialButton = getBaseButton();
        materialButton.setStrokeColorResource(R.color.colorPrimary);
        materialButton.setText(buttonText);
        materialButton.setTextColor(getColor(R.color.colorPrimaryDark));

        return materialButton;
    }

    private MaterialButton getNegativeButton(String buttonText) {

        MaterialButton materialButton = getBaseButton();

        materialButton.setStrokeColorResource(R.color.delete_color);
        materialButton.setText(buttonText);
        materialButton.setTextColor(getColor(R.color.delete_color));

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

        if(GetLoginUser.isLogin() == false) { // 先判斷是不是訪客模式
            userMode = IS_UNKNOWN_USER;
            return;
        }

        if (mTask.getReleaseUserID() == loginUserID) {
            userMode = IS_RELEASE_USER;
        } else if (mTask.getReceiveUserID() == loginUserID){
            userMode = IS_RECEIVE_USER;
        } else if ( isUserAlreadyRequest == false ) {
            userMode = IS_CAN_REQUEST_TASK_USER;
        } else if ( isUserAlreadyRequest == true ) {
            userMode = IS_CAN_CANCEL_REQUEST_TASK_USER;
        }
    }

    private void reloadActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}