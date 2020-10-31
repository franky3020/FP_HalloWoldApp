package TaskState;

public class WaitBoosCheckTheUserIsDoneTaskState implements ITaskStateAction {

    private static WaitBoosCheckTheUserIsDoneTaskState instance = new WaitBoosCheckTheUserIsDoneTaskState();
    private WaitBoosCheckTheUserIsDoneTaskState() {

    }
    public static WaitBoosCheckTheUserIsDoneTaskState getInstance() {
        return instance;
    }


    @Override
    public void showUI(ITaskStateContext context) {
        if (context.isReleaseUser()) {
            context.addBoosAgreeTaskIsDone();

        } else if (context.isReceiveUser()) {

        }

    }

    @Override
    public String toString() {
        return "WaitBoosCheckTheUserIsDoneTaskState{}";
    }
}
