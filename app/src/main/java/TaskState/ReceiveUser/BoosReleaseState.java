package TaskState.ReceiveUser;


import android.widget.LinearLayout;

import Task.CreateTaskStateButtonFactory;
import TaskState.TaskState;


public class BoosReleaseState implements TaskState {


    ReceiveUserTaskStateContext context;

    public BoosReleaseState(ReceiveUserTaskStateContext context) {
        this.context = context;
    }

    @Override
    public void showUIonLinearLayout(LinearLayout linearLayout) {
        CreateTaskStateButtonFactory createTaskStateButtonFactory = new CreateTaskStateButtonFactory(context.getTaskID(), context.getContextThemeWrapper(), context.getActivity());

        linearLayout.addView(createTaskStateButtonFactory.createTasksButton("ToShowRequestUsers"));
        linearLayout.addView(createTaskStateButtonFactory.createTasksButton("Delete"));
    }
}
