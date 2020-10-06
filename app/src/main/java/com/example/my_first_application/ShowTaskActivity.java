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

import Task.GetTasksObserved;
import Task.Task;

public class ShowTaskActivity extends AppCompatActivity implements Observer {

    ShowTaskActivity showTaskActivity = this;

    RecyclerView recyclerView;
    ArrayList<Task> taskList = new ArrayList<>();
    ShowTaskAdapter recyclerViewAdapter;
    Handler uiHandler;

    GetTasksObserved getTasksObserved = GetTasksObserved.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.uiHandler = new Handler();

        this.recyclerView = findViewById(R.id.taskShow);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerViewAdapter = new ShowTaskAdapter(taskList);
        this.recyclerView.setAdapter(recyclerViewAdapter);

        this.getTasksObserved.addObserver(this);

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

    public void showTaskUIUpdate(final ArrayList<Task> taskList) { //必須要在主執行緒上更新UI, 才不會出錯

        recyclerViewAdapter.setShowTaskList(taskList);

        recyclerViewAdapter.setListener(new ShowTaskAdapter.Listener() {

            @Override
            public void onClick(int position) {
                Task task = taskList.get(position);

                Intent intent = new Intent(showTaskActivity, TaskDetailActivity.class);
                intent.putExtra(TaskDetailActivity.EXTRA_TASK_TITLE, task.getTaskName());
                showTaskActivity.startActivity(intent);
            }
        });

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
            ArrayList<Task> taskList = getTasksObserved.getTasks();
            showTaskUIUpdate(taskList);
        }
    }
}

