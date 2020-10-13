package Task;

import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;

public class GetTasksObserved extends Observable {
    private static final String LOG_TAG = GetTasksObserved.class.getSimpleName();

    private static GetTasksObserved instance = new GetTasksObserved();

    ArrayList<Task> latestTasksList = new ArrayList<>();
    ArrayList<Task> tasksList = new ArrayList<>();

    TaskAPIService taskApiService = new TaskAPIService();

    private GetTasksObserved() {
        getTasksThreadLoop();
    }

    public static GetTasksObserved getInstance() {
        return instance;
    }

    public void getTasksThreadLoop() {
        Thread getTasksAPIThread = new Thread() {
            public void run() {

                while(!Thread.currentThread().isInterrupted()) {

                    if(countObservers() > 0) {
                        // 送API請求
                        Log.d(LOG_TAG, "start send API");
                        try {
                            getTaskAndUpdateTaskList();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            setChanged();// 一定先有這個 notifyObservers() 才會有效
                            notifyObservers();
                        }
                    }


                    // 等待幾秒後再重新送請求
                    try {
                        Log.d(LOG_TAG, "start sleep");
                        sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
                    }
                }

            }
        };
        getTasksAPIThread.start();
    }

    private void getTaskAndUpdateTaskList() throws Exception {
        latestTasksList = taskApiService.getTasks();
        tasksList = latestTasksList;
    }

    public ArrayList<Task> getTasks() {
        return tasksList;
    }

}
