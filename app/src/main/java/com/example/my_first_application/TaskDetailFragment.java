package com.example.my_first_application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import Task.TaskAPIService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailFragment extends Fragment implements View.OnClickListener { // 任務細節還沒做完誠

    private int taskID = 0;
    private String taskTitle = "";

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if(view != null) {
            TextView taskTitle = view.findViewById(R.id.taskTitle);
            taskTitle.setText(this.taskTitle); // 把從activity設定的 設定到該TextView
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteButton:
                onClickDeleteTask();
                break;
        }
    }

    private void onClickDeleteTask() {
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



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskDetailFragment newInstance(String param1, String param2) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_task_detail, container, false);
        Button deleteButton = layout.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);

        return layout;
    }



}