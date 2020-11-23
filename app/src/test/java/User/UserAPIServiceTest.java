package User;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import Task.Task;
import Task.TaskAPIService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.junit.Assert.*;

public class UserAPIServiceTest {

    @Test
    public void createUser() {

        User user = UserBuilder.anUser(0)
                .withFirebaseUID("test")
                .build();


        UserAPIService userAPIService = new UserAPIService();
        try {
            userAPIService.createUser(user, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    System.out.println(response);
                }
            });
            Thread.sleep(5000); // 為了等非同步完成, 不然這個test會被突然中斷, 導致失敗
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getAUserByFirebaseUID() {
        UserAPIService userAPIService = new UserAPIService();

        userAPIService.getAUserByFirebaseUID("test", new UserAPIService.UserListener(){

            @Override
            public void onFailure() {
                System.out.println("onFailure");
            }

            @Override
            public void onResponseOK(User user) {
                System.out.println(user);
            }
        });

        try {
            Thread.sleep(5000); // 為了等它完成, 不然這個test會被突然中斷, 導致失敗
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deductionUserPoint() {
        UserAPIService userAPIService = new UserAPIService();

        userAPIService.deductionUserPoint(14, 9, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });

        try {
            Thread.sleep(5000); // 為了等它完成, 不然這個test會被突然中斷, 導致失敗
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}