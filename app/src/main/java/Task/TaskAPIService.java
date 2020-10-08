package Task;

import org.json.JSONObject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskAPIService {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

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

        String API_version = "ms-provider-develop";
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

    public boolean post(Task task) throws Exception {
//        String API_version = "ms-provider-develop";
        String API_version = "ms-provider-test-franky-fix-message-108"; //這應該改成統一的API
        String base_URL = "http://140.134.26.71:46557/" + API_version + "/tasks?";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String taskNameParameter = task.getTaskName();
        String messageParameter = task.getMessage();

        String startPostTimeParameter = dateTimeFormatter.format(task.getStartPostTime());
        String endPostTimeParameter = dateTimeFormatter.format(task.getEndPostTime());
        String salaryParameter = String.valueOf(task.getSalary());
        String taskTypeParameter = task.getTypeName();

        String releaseUserIDParameter = String.valueOf(task.getReleaseUserID());
        String releaseTimeParameter = dateTimeFormatter.format(task.getReleaseTime());

        String receiveUserIDParameter = String.valueOf(task.getReceiveUserID());
        String receiveTimeParameter = dateTimeFormatter.format(task.getReceiveTime());

        String taskAddressParameter = task.getTaskAddress();
        String taskCityParameter = String.valueOf(task.getTaskCity());

        JSONObject jsonEntity = new JSONObject();
        jsonEntity.put("taskName", task.getTaskName());
        jsonEntity.put("message", task.getMessage());
        jsonEntity.put("startPostTime", startPostTimeParameter);
        jsonEntity.put("endPostTime", endPostTimeParameter);
        jsonEntity.put("salary", salaryParameter);
        jsonEntity.put("typeName", taskTypeParameter);
        jsonEntity.put("releaseUserID", task.getReleaseUserID());
        jsonEntity.put("releaseTime", releaseTimeParameter);
        jsonEntity.put("receiveUserID", task.getReceiveUserID());
        jsonEntity.put("receiveTime", receiveTimeParameter);
        jsonEntity.put("taskAddress", taskAddressParameter);
        jsonEntity.put("taskCity", taskCityParameter);

        //參考 https://www.jianshu.com/p/1133389c1f75
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonEntity)); // 被棄用了
//
//        RequestBody formBody = new FormBody.Builder() // 沒資料的應該傳送null出去
//                .add("taskName", taskNameParameter)
//                .add("message", messageParameter)
//                .add("startPostTime", startPostTimeParameter)
//                .add("endPostTime", endPostTimeParameter)
//                .add("salary", salaryParameter)
//                .add("typeName", taskTypeParameter)
//                .add("releaseUserID", releaseUserIDParameter)
//                .add("releaseTime", releaseTimeParameter)
//                .add("receiveUserID", receiveUserIDParameter)
//                .add("receiveTime", receiveTimeParameter)
//                .add("taskAddress", taskAddressParameter)
//                .add("taskCity", taskCityParameter)
//                .build();

        Request request = new Request.Builder().url(base_URL).post(requestBody).build();

        OkHttpClient client = new OkHttpClient().newBuilder()
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

//        String API_version = "ms-provider-develop";
        String API_version = "ms-provider-test-franky-fix-message-108";
        String apiUrl = "http://140.134.26.71:46557/" + API_version + "/tasks";

        Request request = new Request.Builder()
                .url(apiUrl)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject tasksJSONObject = new JSONObject( Objects.requireNonNull(response.body()).string() );

        ArrayList<Task> taskList = new ArrayList<>();

        // 因為API 拿到的字串是這種格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        Iterator<String> taskKeys = tasksJSONObject.keys();
        while (taskKeys.hasNext()) {
            String key = taskKeys.next();
            JSONObject aJSONTask = tasksJSONObject.getJSONObject(key);

            int taskId = Integer.parseInt(key);

            String taskName = aJSONTask.getString("TaskName");

            String taskMessage = aJSONTask.getString("Message");

            /////////////////// 轉換與處理 startPostTime  /////////////////////////////////
            String startPostTimeString = aJSONTask.getString("StartPostTime");
            LocalDateTime startPostTime = LocalDateTime.parse(startPostTimeString, dateTimeFormatter);
            ////////////////////////////////////////////////////////////////////////////

            int salary = aJSONTask.getInt("Salary");

            String typeName = aJSONTask.getString("TypeName");

            int releaseUserID = aJSONTask.getInt("ReleaseUserID");

            /////////////////// 轉換與處理 releaseTime  /////////////////////////////////
            String releaseTimeString = aJSONTask.getString("ReleaseTime");
            LocalDateTime releaseTime = LocalDateTime.parse(releaseTimeString, dateTimeFormatter);
            ////////////////////////////////////////////////////////////////////////////

            Task task = new Task(taskId, taskName,taskMessage, startPostTime, salary, typeName, releaseUserID, releaseTime);
            taskList.add(task);
        }
        return taskList;
    }

}
