package com.example.my_first_application;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import Task.Task;
import Task.TaskApiService;
import TestHttpsApi.TestHttpsApi;


import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Timestamp;

public class ReleaseTaskActivity extends AppCompatActivity {

    String taskName;
    String message;
    String salary;
    String postTime;
    String taskType;
    String taskAddress;
    String taskCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    public void onClickToPostTask(View view) {
        EditText TaskNameField = findViewById(R.id.editText_title_content);
        taskName = TaskNameField.getText().toString();

        EditText MessageField = findViewById(R.id.editText_detail_content);
        message = MessageField.getText().toString();

        EditText SalaryField = findViewById(R.id.editText_pay_content);
        salary = SalaryField.getText().toString();

        EditText DateTimeField = findViewById(R.id.editText_date_content);
        postTime = DateTimeField.getText().toString();

        Spinner taskTypeField = findViewById(R.id.spinner_task_type);
        taskType = taskTypeField.getSelectedItem().toString();

        switch (taskType){
            case "食物代購":
                taskType = "EatTask";
                break;
            case "家務":
                taskType = "HouseworkTask";
                break;
        }

        EditText TaskAddressField = findViewById(R.id.editText_task_region);
        taskAddress = TaskAddressField.getText().toString();

        Spinner TaskCityField = findViewById(R.id.spinner_task_region);
        taskCity = TaskCityField.getSelectedItem().toString();

        switch (taskCity){
            case "台中市":
                taskCity = "1";
                break;
            case "台北市":
                taskCity = "2";
                break;
        }


        TaskApiService taskApiService = new TaskApiService();
        taskApiService.post(taskName, message, salary, postTime, taskType, taskAddress, taskCity);
    }



}
