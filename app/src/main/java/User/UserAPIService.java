package User;

import android.util.Log;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Objects;

import UtilTool.JsonParse;
import UtilTool.TransitTime;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserAPIService {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String LOG_TAG = UserAPIService.class.getSimpleName();

    public static final String API_version = "ms-provider-release-200";

    public static final String base_URL = "http://140.134.26.65:46557/" + API_version + "/users";


    public void createUser(User user, Callback callback) throws Exception {
        JSONObject jsonEntity = new JSONObject();
        jsonEntity.put("name", user.getName());
        jsonEntity.put("firebaseUid", user.getFirebaseUID());

        RequestBody requestBody = RequestBody.create(String.valueOf(jsonEntity), JSON);

        Request request = new Request.Builder().url(base_URL).post(requestBody).build();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        client.newCall(request).enqueue(callback);
    }

    public interface UserListener {
        void onResponseOK(User user);
        void onFailure();
    }

    public void getAUserByUserID(final int UserID, final UserListener userListener) {

        Thread getUserThread = new Thread() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL + "/" + UserID)
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        JSONObject userJSONObject = new JSONObject( Objects.requireNonNull(response.body()).string() );
                        Iterator<String> usersId = userJSONObject.keys();
                        if ( usersId.hasNext() ) {
                            String idStr = usersId.next();
                            JSONObject aUserJSON = userJSONObject.getJSONObject(idStr);
                            User user = JsonParse.parse_a_User(aUserJSON, idStr);
                            userListener.onResponseOK(user);
                        } else {
                            userListener.onFailure();
                        }

                    } else {
                        userListener.onFailure();
                    }


                    response.close();

                } catch (Exception e) {
                    Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
                    userListener.onFailure();
                }
            }
        };
        getUserThread.start();
    }

    public User getAUserByUserID(final int UserID) {
        User user = UserBuilder.anUser(UserID).build();

        Request request = new Request.Builder()
                .url(base_URL + "/" + UserID)
                .method("GET", null)
                .build();
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        try {
            Response response= client.newCall(request).execute();

            if(response.isSuccessful()) {
                JSONObject userJSONObject = new JSONObject( Objects.requireNonNull(response.body()).string() );
                Iterator<String> usersId = userJSONObject.keys();
                if ( usersId.hasNext() ) {
                    String idStr = usersId.next();
                    JSONObject aUserJSON = userJSONObject.getJSONObject(idStr);
                    user = JsonParse.parse_a_User(aUserJSON, idStr);
                }
            }
            response.close();

        } catch (Exception e) {
            Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
        }

        return user;
    }

    public void changeUserPoint(final int userID,final int point, Callback callback) {
        Request request = new Request.Builder().url(base_URL + "/" + userID + "/" + point).post(null).build();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        client.newCall(request).enqueue(callback);
    }

    public void increaseUserPoint(final int userID,final int point, Callback callback) {
        JSONObject jsonEntity = new JSONObject();
        RequestBody requestBody = RequestBody.create(String.valueOf(jsonEntity), JSON); // 需要一個空的

        Request request = new Request.Builder().url(base_URL + "/" + userID + "/increaseUserPoint/" + point).post(requestBody).build();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        client.newCall(request).enqueue(callback);
    }

    public void deductionUserPoint(final int userID,final int point, Callback callback) {
        JSONObject jsonEntity = new JSONObject();
        RequestBody requestBody = RequestBody.create(String.valueOf(jsonEntity), JSON);// 需要一個空的

        Request request = new Request.Builder().url(base_URL + "/" + userID + "/deductionUserPoint/" + point).post(requestBody).build();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        client.newCall(request).enqueue(callback);
    }


    public void getAUserByFirebaseUID(final String firebaseUID, final UserListener userListener) {

        Thread getUserThread = new Thread() {

            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(base_URL + "/" + "firebase" + "/" + firebaseUID)
                        .method("GET", null)
                        .build();
                OkHttpClient client = new OkHttpClient().newBuilder().build();

                try {
                    Response response= client.newCall(request).execute();

                    if(response.isSuccessful()) {
                        JSONObject userJSONObject = new JSONObject( Objects.requireNonNull(response.body()).string() );
                        Iterator<String> usersId = userJSONObject.keys();
                        if ( usersId.hasNext() ) {
                            String idStr = usersId.next();
                            JSONObject aUserJSON = userJSONObject.getJSONObject(idStr);
                            User user = JsonParse.parse_a_User(aUserJSON, idStr);
                            userListener.onResponseOK(user);
                        } else {
                            userListener.onFailure();
                        }

                    } else {
                        userListener.onFailure();
                    }


                    response.close();

                } catch (Exception e) {
                    Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
                    userListener.onFailure();
                }
            }
        };
        getUserThread.start();
    }
}
