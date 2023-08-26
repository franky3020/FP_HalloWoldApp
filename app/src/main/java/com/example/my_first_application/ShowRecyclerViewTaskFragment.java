package com.example.my_first_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import Task.Task;
import Task.TaskAPIService;
import User.GetLoginUser;
import Task.TaskBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowRecyclerViewTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowRecyclerViewTaskFragment extends Fragment {

    private static final String LOG_TAG = ShowRecyclerViewTaskFragment.class.getSimpleName();

    private ArrayList<Task> taskList = new ArrayList<>();
    private ShowTaskAdapter recyclerViewAdapter;
    Handler getTasksAPI_Handler;

    public static final String ALL_TASKS = "allTasks";
    public static final String ALL_TASKS_WITHOUT_LOGIN_USER = "allTasksWithoutLoginUser";
    public static final String USER_RELEASE_TASKS = "userReleaseTasks";
    public static final String USER_REQUEST_TASKS = "userRequestTasks";
    public static final String USER_RECEIVE_TASKS = "userReceiveTasks";
    public static final String USER_END_TASKS = "userEndTasks";
    public static final String UNKNOWN = "unknown";
    public static final String specifyClassification = "specifyClassification";
    private String showTaskMode = UNKNOWN;

    int userID = GetLoginUser.getLoginUser().getId();
    private TaskAPIService.GetAPIListener getTasksAPIListener;


    public static ShowRecyclerViewTaskFragment newInstance(String specify) {
        ShowRecyclerViewTaskFragment fragment = new ShowRecyclerViewTaskFragment();
        Bundle args = new Bundle();
        args.putString(specifyClassification, specify);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            showTaskMode = getArguments().getString(specifyClassification);
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
        final View view = getView();
        if(view != null) {

            this.getTasksAPI_Handler = new Handler();

            RecyclerView recyclerView = view.findViewById(R.id.taskShow);

            LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            this.recyclerViewAdapter = new ShowTaskAdapter(taskList);
            recyclerView.setAdapter(recyclerViewAdapter);

            Task task1 = TaskBuilder.aTask(33, 500, 66).build();
            taskList.add(task1);
            taskUIUpdate(taskList);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    public void taskUIUpdate(final ArrayList<Task> taskList) {
        recyclerViewAdapter.setShowTaskList(taskList);

        final Activity activity = getActivity();
        if( activity == null) {
            return;
        }

        recyclerViewAdapter.setListener(new ShowTaskAdapter.Listener() {

            @Override
            public void onClick(int position) {
                Task task = taskList.get(position);

                Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
                intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, task.getTaskID());
                activity.startActivity(intent);
            }
        });


        activity.runOnUiThread( new Runnable() { //必須要在主執行緒上更新UI, 才不會出錯
            @Override
            public void run() {
                recyclerViewAdapter.notifyDataSetChanged();
                Log.d(LOG_TAG, "over recyclerViewAdapter.notifyDataSetChanged()");
            }
        });
    }

    public ShowRecyclerViewTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_recycler_view_task, container, false);
    }
}