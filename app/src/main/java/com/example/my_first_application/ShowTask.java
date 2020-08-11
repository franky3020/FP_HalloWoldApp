package com.example.my_first_application;
//http://androidthinkermobile.blogspot.com/2017/10/android-ui3adapter-listview-gridview.html
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import Task.ThreadForTaskGet;
public class ShowTask extends AppCompatActivity {

    private GridView taskShow;
    private String[] task_name = new String[]{"Apple","Banana","Orange","Grape","Strawberry"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);
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

    private void setAdapter(){
        ArrayAdapter<String> adapter=
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, task_name);
        taskShow.setAdapter(adapter);

    }

    private void findViews () {
        taskShow = (GridView) findViewById(R.id.taskShow);
    }

}