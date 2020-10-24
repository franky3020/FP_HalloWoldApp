package Task;

import java.util.HashMap;
import java.util.Map;

public enum TaskStateEnum { // 此內容 跟 後端的類別完全一致

    BOOS_RELEASE_AND_SELECTING_WORKER(1),
    BOOS_SELECTED_WORKER(2),
    WORKER_CANCEL_REQUEST(3),
    BOOS_CANCEL_RELEASE(4),
    WORKER_CONFIRM_EXECUTION(5),
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
