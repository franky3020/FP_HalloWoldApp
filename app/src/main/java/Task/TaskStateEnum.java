package Task;

import java.util.HashMap;
import java.util.Map;

public enum TaskStateEnum { // 此內容 跟 後端的類別完全一致 Todo 還需要要加上缺少的狀態 Worker要

    // 只需要五種狀態

    BOOS_RELEASE_AND_SELECTING_WORKER(1), // 需要
    BOOS_SELECTED_WORKER(2),  // 需要
    WORKER_CANCEL_REQUEST(3),
    BOOS_CANCEL_RELEASE(4),
    WORKER_CONFIRM_EXECUTION(5), // 需要   要改名成 Task Doing
    WORKER_REQUEST_CHECK_THE_TASK_IS_DONE(6),
    BOOS_ACCOUNT_THE_TASK_NOT_DONE(7),
    BOOS_ACCOUNT_THE_TASK_IS_DONE(8),
    COORDINATION(9),
    BOOS_REQUEST_STOP_TASK(10),
    WORKER_DISAGREE_STOP_TASK(11),
    BOOS_CANCEL_THE_REQUEST_STOP_TASK(12),
    WORKER_STOP_TASK(13),
    END(14),
    BOOS_CANCEL_SELECTED_THE_WORKER(15);

    // 需要 等待Worker同意中止任務
    // 需要 等待Boos確認完成此任務




    private int value;
    private static Map map = new HashMap<>();

    TaskStateEnum(int value) {
        this.value = value;
    }

    static {
        for (TaskStateEnum taskStateEnum : TaskStateEnum.values()) {
            map.put(taskStateEnum.value, taskStateEnum);
        }
    }

    public static TaskStateEnum valueOf(int state) {
        return (TaskStateEnum) map.get(state);
    }

    public int getValue() {
        return value;
    }

}
