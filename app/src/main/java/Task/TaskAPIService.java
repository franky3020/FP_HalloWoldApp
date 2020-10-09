package Task;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

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

    // 要改成傳入Task 與 送出成功會跳回畫面
    public boolean post(final String taskName, final String message, final String salary , final String postTime
            , final String taskType, final String taskAddress, final String taskCity) throws IOException { // 這應該改成傳入Task

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create("", JSON);

        String API_version = "ms-provider-release-1-0-1";
        String base_URL = "http://140.134.26.71:46557/" + API_version + "/tasks?";
        String taskNameParameter = "TaskName=" + taskName;
        String messageParameter = "Message=" + message;
        String startPostTimeParameter = "StartPostTime=" + postTime;
        String endPostTimeParameter = "EndPostTime=" + postTime;
        String salaryParameter = "Salary=" + salary;
        String taskTypeParameter = "TypeName=" + taskType;
        String releaseUserIDParameter = "ReleaseUserID=" + 1;
        String taskAddressParameter = "TaskAddress=" + taskAddress;
        String taskCityParameter = "TaskCity=" + taskCity;


        Request request = new Request.Builder()
                .url(base_URL + taskNameParameter + "&" + messageParameter + "&" + startPostTimeParameter +
                        "&" + endPostTimeParameter + "&" + salaryParameter + "&" + taskTypeParameter +
                        "&" + releaseUserIDParameter + "&" + taskAddressParameter + "&" + taskCityParameter)
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

        String API_version = "ms-provider-release-1-0-1";
        String apiUrl = "http://140.134.26.71:46557/" + API_version + "/tasks";

        Request request = new Request.Builder()
                .url(apiUrl)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject tasksJSONObject = new JSONObject( Objects.requireNonNull(response.body()).string() );

        ArrayList<Task> taskList = new ArrayList<>();

        // 因為API 拿到的字串是這種格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        Iterator<String> taskKeys = tasksJSONObject.keys();
        while (taskKeys.hasNext()) {
            String key = taskKeys.next();
            JSONObject aJSONTask = tasksJSONObject.getJSONObject(key);

            int taskId = Integer.parseInt(key);

            String taskName = aJSONTask.getString("TaskName");

            /////////////////// 轉換與處理 startPostTime  /////////////////////////////////
            String startPostTimeString = aJSONTask.getString("StartPostTime");
            Date startPostDate = simpleDateFormat.parse(startPostTimeString);
            Timestamp startPostTime = new Timestamp(startPostDate.getTime());
            ////////////////////////////////////////////////////////////////////////////

            int salary = aJSONTask.getInt("Salary");

            String typeName = aJSONTask.getString("TypeName");

            int releaseUserID = aJSONTask.getInt("ReleaseUserID");

//            Timestamp releaseTime = startPostTime; // 未處理 ReleaseTime 是null 時的情況, 如果有一個API找錯, 就會全部失效 這要修正
            /////////////////// 轉換與處理 releaseTime  /////////////////////////////////
            String releaseTimeString = aJSONTask.getString("ReleaseTime");
            Date releaseDate = simpleDateFormat.parse(releaseTimeString);
            Timestamp releaseTime = new Timestamp(releaseDate.getTime());
            ////////////////////////////////////////////////////////////////////////////

            Task task = new Task(taskId, taskName, startPostTime, salary, typeName, releaseUserID, releaseTime); // 時間未完成
            taskList.add(task);
        }
        return taskList;
    }

}
