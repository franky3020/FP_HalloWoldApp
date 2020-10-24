package TaskState.ReceiveUser;


import android.widget.LinearLayout;

import androidx.appcompat.view.ContextThemeWrapper;

import TaskState.TaskState;

public class ReceiveUserTaskStateContext {

    private int taskID;
    private ContextThemeWrapper contextThemeWrapper;
    private LinearLayout linearLayout;

    private TaskState taskState;
    private TaskState boosReleaseState = new BoosReleaseState(this);

    public static int BOOS_RELEASE_STATE = 0;



    public ReceiveUserTaskStateContext(int taskID, ContextThemeWrapper contextThemeWrapper, LinearLayout linearLayout) {
        this.taskID = taskID;
        this.contextThemeWrapper = contextThemeWrapper;
        this.linearLayout = linearLayout;
        this.taskState = boosReleaseState;
    }

    public void setTaskState(int taskStateNum) {
        if (taskStateNum == BOOS_RELEASE_STATE) {
            this.taskState = boosReleaseState;
        }


    }

    public void updateUI() {
        taskState.showUIonLinearLayout(linearLayout);
    }

    public int getTaskID() {
        return taskID;
    }

    public ContextThemeWrapper getContextThemeWrapper() {
        return contextThemeWrapper;
    }

}
