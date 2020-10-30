package TaskState;

public class TaskOnGoingState implements ITaskStateAction {

    private static TaskOnGoingState instance = new TaskOnGoingState();
    private TaskOnGoingState() {

    }
    public static TaskOnGoingState getInstance() {
        return instance;
    }


    @Override
    public void showUI(ITaskStateContext context) {

    }

    @Override
    public String toString() {
        return "TaskOnGoingState{}";
    }
}
