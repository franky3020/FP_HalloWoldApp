package Task;

import java.util.ArrayList;
import java.util.Observable;

public class GetTasksObserved extends Observable{

    ArrayList<Task> latestTasksList = new ArrayList<>();
    ArrayList<Task> tasksList = new ArrayList<>();

    TaskAPIService taskApiService = new TaskAPIService();

    public void startGetTasksThread() {
        Thread getTasksAPIThread = new Thread() {
            public void run() {
                try {
                    getTaskAndUpdateTaskList();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    setChanged();// 一定先有這個 notifyObservers() 才會有效
                    notifyObservers();
                }
            }
        };
        getTasksAPIThread.start();
    }

    private void getTaskAndUpdateTaskList() throws Exception{
        latestTasksList = taskApiService.getTasks();
        tasksList = latestTasksList;
    }

    public ArrayList<Task> getTasks() {
        return tasksList;
    }
}
