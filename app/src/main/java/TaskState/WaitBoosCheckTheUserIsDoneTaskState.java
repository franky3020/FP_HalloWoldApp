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
        context.removeAllViewForTaskStateContext();
        context.addATaskStateShow();

        if (context.isReleaseUser()) {
            context.addBoosAgreeTaskIsDoneButton();
            context.addBoosNotAgreeTaskIsDoneButton();

        } else if (context.isReceiveUser()) {

        } else {
            // no thing
        }

    }

    @Override
    public String toString() {
        return "WaitBoosCheckTheUserIsDoneTaskState{}";
    }
}
