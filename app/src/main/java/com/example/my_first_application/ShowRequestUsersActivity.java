package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import Task.TaskAPIService;
import User.User;
import User.UserBuilder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import Task.TaskStateEnum;


public class ShowRequestUsersActivity extends AppCompatActivity {

    private static final String LOG_TAG = ShowRequestUsersActivity.class.getSimpleName();

    public static final String EXTRA_TASK_ID = "taskID";
    int taskID;

    Activity activity = this;

    RecyclerView recyclerView;
    ArrayList<User> userList = new ArrayList<>();
    ShowRequestUsersAdapter showRequestUsersAdapter;
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
    }

    @Override
    protected void onStop() {
        super.onStop();
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