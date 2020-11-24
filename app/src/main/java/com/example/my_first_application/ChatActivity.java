package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import  androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import Message.Message;
import Message.MessageAPIService;
import User.GetLoginUser;
import User.User;
import User.UserAPIService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ChatActivity extends AppCompatActivity {

    private static final String LOG_TAG = ChatActivity.class.getSimpleName();

    RecyclerView mRecyclerView;

    String mContent;
    private ImageButton sendBtn;
    private EditText messageEt;
    private LinearLayout chatLayout;

    public static final String EXTRA_RECEIVER_ID = "receiverId";
    private int mReceiverId;
    private User loginUser; // Todo 這邊命名風格還沒改成m開頭, 所以先別動

    ArrayList<Message> mMessagesList = new ArrayList<>();
    ChatAdapter mChatAdapter;


    ChatActivity chatActivity = this;

    Handler getMessagesAPI_Handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 初始化此頁面必要資訊
        mReceiverId = getIntent().getExtras().getInt(EXTRA_RECEIVER_ID);
        loginUser = GetLoginUser.getLoginUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Todo 以下不一定要加上, 應該要在去查一下這在幹嘛
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final TextView userNameField = findViewById(R.id.nameTV);
        UserAPIService userAPIService = new UserAPIService();
        userAPIService.getAUserByUserID(mReceiverId, new UserAPIService.UserListener() {
            @Override
            public void onResponseOK(final User user) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userNameField.setText(user.getName());
                    }
                });

            }
            @Override
            public void onFailure() {
            }
        });

        chatLayout = (LinearLayout)findViewById(R.id.chatLayout);
        sendBtn = (ImageButton)findViewById(R.id.sendBtn);
        messageEt = findViewById(R.id.messageEt);

        mRecyclerView = findViewById(R.id.chat_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mChatAdapter = new ChatAdapter(mMessagesList);
        mRecyclerView.setAdapter(mChatAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.getMessagesAPI_Handler.post(getMessagesAPI_Runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.getMessagesAPI_Handler.removeCallbacks(getMessagesAPI_Runnable);
    }

    private final Runnable getMessagesAPI_Runnable = new Runnable() {
        public void run() {
            sendGetChatRoomMessageAPI();
            int delayMillis = 1000;
            getMessagesAPI_Handler.postDelayed(getMessagesAPI_Runnable, delayMillis);
        }
    };

    private void sendGetChatRoomMessageAPI() {

        MessageAPIService messageAPIService = new MessageAPIService();
        messageAPIService.getAllChatMessageFromTwoUsers(loginUser.getId(), mReceiverId, new MessageAPIService.GetAPIListener<ArrayList<Message>>() {

            @Override
            public void onResponseOK(ArrayList<Message> messages) {
                if (messages.size() > mMessagesList.size()){
                    mMessagesList = messages;
                    messageListUIUpdate();
                }
            }
            @Override
            public void onFailure() {

            }
        });
    }

    private void messageListUIUpdate() {
        mChatAdapter.setShowChatList(mMessagesList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mChatAdapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(mMessagesList.size() - 1);
            }
        });
    }

    public void onClickToSendMessage(View view){
        EditText messageEt = findViewById(R.id.messageEt);
        mContent = messageEt.getText().toString();
        if (!"".equals(mContent)){
            MessageAPIService messageAPIService = new MessageAPIService();
            Message message = new Message(mContent, loginUser.getId(), mReceiverId, LocalDateTime.now());

            try {
                messageAPIService.post(message, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            Log.d(LOG_TAG, "onResponse isSuccessful");
                        }
                    }
                });
                messageEt.setText("");
            } catch (Exception e) {
                Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}