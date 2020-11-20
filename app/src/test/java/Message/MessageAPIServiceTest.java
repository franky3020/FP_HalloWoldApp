package Message;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import User.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.junit.Assert.assertTrue;

public class MessageAPIServiceTest {

    @Test
    public void post() {

        Message message= new Message("testing-post", 1, 1, LocalDateTime.now());
        MessageAPIService messageAPIService = new MessageAPIService();
        try {
            messageAPIService.post(message, new Callback() {
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
    public void getUserRelatedWho() {

        MessageAPIService messageAPIService = new MessageAPIService();

        messageAPIService.getUserRelatedWho(14, new MessageAPIService.GetAPIListener<ArrayList<User>>() {
            @Override
            public void onResponseOK(ArrayList<User> users) {
                System.out.println(users);
            }

            @Override
            public void onFailure() {

            }
        });

        try {
            Thread.sleep(5000); // 為了等post完成, 不然這個test會被突然中斷, 導致失敗
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllChatMessageFromTwoUsers() {
        MessageAPIService messageAPIService = new MessageAPIService();


        messageAPIService.getAllChatMessageFromTwoUsers(14, 1, new MessageAPIService.GetAPIListener<ArrayList<Message>>() {
            @Override
            public void onResponseOK(ArrayList<Message> messages) {
                System.out.println(messages);
            }

            @Override
            public void onFailure() {

            }
        });

        try {
            Thread.sleep(5000); // 為了等post完成, 不然這個test會被突然中斷, 導致失敗
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void getLatestMessageFromTwoUsers() {
        MessageAPIService messageAPIService = new MessageAPIService();


        messageAPIService.getLatestMessageFromTwoUsers(14, 21, new MessageAPIService.GetAPIListener<ArrayList<Message>>() {
            @Override
            public void onResponseOK(ArrayList<Message> messages) {
                System.out.println(messages);
            }

            @Override
            public void onFailure() {

            }
        });

        try {
            Thread.sleep(5000); // 為了等post完成, 不然這個test會被突然中斷, 導致失敗
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
