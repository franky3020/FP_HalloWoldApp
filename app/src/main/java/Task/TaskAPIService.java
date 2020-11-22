package Task;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import User.User;
import User.UserAPIService;
import UtilTool.JsonParse;
import UtilTool.TransitTime;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskAPIService {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String LOG_TAG = TaskAPIService.class.getSimpleName();

    public static final String API_version = "ms-provider-release-200";

    public static final String base_URL = "http://140.134.26.65:46557/" + API_version + "/tasks";

    public interface GetAPIListener<T> {
        void onResponseOK(T t);
        void onFailure();
    }


    public void post(Task task, Callback callback) throws Exception {
        JSONObject jsonEntity = new JSONObject();
        jsonEntity.put("name", task.getTaskName());
        jsonEntity.put("message", task.getMessage());
        jsonEntity.put("startPostTime", TransitTime.transitLocalDateTimeToString(task.getStartPostTime()));
        jsonEntity.put("endPostTime", TransitTime.transitLocalDateTimeToString(task.getEndPostTime()));
        jsonEntity.put("salary", task.getSalary());
        jsonEntity.put("releaseUserID", task.getReleaseUserID());
        jsonEntity.put("releaseTime", TransitTime.transitLocalDateTimeToString(task.getReleaseTime()));
        jsonEntity.put("receiveUserID", task.getReceiveUserID());
        jsonEntity.put("taskAddress", task.getTaskAddress());

        //參考 https://www.jianshu.com/p/1133389c1f75
        // 與  https://stackoverflow.com/questions/57100451/okhttp3-requestbody-createcontenttype-content-deprecated
        RequestBody requestBody = RequestBody.create(String.valueOf(jsonEntity), JSON);

        Request request = new Request.Builder().url(base_URL).post(requestBody).build();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        client.newCall(request).enqueue(callback);

    }

    public void getTasksV3(final GetAPIListener< ArrayList<Task> > getAPIListener) {

        Thread getTaskThread = new Thread() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL)
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        JSONArray tasksJSONArray = new JSONArray( Objects.requireNonNull(response.body()).string() );
                        ArrayList<Task> taskList = JsonParse.parseTasksFromJson(tasksJSONArray);
                        getAPIListener.onResponseOK(taskList);
                    } else {
                        getAPIListener.onFailure();
                    }
                    response.close();

                } catch (Exception e) {
                    Log.d(LOG_TAG, e.getMessage());
                    getAPIListener.onFailure();
                }
            }
        };
        getTaskThread.start();
    }

    public void getCanRequestTasks(final int userId, final GetAPIListener< ArrayList<Task> > getAPIListener) {

        Thread getTaskThread = new Thread() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL + "/" + "CanRequest" + "/" + userId)
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        JSONArray tasksJSONArray = new JSONArray( Objects.requireNonNull(response.body()).string() );
                        ArrayList<Task> taskList = JsonParse.parseTasksFromJson(tasksJSONArray);
                        getAPIListener.onResponseOK(taskList);
                    } else {
                        getAPIListener.onFailure();
                    }
                    response.close();

                } catch (Exception e) {
                    Log.d(LOG_TAG, e.getMessage());
                    getAPIListener.onFailure();
                }
            }
        };
        getTaskThread.start();
    }


    public void getReleaseUserTasks(final int userId, final GetAPIListener< ArrayList<Task> > getAPIListener) {

        Thread getTaskThread = new Thread() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL + "/" + "ReleaseUser" + "/" + userId)
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        JSONArray tasksJSONArray = new JSONArray( Objects.requireNonNull(response.body()).string() );
                        ArrayList<Task> taskList = JsonParse.parseTasksFromJson(tasksJSONArray);
                        getAPIListener.onResponseOK(taskList);
                    } else {
                        getAPIListener.onFailure();
                    }
                    response.close();

                } catch (Exception e) {
                    Log.d(LOG_TAG, e.getMessage());
                    getAPIListener.onFailure();
                }
            }
        };
        getTaskThread.start();
    }

    public void getUserRequestTasks(final int userId, final GetAPIListener< ArrayList<Task> > getAPIListener) {
        getUserSpecifyTask("UserRequestTasks", userId, getAPIListener);
    }

    public void getUserReceiveTasks(final int userId, final GetAPIListener< ArrayList<Task> > getAPIListener) {
        getUserSpecifyTask("UserReceiveTasks", userId, getAPIListener);
    }

    public void getUserEndTasks(final int userId, final GetAPIListener< ArrayList<Task> > getAPIListener) {
        getUserSpecifyTask("UserEndTasks", userId, getAPIListener);
    }

    private void getUserSpecifyTask(final String specifyClassification, final int userId,
                                    final GetAPIListener< ArrayList<Task> > getAPIListener) {
        Thread getTaskThread = new Thread() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL + "/" + specifyClassification + "/" + userId)
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        JSONArray tasksJSONArray = new JSONArray( Objects.requireNonNull(response.body()).string() );
                        ArrayList<Task> taskList = JsonParse.parseTasksFromJson(tasksJSONArray);
                        getAPIListener.onResponseOK(taskList);
                    } else {
                        getAPIListener.onFailure();
                    }
                    response.close();

                } catch (Exception e) {
                    Log.d(LOG_TAG, e.getMessage());
                    getAPIListener.onFailure();
                }
            }
        };
        getTaskThread.start();
    }



    public void getATask(final int taskID, final GetAPIListener<Task> getAPIListener) {

        Thread getTaskThread = new Thread() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL + "/" + taskID)
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        JSONObject tasksJSONObject = new JSONObject( Objects.requireNonNull(response.body()).string() );
                        String key = String.valueOf(taskID);
                        JSONObject aJSONTask = tasksJSONObject.getJSONObject(key);
                        Task task = parse_a_Task(aJSONTask, key);
                        getAPIListener.onResponseOK(task);
                    } else {
                        getAPIListener.onFailure();
                    }
                    response.close();

                } catch (Exception e) {
                    Log.d(LOG_TAG, e.getMessage());
                    getAPIListener.onFailure();
                }
            }
        };
        getTaskThread.start();
    }


    public void getTaskRequestUsers(final int taskId, final GetAPIListener< ArrayList<User> > getAPIListener) {

        Thread getTaskThread = new Thread() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL + "/" + taskId + "/" +  "RequestUsers")
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        ArrayList<User> users = new ArrayList<>();
                        JSONArray usersJSONArray = new JSONArray( Objects.requireNonNull(response.body()).string() );
                        users = JsonParse.parseUsersFromJsonArray(usersJSONArray);
                        getAPIListener.onResponseOK(users);
                    } else {
                        getAPIListener.onFailure();
                    }
                    response.close();

                } catch (Exception e) {
                    Log.d(LOG_TAG, e.getMessage());
                    getAPIListener.onFailure();
                }
            }
        };
        getTaskThread.start();
    }


    // Todo 拿單個TASK 的 API 還沒修改
    private Task parse_a_Task(JSONObject aJSONTask, String taskKey) throws Exception {

        int taskId = Integer.parseInt(taskKey);

        String taskName = aJSONTask.optString("TaskName");

        String taskMessage = aJSONTask.optString("Message");

        LocalDateTime startPostTime = TransitTime.transitTimeStampFromGetAPI(aJSONTask.optString("StartPostTime"));

        LocalDateTime endPostTime = TransitTime.transitTimeStampFromGetAPI(aJSONTask.optString("EndPostTime"));

        int salary = aJSONTask.optInt("Salary");

        String typeName = aJSONTask.optString("TypeName");

        int releaseUserID = aJSONTask.optInt("ReleaseUserID");

        LocalDateTime releaseTime = TransitTime.transitTimeStampFromGetAPI(aJSONTask.optString("ReleaseTime"));

        int receiveUserID = aJSONTask.optInt("ReceiveUserID");

        String taskAddress = aJSONTask.optString("TaskAddress");

        Task task = TaskBuilder.aTask(taskId, salary, releaseUserID)
                .withTaskName(taskName)
                .withMessage(taskMessage)
                .withStartPostTime(startPostTime)
                .withEndPostTime(endPostTime)
                .withTypeName(typeName)
                .withReleaseTime(releaseTime)
                .withReceiveUserID(receiveUserID)
                .withTaskAddress(taskAddress)
                .build();

        return task;
    }


    public void deleteTask(int taskId, Callback callback) {
        Request request = new Request.Builder()
                .url(base_URL + "/" + taskId)
                .method("DELETE", null)
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        client.newCall(request).enqueue(callback);
    }

    public void updateTaskState(int taskId, TaskStateEnum taskStateEnum, Callback callback) {
        try {
            JSONObject jsonEntity = new JSONObject();
            jsonEntity.put("taskState", taskStateEnum);
            RequestBody body = RequestBody.create(String.valueOf(jsonEntity), JSON);

            Request request = new Request.Builder()
                    .url(base_URL + "/" + taskId + "/" + "state")
                    .post(body)
                    .build();
            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(callback);

        } catch (JSONException e) {
            Log.d(LOG_TAG, e.getMessage());
        }
    }


    public void getTaskState(final int taskId, final GetAPIListener<TaskState> getAPIListener) {

        Thread getTaskStateThread = new Thread() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL + "/" + taskId + "/" +"state")
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        JSONObject aJSONTaskState = new JSONObject( Objects.requireNonNull(response.body()).string() );
                        TaskStateEnum taskStateEnum = TaskStateEnum.valueOf(aJSONTaskState.optString("taskState"));
                        LocalDateTime stepTime = TransitTime.transitTimeStampFromGetAPI(aJSONTaskState.optString("stepTime"));

                        TaskState taskState = new TaskState(taskStateEnum, stepTime);
                        getAPIListener.onResponseOK(taskState);
                    } else {
                        getAPIListener.onFailure();
                    }
                    response.close();

                } catch (Exception e) {
                    Log.d(LOG_TAG, e.getMessage());
                    getAPIListener.onFailure();
                }
            }
        };
        getTaskStateThread.start();
    }

    public void addTaskRequestUser(int taskId, int userId, Callback callback) {
        Request request = new Request.Builder()
                .url(base_URL + "/" + taskId + "/" + "RequestUsers" + "/" + userId)
                .method("POST", RequestBody.create("", JSON))
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);
    }

    public void deleteTaskRequestUser(int taskId, int userId, Callback callback) {
        Request request = new Request.Builder()
                .url(base_URL + "/" + taskId + "/" + "RequestUsers" + "/" + userId)
                .method("DELETE", null)
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        client.newCall(request).enqueue(callback);
    }

    public void setTaskReceiveUser(int taskId, int userId, Callback callback) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("op", "replace");
            jsonObject.put("path", "/receiveUserID");
            jsonObject.put("value", userId);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObject);

            MediaType patchJSON = MediaType.parse("application/json-patch+json");
            RequestBody body = RequestBody.create(String.valueOf(jsonArray), patchJSON);
            Log.d(LOG_TAG, jsonArray.toString());

            Request request = new Request.Builder()
                    .url(base_URL + "/" + taskId)
                    .patch(body)
                    .build();
            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(callback);

        } catch (JSONException e) {
            Log.d(LOG_TAG, e.getMessage());
        }
    }


    public void checkUserAlreadyRequest(final int taskId, final int userId, final GetAPIListener<Boolean> getAPIListener) {

        Thread getTaskStateThread = new Thread() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL + "/" + taskId + "/" +"checkUserAlreadyRequest" + "/" + userId)
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        String responseStr = Objects.requireNonNull(response.body()).string();
                        if (responseStr.equals("true")) {
                            getAPIListener.onResponseOK(true);
                        } else { // responseStr.equals("false")
                            getAPIListener.onResponseOK(false);
                        }

                    } else {
                        getAPIListener.onFailure();
                    }
                    response.close();

                } catch (Exception e) {
                    Log.d(LOG_TAG, e.getMessage());
                    getAPIListener.onFailure();
                }
            }
        };
        getTaskStateThread.start();
    }


}
