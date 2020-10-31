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
        if (context.isReleaseUser()) {
            // 加上 刪除該請求
            // 與刪除任務
        } else if (context.isReceiveUser()) { // Todo 需要判斷是不是該使用者
            context.addWorkerConfirmExecutionButton();
        } else if (context.isCanRequestTaskUser()) {
            context.addWorkerRequestTaskButton();
        }
    }

    @Override
    public String toString() {
        return "BoosSelectedWorkerState{}";
    }
}