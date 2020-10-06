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
import java.util.Observable;
import java.util.Observer;

import Task.ShowTask;
import Task.GetTasksObserved;

public class ShowTaskActivity extends AppCompatActivity implements Observer {

    ShowTaskActivity showTaskActivity = this;

    RecyclerView recyclerView;
    ArrayList<ShowTask> taskList = new ArrayList<>();
    ShowTaskAdapter recyclerViewAdapter;
    Handler uiHandler;

    GetTasksObserved getTasksObserved = GetTasksObserved.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.recyclerView = findViewById(R.id.taskShow);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerViewAdapter = new ShowTaskAdapter(taskList);

        recyclerViewAdapter.setShowTaskList(getTasksObserved.getShowTasks());
        // 未設定listener 所以第一次功能會失效

        this.recyclerView.setAdapter(recyclerViewAdapter);

        this.getTasksObserved.addObserver(this);
        this.uiHandler = new Handler();
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

    public void showTaskUIUpdate() { //必須要在主執行緒上更新UI, 才不會出錯
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) { // 實作觀察者, 當拿任務api有拿到任務時會接著執行這函式
        if (o instanceof GetTasksObserved) {
            GetTasksObserved getTasksObserved = (GetTasksObserved) o;

            final ArrayList<ShowTask> showTaskList = getTasksObserved.getShowTasks();
            recyclerViewAdapter.setShowTaskList(showTaskList);

            recyclerViewAdapter.setListener(new ShowTaskAdapter.Listener() {

                @Override
                public void onClick(int position) {
                    ShowTask showTask = showTaskList.get(position);

                    Intent intent = new Intent(showTaskActivity, TaskDetailActivity.class);
                    intent.putExtra(TaskDetailActivity.EXTRA_TASK_TITLE, showTask.getTitle());
                    showTaskActivity.startActivity(intent);
                }
            });

            showTaskUIUpdate();
        }
    }
}

