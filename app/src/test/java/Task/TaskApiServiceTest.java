package Task;

import android.util.Log;

import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class TaskApiServiceTest {

    @Test
    public void post_test() {
        final String postTime = "2020-01-01 00:00:00";
        TaskApiService taskApiService = new TaskApiService();
        try {
            boolean is_ok = taskApiService.post("test", "test", "123", postTime, "EatTask", "test", "1");
            assertTrue(is_ok);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTasks_test() {
        TaskApiService taskApiService = new TaskApiService();
        try {
            JSONObject tasks = taskApiService.getTasks();
            assertTrue(tasks.length() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}