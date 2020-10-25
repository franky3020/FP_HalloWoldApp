package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.my_first_application.Util.BottomNavigationSettingFacade;
import com.example.my_first_application.Util.NavigationItemListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import Task.TaskAPIService;
import Task.Task;
import User.GetLoginUser;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class ShowTaskActivity extends AppCompatActivity {

    private static final String LOG_TAG = ShowTaskActivity.class.getSimpleName();

    ShowTaskActivity showTaskActivity = this;

    RecyclerView recyclerView;
    ArrayList<Task> taskList = new ArrayList<>();
    ShowTaskAdapter recyclerViewAdapter;
    Handler getTasksAPI_Handler;

    int loginUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate start");

        GetLoginUser.checkLoginIfNotThenGoToLogin(this);

        this.loginUserId = GetLoginUser.getLoginUser().getId();


        setContentView(R.layout.activity_show_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getTasksAPI_Handler = new Handler();

        this.recyclerView = findViewById(R.id.taskShow);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerViewAdapter = new ShowTaskAdapter(taskList);
        this.recyclerView.setAdapter(recyclerViewAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationSettingFacade.setReleaseModeNavigation(this, bottomNavigationView);

        Log.d(LOG_TAG, "onCreate over"); // 就算跳到 login 頁面, 這一行還是會跑完, onCreate() 完後 會執行 onDestroy
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


    private final Runnable getTaskAPI_Runnable = new Runnable() {
        public void run() {
            sendGetTasksAPI();
            int delayMillis = 3000;
            getTasksAPI_Handler.postDelayed(getTaskAPI_Runnable, delayMillis);
        }
    };


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

    private void sendGetTasksAPI() {
        final TaskAPIService taskApiService = new TaskAPIService();

        taskApiService.getReleaseUserTasks(this.loginUserId, new TaskAPIService.GetAPIListener< ArrayList<Task> >() {

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
                        Toast.makeText(showTaskActivity, "沒有網路連線", Toast.LENGTH_SHORT).show(); // 這之後要用string
                    }
                });
            }
        });
    }

    private void taskUIUpdate(final ArrayList<Task> taskList) { //必須要在主執行緒上更新UI, 才不會出錯

        recyclerViewAdapter.setShowTaskList(taskList);

        recyclerViewAdapter.setListener(new ShowTaskAdapter.Listener() {

            @Override
            public void onClick(int position) {
                Task task = taskList.get(position);

                Intent intent = new Intent(showTaskActivity, TaskDetailActivity.class);
                intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, task.getTaskID());
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

