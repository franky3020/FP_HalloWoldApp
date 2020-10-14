package Task;

import android.util.Log;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskAPIService {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String LOG_TAG = TaskAPIService.class.getSimpleName();

    //String API_version = "ms-provider-develop";
    public static final String API_version = "ms-provider-develop";

    public static final String base_URL = "http://140.134.26.71:46557/" + API_version + "/tasks";


    public void post(Task task, Callback callback) throws Exception {
        JSONObject jsonEntity = new JSONObject();
        jsonEntity.put("taskName", task.getTaskName());
        jsonEntity.put("message", task.getMessage());
        jsonEntity.put("startPostTime", transitLocalDateTimeToStringForPostAPI(task.getStartPostTime()));
        jsonEntity.put("endPostTime", transitLocalDateTimeToStringForPostAPI(task.getEndPostTime()));
        jsonEntity.put("salary", task.getSalary());
        jsonEntity.put("typeName", task.getTypeName());
        jsonEntity.put("releaseUserID", task.getReleaseUserID());
        jsonEntity.put("releaseTime", transitLocalDateTimeToStringForPostAPI(task.getReleaseTime()));
        jsonEntity.put("receiveUserID", task.getReceiveUserID());
        jsonEntity.put("receiveTime", transitLocalDateTimeToStringForPostAPI(task.getReceiveTime()));
        jsonEntity.put("taskAddress", task.getTaskAddress());
        jsonEntity.put("taskCity", task.getTaskCity());

        //參考 https://www.jianshu.com/p/1133389c1f75
        // 與  https://stackoverflow.com/questions/57100451/okhttp3-requestbody-createcontenttype-content-deprecated
        RequestBody requestBody = RequestBody.create(String.valueOf(jsonEntity), JSON);

        Request request = new Request.Builder().url(base_URL).post(requestBody).build();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        client.newCall(request).enqueue(callback);

    }


    public interface TaskListener {
        void onResponseOK(ArrayList<Task> tasks);
        void onFailure();
    }

    public void getTasksV3(final TaskListener taskListener) {

        Thread getTaskThread = new Thread() {
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL)
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        JSONObject tasksJSONObject = new JSONObject( Objects.requireNonNull(response.body()).string() );
                        ArrayList<Task> taskList = parseTasksFromJson(tasksJSONObject);
                        taskListener.onResponseOK(taskList);
                    } else {
                        taskListener.onFailure();
                    }

                } catch (Exception e) {
                    Log.d(LOG_TAG, e.getMessage());
                    taskListener.onFailure();
                }
            }
        };
        getTaskThread.start();
    }



    public ArrayList<Task> parseTasksFromJson(JSONObject tasksJSONObject) {
        ArrayList<Task> taskList = new ArrayList<>();
        Iterator<String> taskKeys = tasksJSONObject.keys();

        while (taskKeys.hasNext()) {
            try {
                String key = taskKeys.next();
                JSONObject aJSONTask = tasksJSONObject.getJSONObject(key);
                Task task = parse_a_Task(aJSONTask, key); //還不確定如果這裡丟出例外 會發生什麼事
                taskList.add(task);
            } catch (Exception e) {
                Log.d(LOG_TAG, e.getMessage());
            }
        }

        return taskList;
    }

    private Task parse_a_Task(JSONObject aJSONTask, String taskKey) throws Exception {

        int taskId = Integer.parseInt(taskKey);

        String taskName = aJSONTask.getString("TaskName");

        String taskMessage = aJSONTask.getString("Message");

        LocalDateTime startPostTime = transitTimeStampFromGetAPI(aJSONTask.getString("StartPostTime"));

        int salary = aJSONTask.getInt("Salary");

        String typeName = aJSONTask.getString("TypeName");

        int releaseUserID = aJSONTask.getInt("ReleaseUserID");

        LocalDateTime releaseTime = transitTimeStampFromGetAPI(aJSONTask.getString("ReleaseTime"));

        return new Task(taskId, taskName,taskMessage, startPostTime, salary, typeName, releaseUserID, releaseTime);
    }

    private LocalDateTime transitTimeStampFromGetAPI(String timeStampString) { // 如果傳入null 則會傳出 null
        if(timeStampString != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            return LocalDateTime.parse(timeStampString, dateTimeFormatter);
        } else {
            return null;
        }
    }

    private String transitLocalDateTimeToStringForPostAPI(LocalDateTime localDateTime) { // 如果傳入null 則會傳出 null
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(localDateTime != null) {
            return dateTimeFormatter.format(localDateTime);
        } else {
            return null;
        }
    }

    public void deleteTask(int taskId, Callback callback) {
        Request request = new Request.Builder()
                .url(base_URL + "/" + taskId)
                .method("DELETE", null)
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        client.newCall(request).enqueue(callback);
    }

}
