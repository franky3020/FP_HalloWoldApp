package Task;

import java.time.LocalDateTime;

public class TaskState {

    private TaskStateEnum taskStateEnum;
    private LocalDateTime stepTime;

    public TaskState(TaskStateEnum taskStateEnum, LocalDateTime stepTime) {
        this.taskStateEnum = taskStateEnum;
        this.stepTime = stepTime;
    }

    public TaskStateEnum getTaskStateEnum() {
        return taskStateEnum;
    }

    public void setTaskStateEnum(TaskStateEnum taskStateEnum) {
        this.taskStateEnum = taskStateEnum;
    }

    public LocalDateTime getStepTime() {
        return stepTime;
    }

    public void setStepTime(LocalDateTime stepTime) {
        this.stepTime = stepTime;
    }

    @Override
    public String toString() {
        return "TaskState{" +
                "taskStateEnum=" + taskStateEnum +
                ", stepTime=" + stepTime +
                '}';
    }
}
