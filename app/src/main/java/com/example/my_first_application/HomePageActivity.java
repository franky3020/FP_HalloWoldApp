package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.my_first_application.Util.BottomNavigationSettingFacade;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import Task.TaskAPIService;
import Task.Task;

public class HomePageActivity extends AppCompatActivity { // 此頁面為顯示所有任務區 如果是訪客 應該先看這頁

    private static final String LOG_TAG = HomePageActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ShowRecyclerViewTaskFragment showRecyclerViewTaskFragment = ShowRecyclerViewTaskFragment
                .newInstance(ShowRecyclerViewTaskFragment.ALL_TASKS);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.tasks_container, showRecyclerViewTaskFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationSettingFacade.setReceiveModeNavigation(this, bottomNavigationView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

}

