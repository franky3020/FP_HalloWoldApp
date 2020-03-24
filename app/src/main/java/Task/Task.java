package Task;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import User.User;

public class Task{
    private String taskName;
    private int taskID;
    private User taskAssigner;
    private User taskAccepter;
    private int taskState;/* 訂單狀態 0=發布中 1=已接受 2=已完成 */
    private TaskTime taskTime;/* 訂單日期 包含年月日 */

    public Task(String taskName, int taskID,User taskAssigner,User taskAccepter, int taskState, TaskTime taskTime){
        /* 基本Constructor */
        this.taskName = taskName;
        this.taskID = taskID;
        this.taskAssigner = taskAssigner;
        this.taskAccepter = taskAccepter;
        this.taskState = taskState;
        this.taskTime = taskTime;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
    public void setTaskState(int taskState){
        this.taskState = taskState;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getTaskID() {
        return taskID;
    }

    public int getTaskState() {
        return taskState;
    }

}
