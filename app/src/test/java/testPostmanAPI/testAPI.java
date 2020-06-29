package testPostmanAPI;
import org.junit.Test;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
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
}
