package testPostmanAPI;
import org.junit.Test;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;


public class testAPI {

    @Test
    public void constructTest() {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://140.134.26.71:50501/SimpleExampleMS/users")
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void postTask() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://140.134.26.71:41394//tasks?taskName=postmanTest&message=postmanTest&postTime=2020-01-01 00:00:00&salary=666")
                .method("POST", body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.code());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
