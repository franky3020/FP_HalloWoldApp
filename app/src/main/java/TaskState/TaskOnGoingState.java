package TaskState;

public class TaskOnGoingState implements ITaskStateAction {

    private static TaskOnGoingState instance = new TaskOnGoingState();
    private TaskOnGoingState() {

    }
    public static TaskOnGoingState getInstance() {
        return instance;
    }


    @Override
    public void showUI(ITaskStateContext context) {
        if (context.isReleaseUser()) {


        } else if (context.isReceiveUser()) { // Todo 需要判斷是不是該使用者

            if (context.isBoosSelectThatUserToDoTask()) {
                context.addWorkerRequestCheckTheTaskDoneButton();
            }

            // 缺聯絡按鈕
        }


    }

    @Override
    public String toString() {
        return "TaskOnGoingState{}";
    }
}
