package Task;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Observable;

public class GetTasksObserved extends Observable implements Runnable{
    JSONObject allTask;
    TaskApiService taskApiService = new TaskApiService();

    @Override
    public void run() {
        try {
            allTask = taskApiService.getTasks();
            setChanged(); // 一定先有這個 notifyObservers() 才會有效
            notifyObservers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getTasks() {
        return allTask;
    }
}
