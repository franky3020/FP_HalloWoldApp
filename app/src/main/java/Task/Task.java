package Task;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private String taskName;
    private String taskID;
    private String taskAssigner;
    private String taskAccepter;
    private int taskState;/* 訂單狀態 0=發布中 1=已接受 2=已完成 */
    private String year,month,day;/* 訂單日期 包含年月日 待優化應該可以有更好的寫法 */

    public Task(String taskName,String taskID,String taskAssigner,String taskAccepter){
        /* 基本Constructor */
        this.taskName = taskName;
        this.taskID = taskID;
        this.taskAssigner = taskAssigner;
        this.taskAccepter = taskAccepter;
        taskState = 0;
        year = "2020";
        month = "03";
        day = "24";

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

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
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

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }
}
