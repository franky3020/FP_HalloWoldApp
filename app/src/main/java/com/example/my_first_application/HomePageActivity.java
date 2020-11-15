package com.example.my_first_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.example.my_first_application.Util.BottomNavigationSettingFacade;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import User.GetLoginUser;

public class HomePageActivity extends AppCompatActivity { // 此頁面為顯示所有任務區 如果是訪客 應該先看這頁

    private static final String LOG_TAG = HomePageActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ShowRecyclerViewTaskFragment showRecyclerViewTaskFragment;
        if (GetLoginUser.isLogin()) { // 檢查是訪客 還是已經登入的使用者而選擇顯示項目
            showRecyclerViewTaskFragment = ShowRecyclerViewTaskFragment
                    .newInstance(ShowRecyclerViewTaskFragment.ALL_TASKS_WITHOUT_LOGIN_USER);
        } else {
            showRecyclerViewTaskFragment = ShowRecyclerViewTaskFragment
                    .newInstance(ShowRecyclerViewTaskFragment.ALL_TASKS);
        }


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

