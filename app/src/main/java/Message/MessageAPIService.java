package Message;

import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import Message.Message;
import Task.Task;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageAPIService {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //String API_version = "ms-provider-develop";
    public static final String API_version = "ms-provider-develop";

    public static final String base_URL = "http://140.134.26.71:46557/" + API_version + "/message";

    public ArrayList<Message> getMessages() throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(base_URL)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject tasksJSONObject = new JSONObject( Objects.requireNonNull(response.body()).string() );

        ArrayList<Message> messageList = new ArrayList<>();


        Iterator<String> messageKeys = tasksJSONObject.keys();
        while (messageKeys.hasNext()) {
            String key = messageKeys.next();
            JSONObject aJSONMessage = tasksJSONObject.getJSONObject(key);

            int messageId = Integer.parseInt(key);
            String content = aJSONMessage.getString("content");
            int receiverID = aJSONMessage.getInt("receiverId");
            int userID = aJSONMessage.getInt("userID");
            int taskID = aJSONMessage.getInt("taskID");
            LocalDateTime postTime = transitTimeStampFromGetAPI(aJSONMessage.getString("postTime"));

            Message message = new Message(messageId, content, userID, receiverID, taskID, postTime);
            messageList.add(message);
        }
        return messageList;
    }


    public void post(Message message, Callback callback) throws Exception {
        JSONObject jsonEntity = new JSONObject();
        jsonEntity.put("content", message.getContent());
        jsonEntity.put("userID", message.getUserID());
        jsonEntity.put("receiverId", message.getReceiverID());
        jsonEntity.put("taskID", message.getTaskID());
        jsonEntity.put("postTime", transitLocalDateTimeToStringForPostAPI(message.getPostTime()));

        RequestBody requestBody = RequestBody.create(String.valueOf(jsonEntity), JSON);

        Request request = new Request.Builder().url(base_URL).post(requestBody).build();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        client.newCall(request).enqueue(callback);
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
