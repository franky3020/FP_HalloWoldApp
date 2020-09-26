package Task;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Observable;

public class GetTasksObserved extends Observable{
    JSONObject tasksJSONObject = null;
    JSONObject latestTasksJSONObject = new JSONObject();
    TaskAPIService taskApiService = new TaskAPIService();
    Thread getTasksAPIThread;


    public GetTasksObserved() {
        getTasksAPIThread = new Thread() {
            public void run() {
                latestTasksJSONObject = taskApiService.getTasks();
                if(latestTasksJSONObject != null) { //有成功拿到資料, 就會更新 tasksJSONObject
                    tasksJSONObject = latestTasksJSONObject;
                }
                setChanged();// 一定先有這個 notifyObservers() 才會有效
                notifyObservers();
            }
        };
    }

    public void startGetTasksThread() {
        getTasksAPIThread.start();
    }

    public JSONObject getTasks() {
        if(tasksJSONObject != null) {
            return tasksJSONObject;
        } else {
            return null;
        }

    }
}
