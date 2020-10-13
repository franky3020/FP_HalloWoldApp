package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

public class HomePageActivity extends AppCompatActivity {

    private static final String LOG_TAG = HomePageActivity.class.getSimpleName();

    private HomePageActivity homePageActivity = this;

    Handler getTasksAPI_Handler;

    private ArrayList<Task> taskList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ShowTaskAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getTasksAPI_Handler = new Handler();

        this.recyclerView = findViewById(R.id.homeTaskShow);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerViewAdapter = new ShowTaskAdapter(taskList);
        this.recyclerView.setAdapter(recyclerViewAdapter);

        BottomNavigationView bottomNavigationView
                = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.icon_search:
                        break;
                    case R.id.icon_message:
                        break;
                    case R.id.icon_profile:
                        Intent intent = new Intent();
                        intent.setClass(HomePageActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

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

    public void taskUIUpdate() {
        recyclerViewAdapter.setShowTaskList(taskList);

        recyclerViewAdapter.setListener(new ShowTaskAdapter.Listener() {

            @Override
            public void onClick(int position) {
                Task task = taskList.get(position);

                Intent intent = new Intent(homePageActivity, TaskDetailActivity.class);
                intent.putExtra(TaskDetailActivity.EXTRA_TASK_TITLE, task.getTaskName());
                homePageActivity.startActivity(intent);
            }
        });

        runOnUiThread( new Runnable() { //必須要在主執行緒上更新UI, 才不會出錯
            @Override
            public void run() {
                recyclerViewAdapter.notifyDataSetChanged();
                Log.d(LOG_TAG, "over recyclerViewAdapter.notifyDataSetChanged()");
            }
        });
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

        taskApiService.getTasksV2( new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(LOG_TAG, "sendGetTasksAPI onFailure");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {

                    JSONObject tasksJSONObject = new JSONObject( Objects.requireNonNull(response.body()).string() );
                    taskList = taskApiService.parseTasksFromJson(tasksJSONObject);
                    taskUIUpdate();
                    Log.d(LOG_TAG, "sendGetTasksAPI onResponse");

                } catch (JSONException e) {
                    Log.d(LOG_TAG, e.getMessage());
                }
            }
        });

    }


}

