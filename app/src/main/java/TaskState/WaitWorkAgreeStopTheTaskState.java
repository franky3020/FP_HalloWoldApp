package TaskState;

public class WaitWorkAgreeStopTheTaskState implements ITaskStateAction {

    private static WaitWorkAgreeStopTheTaskState instance = new WaitWorkAgreeStopTheTaskState();
    private WaitWorkAgreeStopTheTaskState() {

    }
    public static WaitWorkAgreeStopTheTaskState getInstance() {
        return instance;
    }


    @Override
    public void showUI(ITaskStateContext context) {
        context.removeAllViewForTaskStateContext();
        context.addATaskStateShow();

    }

    @Override
    public String toString() {
        return "WaitWorkAgreeStopTheTaskState{}";
    }
}
