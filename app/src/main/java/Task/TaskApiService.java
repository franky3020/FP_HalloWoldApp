package Task;

import java.io.IOException;
import java.net.HttpURLConnection;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskApiService {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public Boolean post() { //未完成
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                } catch (Exception e) {
//
//                }
//            }
//        }
//        );

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        RequestBody body = RequestBody.create("", JSON);
        Request request = new Request.Builder()
                .url("http://140.134.26.71:41394//tasks?taskName=AndroidTest&message=FromAndroidTest&postTime=2020-07-14 00:00:00&salary=777")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if( response.code() == HttpURLConnection.HTTP_CREATED ) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
