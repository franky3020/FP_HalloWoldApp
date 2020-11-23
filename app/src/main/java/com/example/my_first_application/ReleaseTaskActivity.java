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
import User.User;
import User.UserAPIService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import Task.TaskBuilder;

public class ReleaseTaskActivity extends AppCompatActivity {
    private static final String LOG_TAG = ReleaseTaskActivity.class.getSimpleName();
    ReleaseTaskActivity releaseTaskActivity = this;

    int loginUserId;

    EditText taskNameField;
    EditText messageField;
    EditText salaryField;
    EditText mEditText_task_start_date_Field;
    EditText mEditText_task_end_date_Field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_task);

        GetLoginUser.checkLoginIfNotThenGoToLogin(this);

        this.loginUserId = GetLoginUser.getLoginUser().getId();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_release_task);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        taskNameField = findViewById(R.id.editText_title_content);
        messageField = findViewById(R.id.editText_detail_content);
        salaryField = findViewById(R.id.editText_pay_content);

        mEditText_task_start_date_Field = findViewById(R.id.editText_task_start_date);
        mEditText_task_start_date_Field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText_task_start_date_Field.setText(""); // 先設定為空
                mEditText_task_end_date_Field.setError(null); // 為了清掉錯誤訊息
                
                setTaskDateAndTime(new OnTaskDateAndTimeSetListener(){

                    @Override
                    public void onTaskDateAndTimeSet(String dataAndTime) {
                        mEditText_task_start_date_Field.setText(dataAndTime);
                    }
                });
            }
        });

        mEditText_task_end_date_Field = findViewById(R.id.editText_task_end_date);
        mEditText_task_end_date_Field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText_task_end_date_Field.setText(""); // 先設定為空
                mEditText_task_end_date_Field.setError(null);// 為了清掉錯誤訊息

                setTaskDateAndTime(new OnTaskDateAndTimeSetListener(){

                    @Override
                    public void onTaskDateAndTimeSet(String dataAndTime) {
                        // Todo 之後需要修掉顯示 T 的問題
                        mEditText_task_end_date_Field.setText(dataAndTime);
                    }
                });
            }
        });


        Button releaseButton = findViewById(R.id.release_task_button);
        releaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseTask();
            }
        });

    }


    private interface  OnTaskDateAndTimeSetListener {
        void onTaskDateAndTimeSet(String dataAndTime);
    }

    private void setTaskDateAndTime(final OnTaskDateAndTimeSetListener listener) {
        showDatePickerDialogV2(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final String date = "" + year + "-" + (month + 1) + "-" + dayOfMonth;

                showTimePickerDialogV2(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = "" + hourOfDay + ":" + minute + ":00";
                        listener.onTaskDateAndTimeSet(date + "T" + time);
                    }
                });
            }
        });

    }


    private void showDatePickerDialogV2( DatePickerDialog.OnDateSetListener onDateSetListener) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(ReleaseTaskActivity.this, onDateSetListener,
                currentYear, currentMonth, currentDay).show();
    }

    private void showTimePickerDialogV2( TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(ReleaseTaskActivity.this, onTimeSetListener,
                currentHour ,currentMinute,false).show();
    }

    private void releaseTask() {

        if(isAEditTextNotHasValues()) {
            return;
        }

        if(!checkPoint()) {
            Log.d(LOG_TAG, "not enough money");
            return;
        }

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


    private void postTask() {
        String taskName = taskNameField.getText().toString();

        String message = messageField.getText().toString();

        String salaryStr = salaryField.getText().toString();
        int salary;
        try {
            salary = Integer.parseInt(salaryStr); // 防止空字串 程式會崩潰
        } catch (Exception e) {
            salary = 0;
        }

        EditText TaskAddressField = findViewById(R.id.editText_task_region);
        String taskAddress = TaskAddressField.getText().toString();

        String startPostTimeStr= mEditText_task_start_date_Field.getText().toString();
        LocalDateTime startPostTime = UtilTool.TransitTime.transitTimeStamp(startPostTimeStr);

        String endPostTimeStr = mEditText_task_end_date_Field.getText().toString();
        LocalDateTime endPostTime = UtilTool.TransitTime.transitTimeStamp(endPostTimeStr);



        Task task = TaskBuilder.aTask(0, salary, loginUserId)
                .withTaskName(taskName)
                .withMessage(message)
                .withTaskAddress(taskAddress)
                .withStartPostTime(startPostTime)
                .withEndPostTime(endPostTime)
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

    private boolean isAEditTextNotHasValues() {
        ArrayList<EditText> allEditText = new ArrayList<>();

        allEditText.add(taskNameField);
        allEditText.add(messageField);
        allEditText.add(salaryField);
        allEditText.add(mEditText_task_start_date_Field);
        allEditText.add(mEditText_task_end_date_Field);

        for(EditText editText : allEditText) {
            if ("".equals(editText.getText().toString().trim())) {
                editText.setError("不能沒有值");
                return true;
            }
        }
        return false;
    }

    private boolean checkPoint() {

        String salaryStr = salaryField.getText().toString();
        int salary;
        try {
            salary = Integer.parseInt(salaryStr); // 防止空字串 程式會崩潰
        } catch (Exception e) {
            salary = 0;
        }

        UserAPIService userAPIService = new UserAPIService();
        User user = userAPIService.getAUserByUserID(loginUserId);

        if(user.getPoint() > salary) {
            return true;
        } else {
            return false;
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
