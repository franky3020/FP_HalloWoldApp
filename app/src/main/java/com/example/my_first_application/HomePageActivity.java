package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import Task.ShowTask;

public class HomePageActivity extends AppCompatActivity {

    private ArrayList<ShowTask> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initTasks();
        RecyclerView recyclerView = findViewById(R.id.homeTaskShow);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        HomePageAdapter adapter = new HomePageAdapter(this, taskList);
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
            ShowTask user1 = new ShowTask(R.drawable.ic_user_show_task, "買xxx的滷肉飯", "買便當", "台中市西屯區", "2020/9/11", "上午 11:00", "50 min");
            taskList.add(user1);
            taskList.add(new ShowTask(R.drawable.ic_user_show_task, "打掃庭院", "做家務", "台中市北屯區", "2020/10/11", "上午 10:00", "30 min"));
            taskList.add(new ShowTask(R.drawable.ic_user_show_task, "裝燈泡", "修理", "台中市南屯區", "2020/11/11", "下午 2:00", "1 hr"));
        }
    }
}
