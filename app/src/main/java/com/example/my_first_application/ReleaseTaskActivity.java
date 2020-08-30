package com.example.my_first_application;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;

import Task.Task;
import Task.TaskApiService;
import TestHttpsApi.TestHttpsApi;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ReleaseTaskActivity extends AppCompatActivity {

    String taskName;
    String message;
    String salary;
    String postTime;
    String taskType;
    String taskAddress;
    String taskCity;
    String currentTime;
    EditText postTimeField;
    EditText timePickerField;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    int currentYear;
    int currentMonth;
    int currentDay;



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

        postTimeField = findViewById(R.id.editText_date_content);
        postTimeField.setInputType(InputType.TYPE_NULL);
        postTimeField.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    showDatePickerDialog();
                }
            }
        });

        postTimeField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePickerDialog();
            }
        });

        timePickerField = findViewById(R.id.editText_time_content);
        timePickerField.setInputType(InputType.TYPE_NULL);
        timePickerField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showTimePickerDialog();
                }
            }
        });


        timePickerField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showTimePickerDialog();
            }
        });

    }
    private void showTimePickerDialog(){
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(ReleaseTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                currentTime = String.format("%02d:%02d", hourOfDay%12, minute);
                if(hourOfDay >= 12){
                    timePickerField.setText("下午" + currentTime);
                }
                else{
                    timePickerField.setText("上午" + currentTime);

                }
            }
        },  currentHour ,currentMinute,false).show();
    }

    private void showDatePickerDialog() {
        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(ReleaseTaskActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                postTimeField.setText(year+"-" + (monthOfYear+1)+ "-" +dayOfMonth);
            }
        }, currentYear, currentMonth, currentDay).show();

    }




    public void onClickToPostTask(View view) {
        EditText TaskNameField = findViewById(R.id.editText_title_content);
        taskName = TaskNameField.getText().toString();

        EditText MessageField = findViewById(R.id.editText_detail_content);
        message = MessageField.getText().toString();

        EditText SalaryField = findViewById(R.id.editText_pay_content);
        salary = SalaryField.getText().toString();

        postTime = postTimeField.getText().toString() + " " + currentTime + ":00";

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
