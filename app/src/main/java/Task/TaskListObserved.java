package Task;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.my_first_application.R;
import com.example.my_first_application.ShowTaskAdapter;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskListObserved extends Observable implements Runnable{
    final String TAG = "franky-test";
    JSONObject allTask;

    TaskApiService taskApiService = new TaskApiService();
    ArrayList<ShowTask> taskList;

    public TaskListObserved(ArrayList<ShowTask> taskList) {
        this.taskList = taskList;
    }

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

    public int updateAllTasks() {

        ArrayList<ShowTask> tmpTaskList = new ArrayList<ShowTask>();

        Iterator taskKeys = allTask.keys();
        while (taskKeys.hasNext()){
            String key = (String) taskKeys.next();
            String taskName = "";
            String taskAddress = "";
            try {
                JSONObject aTask = allTask.getJSONObject(key);

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
        return allTask.length();
    }

}
