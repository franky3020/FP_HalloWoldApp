package Task;

import com.example.my_first_application.R;

import java.util.ArrayList;
import java.util.Observable;

public class GetTasksObserved extends Observable{

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
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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

    public ArrayList<ShowTask> getShowTasks() {

        ArrayList<ShowTask> tmpShowTaskList = new ArrayList<>();
        for (Task task:tasksList) {
            ShowTask showTask = new ShowTask(R.drawable.ic_user_show_task, task.getName(), "買便當(未完成)", "未完成", task.getStartData().toString(), "上午 11:00(未完成)");
            tmpShowTaskList.add(showTask);
        }
        return tmpShowTaskList;
    }
}
