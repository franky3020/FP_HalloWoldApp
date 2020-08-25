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

    public void post(final String taskName, final String message, final String salary , final String dateTime
                        , final String taskType, final String taskAddress, final String taskCity) {
        final String TAG = "franky-test";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();

                    RequestBody body = RequestBody.create("", JSON);

                    //String base_URL = "http://140.134.26.71:46557/ms-provider-develop/tasks?";
                    String base_URL = "http://140.134.26.71:46557/ms-provider-release-1.0.0/tasks?";
                    String taskParameter1 = "TaskName=" + taskName;
                    String taskParameter2 = "Message=" + message;
                    String taskParameter3 = "PostTime=" + dateTime + " 00:00:00";
                    String taskParameter4 = "Salary=" + salary;
                    String taskParameter5 = "TypeName=" + taskType;
                    String taskParameter6 = "TaskAddress=" + taskAddress;
                    String taskParameter7 = "TaskCity=" + taskCity;



                    Request request = new Request.Builder()
                            .url(base_URL + taskParameter1 + "&" + taskParameter2 + "&" + taskParameter3 +
                                    "&" + taskParameter4 + "&" + taskParameter5 + "&" + taskParameter6 +
                                    "&" + taskParameter7)
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
