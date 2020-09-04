package Task;

import android.util.Log;

import org.json.JSONObject;

import java.util.Observable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskListObserved extends Observable implements Runnable{
    final String TAG = "franky-test";
    JSONObject allTask;

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            String apiUrl = "http://140.134.26.71:46557/ms-provider-develop/tasks";
//            String apiUrl = "http://140.134.26.71:33836/tasks"; // 因目前gateway 會有timeout的問題
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .method("GET", null)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                allTask = new JSONObject(jsonData);
                Log.i(TAG, "okHttp is request success" + allTask.length());
                setChanged(); // 一定先有這個 notifyObservers() 才會有效
                notifyObservers();
            } else {
                Log.i(TAG, "okHttp is request error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTaskLength() {
        return allTask.length();
    }

}
