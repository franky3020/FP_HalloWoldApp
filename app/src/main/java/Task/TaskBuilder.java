package Task;

import java.time.LocalDateTime;

public final class TaskBuilder {
    private final int taskID;
    private LocalDateTime endPostTime;
    private int receiveUserID;
    private String taskAddress;
    private String taskName;
    private String message;
    private LocalDateTime startPostTime;
    private final int salary;
    private final int releaseUserID;
    private LocalDateTime releaseTime;

    private TaskBuilder(int taskID, int salary, int releaseUserID) {
        this.taskID = taskID;
        this.salary = salary;
        this.releaseUserID = releaseUserID;
    }


    public static TaskBuilder aTask(int taskID, int salary, int releaseUserID) {
        return new TaskBuilder(taskID, salary, releaseUserID);
    }

    public TaskBuilder withEndPostTime(LocalDateTime endPostTime) {
        this.endPostTime = endPostTime;
        return this;
    }

    public TaskBuilder withReceiveUserID(int receiveUserID) {
        this.receiveUserID = receiveUserID;
        return this;
    }

    public TaskBuilder withReceiveTime(LocalDateTime receiveTime) { // 好像不需要接收時間 因為狀態那邊會存
        return this;
    }

    public TaskBuilder withTaskAddress(String taskAddress) {
        this.taskAddress = taskAddress;
        return this;
    }

    public TaskBuilder withTaskCity(int taskCity) { // Todo 待刪除
        return this;
    }

    public TaskBuilder withTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public TaskBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public TaskBuilder withStartPostTime(LocalDateTime startPostTime) {
        this.startPostTime = startPostTime;
        return this;
    }

    public TaskBuilder withTypeName(String typeName) {
        return this;
    }

    public TaskBuilder withReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
        return this;
    }

    public Task build() {
        Task task = new Task();
        task.setTaskID(taskID);
        task.setEndPostTime(endPostTime);
        task.setReceiveUserID(receiveUserID);
        task.setTaskAddress(taskAddress);
        task.setTaskName(taskName);
        task.setMessage(message);
        task.setStartPostTime(startPostTime);
        task.setSalary(salary);
        task.setReleaseUserID(releaseUserID);
        task.setReleaseTime(releaseTime);

        return task;
    }
}
