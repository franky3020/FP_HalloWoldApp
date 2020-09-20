package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import java.util.ArrayList;

import Task.ShowTask;
import Task.ShowTaskListObservable;
import Task.TaskListObserved;
import Task.ThreadForTaskGet;

public class ShowTaskActivity extends AppCompatActivity {

    private ArrayList<ShowTask> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initTasks();
        RecyclerView recyclerView = findViewById(R.id.taskShow);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ShowTaskAdapter adapter = new ShowTaskAdapter(this, taskList);
        recyclerView.setAdapter(adapter);
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

    private void initTasks() {
        for(int i = 0; i <= 10; i++){
            ShowTask user1 = new ShowTask(R.drawable.ic_user_show_task,"買xxx的滷肉飯", "買便當", "台中市西屯區", "2020/9/11", "上午 11：00");
            taskList.add(user1);
            taskList.add(new ShowTask(R.drawable.ic_user_show_task,"打掃庭院", "做家務", "台中市北屯區", "2020/10/11", "上午 10：00"));
            taskList.add(new ShowTask(R.drawable.ic_user_show_task,"裝燈泡", "修理", "台中市南屯區", "2020/11/11", "下午 2：00"));
        }

        TaskListObserved taskListObserved = new TaskListObserved();
        ShowTaskListObservable showTaskListObservable = new ShowTaskListObservable();
        taskListObserved.addObserver(showTaskListObservable);

        System.out.println("franky-test");
        System.out.println(taskListObserved.countObservers());
        Thread th1 = new Thread(taskListObserved,"taskListObserved 1");
        th1.start();
    }
}

