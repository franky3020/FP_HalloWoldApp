package Task;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task extends TaskTime{
    private String taskName;
    private String taskID;
    private String taskAssigner;
    private String taskAccepter;
    private int taskState;/* 訂單狀態 0=發布中 1=已接受 2=已完成 */
    private TaskTime taskTime = new TaskTime();/* 訂單日期 包含年月日 */

    public Task(String taskName,String taskID,String taskAssigner,String taskAccepter){
        /* 基本Constructor */
        this.taskName = taskName;
        this.taskID = taskID;
        this.taskAssigner = taskAssigner;
        this.taskAccepter = taskAccepter;
        this.taskState = 0;
        this.setYear("2020");
        this.setMonth("03");
        this.setDay("24");
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public void setTaskAssigner(String taskAssigner){
        this.taskAssigner = taskAssigner;
    }
    public void setTaskAccepter(String taskAccepter){
        this.taskAccepter = taskAccepter;
    }
    public void setTaskState(int taskState){
        this.taskState = taskState;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getTaskAssigner() {
        return taskAssigner;
    }

    public String getTaskAccepter() {
        return taskAccepter;
    }

    public int getTaskState() {
        return taskState;
    }

}
