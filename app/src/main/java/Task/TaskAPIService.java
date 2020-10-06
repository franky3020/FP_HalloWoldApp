package Task;

import android.icu.text.SimpleDateFormat; //時間未完成
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

import User.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.sql.Timestamp;

public class TaskAPIService {

    public void postUseThread(final String taskName, final String message, final String salary , final String postTime
                        , final String taskType, final String taskAddress, final String taskCity) { // 這應該改成傳入Task
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    post(taskName, message, salary , postTime, taskType, taskAddress, taskCity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ).start();
    }


    public boolean post(final String taskName, final String message, final String salary , final String postTime
            , final String taskType, final String taskAddress, final String taskCity) throws IOException { // 這應該改成傳入Task

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create("", JSON);

        String API_version = "ms-provider-develop";
        String base_URL = "http://140.134.26.71:46557/" + API_version + "/tasks?";
        String taskNameParameter = "TaskName=" + taskName;
        String messageParameter = "Message=" + message;
        String startPostTimeParameter = "StartPostTime=" + postTime;
        String endPostTimeParameter = "EndPostTime=" + postTime;
        String salaryParameter = "Salary=" + salary;
        String taskTypeParameter = "TypeName=" + taskType;
        String taskAddressParameter = "TaskAddress=" + taskAddress;
        String taskCityParameter = "TaskCity=" + taskCity;


        Request request = new Request.Builder()
                .url(base_URL + taskNameParameter + "&" + messageParameter + "&" + startPostTimeParameter +
                        "&" + endPostTimeParameter + "&" + salaryParameter + "&" + taskTypeParameter +
                        "&" + taskAddressParameter + "&" + taskCityParameter)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            response.close();
            return true;
        } else {
            response.close();
            return false;
        }
    }

    public ArrayList<Task> getTasks() throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        String API_version = "ms-provider-develop";
        String apiUrl = "http://140.134.26.71:46557/" + API_version + "/tasks";

        Request request = new Request.Builder()
                .url(apiUrl)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject tasksJSONObject = new JSONObject( Objects.requireNonNull(response.body()).string() );

        ArrayList<Task> taskList = new ArrayList<>();

        Iterator<String> taskKeys = tasksJSONObject.keys();
        while (taskKeys.hasNext()) {
            String key = taskKeys.next();


            JSONObject aJSONTask = tasksJSONObject.getJSONObject(key);

            int taskId = Integer.parseInt(key);
            String taskName = aJSONTask.getString("TaskName");

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 應該要用這個轉換

            Timestamp startPostTime = new Timestamp(new Date().getTime()); // 先不處理
            int salary = aJSONTask.getInt("Salary");
            String typeName = aJSONTask.getString("TypeName");
            int releaseUserID = aJSONTask.getInt("ReleaseUserID");
            Timestamp releaseTime = new Timestamp(new Date().getTime()); // 先不處理

            Task task = new Task(taskId, taskName, startPostTime, salary, typeName, releaseUserID, releaseTime); // 時間未完成
            taskList.add(task);
        }
        return taskList;
    }

}
