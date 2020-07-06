package com.example.my_first_application;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import Task.Task;
import Task.TaskApiService;
import TestHttpsApi.TestHttpsApi;


import android.widget.Toast;
public class ReleaseTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_task);
    }

    public void onClickToPostTask(View view) {
//        TaskApiService taskApiService = new TaskApiService();
//        boolean is_success = taskApiService.post_test();
//        if(is_success) {
//            Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
//        }
        TestHttpsApi testHttpsApi = new TestHttpsApi();
        boolean is_success = testHttpsApi.get_test();
        if(is_success) {
            Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
        }
    }



}
