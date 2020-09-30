package Task;

import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class TaskAPIServiceTest {

    @Test
    public void post_test() {
        final String postTime = "2020-01-01 00:00:00";
        TaskAPIService taskApiService = new TaskAPIService();
        try {
            boolean is_ok = taskApiService.post("test", "test", "123", postTime, "EatTask", "test", "1");
            assertTrue(is_ok);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTasks_test() {
        TaskAPIService taskApiService = new TaskAPIService();
        try {
            ArrayList<Task> tasks = taskApiService.getTasks();
            assertTrue(tasks.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}