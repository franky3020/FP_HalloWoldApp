package TaskState;

public class TaskOnGoingState implements ITaskStateAction {

    private static TaskOnGoingState instance = new TaskOnGoingState();
    private TaskOnGoingState() {

    }
    public static TaskOnGoingState getInstance() {
        return instance;
    }


    @Override
    public void showUI(ITaskStateContext context) { // 缺聯絡按鈕
        context.removeAllViewForTaskStateContext();
        context.addATaskStateShow();

        if (context.isReleaseUser()) {
            context.addBoosRequestStopTaskButton();
            context.addSendMessageToReceiveUserButton();
        } else if (context.isReceiveUser()) {
            context.addWorkerRequestCheckTheTaskDoneButton();
            context.addWorkerStopTaskButton();
            context.addSendMessageToReleaseUserButton();
        } else if (context.isCanRequestTaskUser()) {
            // 顯示已被執行中 不得申請
        } else {
            // no thing
        }
    }

    @Override
    public String toString() {
        return "TaskOnGoingState{}";
    }
}
