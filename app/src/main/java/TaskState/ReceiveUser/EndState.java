package TaskState.ReceiveUser;

import android.widget.LinearLayout;

import Task.CreateTaskStateButtonFactory;
import TaskState.TaskState;

public class EndState implements TaskState {

    ReceiveUserTaskStateContext context;

    public EndState(ReceiveUserTaskStateContext context) {
        this.context = context;
    }

    @Override
    public void showUIonLinearLayout(LinearLayout linearLayout) {
//        // nothing
        CreateTaskStateButtonFactory createTaskStateButtonFactory = new CreateTaskStateButtonFactory(context.getTaskID(), context.getContextThemeWrapper());
        linearLayout.addView(createTaskStateButtonFactory.createTasksButton("OK"));
    }
}
