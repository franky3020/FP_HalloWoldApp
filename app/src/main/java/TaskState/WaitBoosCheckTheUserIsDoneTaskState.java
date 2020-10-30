package TaskState;

public class WaitBoosCheckTheUserIsDoneTaskState implements ITaskStateAction {

    private static WaitBoosCheckTheUserIsDoneTaskState instance = new WaitBoosCheckTheUserIsDoneTaskState();
    private WaitBoosCheckTheUserIsDoneTaskState() {

    }
    public static WaitBoosCheckTheUserIsDoneTaskState getInstance() {
        return instance;
    }


    @Override
    public void showUI(ITaskStateContext context) {
        if (context.isReleaseUser()) {
            context.addBoosAgreeTaskIsDone();

        } else if (context.isReceiveUser()) { // Todo 需改成 判斷是不是該使用者 而不是只有 receiveUser 或是其它種使用者改名

            // 缺聯絡按鈕
        }

    }

    @Override
    public String toString() {
        return "WaitBoosCheckTheUserIsDoneTaskState{}";
    }
}
