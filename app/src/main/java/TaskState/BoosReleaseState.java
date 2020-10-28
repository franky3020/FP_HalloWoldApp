package TaskState;


public class BoosReleaseState implements ITaskStateAction {

    private static BoosReleaseState instance = new BoosReleaseState();
    private BoosReleaseState() {

    }
    public static BoosReleaseState getInstance() {
        return instance;
    }


    @Override
    public void showUI(ITaskStateContext context) {
        if (context.isReleaseUser()) {
            context.addBoosSelectedWorkerButton();
            context.addBoosDeleteButton();
        } else if (context.isReceiveUser()) {
            // context.申請按鈕
            // 聯絡按鈕
        }
    }
}
