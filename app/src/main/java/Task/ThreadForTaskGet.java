package Task;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ThreadForTaskGet extends Thread {
    final String TAG = "franky-test";
    private Response response;
    private int taskLength = 0;


    public int getTaskLength() throws InterruptedException {
        for (int i = 0 ; i < 3 ; i++) {
            if(taskLength != 0) {
                return taskLength;
            } else {
                Thread.sleep(500);
            }
        }
        return -1;


    }

    @Override
    public void run() {
        super.run();
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            String apiUrl = "http://140.134.26.71:46557/ms-provider-develop/tasks";
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .method("GET", null)
                    .build();

            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                Log.i(TAG, jsonData);
                JSONObject allTask = new JSONObject(jsonData);
                this.taskLength = allTask.length();



            } else {
                Log.i(TAG, "okHttp is request error");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
