package Task;

import java.sql.Timestamp;
import java.util.Date;
import User.User;

public class Task {

    private int taskID;
    private String taskName;
    private String message = "";
    private Timestamp startPostTime; // 要改成localTime 反正存到資料庫的是timestamp 但拿出來要轉成localTime
    private Timestamp endPostTime = new Timestamp(new Date().getTime());
    private int salary;
    private String typeName;
    private int releaseUserID;
    private Timestamp releaseTime;
    private int receiveUserID = -1;
    private Timestamp receiveTime = new Timestamp(new Date().getTime());
    private String taskAddress = "";
    private int taskCity = -1;
//    private TaskState state; // 目前後端無這資料 但之後必須加上


    public Task(int taskID, String taskName, Timestamp startPostTime, int salary,
                String typeName, int releaseUserID, Timestamp releaseTime) {
        this.taskID = taskID;
        this.taskName = taskName;
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

    public Timestamp getStartPostTime() {
        return startPostTime;
    }

    public void setStartPostTime(Timestamp startPostTime) {
        this.startPostTime = startPostTime;
    }

    public Timestamp getEndPostTime() {
        return endPostTime;
    }

    public void setEndPostTime(Timestamp endPostTime) {
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

    public Timestamp getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Timestamp releaseTime) {
        this.releaseTime = releaseTime;
    }

    public int getReceiveUserID() {
        return receiveUserID;
    }

    public void setReceiveUserID(int receiveUserID) {
        this.receiveUserID = receiveUserID;
    }

    public Timestamp getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Timestamp receiveTime) {
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
}
