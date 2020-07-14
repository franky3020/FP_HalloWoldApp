package TestHttpsApi;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestHttpsApi {

    public boolean get_test() { //未完成
        final String TAG = "franky-test";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    String apiUrl = "http://140.134.26.71:41394/tasks";
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
