package com.example.my_first_application;

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
import android.widget.Toast;

import java.util.ArrayList;

import Task.Task;
import Task.TaskAPIService;

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

            this.getTasksAPI_Handler.post(getTaskAPI_Runnable);


        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
        this.getTasksAPI_Handler.removeCallbacks(getTaskAPI_Runnable);
    }

    private final Runnable getTaskAPI_Runnable = new Runnable() {
        public void run() {
            sendGetTasksAPI();
            int delayMillis = 3000;
            getTasksAPI_Handler.postDelayed(getTaskAPI_Runnable, delayMillis);
        }
    };

    private void sendGetTasksAPI() {
        final TaskAPIService taskApiService = new TaskAPIService();

        taskApiService.getTasksV3(new TaskAPIService.GetAPIListener< ArrayList<Task> >() {

            @Override
            public void onResponseOK(ArrayList<Task> tasks) {
                taskList = tasks;
                taskUIUpdate(taskList);
                Log.d(LOG_TAG, "sendGetTasksAPI onResponse");
            }

            @Override
            public void onFailure() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "沒有網路連線", Toast.LENGTH_SHORT).show(); // 這之後要用string
                    }
                });
            }
        });

    }

    public void taskUIUpdate(final ArrayList<Task> taskList) {
        recyclerViewAdapter.setShowTaskList(taskList);

        recyclerViewAdapter.setListener(new ShowTaskAdapter.Listener() {

            @Override
            public void onClick(int position) {
                Task task = taskList.get(position);

                Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
                intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, task.getTaskID());
                getActivity().startActivity(intent);
            }
        });

        getActivity().runOnUiThread( new Runnable() { //必須要在主執行緒上更新UI, 才不會出錯
            @Override
            public void run() {
                recyclerViewAdapter.notifyDataSetChanged();
                Log.d(LOG_TAG, "over recyclerViewAdapter.notifyDataSetChanged()");
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

    public ShowRecyclerViewTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowRecyclerViewTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowRecyclerViewTaskFragment newInstance(String param1, String param2) {
        ShowRecyclerViewTaskFragment fragment = new ShowRecyclerViewTaskFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_recycler_view_task, container, false);
    }
}