package Task;

import java.time.LocalDateTime;

public class Task {

    private int taskID;
    private String taskName;
    private String message = "";
    private LocalDateTime startPostTime;
    private LocalDateTime endPostTime;
    private int salary;
    private String typeName;
    private int releaseUserID;
    private LocalDateTime releaseTime;
    private int receiveUserID = -1;
    private LocalDateTime receiveTime;
    private String taskAddress = "";
    private int taskCity = -1;
//    private TaskState state; // 目前後端無這資料 但之後必須加上


    public Task(int taskID, String taskName, String message, LocalDateTime startPostTime, int salary,
                String typeName, int releaseUserID, LocalDateTime releaseTime) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.message = message;
        this.startPostTime = startPostTime;
        this.salary = salary;
        this.typeName = typeName;
        this.releaseUserID = releaseUserID;
        this.releaseTime = releaseTime;
    }

    public Task(String taskName, String message, LocalDateTime startPostTime, int salary, // 用來post 所以沒有id
                String typeName, int releaseUserID, LocalDateTime releaseTime) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.message = message;
        this.startPostTime = startPostTime;
        this.salary = salary;
        this.typeName = typeName;
        this.releaseUserID = releaseUserID;
        this.releaseTime = releaseTime;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getStartPostTime() {
        return startPostTime;
    }

    public void setStartPostTime(LocalDateTime startPostTime) {
        this.startPostTime = startPostTime;
    }

    public LocalDateTime getEndPostTime() {
        return endPostTime;
    }

    public void setEndPostTime(LocalDateTime endPostTime) {
        this.endPostTime = endPostTime;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getReleaseUserID() {
        return releaseUserID;
    }

    public void setReleaseUserID(int releaseUserID) {
        this.releaseUserID = releaseUserID;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public int getReceiveUserID() {
        return receiveUserID;
    }

    public void setReceiveUserID(int receiveUserID) {
        this.receiveUserID = receiveUserID;
    }

    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getTaskAddress() {
        return taskAddress;
    }

    public void setTaskAddress(String taskAddress) {
        this.taskAddress = taskAddress;
    }

    public int getTaskCity() {
        return taskCity;
    }

    public void setTaskCity(int taskCity) {
        this.taskCity = taskCity;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + taskID +
                ", taskName='" + taskName + '\'' +
                ", message='" + message + '\'' +
                ", startPostTime=" + startPostTime +
                ", endPostTime=" + endPostTime +
                ", salary=" + salary +
                ", typeName='" + typeName + '\'' +
                ", releaseUserID=" + releaseUserID +
                ", releaseTime=" + releaseTime +
                ", receiveUserID=" + receiveUserID +
                ", receiveTime=" + receiveTime +
                ", taskAddress='" + taskAddress + '\'' +
                ", taskCity=" + taskCity +
                '}';
    }
}
