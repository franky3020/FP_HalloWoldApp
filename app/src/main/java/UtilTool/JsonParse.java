package UtilTool;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import Message.Message;
import User.UserBuilder;
import User.User;

public class JsonParse {

    private static final String LOG_TAG = JsonParse.class.getSimpleName();

    public static User parse_a_User(JSONObject aJSONObj, String idKey) throws Exception {
        int id = Integer.parseInt(idKey);

        String name =  aJSONObj.optString("name");

        String phone = aJSONObj.optString("phone");

        String firebase_uid = aJSONObj.optString("firebase_uid");

        return UserBuilder.anUser(id)
                .withName(name)
                .withPhone(phone)
                .withFirebaseUID(firebase_uid)
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

//        int messageId = Integer.parseInt(messageKey);

        String content =  aJSONMessage.getString("content");

        int userID = aJSONMessage.getInt("userID");

        int receiverID = aJSONMessage.getInt("receiverID");

//        int taskID = aJSONMessage.getInt("taskID");

        LocalDateTime postTime = TransitTime.transitTimeStampFromGetAPI(aJSONMessage.getString("postTime"));

        return new Message(content, userID, receiverID, postTime);
    }

}
