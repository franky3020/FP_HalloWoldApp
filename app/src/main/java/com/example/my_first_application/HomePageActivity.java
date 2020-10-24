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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import Task.TaskAPIService;
import Task.Task;

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

    public void taskUIUpdate(final ArrayList<Task> taskList) {
        recyclerViewAdapter.setShowTaskList(taskList);

        recyclerViewAdapter.setListener(new ShowTaskAdapter.Listener() {

            @Override
            public void onClick(int position) {
                Task task = taskList.get(position);

                Intent intent = new Intent(homePageActivity, TaskDetailActivity.class);
                intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, task.getTaskID());
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
            int delayMillis = 3000;
            getTasksAPI_Handler.postDelayed(getTaskAPI_Runnable, delayMillis);
        }
    };

    private void sendGetTasksAPI() {
        final TaskAPIService taskApiService = new TaskAPIService();

        taskApiService.getTasksV3(new TaskAPIService.GetAPIListener< ArrayList<Task> >() {

            @Override
            public void onResponseOK(ArrayList<Task> tasks) {
                taskList = tasks;
                taskUIUpdate(taskList);
                Log.d(LOG_TAG, "sendGetTasksAPI onResponse");
            }

            @Override
            public void onFailure() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(homePageActivity, "沒有網路連線", Toast.LENGTH_SHORT).show(); // 這之後要用string
                    }
                });
            }
        });

    }


}

