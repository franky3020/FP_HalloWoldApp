package TestHttpsApi;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestHttpsApi {

    public boolean get_test() { //未完成

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();



        Request request = new Request.Builder()
                .url("https://datacenter.taichung.gov.tw/swagger/yaml/387010000A")
                .method("GET", null)
                .build();

        try {

            Response response = client.newCall(request).execute();
            if(response == null) {
                return false;
            }
            if( response.code() == HttpURLConnection.HTTP_OK ) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }


}
