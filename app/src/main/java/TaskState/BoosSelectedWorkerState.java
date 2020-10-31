package TaskState;

public class BoosSelectedWorkerState implements ITaskStateAction {

    private static BoosSelectedWorkerState instance = new BoosSelectedWorkerState();
    private BoosSelectedWorkerState() {

    }
    public static BoosSelectedWorkerState getInstance() {
        return instance;
    }

    @Override
    public void showUI(ITaskStateContext context) { // 缺聯絡按鈕
        context.removeAllViewForTaskStateContext();
        context.addATaskStateShow();

        if (context.isReleaseUser()) {
            context.addBoosCancelRequestThatUser();
            context.addBoosDeleteButton();
        } else if (context.isReceiveUser()) { // Todo 需要判斷是不是該使用者
            context.addWorkerConfirmExecutionButton();
        } else if (context.isCanRequestTaskUser()) {
            context.addWorkerRequestTaskButton();
        } else if (context.isCanCancelRequestTaskUser()) {
            context.addWorkerCancelRequestButton();
        } else {
            // no thing
        }
    }

    @Override
    public String toString() {
        return "BoosSelectedWorkerState{}";
    }
}