package TaskState;

public class WorkerConfirmExecutionState implements ITaskStateAction {

    private static WorkerConfirmExecutionState instance = new WorkerConfirmExecutionState();
    private WorkerConfirmExecutionState() {

    }

    public static WorkerConfirmExecutionState getInstance() {
        return instance;
    }

    @Override
    public void showUI(ITaskStateContext context) {
        context.removeAllViewForTaskStateContext(); // Todo 未完成
    }

    @Override
    public String toString() {
        return "WorkerConfirmExecutionState{}";
    }
}
