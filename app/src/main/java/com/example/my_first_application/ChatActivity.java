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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ChatActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageView profileIV;
    TextView nameTV,userStatusTV;
    EditText messageET;
    ImageButton sendBtn;

    String content;

    public static final String EXTRA_Receiver_ID = "receiverID";

    List<ModelChat> chatList;
    ChatAdapter adapterChat;

    private static final String LOG_TAG = ChatActivity.class.getSimpleName();
    ChatActivity chatActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("");
        recyclerView = findViewById(R.id.chat_recyclerView);
        profileIV = findViewById(R.id.profileIV);
        nameTV = findViewById(R.id.nameTV);
        userStatusTV = findViewById(R.id.userStatusTV);
        messageET = findViewById(R.id.messageEt);
        sendBtn = findViewById(R.id.sendBtn);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setStackFromEnd(true);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(linearLayoutManager);
    }
    public void onClickToSendMessage(View view){
        EditText messageEt = findViewById(R.id.messageEt);
        content = messageEt.getText().toString();

        MessageAPIService messageAPIService = new MessageAPIService();
        Message message = new Message(content,1, 1,200, LocalDateTime.now());
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