package com.example.my_first_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.View;

import com.example.my_first_application.Util.BottomNavigationSettingFacade;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import User.GetLoginUser;


public class ShowTaskActivity extends AppCompatActivity {

    private static final String LOG_TAG = ShowTaskActivity.class.getSimpleName();

    int loginUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate start");

        GetLoginUser.checkLoginIfNotThenGoToLogin(this);

        this.loginUserId = GetLoginUser.getLoginUser().getId();


        setContentView(R.layout.activity_show_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ShowRecyclerViewTaskFragment showRecyclerViewTaskFragment = ShowRecyclerViewTaskFragment
                                              .newInstance(ShowRecyclerViewTaskFragment.USER_RELEASE_TASKS);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.tasks_container, showRecyclerViewTaskFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationSettingFacade.setReleaseModeNavigation(this, bottomNavigationView);

        Log.d(LOG_TAG, "onCreate over"); // 就算跳到 login 頁面, 這一行還是會跑完, onCreate() 完後 會執行 onDestroy
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_new_task:
                Intent intent = new Intent(this, ReleaseTaskActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickToReleaseTask(View view) {
        Intent intent = new Intent(this, ReleaseTaskActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

}

