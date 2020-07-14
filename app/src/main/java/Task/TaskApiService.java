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
                            .url("http://140.134.26.71:41394//tasks?taskName=AndroidTest&message=FromAndroidTest&postTime=2020-07-14 00:00:00&salary=777")
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


}
