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
            // 加上Boos 請求中止任務選項
            //
        } else if (context.isReceiveUser()) {
            context.addWorkerRequestCheckTheTaskDoneButton();
            // 加上worker 中止任務選項
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
