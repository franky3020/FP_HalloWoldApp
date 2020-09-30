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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import Task.ShowTask;
import Task.GetTasksObserved;
import Task.Task;

public class ShowTaskActivity extends AppCompatActivity implements Observer {

    RecyclerView recyclerView;
    static ArrayList<ShowTask> taskList = new ArrayList<>();
    ShowTaskAdapter showTaskAdapter;
    Handler uiHandler;

    GetTasksObserved getTasksObserved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.recyclerView = findViewById(R.id.taskShow);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.showTaskAdapter = new ShowTaskAdapter(this, taskList);
        this.recyclerView.setAdapter(showTaskAdapter);

        this.getTasksObserved = new GetTasksObserved();
        this.getTasksObserved.addObserver(this);

        this.uiHandler = new Handler();
        runGetTaskAPI(0); // 馬上執行拿任務的api
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
        uiHandler.removeCallbacks(getTaskAPIRunnable);
    }

    private final Runnable getTaskAPIRunnable = new Runnable() {
        public void run() {
            getTasksObserved.startGetTasksThread();
        }
    };

    public void showTaskUIUpdate() { //必須要在主執行緒上更新UI, 才不會出錯
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                showTaskAdapter.notifyDataSetChanged();
            }
        });
    }

    public void runGetTaskAPI(int delayMillis) { // 在觀察者中呼叫 可以確保上一個拿取確實拿取完後 才在請求下一次
        uiHandler.postDelayed(getTaskAPIRunnable, delayMillis);
    }

    @Override
    public void update(Observable o, Object arg) { // 實作觀察者, 當拿任務api有拿到任務時會接著執行這函式
        GetTasksObserved getTasksObserved = (GetTasksObserved) o;

        ArrayList<ShowTask> tmpShowTaskList = new ArrayList<>();

        ArrayList<Task> tasksList = getTasksObserved.getTasks();
        if( tasksList.size() == 0 ) {
            runGetTaskAPI(1000); // 重送請求
            return; // 直接退出
        }

        for(Task task:tasksList) {
            ShowTask showTask = new ShowTask(R.drawable.ic_user_show_task, task.getName(), "買便當(未完成)", "未完成", task.getStartData().toString(), "上午 11:00(未完成)");
            tmpShowTaskList.add(showTask);
        }
        taskList.clear();
        taskList.addAll(tmpShowTaskList);

        showTaskUIUpdate();
        runGetTaskAPI(1000);
    }
}

