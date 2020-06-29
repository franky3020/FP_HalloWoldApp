package Task;

import java.io.IOException;
import java.net.HttpURLConnection;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskApiService {

    public boolean post(Task task) { //未完成

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://140.134.26.71:41394//tasks?taskName=frankyTest&message=postmanTest&postTime=2020-01-01 00:00:00&salary=666")
                .method("POST", body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if( response.code() == HttpURLConnection.HTTP_CREATED ) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }


}
