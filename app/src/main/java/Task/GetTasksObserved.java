package Task;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Observable;

public class GetTasksObserved extends Observable{
    JSONObject allTask;
    TaskApiService taskApiService = new TaskApiService();
    Thread getTasksAPIThread;


    public GetTasksObserved() {
        getTasksAPIThread = new Thread() {
            public void run() {
                try {
                    allTask = taskApiService.getTasks();
                    if(allTask != null) {
                        setChanged();// 一定先有這個 notifyObservers() 才會有效
                        notifyObservers();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    public void startGetTasksThread() {
        getTasksAPIThread.start();
    }

    public JSONObject getTasks() {
        return allTask;
    }
}
