package UtilTool;

import android.util.Log;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import Message.Message;
import User.UserBuilder;
import User.User;

public class JsonParse {

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
                e.printStackTrace();
            }
        }

        return userList;
    }

}
