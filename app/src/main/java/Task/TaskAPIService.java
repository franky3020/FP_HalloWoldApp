package Task;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskAPIService {

    public void postUseThread(final String taskName, final String message, final String salary , final String postTime
                        , final String taskType, final String taskAddress, final String taskCity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    post(taskName, message, salary , postTime, taskType, taskAddress, taskCity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ).start();
    }


    public boolean post(final String taskName, final String message, final String salary , final String postTime
            , final String taskType, final String taskAddress, final String taskCity) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create("", JSON);

        String API_version = "ms-provider-develop";
        String base_URL = "http://140.134.26.71:46557/" + API_version + "/tasks?";
        String taskNameParameter = "TaskName=" + taskName;
        String messageParameter = "Message=" + message;
        String startPostTimeParameter = "StartPostTime=" + postTime;
        String endPostTimeParameter = "EndPostTime=" + postTime;
        String salaryParameter = "Salary=" + salary;
        String taskTypeParameter = "TypeName=" + taskType;
        String taskAddressParameter = "TaskAddress=" + taskAddress;
        String taskCityParameter = "TaskCity=" + taskCity;


        Request request = new Request.Builder()
                .url(base_URL + taskNameParameter + "&" + messageParameter + "&" + startPostTimeParameter +
                        "&" + endPostTimeParameter + "&" + salaryParameter + "&" + taskTypeParameter +
                        "&" + taskAddressParameter + "&" + taskCityParameter)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            response.close();
            return true;
        } else {
            response.close();
            return false;
        }
    }


    public JSONObject getTasks() {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            String API_version = "ms-provider-develop";
            String apiUrl = "http://140.134.26.71:46557/" + API_version + "/tasks";

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .method("GET", null)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                return new JSONObject( Objects.requireNonNull(response.body()).string() );
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

}
