package UtilTool;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import Message.Message;
import Task.Task;
import Task.TaskBuilder;
import User.UserBuilder;
import User.User;

public class JsonParse {

    private static final String LOG_TAG = JsonParse.class.getSimpleName();

    public static User parse_a_User(JSONObject aJSONObj) throws Exception {
        int id =  aJSONObj.optInt("id");

        String name =  aJSONObj.optString("name");

        String firebase_uid = aJSONObj.optString("firebase_uid");

        int point = aJSONObj.optInt("point");

        return UserBuilder.anUser(id)
                .withName(name)
                .withFirebaseUID(firebase_uid)
                .withPoint(point)
                .build();
    }

    public static User parse_a_User(JSONObject aJSONObj, String idKey) throws Exception {
        int id = Integer.parseInt(idKey);

        String name =  aJSONObj.optString("name");

        String firebase_uid = aJSONObj.optString("firebase_uid");

        int point = aJSONObj.optInt("point");

        return UserBuilder.anUser(id)
                .withName(name)
                .withFirebaseUID(firebase_uid)
                .withPoint(point)
                .build();
    }

    public static ArrayList<User> parseUsersFromJson(JSONObject usersJSONObject) {
        ArrayList<User> userList = new ArrayList<>();
        Iterator<String> userKeys = usersJSONObject.keys();

        while (userKeys.hasNext()) {
            try {
                String key = userKeys.next();
                JSONObject aJSONObj = usersJSONObject.getJSONObject(key);
                User user = parse_a_User(aJSONObj, key); //還不確定如果這裡丟出例外 會發生什麼事
                userList.add(user);
            } catch (Exception e) {
                Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
            }
        }

        return userList;
    }

    public static ArrayList<User> parseUsersFromJsonArray(JSONArray usersJSONArray) {
        ArrayList<User> userList = new ArrayList<>();

        for(int i = 0; i < usersJSONArray.length() ; i++) {
            try {
                JSONObject a_user = usersJSONArray.getJSONObject(i);
                User user = parse_a_User(a_user);
                userList.add(user);
            } catch (Exception e) {
                Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
            }
        }
        return userList;
    }

    public static ArrayList<Message> parseMessagesFromJson(JSONArray jsonArray) {
        ArrayList<Message> messageList = new ArrayList<>();

        for(int i = 0; i < jsonArray.length() ; i++) {

            try {
                JSONObject a_message = jsonArray.getJSONObject(i);
                Message message = parse_a_Message(a_message);
                messageList.add(message);
            } catch (Exception e) {
                Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
            }
        }

        return messageList;
    }

    private static Message parse_a_Message(JSONObject aJSONMessage) throws Exception {

        String content =  aJSONMessage.getString("content");

        int userID = aJSONMessage.getInt("userID");

        int receiverID = aJSONMessage.getInt("receiverID");

        LocalDateTime postTime = TransitTime.transitTimeStampFromGetAPI(aJSONMessage.getString("postTime"));

        return new Message(content, userID, receiverID, postTime);
    }


    public static ArrayList<Task> parseTasksFromJson(JSONArray jsonArray) {
        ArrayList<Task> taskList = new ArrayList<>();

        for(int i = 0; i < jsonArray.length() ; i++) {

            try {
                JSONObject a_task = jsonArray.getJSONObject(i);
                Task task = parse_a_Task(a_task);
                taskList.add(task);
            } catch (Exception e) {
                Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
            }
        }

        return taskList;
    }

    private static Task parse_a_Task(JSONObject aJSONTask) throws Exception {

        int taskId = aJSONTask.optInt("TaskID");

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

}
