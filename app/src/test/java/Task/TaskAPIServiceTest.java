package Task;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.junit.Assert.assertTrue;

public class TaskAPIServiceTest {

    @Test
    public void getTasks_test() {
        final TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.getTasksV3(new TaskAPIService.TaskListener() {
            @Override
            public void onResponseOK(ArrayList<Task> tasks) {
                System.out.println(tasks.size());
                for(Task task: tasks) {
                    System.out.println(task.getTaskID());
                }
                assertTrue(tasks.size() > 0);
            }

            @Override
            public void onFailure() {
                assertTrue(false);
            }
        });

        try {
            Thread.sleep(5000); // 為了等Get完成, 不然這個test會被突然中斷, 導致失敗
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void post() {

        Task task = new Task("franky-1010", "franky-108", LocalDateTime.now(),
                500, "測試", 1, LocalDateTime.now());
        TaskAPIService taskApiService = new TaskAPIService();
        try {
            taskApiService.post(task, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    System.out.println(response);
                }
            });
            Thread.sleep(5000); // 為了等post完成, 不然這個test會被突然中斷, 導致失敗
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void deleteTask() {
        TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.deleteTask(223, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println(response);
            }
        });
        try {
            Thread.sleep(5000); // 為了等API完成, 不然這個test會被突然中斷, 導致失敗
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}