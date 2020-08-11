package Task;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskApiService {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public void post() { //未完成
        final String TAG = "franky-test";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();

                    RequestBody body = RequestBody.create("", JSON);
                    Request request = new Request.Builder()
                            .url("http://140.134.26.71:46557/ms-provider-develop/tasks?taskName=AndroidTest&message=FromAndroidTest&postTime=2020-07-14 00:00:00&salary=777")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.i(TAG, "isSuccessful");
                    } else {
                        Log.i(TAG, "okHttp is request error");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ).start();

    }

    public static boolean getTasks() {
        final String TAG = "franky-test";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    String apiUrl = "http://140.134.26.71:46557/ms-provider-develop/tasks";
                    Request request = new Request.Builder()
                            .url(apiUrl)
                            .method("GET", null)
                            .build();

                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.i(TAG, response.body().string());
                    } else {
                        Log.i(TAG, "okHttp is request error");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return true;

    }






}
