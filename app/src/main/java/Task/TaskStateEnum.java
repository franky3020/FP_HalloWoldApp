package Task;

import java.util.HashMap;
import java.util.Map;

public enum TaskStateEnum { // 此內容 跟 後端的類別完全一致

    // 只需要7種狀態

    BOOS_RELEASE_AND_SELECTING_WORKER(1),
    BOOS_SELECTED_WORKER(2),
    TASK_ON_GOING(3),
    WAIT_BOOS_CHECK_THE_USER_IS_DONE_TASK(4),
    WAIT_WORK_AGREE_STOP_THE_TASK(5),
    PERFECT_END(6),
    FAILURE_END(7);

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
