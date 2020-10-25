package Task;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.junit.Assert.assertTrue;

public class TaskAPIServiceTest {

    @Test
    public void getTasks_test() {
        final TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.getTasksV3(new TaskAPIService.GetAPIListener< ArrayList<Task> >() {
            @Override
            public void onResponseOK(ArrayList<Task> tasks) {
                System.out.println(tasks.size());
                for(Task task: tasks) {
                    System.out.println(task);
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
        LocalDateTime currentTime = LocalDateTime.now();

        Task task = TaskBuilder.aTask(0, 400,14)
                .withTaskName("franky")
                .build();



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
        taskApiService.deleteTask(337, new Callback() {
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

    @Test
    public void getATask() {
        TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.getATask(325, new TaskAPIService.GetAPIListener<Task>() {
            @Override
            public void onResponseOK(Task task) {
                System.out.println(task);
            }

            @Override
            public void onFailure() {
                System.out.println("ERROR");
            }
        });

        try {
            Thread.sleep(5000); // 為了等API完成, 不然這個test會被突然中斷, 導致失敗
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateTaskState() {
        TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.updateTaskState(325, TaskStateEnum.BOOS_CANCEL_THE_REQUEST_STOP_TASK, new Callback() {
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

    @Test
    public void getTaskState() {
        final TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.getTaskState(325, new TaskAPIService.GetAPIListener<TaskState>() {
            @Override
            public void onResponseOK(TaskState taskState) {
                System.out.println(taskState);
            }

            @Override
            public void onFailure() {
                System.out.println("Failure");
            }
        });

        try {
            Thread.sleep(5000); // 為了等API完成, 不然這個test會被突然中斷, 導致失敗
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getTaskRequestUsersID() {
        final TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.getTaskRequestUsersID(374, new TaskAPIService.GetAPIListener<ArrayList<Integer>>() {
            @Override
            public void onResponseOK(ArrayList<Integer> integers) {
                System.out.println(integers);
            }

            @Override
            public void onFailure() {

            }
        });

        try {
            Thread.sleep(5000); // 為了等API完成, 不然這個test會被突然中斷, 導致失敗
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}