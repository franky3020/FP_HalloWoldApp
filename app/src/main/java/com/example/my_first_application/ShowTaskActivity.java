package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import java.util.ArrayList;

import Task.ShowTask;
import Task.UpdateTaskListObservable;
import Task.GetTasksObserved;

public class ShowTaskActivity extends AppCompatActivity {

    private ArrayList<ShowTask> taskList = new ArrayList<>();
    RecyclerView recyclerView;
    ShowTaskAdapter showTaskAdapter;
    Handler getTasksAPI_Handler;
    int sendAPI_DelayMillis = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.recyclerView = findViewById(R.id.taskShow);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.showTaskAdapter = new ShowTaskAdapter(this, taskList);
        this.recyclerView.setAdapter(showTaskAdapter);

        this.getTasksAPI_Handler = new Handler();
        this.getTasksAPI_Handler.postDelayed(getTaskAPI_Runnable, sendAPI_DelayMillis); // 第一次會比較久, 因為會先delay在執行, 這之後要修正
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
    @Override
    protected void onPause() {
        super.onPause();
        getTasksAPI_Handler.removeCallbacks(getTaskAPI_Runnable);
    }

    private final Runnable getTaskAPI_Runnable = new Runnable()
    {
        public void run() {
            GetTasksObserved getTasksObserved = new GetTasksObserved();
            UpdateTaskListObservable updateTaskListObservable = new UpdateTaskListObservable(taskList);
            getTasksObserved.addObserver(updateTaskListObservable);
            getTasksObserved.startGetTasksThread();
            showTaskAdapter.notifyDataSetChanged(); // 這一定要在主執行緒上執行 不然會錯 有夠怪
            getTasksAPI_Handler.postDelayed(getTaskAPI_Runnable, sendAPI_DelayMillis);
        }
    };
}

