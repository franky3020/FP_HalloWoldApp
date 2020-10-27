package TaskState;

public interface ITaskStateContext {

    void changeTaskState(ITaskStateAction stateAction);
    void addDeleteButton();
    void addSelectedWorkerButton();
    boolean isReleaseUser();
    boolean isReceiveUser();

    // 加上各種按鈕, 因為是Activity需要實做出來的, 這樣就不需要一直傳遞Activity 去生成按鈕了, Activity 只耦合介面



}
