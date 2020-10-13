package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import Task.TaskAPIService;
import Task.Task;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShowTaskActivity extends AppCompatActivity {

    private static final String LOG_TAG = ShowTaskActivity.class.getSimpleName();

    ShowTaskActivity showTaskActivity = this;

    RecyclerView recyclerView;
    ArrayList<Task> taskList = new ArrayList<>();
    ShowTaskAdapter recyclerViewAdapter;
    Handler getTasksAPI_Handler;


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");

        this.getTasksAPI_Handler.post(getTaskAPI_Runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");

        this.getTasksAPI_Handler.removeCallbacks(getTaskAPI_Runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate");

        setContentView(R.layout.activity_show_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getTasksAPI_Handler = new Handler();

        this.recyclerView = findViewById(R.id.taskShow);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerViewAdapter = new ShowTaskAdapter(taskList);
        this.recyclerView.setAdapter(recyclerViewAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_new_task:
                Intent intent = new Intent(this, ReleaseTaskActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void taskUIUpdate(final ArrayList<Task> taskList) { //必須要在主執行緒上更新UI, 才不會出錯

        recyclerViewAdapter.setShowTaskList(taskList);

        recyclerViewAdapter.setListener(new ShowTaskAdapter.Listener() {

            @Override
            public void onClick(int position) {
                Task task = taskList.get(position);

                Intent intent = new Intent(showTaskActivity, TaskDetailActivity.class);
                intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, task.getTaskID());
                intent.putExtra(TaskDetailActivity.EXTRA_TASK_TITLE, task.getTaskName());
                showTaskActivity.startActivity(intent);
            }
        });

        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }


    public void onClickToReleaseTask(View view) {
        Intent intent = new Intent(this, ReleaseTaskActivity.class);
        startActivity(intent);
    }

    private final Runnable getTaskAPI_Runnable = new Runnable() {
        public void run() {
            sendGetTasksAPI();
            int delayMillis = 1000;
            getTasksAPI_Handler.postDelayed(getTaskAPI_Runnable, delayMillis);
        }
    };

    private void sendGetTasksAPI() {
        final TaskAPIService taskApiService = new TaskAPIService();

        taskApiService.getTasksV3(new TaskAPIService.TaskListener() {

            @Override
            public void onResponseOK(ArrayList<Task> tasks) {
                taskList = tasks;
                taskUIUpdate(taskList);
                Log.d(LOG_TAG, "sendGetTasksAPI onResponse");
            }
        });

    }

}

