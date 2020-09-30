package Task;
import org.json.JSONObject;

import java.util.Observable;

public class GetTasksObserved extends Observable{
    JSONObject tasksJSONObject = new JSONObject();;
    JSONObject latestTasksJSONObject = new JSONObject();
    TaskAPIService taskApiService = new TaskAPIService();

    public void startGetTasksThread() {
        Thread getTasksAPIThread = new Thread() {
            public void run() {
                try {
                    getTaskAndUpdateTaskJSONObject();
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

    private void getTaskAndUpdateTaskJSONObject() throws Exception{
        latestTasksJSONObject = taskApiService.getTasks();
        tasksJSONObject = latestTasksJSONObject;
    }

    public JSONObject getTasks() {
        return tasksJSONObject;
    }
}
