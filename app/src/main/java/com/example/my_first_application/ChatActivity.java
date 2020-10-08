package com.example.my_first_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import  androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class ChatActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageView profileIV;
    TextView nameTV,userStatusTV;
    EditText messageET;
    ImageButton sendBtn;

    List<ModelChat> chatList;
    AdapterChat adapterChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        recyclerView = findViewById(R.id.chat_recyclerView);
        profileIV = findViewById(R.id.profileIV);
        nameTV = findViewById(R.id.nameTV);
        userStatusTV = findViewById(R.id.userStatusTV);
        messageET = findViewById(R.id.messageEt);
        sendBtn = findViewById(R.id.sendBtn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}