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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    String date1;
    String date2;
    String PeriodDate1;
    String PeriodDate2;
    Button SingleDateButton;
    Button PeriodDateButton;
    TextView postTimeField;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    int currentYear;
    int currentMonth;
    int currentDay;
    boolean twice = false;
    boolean flag = false;



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

        SingleDateButton = findViewById(R.id.button_single_date);
        PeriodDateButton = findViewById(R.id.button_period_date);
        SingleDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        PeriodDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                showDatePickerDialog();
            }
        });
    }


    private void showDatePickerDialog() {
        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(ReleaseTaskActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date1 = year+"-" + (monthOfYear+1)+ "-" +dayOfMonth;
                showTimePickerDialog();
            }
        }, currentYear, currentMonth, currentDay).show();


    }
    private void showTimePickerDialog(){
        postTimeField = findViewById(R.id.textView_time_content);
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(ReleaseTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                currentTime = String.format("%02d:%02d", hourOfDay%12, minute);
                if(hourOfDay >= 12){
                    date2 = "下午" + currentTime;
                }
                else{
                    date2 = "上午" + currentTime;
                }
                if(twice){
                    PeriodDate2 = date1 + " " + date2;
                    twice = false;
                }
                else {
                    PeriodDate1 = date1 + "  " + date2;
                    PeriodDate2 = "";
                }
                if(flag){
                    twice = true;
                    showDatePickerDialog();
                }
                flag = false;
                postTimeField.setText(PeriodDate1 + "～\n" + PeriodDate2);
            }
        },  currentHour ,currentMinute,false).show();
    }




    public void onClickToPostTask(View view) {
        EditText TaskNameField = findViewById(R.id.editText_title_content);
        taskName = TaskNameField.getText().toString();

        EditText MessageField = findViewById(R.id.editText_detail_content);
        message = MessageField.getText().toString();

        EditText SalaryField = findViewById(R.id.editText_pay_content);
        salary = SalaryField.getText().toString();

        postTime = date1 + " " + currentTime + ":00";

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
