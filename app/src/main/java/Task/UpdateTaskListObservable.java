package Task;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import com.example.my_first_application.R;
import com.example.my_first_application.ShowTaskActivity;

public class UpdateTaskListObservable implements Observer {

    private static ArrayList<ShowTask> taskList = new ArrayList<ShowTask>();
    private ShowTaskActivity activity;

    public UpdateTaskListObservable(ShowTaskActivity activity) {
        this.activity = activity;
    }

    @Override
    public void update(Observable o, Object arg) {
        GetTasksObserved getTasksObserved = (GetTasksObserved) o;

        ArrayList<ShowTask> tmpTaskList = new ArrayList<ShowTask>();

        JSONObject tasksJSON = getTasksObserved.getTasks();
        Iterator<String> taskKeys = tasksJSON.keys();

        while (taskKeys.hasNext()){
            String key = taskKeys.next();
            String taskName = "";
            String taskAddress = "";
            try {
                JSONObject aTask = tasksJSON.getJSONObject(key);

                taskName = aTask.getString("TaskName");
                taskAddress = aTask.getString("TaskAddress");
            } catch (Exception e) {
                e.printStackTrace();
            }

            ShowTask user1 = new ShowTask(R.drawable.ic_user_show_task, taskName, "買便當", taskAddress, "2020/9/11", "上午 11:00");
            tmpTaskList.add(user1);
        }
        taskList.clear();
        taskList.addAll(tmpTaskList);

        activity.showTaskUI_Update();
        activity.runGetTaskAPI_Runnable(200);
    }

    public static ArrayList<ShowTask> getTaskList() {
        return taskList;
    }
}
