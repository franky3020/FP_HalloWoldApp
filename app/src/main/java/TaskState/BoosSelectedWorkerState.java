package TaskState;

public class BoosSelectedWorkerState implements ITaskStateAction {

    private static BoosSelectedWorkerState instance = new BoosSelectedWorkerState();
    private BoosSelectedWorkerState() {

    }
    public static BoosSelectedWorkerState getInstance() {
        return instance;
    }

    @Override
    public void showUI(ITaskStateContext context) {
        if (context.isReleaseUser()) {

        } else if (context.isReceiveUser()) { // Todo 需要判斷是不是該使用者


            context.addWorkerConfirmExecutionButton();
            context.addWorkerCancelRequestButton(); // Todo 這要改
            // 缺聯絡按鈕
        }

    }

    @Override
    public String toString() {
        return "BoosSelectedWorkerState{}";
    }
}