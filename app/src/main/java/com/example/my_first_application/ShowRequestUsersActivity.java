package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import Task.Task;
import Task.TaskAPIService;
import User.User;
import User.UserBuilder;


public class ShowRequestUsersActivity extends AppCompatActivity {

    private static final String LOG_TAG = ShowRequestUsersActivity.class.getSimpleName();

    public static final String EXTRA_TASK_ID = "taskID";
    int taskID;

    Activity activity = this;

    RecyclerView recyclerView;
    ArrayList<User> userList = new ArrayList<>();
    ShowRequestUsersAdapter showRequestUsersAdapter;

    Handler getRequestUsersAPI_Handler = new Handler();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_request_users);

        Bundle taskInfo = getIntent().getExtras();
        taskID = taskInfo.getInt(EXTRA_TASK_ID);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        this.recyclerView = findViewById(R.id.request_users);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);

        this.showRequestUsersAdapter = new ShowRequestUsersAdapter(userList);
        this.recyclerView.setAdapter(showRequestUsersAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.getRequestUsersAPI_Handler.post(getRequestUsersAPI_Runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.getRequestUsersAPI_Handler.removeCallbacks(getRequestUsersAPI_Runnable);
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

    private final Runnable getRequestUsersAPI_Runnable = new Runnable() {
        public void run() {
            sendGetRequestUsersAPI();
            int delayMillis = 3000;
            getRequestUsersAPI_Handler.postDelayed(getRequestUsersAPI_Runnable, delayMillis);
        }
    };


    private void sendGetRequestUsersAPI() {
        final TaskAPIService taskApiService = new TaskAPIService();

        taskApiService.getTaskRequestUsersID(taskID, new TaskAPIService.GetAPIListener< ArrayList<Integer> >() {

            @Override
            public void onResponseOK(ArrayList<Integer> usersId) {
                Log.d(LOG_TAG, usersId.toString());

                ArrayList<User> users = new ArrayList<>();

                for(Integer userId : usersId) {
                    users.add(UserBuilder.anUser(userId.intValue()).build());
                }

                userList = users;
                userListUIUpdate(userList);

                Log.d(LOG_TAG, "sendGetRequestUsersAPI onResponse");
            }

            @Override
            public void onFailure() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "沒有網路連線", Toast.LENGTH_SHORT).show(); // 這之後要用string
                    }
                });
            }
        });
    }

    private void userListUIUpdate(final ArrayList<User> userList) { //必須要在主執行緒上更新UI, 才不會出錯
        showRequestUsersAdapter.setRequestUsers(userList);
        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                showRequestUsersAdapter.notifyDataSetChanged();
            }
        });
    }

}