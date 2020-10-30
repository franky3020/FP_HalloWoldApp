package TaskState;

public interface ITaskStateContext {

    void changeTaskState(ITaskStateAction stateAction);
    void addBoosDeleteButton();
    void addBoosSelectedWorkerButton();
    void addWorkerRequestTaskButton(); // 要做申請訊息
    void addWorkerCancelRequestButton();
    void addWorkerConfirmExecutionButton();
    void addWorkerRequestCheckTheTaskDoneButton();

    void removeAllViewForTaskStateContext();






    void addSendMessageButton();
    boolean isReleaseUser();
    boolean isReceiveUser();
    boolean hasRequestTask();
    boolean isBoosSelectThatUserToDoTask();

    // 加上各種按鈕, 因為是Activity需要實做出來的, 這樣就不需要一直傳遞Activity 去生成按鈕了, Activity 只耦合介面



}
