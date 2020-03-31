package Task;

import java.util.Date;
import User.User;

public class Task{
    private String name;
    private int id;
    private User assigner;
    private User executor;
    private int state;/* 訂單狀態 0=發布中 1=已接受 2=已完成 */
    private Date data;/* 訂單日期 包含年月日 */

    public Task (String name, int id, User assigner, User executor, int taskState, Date data) {
        /* 基本Constructor */
        this.name = name;
        this.id = id;
        this.assigner = assigner;
        this.executor = executor;
        this.state = taskState;
        this.data = data;
    }

    public void setName(String taskName) {
        this.name = taskName;
    }

    public void setID(int taskID) {
        this.id = taskID;
    }

    public void setState(int taskState){
        this.state = taskState;
    }

    public void setData(Date taskData){
        this.data = taskData;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public int getState() {
        return state;
    }

    public Date getTime() {
        return data;
    }

}
