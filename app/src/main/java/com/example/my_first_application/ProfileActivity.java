package com.example.my_first_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.my_first_application.Util.NavigationItemListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import User.GetLoginUser;
import User.User;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        GetLoginUser.checkLoginIfNotThenGoToLogin(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView
                = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationItemListener(this));


        // 以下為測試用, 如果系統有被登入 則會修改 memberPoints 的文字
        User loginUser = GetLoginUser.getLoginUser();
        if(loginUser !=  null) {
            TextView memberPoints = findViewById(R.id.textView_member_points);
            memberPoints.setText("(此為測試用) 已登入使用者為" + loginUser.getId());
        }
        ////////////////////////////////////////////////////////////////////////

    }
}
