package TaskState.ReceiveUser;

import android.widget.LinearLayout;

import Task.CreateTaskStateButtonFactory;
import TaskState.TaskState;

public class BoosSelectedWorkerState implements TaskState {

    ReceiveUserTaskStateContext context;
    public BoosSelectedWorkerState(ReceiveUserTaskStateContext context) {
        this.context = context;
    }

    @Override
    public void showUIonLinearLayout(LinearLayout linearLayout) {
//        CreateTaskStateButtonFactory createTaskStateButtonFactory = new CreateTaskStateButtonFactory(context.getTaskID(), context.getContextThemeWrapper());
//        linearLayout.addView(createTaskStateButtonFactory.createTasksButton("Delete"));
    }


}