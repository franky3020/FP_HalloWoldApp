package com.example.my_first_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.my_first_application.Util.BottomNavigationSettingFacade;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import Message.MessageAPIService;
import User.GetLoginUser;
import User.User;

public class ShowChatActivity extends AppCompatActivity { // 顯示訊息的管理介面, Todo 這命名有可能要改

    private static final String LOG_TAG = ShowChatActivity.class.getSimpleName();

    ShowChatActivity showChatActivity = this;

    RecyclerView recyclerView;
    ArrayList<User> mUsers = new ArrayList<>();
    ShowChatAdapter recyclerViewAdapter;
    Handler getUserRelatedAPI_Handler;

    int loginUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate start");

        GetLoginUser.checkLoginIfNotThenGoToLogin(this);

        this.loginUserId = GetLoginUser.getLoginUser().getId();


        setContentView(R.layout.activity_show_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getUserRelatedAPI_Handler = new Handler();

        this.recyclerView = findViewById(R.id.chatListShow);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerViewAdapter = new ShowChatAdapter(mUsers);
        this.recyclerView.setAdapter(recyclerViewAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationSettingFacade.setNavigation(this, bottomNavigationView);

        Log.d(LOG_TAG, "onCreate over"); // 就算跳到 login 頁面, 這一行還是會跑完, onCreate() 完後 會執行 onDestroy
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

}
