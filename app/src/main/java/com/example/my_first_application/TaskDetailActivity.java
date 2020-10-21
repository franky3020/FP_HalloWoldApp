package com.example.my_first_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import Task.TaskAPIService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TaskDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "taskID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail2);

        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getSupportFragmentManager().findFragmentById(R.id.task_detail_frag);
        final int taskID = getIntent().getExtras().getInt(EXTRA_TASK_ID);
        assert taskDetailFragment != null;
        taskDetailFragment.setTaskID(taskID);


        // 加上button
        LinearLayout stateButtonsLayout = (LinearLayout)findViewById(R.id.task_state_buttons_container);
        Button stateButton = new Button(this);
        stateButton.setText("Delete");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        stateButton.setLayoutParams(params);

        stateButton.setBackgroundResource(R.color.colorAccent);
        stateButtonsLayout.addView(stateButton); // 不能重複加同一實例
    }

    private void onClickDeleteTask(int taskID) {
        TaskAPIService taskApiService = new TaskAPIService();
        taskApiService.deleteTask(taskID, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("franky-test ok");
            }
        });
    }

}