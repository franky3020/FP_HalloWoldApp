package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;


import Task.ThreadForTaskGet;
public class ShowTask extends AppCompatActivity {

    private GridView taskShow;
    private String[] task_name = new String[]{"Apple","Banana","Orange","Grape","Strawberry"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViews();
        setAdapter();
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
        getMenuInflater().inflate(R.menu.manu_main, menu);
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

    private void setAdapter(){
        ArrayAdapter<String> adapter=
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, task_name);
        taskShow.setAdapter(adapter);

    }

    private void findViews () {
        taskShow = (GridView) findViewById(R.id.taskShow);
    }

}