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
            if(context.hasRequestTask()) { // 如果已經申請 則新增刪除申請按鈕
                context.addWorkerCancelRequestButton();
            } else {
                context.addWorkerRequestTaskButton();
            }
            // 缺聯絡按鈕
        }
    }

    @Override
    public String toString() {
        return "BoosReleaseState{}";
    }
}
