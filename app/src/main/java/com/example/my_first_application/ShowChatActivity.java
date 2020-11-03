package com.example.my_first_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.my_first_application.Util.BottomNavigationSettingFacade;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import Message.MessageAPIService;
import Task.Task;
import Task.TaskAPIService;
import User.GetLoginUser;

public class ShowChatActivity extends AppCompatActivity {

    private static final String LOG_TAG = ShowChatActivity.class.getSimpleName();

    ShowChatActivity showChatActivity = this;

    RecyclerView recyclerView;
    ArrayList<Task> chatList = new ArrayList<>();
    ShowChatAdapter recyclerViewAdapter;
    Handler getMessagesAPI_Handler;

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

        this.getMessagesAPI_Handler = new Handler();

        this.recyclerView = findViewById(R.id.chatListShow);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerViewAdapter = new ShowChatAdapter(chatList);
        this.recyclerView.setAdapter(recyclerViewAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationSettingFacade.setReleaseModeNavigation(this, bottomNavigationView);

        Log.d(LOG_TAG, "onCreate over"); // 就算跳到 login 頁面, 這一行還是會跑完, onCreate() 完後 會執行 onDestroy
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");

        this.getMessagesAPI_Handler.post(getMessageAPI_Runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");

        this.getMessagesAPI_Handler.removeCallbacks(getMessageAPI_Runnable);
    }


    private final Runnable getMessageAPI_Runnable = new Runnable() {
        public void run() {
            sendGetMessageTaskAPI();
            int delayMillis = 3000;
            getMessagesAPI_Handler.postDelayed(getMessageAPI_Runnable, delayMillis);
        }
    };


    private void sendGetMessageTaskAPI() {
        final MessageAPIService messageApiService = new MessageAPIService();

        messageApiService.getUserHasWhichTasks(loginUserId, new TaskAPIService.GetAPIListener<ArrayList<Task>>() {
            @Override
            public void onResponseOK(ArrayList<Task> taskList) {
                chatList = taskList;
                messageUIUpdate(chatList);
                Log.d(LOG_TAG, "sendGetMessagesAPI onResponse");
            }

            @Override
            public void onFailure() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(LOG_TAG, "sendGetMessagesAPI Failure");
                        Toast.makeText(showChatActivity, "沒有網路連線", Toast.LENGTH_SHORT).show(); // 這之後要用string
                    }
                });
            }
        });
    }

    private void messageUIUpdate(final ArrayList<Task> chatList) { //必須要在主執行緒上更新UI, 才不會出錯

        recyclerViewAdapter.setShowChatList(chatList);

        recyclerViewAdapter.setListener(new ShowChatAdapter.Listener() {

            @Override
            public void onClick(int position) {
                Task task = chatList.get(position);

//                Intent intent = new Intent(showChatActivity, TaskDetailActivity.class);
//                intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, task.getTaskID());
//                showChatActivity.startActivity(intent);
            }
        });

        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });
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
