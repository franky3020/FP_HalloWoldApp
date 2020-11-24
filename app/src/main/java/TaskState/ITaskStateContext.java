package TaskState;

public interface ITaskStateContext {

    void changeTaskState(ITaskStateAction stateAction);
    void addBoosDeleteButton();
    void addBoosRevokeTaskButton(); // 這會刪除任務且拿回金錢
    void addBoosSelectedWorkerButton();
    void addBoosCancelRequestThatUserButton();
    void addWorkerRequestTaskButton(); // 要做申請訊息
    void addWorkerCancelRequestButton();

    void addWorkerConfirmExecutionButton();
    void addWorkerNotConfirmExecutionButton(); // 決定不接收

    void addWorkerRequestCheckTheTaskDoneButton();
    void addBoosAgreeTaskIsDoneButton();
    void addBoosNotAgreeTaskIsDoneButton();

    void addBoosRequestStopTaskButton();
    void addBoosCancelTheStopTaskRequestButton();

    void addWorkerNotAgreeStopTaskButton();
    void addWorkerStopTaskButton();




    void addATaskStateShow();

    void removeAllViewForTaskStateContext();

    void addSendMessageToReleaseUserButton();
    void addSendMessageToReceiveUserButton();

    boolean isReleaseUser();
    boolean isReceiveUser();

    boolean isCanRequestTaskUser();
    boolean isCanCancelRequestTaskUser();

    int getReleaseUserId();
    int getReceiveUserId();



    // 加上各種按鈕, 因為是Activity需要實做出來的, 這樣就不需要一直傳遞Activity 去生成按鈕了, Activity 只耦合介面



}
