package TaskState;

public class PerfectEndState implements ITaskStateAction {

    private static PerfectEndState instance = new PerfectEndState();
    private PerfectEndState() {

    }
    public static PerfectEndState getInstance() {
        return instance;
    }


    @Override
    public void showUI(ITaskStateContext context) {
        context.removeAllViewForTaskStateContext();
        context.addATaskStateShow();

        if (context.isReleaseUser()) {
            context.addBoosDeleteButton(); // 測試用 如果任務為結束狀態 可以刪除
            context.addSendMessageToUserButton(context.getReceiveUserId());
        }

    }

    @Override
    public String toString() {
        return "PerfectEndState{}";
    }
}
