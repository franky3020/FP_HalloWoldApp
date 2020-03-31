package Task;

import java.util.Date;
import User.User;

public class Task{
    private String name;
    private int id;
    private User assigner;
    private User executor;
    private TaskState state;
    private Date startData;/* 訂單日期 包含年月日 */

    public Task (String name, int id, User assigner, User executor, TaskState taskState, Date startData) {
        this.name = name;
        this.id = id;
        this.assigner = assigner;
        this.executor = executor;
        this.state = taskState;
        this.startData = startData;
    }

    public void setName(String taskName) {
        this.name = taskName;
    }

    public void setID(int taskID) {
        this.id = taskID;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public void setStartData(Date startData){
        this.startData = startData;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public TaskState getState() {
        return state;
    }

    public Date getStartData() {
        return startData;
    }

}
