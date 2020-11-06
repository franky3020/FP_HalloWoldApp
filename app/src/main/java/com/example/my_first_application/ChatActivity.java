package com.example.my_first_application;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import  androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import Message.Message;
import Message.MessageAPIService;
import Message.ModelChat;
import User.GetLoginUser;
import User.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar; // Todo 這還要加上

    RecyclerView mRecyclerView;
    ImageView mProfileIV;
    TextView mNameTV, mUserStatusTV;
    EditText mMessageET;
    ImageButton mSendBtn;

    String mContent;

    public static final String EXTRA_RECEIVER_ID = "receiverId";
    private int mReceiverId;
    private User loginUser; // Todo 這邊命名風格還沒改成m開頭, 所以先別動

    List<ModelChat> chatList;
    ChatAdapter adapterChat;

    private static final String LOG_TAG = ChatActivity.class.getSimpleName();
    ChatActivity chatActivity = this;

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
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Todo 以下不一定要加上, 應該要在去查一下這在幹嘛
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("");

        mRecyclerView = findViewById(R.id.chat_recyclerView);
        mProfileIV = findViewById(R.id.profileIV);

        mNameTV = findViewById(R.id.nameTV);
        mNameTV.setText("" + mReceiverId); // Todo 先加上ID來測試


        mUserStatusTV = findViewById(R.id.userStatusTV);
        mMessageET = findViewById(R.id.messageEt);
        mSendBtn = findViewById(R.id.sendBtn);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setStackFromEnd(true);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(linearLayoutManager);
    }
    public void onClickToSendMessage(View view){
        EditText messageEt = findViewById(R.id.messageEt);
        mContent = messageEt.getText().toString();

        MessageAPIService messageAPIService = new MessageAPIService();

        // Todo 以下的taskID 完全沒用, 可以不要設定
        Message message = new Message(mContent, loginUser.getId(), mReceiverId,0, LocalDateTime.now());

        try {
             messageAPIService.post(message, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()) {
                        Intent intent = new Intent();
                        startActivity(intent);
                    }
                }
            });


        } catch (Exception e) {
            Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
        }
    }
}