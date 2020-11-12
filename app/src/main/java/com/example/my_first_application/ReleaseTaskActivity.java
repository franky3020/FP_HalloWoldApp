package com.example.my_first_application;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import Task.Task;
import Task.TaskAPIService;
import User.GetLoginUser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import Task.TaskBuilder;

public class ReleaseTaskActivity extends AppCompatActivity {
    private static final String LOG_TAG = ReleaseTaskActivity.class.getSimpleName();
    ReleaseTaskActivity releaseTaskActivity = this;

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

    int loginUserId;


    EditText taskNameField;
    EditText messageField;
    EditText salaryField;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GetLoginUser.checkLoginIfNotThenGoToLogin(this);

        this.loginUserId = GetLoginUser.getLoginUser().getId();



        setContentView(R.layout.activity_release_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_release_task);
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



        taskNameField = findViewById(R.id.editText_title_content);
        messageField = findViewById(R.id.editText_detail_content);
        salaryField = findViewById(R.id.editText_pay_content);







        Button releaseButton = findViewById(R.id.release_task_button);
        releaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseTask();
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
        postTimeField = findViewById(R.id.textView_task_time);
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
                postTimeField.setText(PeriodDate1 +  " ～ " + PeriodDate2);
            }
        },  currentHour ,currentMinute,false).show();
    }


    private void releaseTask() {
        checkAlertDialog(new CheckAlertDialogListener() {
            @Override
            public void onPositive() {
                postTask();
            }

            @Override
            public void onNegative() {
                // nothing
            }
        });
    }


    private void postTask() { // Todo 應該改名
        String taskName = taskNameField.getText().toString();

        String message = messageField.getText().toString();

        String salaryStr = salaryField.getText().toString();
        int salary;
        try {
            salary = Integer.parseInt(salaryStr); // 防止空字串 程式會崩潰
        } catch (Exception e) {
            salary = 0;
        }
//
//        EditText TaskAddressField = findViewById(R.id.editText_task_region);
//        String taskAddress = TaskAddressField.getText().toString();
//
//        postTime = date1 + " " + currentTime + ":00"; // Todo 時間需要改  所以先不處理

        Task task = TaskBuilder.aTask(0, salary, loginUserId)
                .withTaskName(taskName)
                .withMessage(message)
                .build();

        TaskAPIService taskApiService = new TaskAPIService();
        try {
            taskApiService.post(task, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()) {
                        Intent intent = new Intent();
                        intent.setClass(releaseTaskActivity, ShowTaskActivity.class);
                        startActivity(intent);
                    }
                }
            });

        } catch (Exception e) {
            Log.d(LOG_TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    private interface  CheckAlertDialogListener {
        void onPositive();
        void onNegative();
    }

    private void checkAlertDialog(final CheckAlertDialogListener checkAlertDialogListener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ReleaseTaskActivity.this);
        dialog.setTitle("確認");
        dialog.setMessage("test");

        dialog.setNegativeButton("NO",new DialogInterface.OnClickListener() { // 改顏色
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                checkAlertDialogListener.onNegative();
            }
        });

        dialog.setPositiveButton("YES",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                checkAlertDialogListener.onPositive();
            }
        });

        dialog.show();
    }

}
