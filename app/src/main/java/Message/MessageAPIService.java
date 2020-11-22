package Message;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import User.User;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import UtilTool.JsonParse;
import UtilTool.TransitTime;

public class MessageAPIService {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String LOG_TAG = MessageAPIService.class.getSimpleName();

    public static final String API_version = "ms-provider-release-200"; // Todo 之後需要統一管理

    public static final String base_URL = "http://140.134.26.65:46557/" + API_version + "/message";

    public interface GetAPIListener<T> {
        void onResponseOK(T t);
        void onFailure();
    }

    public void getUserRelatedWho(final int userId, final GetAPIListener< ArrayList<User> > getAPIListener) {

        Thread getMessageThread = new Thread() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL + "/" + "userRelatedWho" + "/" + userId)
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        JSONObject usersJSONObject = new JSONObject( Objects.requireNonNull(response.body()).string() );
                        ArrayList<User> userList = JsonParse.parseUsersFromJson(usersJSONObject);
                        getAPIListener.onResponseOK(userList);

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

    public void getAllChatMessageFromTwoUsers(final int user1Id, final int user2Id, final GetAPIListener< ArrayList<Message> > getAPIListener) {

        Thread getMessageThread = new Thread() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL + "/" + "allChatMessage" + "/" + user1Id + "/" + user2Id)
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        JSONArray messageJSONArray = new JSONArray( Objects.requireNonNull(response.body()).string() );
                        ArrayList<Message> messagesList = JsonParse.parseMessagesFromJson(messageJSONArray);
                        getAPIListener.onResponseOK(messagesList);

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

    public void getLatestMessageFromTwoUsers(final int user1Id, final int user2Id, final GetAPIListener< ArrayList<Message> > getAPIListener) {

        Thread getMessageThread = new Thread() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL + "/" + "getLatestMessage" + "/" + user1Id + "/" + user2Id)
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        JSONArray messageJSONArray = new JSONArray( Objects.requireNonNull(response.body()).string() );
                        ArrayList<Message> messagesList = JsonParse.parseMessagesFromJson(messageJSONArray);
                        getAPIListener.onResponseOK(messagesList);

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
        jsonEntity.put("postTime", TransitTime.transitLocalDateTimeToString(message.getPostTime()));

        RequestBody requestBody = RequestBody.create(String.valueOf(jsonEntity), JSON);

        Request request = new Request.Builder().url(base_URL).post(requestBody).build();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        client.newCall(request).enqueue(callback);
    }

}
