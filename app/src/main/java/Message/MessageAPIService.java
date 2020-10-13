package Message;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageAPIService {
    public void postUseThread(final String content, final String userID, final String receiverID
            , final String taskID, final String postTime) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //發布訊息 未完成
                    //post(content, userID, receiverID, taskID, postTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ).start();
    }
}
