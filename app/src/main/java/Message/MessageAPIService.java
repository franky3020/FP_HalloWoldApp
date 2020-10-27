package Message;

import android.util.Log;

import com.example.my_first_application.ChatActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import Task.Task;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageAPIService {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String LOG_TAG = ChatActivity.class.getSimpleName();

    //String API_version = "ms-provider-develop";
    public static final String API_version = "ms-provider-test-release150";

    public static final String base_URL = "http://140.134.26.71:46557/" + API_version + "/message";

    public interface GetAPIListener<T> {
        void onResponseOK(T t);
        void onFailure();
    }

    public void getUserMessages(final int userId, final GetAPIListener< ArrayList<Message> > getAPIListener) {

        Thread getMessageThread = new Thread() {
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL + "/" + "conversationByUserID" + "/" + userId)
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        JSONObject tasksJSONObject = new JSONObject( Objects.requireNonNull(response.body()).string() );
                        ArrayList<Message> messageList = parseMessagesFromJson(tasksJSONObject);
                        getAPIListener.onResponseOK(messageList);
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
        getMessageThread.start();
    }


    public void post(Message message, Callback callback) throws Exception {
        JSONObject jsonEntity = new JSONObject();
        jsonEntity.put("content", message.getContent());
        jsonEntity.put("userID", message.getUserID());
        jsonEntity.put("receiverID", message.getReceiverID());
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

    public ArrayList<Message> parseMessagesFromJson(JSONObject tasksJSONObject) {
        ArrayList<Message> messageList = new ArrayList<>();
        Iterator<String> messageKeys = tasksJSONObject.keys();

        while (messageKeys.hasNext()) {
            try {
                String key = messageKeys.next();
                JSONObject aJSONTask = tasksJSONObject.getJSONObject(key);
                Message message = parse_a_Message(aJSONTask, key); //還不確定如果這裡丟出例外 會發生什麼事
                messageList.add(message);
            } catch (Exception e) {
                Log.d(LOG_TAG, e.getMessage());
            }
        }

        return messageList;
    }
    private Message parse_a_Message(JSONObject aJSONMessage, String messageKey) throws Exception {

        int messageId = Integer.parseInt(messageKey);

        String content =  aJSONMessage.getString("content");

        int userID = aJSONMessage.getInt("userID");

        int receiverID = aJSONMessage.getInt("receiverID");

        int taskID = aJSONMessage.getInt("taskID");

        LocalDateTime postTime = transitTimeStampFromGetAPI(aJSONMessage.getString("postTime"));

        return new Message(messageId, content, userID, receiverID, taskID, postTime);
    }

}
