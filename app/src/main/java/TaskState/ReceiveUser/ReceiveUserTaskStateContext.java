package TaskState.ReceiveUser;


import android.app.Activity;
import android.widget.LinearLayout;

import androidx.appcompat.view.ContextThemeWrapper;

import Task.TaskStateEnum;
import TaskState.TaskState;

public class ReceiveUserTaskStateContext {

    private int taskID;
    private ContextThemeWrapper contextThemeWrapper;
    private LinearLayout linearLayout;
    private Activity activity;

    private TaskState taskState;
    private TaskState boosReleaseState = new BoosReleaseState(this);
    private TaskState otherState = new EndState(this);


    public static int BOOS_RELEASE_STATE = 0;


    // 因為更新UI必須要在主執行緒上才能更新, 所以必須傳入Activity
    public ReceiveUserTaskStateContext(int taskID, ContextThemeWrapper contextThemeWrapper, LinearLayout linearLayout, Activity activity) {
        this.taskID = taskID;
        this.contextThemeWrapper = contextThemeWrapper;
        this.linearLayout = linearLayout;
        this.activity = activity;
    }

    public void setTaskState(TaskStateEnum taskStateEnum) {
        if (taskStateEnum == TaskStateEnum.BOOS_RELEASE_AND_SELECTING_WORKER) {
            this.taskState = boosReleaseState;
        } else {
            this.taskState = otherState;
        }

    }

    public void updateUI() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                taskState.showUIonLinearLayout(linearLayout);
            }
        });

    }

    public int getTaskID() {
        return taskID;
    }

    public ContextThemeWrapper getContextThemeWrapper() {
        return contextThemeWrapper;
    }

    public Activity getActivity() {
        return activity;
    }
}
