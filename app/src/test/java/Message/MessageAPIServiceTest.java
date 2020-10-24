package Message;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Task.Task;
import Task.TaskAPIService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.junit.Assert.assertTrue;

public class MessageAPIServiceTest {
    @Test
    public void getMessages_test() {
        final MessageAPIService messageAPIService = new MessageAPIService();
        messageAPIService.getMessages(new MessageAPIService.MessageListener() {

            public void onResponseOK(ArrayList<Message> messages) {
                System.out.println(messages.size());
                for(Message message: messages) {
                    System.out.println(message.getMessageID());
                }
                assertTrue(messages.size() > 0);
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

        Message message= new Message("testing-post", 1, 1, 200, LocalDateTime.now());
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

}
