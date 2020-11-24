package TaskState;

public class FailureEndState implements ITaskStateAction {

    private static FailureEndState instance = new FailureEndState();
    private FailureEndState() {

    }
    public static FailureEndState getInstance() {
        return instance;
    }


    @Override
    public void showUI(ITaskStateContext context) {
        context.removeAllViewForTaskStateContext();
        context.addATaskStateShow();

        if (context.isReleaseUser()) {
            context.addBoosDeleteButton(); // 測試用 如果任務為結束狀態 可以刪除
            context.addSendMessageToReceiveUserButton();
        } else if (context.isReceiveUser()) {
            context.addSendMessageToReleaseUserButton();
        }
    }

    @Override
    public String toString() {
        return "FailureEndState{}";
    }
}

