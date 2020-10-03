package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import Task.Task;

public class ShowTaskActivity extends AppCompatActivity implements Observer {

    RecyclerView recyclerView;
    static ArrayList<ShowTask> taskList = new ArrayList<>();
    ShowTaskAdapter recyclerViewAdapter;
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
        this.recyclerViewAdapter = new ShowTaskAdapter(this, taskList); // taskList 是被綁定在這個 recyclerViewAdapter 裡
        this.recyclerView.setAdapter(recyclerViewAdapter);

        this.getTasksObserved = new GetTasksObserved();
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

    @Override
    protected void onStart() {
        super.onStart();
        runGetTaskAPI(0); // 馬上執行拿任務的api
    }

    @Override
    protected void onStop() {
        super.onStop();
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
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    public void runGetTaskAPI(int delayMillis) {
        uiHandler.postDelayed(getTaskAPIRunnable, delayMillis);
    }

    @Override
    public void update(Observable o, Object arg) { // 實作觀察者, 當拿任務api有拿到任務時會接著執行這函式
        if (o instanceof GetTasksObserved) {
            GetTasksObserved getTasksObserved = (GetTasksObserved) o;

            ArrayList<Task> tasks = getTasksObserved.getTasks();
            if( tasks.size() == 0 ) {
                runGetTaskAPI(1000); // 重送請求
                return; // 直接退出
            }

            ArrayList<ShowTask> tmpShowTaskList = new ArrayList<>();
            for(Task task:tasks) {
                ShowTask showTask = new ShowTask(R.drawable.ic_user_show_task, task.getName(), "買便當(未完成)", "未完成", task.getStartData().toString(), "上午 11:00(未完成)");
                tmpShowTaskList.add(showTask);
            }
            recyclerViewAdapter.setTaskShowList(tmpShowTaskList);

            showTaskUIUpdate(); // 會去看 taskList 的修改而更新
            runGetTaskAPI(1000);// 由觀察者去啟動發布者非常不合理 需修改
        }
    }
}

