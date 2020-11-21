package com.example.my_first_application;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.LocalDateTime;

import Task.Task;
import Task.TaskAPIService;
import User.User;
import User.UserAPIService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailFragment extends Fragment {

    private int taskID = 0;
    private String taskTitle = "XXX";

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    @Override
    public void onStart() {
        super.onStart();
        final View view = getView();
        if(view != null) {
            TaskAPIService taskApiService = new TaskAPIService();

            taskApiService.getATask(taskID, new TaskAPIService.GetAPIListener<Task>() {
                @Override
                public void onResponseOK(Task task) {
                    uIUpdate(task);
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

    private void uIUpdate(final Task task) { //必須要在主執行緒上更新UI, 才不會出錯

        getActivity().runOnUiThread( new Runnable() {
            @Override
            public void run() {
                final View view = getView();

                TextView taskTitle = view.findViewById(R.id.taskTitle);
                taskTitle.setText(task.getTaskName());

                UserAPIService userAPIService = new UserAPIService();
                userAPIService.getAUserByUserID(task.getReleaseUserID(), new UserAPIService.UserListener() {
                    @Override
                    public void onResponseOK(User user) {
                        TextView releaseUserID = view.findViewById(R.id.releaseUserID);
                        releaseUserID.setText(user.getName());
                    }
                    @Override
                    public void onFailure() {
                    }
                });
                TextView releaseTime = view.findViewById(R.id.releaseTime);
                LocalDateTime startPostTime = task.getStartPostTime();
                if (startPostTime != null) {
                    int year = startPostTime.getYear();
                    int month = startPostTime.getMonthValue() ;
                    int day = startPostTime.getDayOfMonth();
                    String AMPM = "上午";
                    int hour = startPostTime.getHour();
                    if (hour > 12){
                        AMPM = "下午";
                        hour = hour - 12;
                    }
                    int minute = startPostTime.getMinute();
                    if(minute < 10){
                        releaseTime.setText("" + year + "/" + month + "/" + day + " "+ AMPM + hour + ":0" + minute);
                    }
                    else {
                        releaseTime.setText("" + year + "/" + month + "/" + day + " "+ AMPM + hour + ":" + minute);
                    }
                }

                TextView salary = view.findViewById(R.id.salary);
                salary.setText(String.valueOf(task.getSalary()));

                TextView taskAddress = view.findViewById(R.id.taskAddress);
                taskAddress.setText(task.getTaskAddress());

                TextView message = view.findViewById(R.id.message);
                message.setText(task.getMessage());

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
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }
}