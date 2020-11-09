package com.example.my_first_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.my_first_application.Util.BottomNavigationSettingFacade;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import User.GetLoginUser;
import User.User;

public class ProfileActivity extends AppCompatActivity {

    Button userModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        GetLoginUser.checkLoginIfNotThenGoToLogin(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView
                = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        userModeSwitch = findViewById(R.id.button_profile_switch_mode);

//        if (GetLoginUser.isReleaseMode()) {
//            BottomNavigationSettingFacade.setReleaseModeNavigation(this, bottomNavigationView);
//            userModeSwitch.setChecked(false);
//
//        } else if (GetLoginUser.isReceiveMode()) {
//            BottomNavigationSettingFacade.setReceiveModeNavigation(this, bottomNavigationView);
//            userModeSwitch.setChecked(true);
//        }
//
//        userModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) { // to Receive
//                    GetLoginUser.setUserMode(GetLoginUser.RECEIVE_MODE_STR);
//                } else {
//                    GetLoginUser.setUserMode(GetLoginUser.RELEASE_MODE_STR);
//                }
//                finish();
//                startActivity(getIntent());
//            }
//        });


        // 以下為測試用, 如果系統有被登入 則會修改 memberPoints 的文字
        User loginUser = GetLoginUser.getLoginUser();
        if(loginUser !=  null) {
            TextView memberPoints = findViewById(R.id.textView_member_points);
            memberPoints.setText("(此為測試用) 已登入使用者為" + loginUser.getId());
        }
        ////////////////////////////////////////////////////////////////////////

    }

    public void onClickToTestMain(View view) {

        // 初始這頁的切換按鈕
//        userModeSwitch.setChecked(false);

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

}
