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
            context.addBoosCancelRequestThatUserButton();
            context.addBoosRevokeTaskButton();
            context.addSendMessageToReceiveUserButton();
        } else if (context.isReceiveUser()) {
            context.addWorkerConfirmExecutionButton();
            context.addWorkerNotConfirmExecutionButton();
            context.addSendMessageToReleaseUserButton();
        } else if (context.isCanRequestTaskUser()) {
            context.addWorkerRequestTaskButton();
            context.addSendMessageToReleaseUserButton();
        } else if (context.isCanCancelRequestTaskUser()) {
            context.addWorkerCancelRequestButton();
            context.addSendMessageToReleaseUserButton();
        } else {
            // no thing
        }
    }

    @Override
    public String toString() {
        return "BoosSelectedWorkerState{}";
    }
}