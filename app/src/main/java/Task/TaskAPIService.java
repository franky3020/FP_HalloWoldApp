package Task;

import org.json.JSONObject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskAPIService {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //String API_version = "ms-provider-develop";
    public static final String API_version = "ms-provider-develop";

    public static final String base_URL = "http://140.134.26.71:46557/" + API_version + "/tasks?";


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

    public ArrayList<Task> getTasks() throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(base_URL)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject tasksJSONObject = new JSONObject( Objects.requireNonNull(response.body()).string() );

        ArrayList<Task> taskList = new ArrayList<>();

        // 因為API 拿到的字串是這種格式


        Iterator<String> taskKeys = tasksJSONObject.keys();
        while (taskKeys.hasNext()) {
            String key = taskKeys.next();
            JSONObject aJSONTask = tasksJSONObject.getJSONObject(key);

            int taskId = Integer.parseInt(key);

            String taskName = aJSONTask.getString("TaskName");

            String taskMessage = aJSONTask.getString("Message");

            LocalDateTime startPostTime = transitTimeStampFromGetAPI(aJSONTask.getString("StartPostTime"));

            int salary = aJSONTask.getInt("Salary");

            String typeName = aJSONTask.getString("TypeName");

            int releaseUserID = aJSONTask.getInt("ReleaseUserID");

            LocalDateTime releaseTime = transitTimeStampFromGetAPI(aJSONTask.getString("ReleaseTime"));

            Task task = new Task(taskId, taskName,taskMessage, startPostTime, salary, typeName, releaseUserID, releaseTime);
            taskList.add(task);
        }
        return taskList;
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

}
