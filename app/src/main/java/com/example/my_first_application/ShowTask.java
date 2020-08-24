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
import java.util.List;

import Task.ThreadForTaskGet;
public class ShowTask extends AppCompatActivity {

    private List<ShowTaskAdapter.Task> taskList = new ArrayList<>();

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
        ShowTaskAdapter adapter = new ShowTaskAdapter(taskList);
        recyclerView.setAdapter(adapter);
        ThreadForTaskGet threadForTaskGet = new ThreadForTaskGet();
        threadForTaskGet.start();
        System.out.println("franky test: allTask.length(): ");
        try {
            System.out.println(threadForTaskGet.getTaskLength());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        for(int i = 0; i < 10; i++) {
            ShowTaskAdapter.Task apple = new ShowTaskAdapter.Task("Apple");
            taskList.add(apple);
            ShowTaskAdapter.Task banana = new ShowTaskAdapter.Task("Banana");
            taskList.add(banana);
        }
    }

}

