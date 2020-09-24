package Task;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Observable;

public class GetTasksObserved extends Observable{
    JSONObject tasksJSONObject;
    TaskAPIService taskApiService = new TaskAPIService();
    Thread getTasksAPIThread;


    public GetTasksObserved() {
        getTasksAPIThread = new Thread() {
            public void run() {
                try {
                    tasksJSONObject = taskApiService.getTasks();
                    if(tasksJSONObject != null) {
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
        return tasksJSONObject;
    }
}
