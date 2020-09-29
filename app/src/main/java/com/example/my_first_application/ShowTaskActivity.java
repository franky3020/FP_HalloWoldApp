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

import Task.UpdateTaskListObservable;
import Task.GetTasksObserved;

public class ShowTaskActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShowTaskAdapter showTaskAdapter;
    Handler getTasksAPI_Handler;

    GetTasksObserved getTasksObserved;
    UpdateTaskListObservable updateTaskListObservable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.recyclerView = findViewById(R.id.taskShow);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.showTaskAdapter = new ShowTaskAdapter(this, UpdateTaskListObservable.getTaskList());
        this.recyclerView.setAdapter(showTaskAdapter);

        this.getTasksObserved = new GetTasksObserved();
        this.updateTaskListObservable = new UpdateTaskListObservable(this);
        this.getTasksObserved.addObserver(updateTaskListObservable);

        this.getTasksAPI_Handler = new Handler();
        this.getTasksAPI_Handler.post(getTaskAPI_Runnable);
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

    private final Runnable getTaskAPI_Runnable = new Runnable() {
        public void run() {
            getTasksObserved.startGetTasksThread();
        }
    };

    public void showTaskUI_Update() { //必須要在主執行緒上更新UI, 才不會出錯
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                showTaskAdapter.notifyDataSetChanged();
            }
        });
    }

    public void runGetTaskAPI_Runnable(int delayMillis) { // 在觀察者中呼叫 可以確保上一個拿取確實拿取完後 才在請求下一次
        getTasksAPI_Handler.postDelayed(getTaskAPI_Runnable, delayMillis);
    }

}

